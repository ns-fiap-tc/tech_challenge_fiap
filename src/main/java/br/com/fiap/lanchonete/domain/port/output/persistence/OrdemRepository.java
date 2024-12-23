package br.com.fiap.lanchonete.domain.port.output.persistence;

import br.com.fiap.lanchonete.domain.model.Ordem;
import java.util.List;
import java.util.Optional;

public interface OrdemRepository {

    Ordem save(Ordem ordem);

    List<Ordem> findAll();

    Optional<Ordem> findById(Long id);
}
