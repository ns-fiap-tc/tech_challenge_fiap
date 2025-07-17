package br.com.fiap.lanchonete.categoria.core.usecase;

import br.com.fiap.lanchonete.categoria.core.domain.Categoria;
import java.util.List;

public interface CategoriaUseCases {
    Categoria save(Categoria categoria);
    Categoria findById(Long id);
    List<Categoria> findByNome(String nome);
    List<Categoria> findAll();
    void deleteById(Long id);
}