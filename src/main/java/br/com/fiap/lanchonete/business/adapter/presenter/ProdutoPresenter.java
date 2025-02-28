package br.com.fiap.lanchonete.business.adapter.presenter;

import br.com.fiap.lanchonete.business.common.dto.ProdutoDto;
import br.com.fiap.lanchonete.business.common.mapper.ProdutoMapper;
import br.com.fiap.lanchonete.business.core.domain.Produto;
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
        return MAPPER.mapToDto(list);
    }
}