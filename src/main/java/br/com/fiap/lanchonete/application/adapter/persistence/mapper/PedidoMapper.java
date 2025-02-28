package br.com.fiap.lanchonete.application.adapter.persistence.mapper;

import br.com.fiap.lanchonete.application.adapter.persistence.entity.PedidoEntity;
import br.com.fiap.lanchonete.business.common.dto.PedidoDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PedidoMapper {
    PedidoMapper INSTANCE = Mappers.getMapper(PedidoMapper.class);

    PedidoDto toDto(PedidoEntity entity);
    PedidoEntity toEntity(PedidoDto dto);
    List<PedidoDto> map(List<PedidoEntity> entities);
}