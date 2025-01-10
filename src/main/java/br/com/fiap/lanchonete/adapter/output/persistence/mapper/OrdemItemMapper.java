package br.com.fiap.lanchonete.adapter.output.persistence.mapper;

import br.com.fiap.lanchonete.adapter.output.persistence.entity.OrdemItemEntity;
import br.com.fiap.lanchonete.domain.model.OrdemItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrdemItemMapper {
    OrdemItemMapper INSTANCE = Mappers.getMapper(OrdemItemMapper.class);

    OrdemItem toDomain(OrdemItemMapper jpaOrdemItem);
    OrdemItemEntity toJpaEntity(OrdemItem ordemItem);
}
