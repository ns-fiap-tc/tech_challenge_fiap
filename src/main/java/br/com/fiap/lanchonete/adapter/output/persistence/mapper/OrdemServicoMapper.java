package br.com.fiap.lanchonete.adapter.output.persistence.mapper;

import br.com.fiap.lanchonete.adapter.output.persistence.entity.OrdemServicoEntity;
import br.com.fiap.lanchonete.domain.model.OrdemServico;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrdemServicoMapper {
    OrdemServicoMapper INSTANCE = Mappers.getMapper(OrdemServicoMapper.class);

    OrdemServico toDomain(OrdemServicoEntity entity);
    OrdemServicoEntity toEntity(OrdemServico ordemServico);
}
