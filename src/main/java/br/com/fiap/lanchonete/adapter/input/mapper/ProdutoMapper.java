package br.com.fiap.lanchonete.adapter.input.mapper;

import br.com.fiap.lanchonete.adapter.input.dto.ProdutoDto;
import br.com.fiap.lanchonete.domain.model.Produto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProdutoMapper {
    ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);

    Produto toDomain(ProdutoDto dto);
    ProdutoDto toDto(Produto produto);
    List<ProdutoDto> map(List<Produto> list);
}
