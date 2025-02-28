package br.com.fiap.lanchonete.application.adapter.persistence.mapper;

import br.com.fiap.lanchonete.application.adapter.persistence.entity.CategoriaEntity;
import br.com.fiap.lanchonete.business.common.dto.CategoriaDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoriaEntityMapper {
    CategoriaEntityMapper INSTANCE = Mappers.getMapper(CategoriaEntityMapper.class);

    CategoriaDto toDto(CategoriaEntity entity);
    CategoriaEntity toEntity(CategoriaDto dto);
    List<CategoriaDto> map(List<CategoriaEntity> entities);
}