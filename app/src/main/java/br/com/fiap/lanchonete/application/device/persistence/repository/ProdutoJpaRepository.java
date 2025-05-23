package br.com.fiap.lanchonete.application.device.persistence.repository;

import br.com.fiap.lanchonete.application.device.persistence.entity.ProdutoEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoJpaRepository extends JpaRepository<ProdutoEntity, Long> {
    @Query("FROM ProdutoEntity WHERE lower(nome) like lower(concat('%', :nameToFind,'%'))")
    List<ProdutoEntity> findByNome(@Param("nameToFind") String nome);
    List<ProdutoEntity> findByCategoriaId(Long categoriaId);
}