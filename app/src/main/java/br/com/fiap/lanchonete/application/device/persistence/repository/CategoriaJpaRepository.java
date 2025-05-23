package br.com.fiap.lanchonete.application.device.persistence.repository;

import br.com.fiap.lanchonete.application.device.persistence.entity.CategoriaEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaJpaRepository extends JpaRepository<CategoriaEntity, Long> {
    @Query("FROM CategoriaEntity WHERE lower(nome) like lower(concat('%', :nameToFind,'%'))")
    List<CategoriaEntity> findByNome(@Param("nameToFind") String nome);
}