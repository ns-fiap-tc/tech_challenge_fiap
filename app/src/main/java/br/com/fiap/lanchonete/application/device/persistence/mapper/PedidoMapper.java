package br.com.fiap.lanchonete.application.device.persistence.mapper;

import br.com.fiap.lanchonete.application.device.persistence.entity.PedidoEntity;
import br.com.fiap.lanchonete.pedido.commons.dto.PedidoDto;
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