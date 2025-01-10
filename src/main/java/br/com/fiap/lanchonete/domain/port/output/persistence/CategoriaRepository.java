package br.com.fiap.lanchonete.domain.port.output.persistence;

import br.com.fiap.lanchonete.domain.model.Categoria;
import java.util.List;

public interface CategoriaRepository {
    Categoria save(Categoria categoria);
    Categoria findById(Long id);
    List<Categoria> findByNome(String nome);
    List<Categoria> findAll();
    void deleteById(Long id);
}
