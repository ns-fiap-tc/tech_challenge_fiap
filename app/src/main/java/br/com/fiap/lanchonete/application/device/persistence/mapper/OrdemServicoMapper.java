package br.com.fiap.lanchonete.application.device.persistence.mapper;

import br.com.fiap.lanchonete.application.device.persistence.entity.OrdemServicoEntity;
import br.com.fiap.lanchonete.business.common.dto.OrdemServicoDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrdemServicoMapper {
    OrdemServicoMapper INSTANCE = Mappers.getMapper(OrdemServicoMapper.class);

    OrdemServicoDto toDto(OrdemServicoEntity entity);
    OrdemServicoEntity toEntity(OrdemServicoDto dto);
}