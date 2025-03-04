package br.com.fiap.lanchonete.application.device.persistence.repository;

import br.com.fiap.lanchonete.application.device.persistence.entity.PagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoJpaRepository extends JpaRepository<PagamentoEntity, Long> {

}