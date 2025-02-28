package br.com.fiap.lanchonete.application.adapter.persistence.repository;

import br.com.fiap.lanchonete.application.adapter.persistence.mapper.ClienteMapper;
import br.com.fiap.lanchonete.business.common.dto.ClienteDto;
import br.com.fiap.lanchonete.business.common.persistence.ClienteRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ClienteRepositoryImpl implements ClienteRepository {
    private static final ClienteMapper MAPPER = ClienteMapper.INSTANCE;
    private final ClienteJpaRepository repository;

    @Override
    public ClienteDto save(ClienteDto dto) {
/*
        Date now = new Date();
        if (dto.getId() == null) {
            dto.setCreatedAt(now);
        }
        dto.setUpdatedAt(now);
 */
        return MAPPER.toDto(
                this.repository.save(
                        MAPPER.toEntity(dto)));
    }

    @Override
    public ClienteDto findById(Long id) {
        return MAPPER.toDto(
                this.repository.findById(id).orElse(null));
    }

    @Override
    public ClienteDto findByEmail(String email) {
        return MAPPER.toDto(
                this.repository.findByEmail(email).orElse(null));
    }

    @Override
    public ClienteDto findByCpf(String cpf) {
        return MAPPER.toDto(
                this.repository.findByCpf(cpf).orElse(null));
    }

    @Override
    public List<ClienteDto> findAll() {
        return this.repository.findAll()
                .stream()
                .map(MAPPER::toDto)
                .toList();
    }

    @Override
    public void deleteByCpf(String cpf) {
        this.repository.deleteByCpf(cpf);
    }

    @Override
    public void deleteByEmail(String email) {
        this.repository.deleteByEmail(email);
    }
}