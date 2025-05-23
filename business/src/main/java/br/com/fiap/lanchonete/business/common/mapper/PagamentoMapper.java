package br.com.fiap.lanchonete.business.common.mapper;

import br.com.fiap.lanchonete.business.common.dto.PagamentoDto;
import br.com.fiap.lanchonete.business.core.domain.Pagamento;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PagamentoMapper {
    PagamentoMapper INSTANCE = Mappers.getMapper(PagamentoMapper.class);

    Pagamento toDomain(PagamentoDto dto);
    PagamentoDto toDto(Pagamento pagamento);
    List<Pagamento> map(List<PagamentoDto> list);
}