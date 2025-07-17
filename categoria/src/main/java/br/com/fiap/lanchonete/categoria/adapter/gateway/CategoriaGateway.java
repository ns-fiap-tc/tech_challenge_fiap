package br.com.fiap.lanchonete.categoria.adapter.gateway;

import br.com.fiap.lanchonete.categoria.commons.mapper.CategoriaMapper;
import br.com.fiap.lanchonete.categoria.commons.persistence.CategoriaRepository;
import br.com.fiap.lanchonete.categoria.core.domain.Categoria;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoriaGateway {
    private static final CategoriaMapper MAPPER = CategoriaMapper.INSTANCE;
    private final CategoriaRepository repository;

    public Categoria save(Categoria categoria) {
        Date now = new Date();
        if (categoria.getId() == null) {
            categoria.setCreatedAt(now);
        }
        categoria.setUpdatedAt(now);
        return MAPPER.toDomain(repository.save(MAPPER.toDto(categoria)));
    }

    public Categoria findById(Long id) {
        return MAPPER.toDomain(repository.findById(id));
    }

    public List<Categoria> findByNome(String nome) {
        return MAPPER.mapToDomain(repository.findByNome(nome));
    }

    public List<Categoria> findAll() {
        return MAPPER.mapToDomain(repository.findAll());
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}