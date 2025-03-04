package br.com.fiap.lanchonete.application.device.persistence.repository;

import br.com.fiap.lanchonete.application.device.persistence.mapper.CategoriaEntityMapper;
import br.com.fiap.lanchonete.business.common.dto.CategoriaDto;
import br.com.fiap.lanchonete.business.common.persistence.CategoriaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CategoriaRepositoryImpl implements CategoriaRepository {
    private static final CategoriaEntityMapper MAPPER = CategoriaEntityMapper.INSTANCE;
    private final CategoriaJpaRepository repository;

    @Override
    public CategoriaDto save(CategoriaDto dto) {
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
    public CategoriaDto findById(Long id) {
        return MAPPER.toDto(
                this.repository.findById(id).orElse(null));
    }

    @Override
    public List<CategoriaDto> findByNome(String nome) {
        return MAPPER.map(this.repository.findByNome(nome));
    }

    @Override
    public List<CategoriaDto> findAll() {
        return MAPPER.map(repository.findAll());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}