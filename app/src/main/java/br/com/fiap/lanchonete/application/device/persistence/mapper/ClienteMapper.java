package br.com.fiap.lanchonete.application.device.persistence.mapper;

import br.com.fiap.lanchonete.application.device.persistence.entity.ClienteEntity;
import br.com.fiap.lanchonete.business.common.dto.ClienteDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClienteMapper {
    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    ClienteDto toDto(ClienteEntity entity);
    ClienteEntity toEntity(ClienteDto dto);
    List<ClienteDto> map(List<ClienteEntity> entities);
}