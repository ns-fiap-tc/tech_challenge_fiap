package br.com.fiap.lanchonete.produto.adapter.presenter;

import br.com.fiap.lanchonete.produto.commons.dto.ProdutoDto;
import br.com.fiap.lanchonete.produto.core.domain.Produto;
import br.com.fiap.lanchonete.produto.commons.mapper.ProdutoMapper;
import java.util.List;

public class ProdutoPresenter {
    private static final ProdutoMapper MAPPER = ProdutoMapper.INSTANCE;

    public ProdutoDto toDto(Produto produto) {
        return MAPPER.toDto(produto);
    }

    public Produto toDomain(ProdutoDto dto) {
        return MAPPER.toDomain(dto);
    }

    public List<ProdutoDto> map(List<Produto> list) {
        return MAPPER.mapDomainToDto(list);
    }
}