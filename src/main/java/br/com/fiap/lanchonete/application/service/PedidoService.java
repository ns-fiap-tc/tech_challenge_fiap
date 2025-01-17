package br.com.fiap.lanchonete.application.service;

import br.com.fiap.lanchonete.adapter.output.producer.RabbitMqMessageProducer;
import br.com.fiap.lanchonete.domain.exception.ValidacaoException;
import br.com.fiap.lanchonete.domain.model.*;
import br.com.fiap.lanchonete.domain.port.output.persistence.PedidoRepository;
import br.com.fiap.lanchonete.domain.usecase.CategoriaUseCases;
import br.com.fiap.lanchonete.domain.usecase.OrdemServicoUseCases;
import br.com.fiap.lanchonete.domain.usecase.PagamentoUseCases;
import br.com.fiap.lanchonete.domain.usecase.PedidoUseCases;
import br.com.fiap.lanchonete.domain.usecase.ProdutoUseCases;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class PedidoService implements PedidoUseCases {

    private final PagamentoUseCases pagamentoService;
    private final CategoriaUseCases categoriaService;
    private final ProdutoUseCases produtoService;
    private final OrdemServicoUseCases ordemServicoService;
    private final RabbitMqMessageProducer messageProducer;
    private final PedidoRepository repository;
    @Autowired PagamentoMockService pagamentoMockService;

    @Override
    public Pedido create(Pedido pedido) {
        Date now = new Date();
        Pagamento pagamento = pedido.getPagamento();
        if (pagamento != null) {
            if (pagamento.getId() == null) {
                pagamento.setCreatedAt(now);
            }
            pagamento.setUpdatedAt(now);
        }

        pedido = repository.save(pedido);

        if(pagamentoMockService.pagar()){
            atualizarStatusPedidoAposPagamento(pedido);

            criarItensEOrdemAposPagamento(pedido);
        }
        return pedido;
    }

    private void atualizarStatusPedidoAposPagamento(Pedido pedido){
        pedido.setStatus(PedidoStatus.PREPARACAO);
        this.updateStatus(pedido.getId(), PedidoStatus.PREPARACAO);
    }

    private void criarItensEOrdemAposPagamento(Pedido pedido){
        for (PedidoItem item : pedido.getItens()) {
            Produto produto = produtoService.findById(item.getProdutoId());
            Categoria cat = categoriaService.findById(produto.getCategoriaId());
            OrdemServico os = this.criarOrdemServico(pedido.getId(), item, produto);
            os = ordemServicoService.save(os);
            this.messageProducer.send(cat.getTipo().name(), os);
        }
    }

    private OrdemServico criarOrdemServico(Long pedidoId, PedidoItem item, Produto produto) {
        OrdemServico os = new OrdemServico();
        os.setStatus(OrdemServicoStatus.AGUARDANDO);
        os.setNome(produto.getNome());
        os.setQuantidade(item.getQuantidade());
        os.setTempoPreparo(produto.getTempoPreparo());
        os.setProdutoId(produto.getId());
        os.setPedidoId(pedidoId);
        os.setPedidoItemId(item.getId());
        return os;
    }

    @Override
    public Pedido update(Pedido pedido) {
        return repository.save(pedido);
    }

    @Override
    public List<Pedido> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Pedido> findByStatus(PedidoStatus status) {
        return repository.findByStatus(status);
    }

    @Override
    public Pedido findById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<Pedido> findByCliente(long clienteId) {
        return repository.findByClienteId(clienteId);
    }

    @Override
    public void updateStatus(Long id, PedidoStatus status) {
        repository.updateStatus(id, status);
    }

    @Override
    public void validarPedidoStatus(Long id) {
    }

    @Override
    public void retryPayment(long pedidoId,boolean statusPagamento){
        Pedido pedido = repository.findById(pedidoId);
        if(statusPagamento){
            atualizarStatusPedidoAposPagamento(pedido);
            criarItensEOrdemAposPagamento(pedido);
        }
    }
}
