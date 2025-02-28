package br.com.fiap.lanchonete.application.adapter.persistence.mapper;

import br.com.fiap.lanchonete.application.adapter.persistence.entity.ProdutoEntity;
import br.com.fiap.lanchonete.business.common.dto.ProdutoDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProdutoMapper {
    ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);

    ProdutoDto toDto(ProdutoEntity entity);
    ProdutoEntity toEntity(ProdutoDto dto);
    List<ProdutoDto> map(List<ProdutoEntity> entities);
}