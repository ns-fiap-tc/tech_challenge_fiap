package br.com.fiap.lanchonete.application.service;

import br.com.fiap.lanchonete.domain.model.Categoria;
import br.com.fiap.lanchonete.domain.port.output.persistence.CategoriaRepository;
import br.com.fiap.lanchonete.domain.usecase.CategoriaUseCases;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoriaService implements CategoriaUseCases {

    private final CategoriaRepository repository;

    @Override
    public Categoria save(Categoria categoria) {
        return repository.save(categoria);
    }

    @Override
    public Categoria findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Categoria> findByNome(String nome) {
        return repository.findByNome(nome);
    }

    @Override
    public List<Categoria> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
