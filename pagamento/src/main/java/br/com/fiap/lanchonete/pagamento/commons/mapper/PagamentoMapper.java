package br.com.fiap.lanchonete.pagamento.commons.mapper;

import br.com.fiap.lanchonete.pagamento.commons.dto.PagamentoDto;
import br.com.fiap.lanchonete.pagamento.core.domain.Pagamento;
import br.com.fiap.lanchonete.pagamento.device.persistence.entity.PagamentoEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PagamentoMapper {
    PagamentoMapper INSTANCE = Mappers.getMapper(PagamentoMapper.class);

    PagamentoDto toDto(Pagamento pagamento);
    PagamentoDto toDto(PagamentoEntity entity);
    PagamentoEntity toEntity(PagamentoDto dto);
    Pagamento toDomain(PagamentoDto dto);
    List<Pagamento> mapDtoToDomain(List<PagamentoDto> list);
    List<PagamentoDto> mapEntityToDto(List<PagamentoEntity> entities);
    List<PagamentoDto> mapDomainToDto(List<Pagamento> pagamentos);
}