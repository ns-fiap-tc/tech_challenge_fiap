package br.com.fiap.lanchonete.application.adapter.persistence.repository;

import br.com.fiap.lanchonete.application.adapter.persistence.mapper.ProdutoMapper;
import br.com.fiap.lanchonete.business.common.dto.ProdutoDto;
import br.com.fiap.lanchonete.business.common.persistence.ProdutoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProdutoRepositoryImpl implements ProdutoRepository {
    private static final ProdutoMapper MAPPER = ProdutoMapper.INSTANCE;
    private final ProdutoJpaRepository repository;

    @Override
    public ProdutoDto save(ProdutoDto dto) {
/*
        Date now = new Date();
        if (dto.getId() == null) {
            dto.setCreatedAt(now);
        }
        dto.setUpdatedAt(now);
*/
        return MAPPER.toDto(
                this.repository.save(
                        MAPPER.toEntity(dto)));
    }

    @Override
    public ProdutoDto findById(Long id) {
        return MAPPER.toDto(
                this.repository.findById(id).orElse(null));
    }

    @Override
    public List<ProdutoDto> findByNome(String nome) {
        return MAPPER.map(this.repository.findByNome(nome));
    }

    @Override
    public List<ProdutoDto> findAll() {
        return this.repository.findAll()
                .stream()
                .map(MAPPER::toDto)
                .toList();
    }

    @Override
    public List<ProdutoDto> findByCategoriaId(Long categoriaId) {
        return this.repository.findByCategoriaId(categoriaId)
                .stream()
                .map(MAPPER::toDto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        this.repository.deleteById(id);
    }
}