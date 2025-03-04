package br.com.fiap.lanchonete.business.common.mapper;

import br.com.fiap.lanchonete.business.common.dto.ProdutoDto;
import br.com.fiap.lanchonete.business.core.domain.Produto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProdutoMapper {
    ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);

    Produto toDomain(ProdutoDto dto);
    ProdutoDto toDto(Produto produto);
    List<ProdutoDto> mapToDto(List<Produto> list);
    List<Produto> mapToDomain(List<ProdutoDto> list);
}