package br.com.fiap.lanchonete.domain.port.output.persistence;

import br.com.fiap.lanchonete.domain.model.Cliente;
import java.util.List;

public interface ClienteRepository {
    Cliente save(Cliente cliente);
    Cliente findByEmail(String email);
    Cliente findByCpf(String cpf);
    List<Cliente> findAll();
    void deleteByCpf(String cpf);
    void deleteByEmail(String email);
}
