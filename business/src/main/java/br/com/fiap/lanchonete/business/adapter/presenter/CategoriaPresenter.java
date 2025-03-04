package br.com.fiap.lanchonete.business.adapter.presenter;

import br.com.fiap.lanchonete.business.common.dto.CategoriaDto;
import br.com.fiap.lanchonete.business.common.mapper.CategoriaMapper;
import br.com.fiap.lanchonete.business.core.domain.Categoria;
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