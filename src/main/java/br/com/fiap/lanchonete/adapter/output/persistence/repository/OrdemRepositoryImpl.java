package br.com.fiap.lanchonete.adapter.output.persistence.repository;

import br.com.fiap.lanchonete.adapter.output.persistence.entity.OrdemEntity;
import br.com.fiap.lanchonete.adapter.output.persistence.mapper.OrdemMapper;
import br.com.fiap.lanchonete.domain.port.output.persistence.OrdemRepository;
import br.com.fiap.lanchonete.domain.model.Ordem;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrdemRepositoryImpl implements OrdemRepository {

    private final OrdemJpaRepository repository;
    private final OrdemMapper mapper;

    @Override
    public Ordem save(Ordem ordem) {
        OrdemEntity jpaEntity = this.repository.save(mapper.toJpaEntity(ordem));
        return mapper.toDomain(jpaEntity);
    }

    @Override
    public List<Ordem> findAll() {
        return this.repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<Ordem> findById(Long id) {
        Optional<OrdemEntity> jpaEntity = this.repository.findById(id);
        return jpaEntity.map(mapper::toDomain);
    }
}
