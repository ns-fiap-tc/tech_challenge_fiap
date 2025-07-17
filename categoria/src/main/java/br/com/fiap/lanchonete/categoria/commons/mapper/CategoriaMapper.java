package br.com.fiap.lanchonete.categoria.commons.mapper;

import br.com.fiap.lanchonete.categoria.commons.dto.CategoriaDto;
import br.com.fiap.lanchonete.categoria.core.domain.Categoria;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoriaMapper {
    CategoriaMapper INSTANCE = Mappers.getMapper(CategoriaMapper.class);

    Categoria toDomain(CategoriaDto dto);
    CategoriaDto toDto(Categoria categoria);
    List<CategoriaDto> mapToDto(List<Categoria> list);
    List<Categoria> mapToDomain(List<CategoriaDto> list);
}