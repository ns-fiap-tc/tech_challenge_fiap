package br.com.fiap.lanchonete.adapter.output.persistence.mapper;

import br.com.fiap.lanchonete.adapter.output.persistence.entity.CategoriaEntity;
import br.com.fiap.lanchonete.domain.model.Categoria;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoriaMapper {
    CategoriaMapper INSTANCE = Mappers.getMapper(CategoriaMapper.class);

    Categoria toDomain(CategoriaEntity entity);
    CategoriaEntity toEntity(Categoria categoria);
    List<Categoria> map(List<CategoriaEntity> entities);
}
