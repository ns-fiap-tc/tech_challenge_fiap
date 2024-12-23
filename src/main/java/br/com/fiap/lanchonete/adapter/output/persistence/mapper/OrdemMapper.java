package br.com.fiap.lanchonete.adapter.output.persistence.mapper;

import br.com.fiap.lanchonete.adapter.output.persistence.entity.OrdemEntity;
import br.com.fiap.lanchonete.adapter.output.persistence.entity.OrdemItemEntity;
import br.com.fiap.lanchonete.domain.model.Ordem;
import br.com.fiap.lanchonete.domain.model.OrdemItem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrdemMapper {
    Ordem toDomain(OrdemEntity jpaOrdem);
    OrdemEntity toJpaEntity(Ordem ordem);
    OrdemItem map(OrdemItemEntity ordemItem);
}
