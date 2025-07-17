package br.com.fiap.lanchonete.pagamento.adapter.controller;

import br.com.fiap.lanchonete.pagamento.adapter.gateway.PagamentoGateway;
import br.com.fiap.lanchonete.pagamento.commons.persistence.PagamentoRepository;
import br.com.fiap.lanchonete.pagamento.commons.domain.PagamentoStatus;
import br.com.fiap.lanchonete.pagamento.commons.dto.PagamentoDto;
import br.com.fiap.lanchonete.pagamento.commons.mapper.PagamentoMapper;
import br.com.fiap.lanchonete.pagamento.core.domain.Pagamento;
import br.com.fiap.lanchonete.pagamento.core.usecase.PagamentoUseCases;
import br.com.fiap.lanchonete.pagamento.core.usecase.impl.PagamentoUseCasesImpl;
import java.util.List;

public class PagamentoController {
    private static final PagamentoMapper MAPPER = PagamentoMapper.INSTANCE;
    private final PagamentoUseCases useCase;
    private final PagamentoMockServiceClient pagamentoMockServiceClient;

    public PagamentoController(PagamentoMockServiceClient pagamentoMockServiceClient, PagamentoRepository pagamentoRepository) {
        this.pagamentoMockServiceClient = pagamentoMockServiceClient;
        useCase = new PagamentoUseCasesImpl(new PagamentoGateway(pagamentoRepository));
    }

    PagamentoUseCases getUseCases() {
        return this.useCase;
    }

    public PagamentoDto save(PagamentoDto pagamentoDto) {
        return MAPPER.toDto(useCase.save(MAPPER.toDomain(pagamentoDto)));
    }

    public PagamentoDto findById(String id) {
        return MAPPER.toDto(useCase.findById(id));
    }

    public List<PagamentoDto> findAll() {
        return MAPPER.mapDomainToDto(useCase.findAll());
    }

    public void deleteById(String id) {
        useCase.deleteById(id);
    }

    public void updateStatus(Long pedidoId, PagamentoStatus status) {
        useCase.updateStatus(pedidoId, status);
    }

    public PagamentoDto findByPedidoId(Long pedidoId) {
        return MAPPER.toDto(useCase.findByPedidoId(pedidoId));
    }

    public PagamentoDto realizarPagamento(PagamentoDto pagamentoDto) {
        Pagamento pagto = useCase.findByPedidoId(pagamentoDto.getPedidoId());
        if (pagto == null) {
            pagto = new Pagamento();
            pagto.setPedidoId(pagamentoDto.getPedidoId());
        }

        if (pagto.getStatus() != PagamentoStatus.CONFIRMADO) {
            this.pagamentoMockServiceClient.realizarPagamento(pagamentoDto.getPedidoId(), pagamentoDto.getValor());
        }

        pagto.setForma(pagamentoDto.getForma());
        pagto.setStatus(pagamentoDto.getStatus());
        pagto.setValor(pagamentoDto.getValor().doubleValue());
        pagto.setStatus(pagamentoDto.getStatus());
        return MAPPER.toDto(useCase.save(pagto));
    }
}