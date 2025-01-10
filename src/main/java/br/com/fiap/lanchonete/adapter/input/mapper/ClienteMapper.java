package br.com.fiap.lanchonete.adapter.input.mapper;

import br.com.fiap.lanchonete.adapter.input.dto.ClienteDto;
import br.com.fiap.lanchonete.domain.model.Cliente;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClienteMapper {
    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    Cliente toDomain(ClienteDto dto);
    ClienteDto toDto(Cliente cliente);
    List<ClienteDto> map(List<Cliente> list);
}
