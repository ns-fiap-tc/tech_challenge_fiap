package br.com.fiap.lanchonete.business.core.usecase.impl;

import br.com.fiap.lanchonete.business.adapter.controller.PagamentoServiceClient;
import br.com.fiap.lanchonete.business.adapter.gateway.PedidoGateway;
import br.com.fiap.lanchonete.business.common.queue.MessageProducer;
import br.com.fiap.lanchonete.business.core.domain.Categoria;
import br.com.fiap.lanchonete.business.core.domain.OrdemServico;
import br.com.fiap.lanchonete.business.core.domain.OrdemServicoStatus;
import br.com.fiap.lanchonete.business.core.domain.Pagamento;
import br.com.fiap.lanchonete.business.core.domain.PagamentoStatus;
import br.com.fiap.lanchonete.business.core.domain.Pedido;
import br.com.fiap.lanchonete.business.core.domain.PedidoItem;
import br.com.fiap.lanchonete.business.core.domain.PedidoStatus;
import br.com.fiap.lanchonete.business.core.domain.Produto;
import br.com.fiap.lanchonete.business.core.domain.ValidacaoEnum;
import br.com.fiap.lanchonete.business.core.exception.PagamentoConfirmacaoException;
import br.com.fiap.lanchonete.business.core.usecase.CategoriaUseCases;
import br.com.fiap.lanchonete.business.core.usecase.OrdemServicoUseCases;
import br.com.fiap.lanchonete.business.core.usecase.PagamentoUseCases;
import br.com.fiap.lanchonete.business.core.usecase.PedidoUseCases;
import br.com.fiap.lanchonete.business.core.usecase.ProdutoUseCases;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PedidoUseCasesImpl implements PedidoUseCases {
    private final PedidoGateway gateway;
    private final MessageProducer messageProducer;
    private final PagamentoServiceClient pagamentoServiceClient;

    private final PagamentoUseCases pagamentoService;
    private final CategoriaUseCases categoriaService;
    private final ProdutoUseCases produtoService;
    private final OrdemServicoUseCases ordemServicoService;

    @Override
    public Pedido create(Pedido pedido) {
        return this.updateHandler(pedido);
    }

    private void atualizarStatusPedidoAposPagamento(Pedido pedido) {
        //Pagamento pagamento = pedido.getPagamento();
        //pagamento.setStatus(PagamentoStatus.CONFIRMADO);
        //pagamentoService.save(pagamento);
        pedido.setStatus(PedidoStatus.PREPARACAO);
        this.updateStatus(pedido.getId(), PedidoStatus.PREPARACAO);
    }

    private void criarItensEOrdemAposPagamento(Pedido pedido) {
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
        return this.updateHandler(pedido);
    }

    private Pedido updateHandler(Pedido pedido) {
        Date now = new Date();
        Pagamento pagamento = pedido.getPagamento();
        if (pagamento == null) {
            pagamento = new Pagamento();
            pedido.setPagamento(pagamento);
        }
        if (pagamento.getId() == null) {
            pagamento.setCreatedAt(now);
        }
        if (pedido.getId() == null) {
            pedido.setCreatedAt(now);
        }
        pagamento.setUpdatedAt(now);
        pagamento.setValor(pedido.getValorTotal());

        if (pedido.getStatus() == PedidoStatus.RECEBIDO
                && pagamentoServiceClient != null)
        {
            pagamentoServiceClient.realizarPagamento(
                    pedido.getId(),
                    BigDecimal.valueOf(pagamento.getValor()));
        }
        return gateway.save(pedido);
    }

    @Override
    public List<Pedido> findAllOrdered() {
        return gateway.findAllOrdered();
    }

    @Override
    public List<Pedido> findByStatus(PedidoStatus status) {
        return gateway.findByStatus(status);
    }

    @Override
    public Pedido findById(long id) {
        return gateway.findById(id);
    }

    @Override
    public List<Pedido> findByCliente(long clienteId) {
        return gateway.findByClienteId(clienteId);
    }

    @Override
    public void updateStatus(Long id, PedidoStatus status) {
        gateway.updateStatus(id, status);
    }

    @Override
    public void updatePagamentoStatus(Long pedidoId, PagamentoStatus status) {
        pagamentoService.updateStatus(pedidoId, status);
        Pedido pedido = this.findById(pedidoId);
        if (status == PagamentoStatus.CONFIRMADO) {
            atualizarStatusPedidoAposPagamento(pedido);
            criarItensEOrdemAposPagamento(pedido);
        } else {
            throw new PagamentoConfirmacaoException(ValidacaoEnum.PAGAMENTO_NAO_CONFIRMADO);
        }
    }
}