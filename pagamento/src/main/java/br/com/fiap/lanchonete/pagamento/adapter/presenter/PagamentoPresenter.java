package br.com.fiap.lanchonete.pagamento.adapter.presenter;

import br.com.fiap.lanchonete.pagamento.commons.dto.PagamentoDto;
import br.com.fiap.lanchonete.pagamento.commons.mapper.PagamentoMapper;
import br.com.fiap.lanchonete.pagamento.core.domain.Pagamento;
import java.util.List;

public class PagamentoPresenter {
    private static final PagamentoMapper MAPPER = PagamentoMapper.INSTANCE;

    public PagamentoDto toDto(Pagamento pagamento) {
        return MAPPER.toDto(pagamento);
    }

    public Pagamento toDomain(PagamentoDto dto) {
        return MAPPER.toDomain(dto);
    }

    public List<Pagamento> map(List<PagamentoDto> list) {
        return MAPPER.mapDtoToDomain(list);
    }
}