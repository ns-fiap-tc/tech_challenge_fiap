package br.com.fiap.lanchonete.adapter.input.mapper;

import br.com.fiap.lanchonete.adapter.input.dto.PedidoDto;
import br.com.fiap.lanchonete.domain.model.Pedido;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PedidoMapper {
    PedidoMapper INSTANCE = Mappers.getMapper(PedidoMapper.class);

    @Mapping(target = "tempoEspera", ignore = true)
    Pedido toDomain(PedidoDto dto);

    @Mapping(target = "tempoEspera", expression = "java(pedido.getTempoEspera())")
    PedidoDto toDto(Pedido pedido);

    List<PedidoDto> map(List<Pedido> list);
}
