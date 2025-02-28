package br.com.fiap.lanchonete.application.adapter.persistence.repository;

import br.com.fiap.lanchonete.application.adapter.persistence.entity.ClienteEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteJpaRepository extends JpaRepository<ClienteEntity, Long> {
    Optional<ClienteEntity> findByEmail(String email);
    Optional<ClienteEntity> findByCpf(String cpf);
    void deleteByCpf(String cpf);
    void deleteByEmail(String email);
}