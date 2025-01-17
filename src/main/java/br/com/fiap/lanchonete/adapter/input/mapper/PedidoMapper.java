package br.com.fiap.lanchonete.adapter.input.mapper;

import br.com.fiap.lanchonete.adapter.input.dto.PedidoDto;
import br.com.fiap.lanchonete.domain.model.Pedido;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PedidoMapper {
    PedidoMapper INSTANCE = Mappers.getMapper(PedidoMapper.class);

    Pedido toDomain(PedidoDto dto);
    PedidoDto toDto(Pedido pedido);
    List<PedidoDto> map(List<Pedido> list);
}
