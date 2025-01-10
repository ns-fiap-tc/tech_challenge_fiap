package br.com.fiap.lanchonete.adapter.output.persistence.mapper;

import br.com.fiap.lanchonete.adapter.output.persistence.entity.OrdemEntity;
import br.com.fiap.lanchonete.adapter.output.persistence.entity.OrdemItemEntity;
import br.com.fiap.lanchonete.domain.model.Ordem;
import br.com.fiap.lanchonete.domain.model.OrdemItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrdemMapper {
    OrdemMapper INSTANCE = Mappers.getMapper(OrdemMapper.class);

    Ordem toDomain(OrdemEntity entity);
    OrdemEntity toEntity(Ordem ordem);
    OrdemItem map(OrdemItemEntity ordemItem);
}
