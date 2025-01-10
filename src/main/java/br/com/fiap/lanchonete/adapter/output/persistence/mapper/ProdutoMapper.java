package br.com.fiap.lanchonete.adapter.output.persistence.mapper;

import br.com.fiap.lanchonete.adapter.output.persistence.entity.ClienteEntity;
import br.com.fiap.lanchonete.adapter.output.persistence.entity.ProdutoEntity;
import br.com.fiap.lanchonete.domain.model.Cliente;
import br.com.fiap.lanchonete.domain.model.Produto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProdutoMapper {
    ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);

    Produto toDomain(ProdutoEntity entity);
    ProdutoEntity toEntity(Produto produto);
    List<Produto> map(List<ProdutoEntity> entities);
}
