package br.com.fiap.lanchonete.adapter.output.persistence.repository;

import br.com.fiap.lanchonete.adapter.output.persistence.mapper.CategoriaMapper;
import br.com.fiap.lanchonete.domain.model.Categoria;
import br.com.fiap.lanchonete.domain.port.output.persistence.CategoriaRepository;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CategoriaRepositoryImpl implements CategoriaRepository {

    private static final CategoriaMapper MAPPER = CategoriaMapper.INSTANCE;
    private final CategoriaJpaRepository repository;

    @Override
    public Categoria save(Categoria categoria) {
        Date now = new Date();
        if (categoria.getId() == null) {
            categoria.setCreatedAt(now);
        }
        categoria.setUpdatedAt(now);
        return MAPPER.toDomain(
                this.repository.save(
                        MAPPER.toEntity(categoria)));
    }

    @Override
    public Categoria findById(Long id) {
        return MAPPER.toDomain(
                this.repository.findById(id).orElse(null));
    }

    @Override
    public List<Categoria> findByNome(String nome) {
        return MAPPER.map(this.repository.findByNome(nome));
    }

    @Override
    public List<Categoria> findAll() {
        return MAPPER.map(repository.findAll());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
