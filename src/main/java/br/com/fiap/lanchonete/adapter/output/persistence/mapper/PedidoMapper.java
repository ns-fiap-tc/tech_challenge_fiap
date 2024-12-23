package br.com.fiap.lanchonete.adapter.output.persistence.mapper;

import br.com.fiap.lanchonete.adapter.output.persistence.entity.PedidoEntity;
import br.com.fiap.lanchonete.domain.model.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper//(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PedidoMapper {
    PedidoMapper INSTANCE = Mappers.getMapper(PedidoMapper.class);

    Pedido toDomain(PedidoEntity jpaPedido);
    PedidoEntity toJpaEntity(Pedido pedido);
}
