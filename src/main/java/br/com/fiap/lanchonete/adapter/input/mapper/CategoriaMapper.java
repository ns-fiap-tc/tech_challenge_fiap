package br.com.fiap.lanchonete.adapter.input.mapper;

import br.com.fiap.lanchonete.adapter.input.dto.CategoriaDto;
import br.com.fiap.lanchonete.domain.model.Categoria;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoriaMapper {
    CategoriaMapper INSTANCE = Mappers.getMapper(CategoriaMapper.class);

    Categoria toDomain(CategoriaDto dto);
    CategoriaDto toDto(Categoria categoria);
    List<CategoriaDto> map(List<Categoria> list);
}
