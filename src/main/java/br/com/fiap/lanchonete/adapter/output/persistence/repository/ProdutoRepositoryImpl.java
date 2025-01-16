package br.com.fiap.lanchonete.adapter.output.persistence.repository;

import br.com.fiap.lanchonete.adapter.output.persistence.mapper.ProdutoMapper;
import br.com.fiap.lanchonete.domain.model.Produto;
import br.com.fiap.lanchonete.domain.port.output.persistence.ProdutoRepository;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProdutoRepositoryImpl implements ProdutoRepository {

    private static final ProdutoMapper MAPPER = ProdutoMapper.INSTANCE;
    private final ProdutoJpaRepository repository;

    @Override
    public Produto save(Produto produto) {
        Date now = new Date();
        if (produto.getId() == null) {
            produto.setCreatedAt(now);
        }
        produto.setUpdatedAt(now);
        return MAPPER.toDomain(
                this.repository.save(
                        MAPPER.toEntity(produto)));
    }

    @Override
    public Produto findById(Long id) {
        return MAPPER.toDomain(
                this.repository.findById(id).orElse(null));
    }

    @Override
    public List<Produto> findByNome(String nome) {
        return MAPPER.map(this.repository.findByNome(nome));
    }

    @Override
    public List<Produto> findAll() {
        return this.repository.findAll()
                .stream()
                .map(MAPPER::toDomain)
                .toList();
    }

    @Override
    public List<Produto> findByCategoriaId(Long categoriaId) {
        return this.repository.findByCategoriaId(categoriaId)
                .stream()
                .map(MAPPER::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        this.repository.deleteById(id);
    }
}
