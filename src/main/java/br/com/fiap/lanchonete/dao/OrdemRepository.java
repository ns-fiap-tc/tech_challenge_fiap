package br.com.fiap.lanchonete.dao;

import br.com.fiap.lanchonete.domain.Ordem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdemRepository extends CrudRepository<Ordem, Long> {
}
