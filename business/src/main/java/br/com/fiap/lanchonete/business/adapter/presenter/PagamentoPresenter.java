package br.com.fiap.lanchonete.business.adapter.presenter;

import br.com.fiap.lanchonete.business.common.dto.PagamentoDto;
import br.com.fiap.lanchonete.business.common.mapper.PagamentoMapper;
import br.com.fiap.lanchonete.business.core.domain.Pagamento;
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
        return MAPPER.map(list);
    }
}