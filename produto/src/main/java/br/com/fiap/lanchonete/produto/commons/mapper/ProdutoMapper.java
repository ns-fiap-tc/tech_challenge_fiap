package br.com.fiap.lanchonete.produto.commons.mapper;

import br.com.fiap.lanchonete.produto.commons.dto.ProdutoDto;
import br.com.fiap.lanchonete.produto.core.domain.Produto;
import br.com.fiap.lanchonete.produto.device.persistence.entity.ProdutoEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProdutoMapper {
    ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);

    ProdutoDto toDto(ProdutoEntity entity);
    ProdutoDto toDto(Produto produto);
    ProdutoEntity toEntity(ProdutoDto dto);
    Produto toDomain(ProdutoDto dto);
    List<ProdutoDto> mapEntityToDto(List<ProdutoEntity> entities);
    List<ProdutoDto> mapDomainToDto(List<Produto> list);
    List<Produto> mapToDomain(List<ProdutoDto> list);
}