package br.com.fiap.lanchonete.business.common.mapper;

import br.com.fiap.lanchonete.business.common.dto.PedidoDto;
import br.com.fiap.lanchonete.business.core.domain.Pedido;
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

    List<PedidoDto> mapToDto(List<Pedido> list);

    List<Pedido> mapToDomain(List<PedidoDto> list);
}