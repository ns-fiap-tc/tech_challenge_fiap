package br.com.fiap.lanchonete.categoria.adapter.presenter;

import br.com.fiap.lanchonete.categoria.commons.dto.CategoriaDto;
import br.com.fiap.lanchonete.categoria.commons.mapper.CategoriaMapper;
import br.com.fiap.lanchonete.categoria.core.domain.Categoria;
import java.util.List;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CategoriaPresenter {
    private static final CategoriaMapper MAPPER = CategoriaMapper.INSTANCE;

    public CategoriaDto toDto(Categoria categoria) {
        return MAPPER.toDto(categoria);
    }

    public Categoria toDomain(CategoriaDto dto) {
        return MAPPER.toDomain(dto);
    }

    public List<CategoriaDto> map(List<Categoria> list) {
        return MAPPER.mapToDto(list);
    }
}