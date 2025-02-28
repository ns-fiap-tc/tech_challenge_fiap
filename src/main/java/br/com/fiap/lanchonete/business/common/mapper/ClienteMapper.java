package br.com.fiap.lanchonete.business.common.mapper;

import br.com.fiap.lanchonete.business.common.dto.ClienteDto;
import br.com.fiap.lanchonete.business.core.domain.Cliente;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClienteMapper {
    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    Cliente toDomain(ClienteDto dto);
    ClienteDto toDto(Cliente cliente);
    List<ClienteDto> mapToDto(List<Cliente> list);
    List<Cliente> mapToDomain(List<ClienteDto> list);
}