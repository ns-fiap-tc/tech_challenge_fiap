package br.com.fiap.lanchonete.business.core.usecase.impl;

import br.com.fiap.lanchonete.business.adapter.controller.CategoriaServiceClient;
import br.com.fiap.lanchonete.business.adapter.controller.PagamentoServiceClient;
import br.com.fiap.lanchonete.business.adapter.controller.ProdutoServiceClient;
import br.com.fiap.lanchonete.business.adapter.gateway.PedidoGateway;
import br.com.fiap.lanchonete.business.common.mapper.PedidoMapper;
import br.com.fiap.lanchonete.business.common.queue.MessageProducer;
import br.com.fiap.lanchonete.business.core.domain.OrdemServico;
import br.com.fiap.lanchonete.business.core.domain.OrdemServicoStatus;
import br.com.fiap.lanchonete.business.core.domain.Pedido;
import br.com.fiap.lanchonete.business.core.domain.PedidoItem;
import br.com.fiap.lanchonete.business.core.usecase.OrdemServicoUseCases;
import br.com.fiap.lanchonete.business.core.usecase.PedidoUseCases;
import br.com.fiap.lanchonete.categoria.commons.dto.CategoriaDto;
import br.com.fiap.lanchonete.pagamento.commons.domain.PagamentoStatus;
import br.com.fiap.lanchonete.pagamento.commons.dto.PagamentoDto;
import br.com.fiap.lanchonete.pedido.commons.domain.PedidoStatus;
import br.com.fiap.lanchonete.pedido.commons.dto.PedidoDto;
import br.com.fiap.lanchonete.produto.commons.dto.ProdutoDto;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@RequiredArgsConstructor
public class PedidoUseCasesImpl implements PedidoUseCases {
    private static final PedidoMapper MAPPER = PedidoMapper.INSTANCE;

    private final PedidoGateway gateway;
    private final MessageProducer messageProducer;

    private final PagamentoServiceClient pagamentoServiceClient;
    private final CategoriaServiceClient categoriaServiceClient;
    private final ProdutoServiceClient produtoServiceClient;
    private final OrdemServicoUseCases ordemServicoService;

    @Override
    public Pedido create(PedidoDto pedidoDto) {
        return this.updateHandler(MAPPER.toDomain(pedidoDto), pedidoDto.getPagamentoDto());
    }

    private void atualizarStatusPedidoAposPagamento(Pedido pedido) {
        pedido.setStatus(PedidoStatus.PREPARACAO);
        this.updateStatus(pedido.getId(), PedidoStatus.PREPARACAO);
    }

    private void criarItensEOrdemAposPagamento(Pedido pedido) {
        for (PedidoItem item : pedido.getItens()) {
            ProdutoDto produtoDto = produtoServiceClient.findById(item.getProdutoId());
            CategoriaDto catDto = categoriaServiceClient.findById(produtoDto.getCategoriaId());
            OrdemServico os = this.criarOrdemServico(pedido.getId(), item, produtoDto);
            os = ordemServicoService.save(os);
            this.messageProducer.send(catDto.getTipo().name(), os);
        }
    }

    private OrdemServico criarOrdemServico(Long pedidoId, PedidoItem item, ProdutoDto produtoDto) {
        OrdemServico os = new OrdemServico();
        os.setStatus(OrdemServicoStatus.AGUARDANDO);
        os.setNome(produtoDto.getNome());
        os.setQuantidade(item.getQuantidade());
        os.setTempoPreparo(produtoDto.getTempoPreparo());
        os.setProdutoId(produtoDto.getId());
        os.setPedidoId(pedidoId);
        os.setPedidoItemId(item.getId());
        return os;
    }

    @Override
    public Pedido update(PedidoDto pedidoDto) {
        return this.updateHandler(MAPPER.toDomain(pedidoDto), pedidoDto.getPagamentoDto());
    }

    private Pedido updateHandler(Pedido pedido, PagamentoDto pagamentoDto) {
        //gravar o pedido caso seja novo.
        if (pedido.getId() == null) {
            if (pedido.getPagamentoId() == null || pedido.getPagamentoId().isEmpty()) {
                pedido.setPagamentoId("-1");
            }

            pedido = gateway.save(pedido);;
            pagamentoDto.setPedidoId(pedido.getId());
        }

        if (pedido.getStatus() == PedidoStatus.CRIACAO) {
            pagamentoDto = pagamentoServiceClient.save(pagamentoDto);
            pedido.setPagamentoId(pagamentoDto.getId());
        } else if (pedido.getStatus() == PedidoStatus.RECEBIDO) {
            //pagamento nao iniciado
            pagamentoDto = pagamentoServiceClient.realizarPagamento(pagamentoDto);
            pedido.setPagamentoId(pagamentoDto.getId());
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
/*        pagamentoServiceClient.updateStatus(pedidoId, status);
        Pedido pedido = this.findById(pedidoId);
        if (status == PagamentoStatus.CONFIRMADO) {
            atualizarStatusPedidoAposPagamento(pedido);
            criarItensEOrdemAposPagamento(pedido);
        } else {
            throw new PagamentoConfirmacaoException(ValidacaoEnum.PAGAMENTO_NAO_CONFIRMADO);
        }
 */
    }
}