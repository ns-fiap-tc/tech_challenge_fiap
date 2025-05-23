package br.com.fiap.lanchonete.application.device.persistence.mapper;

import br.com.fiap.lanchonete.application.device.persistence.entity.PagamentoEntity;
import br.com.fiap.lanchonete.business.common.dto.PagamentoDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PagamentoMapper {
    PagamentoMapper INSTANCE = Mappers.getMapper(PagamentoMapper.class);

    PagamentoDto toDto(PagamentoEntity entity);
    PagamentoEntity toEntity(PagamentoDto dto);
    List<PagamentoDto> map(List<PagamentoEntity> entities);
}