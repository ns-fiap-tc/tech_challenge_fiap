package br.com.fiap.lanchonete.adapter.output.persistence.mapper;

import br.com.fiap.lanchonete.adapter.output.persistence.entity.PedidoEntity;
import br.com.fiap.lanchonete.domain.model.Pedido;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PedidoMapper {
    PedidoMapper INSTANCE = Mappers.getMapper(PedidoMapper.class);

    Pedido toDomain(PedidoEntity entity);
    PedidoEntity toEntity(Pedido pedido);
    List<Pedido> map(List<PedidoEntity> entities);
}
