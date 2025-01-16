package br.com.fiap.lanchonete.domain.usecase;

import br.com.fiap.lanchonete.domain.model.Cliente;
import java.util.List;

public interface ClienteUseCases {
    Cliente save(Cliente cliente);
    Cliente findById(Long id);
    Cliente findByEmail(String email);
    Cliente findByCpf(String cpf);
    List<Cliente> findAll();
    void deleteByCpf(String cpf);
    void deleteByEmail(String email);
}
