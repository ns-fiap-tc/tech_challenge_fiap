package br.com.fiap.lanchonete.adapter.output.persistence.mapper;

import br.com.fiap.lanchonete.adapter.output.persistence.entity.ClienteEntity;
import br.com.fiap.lanchonete.domain.model.Cliente;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClienteMapper {
    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    Cliente toDomain(ClienteEntity entity);
    ClienteEntity toEntity(Cliente cliente);
    List<Cliente> map(List<ClienteEntity> entities);
}
