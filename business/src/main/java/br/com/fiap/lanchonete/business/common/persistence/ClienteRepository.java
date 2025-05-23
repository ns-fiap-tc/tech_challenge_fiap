package br.com.fiap.lanchonete.business.common.persistence;

import br.com.fiap.lanchonete.business.common.dto.ClienteDto;
import java.util.List;

public interface ClienteRepository {
    ClienteDto save(ClienteDto dto);
    ClienteDto findById(Long id);
    ClienteDto findByEmail(String email);
    ClienteDto findByCpf(String cpf);
    List<ClienteDto> findAll();
    void deleteByCpf(String cpf);
    void deleteByEmail(String email);
}