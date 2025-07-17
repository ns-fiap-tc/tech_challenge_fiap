package br.com.fiap.lanchonete.categoria.commons.mapper;

import br.com.fiap.lanchonete.categoria.commons.dto.CategoriaDto;
import br.com.fiap.lanchonete.categoria.device.persistence.entity.CategoriaEntity;
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