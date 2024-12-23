package br.com.fiap.lanchonete.domain.usecase;

import br.com.fiap.lanchonete.domain.model.Cliente;

public interface ClienteUseCases {

    Cliente save(Cliente cliente);

    Cliente findByEmail(String email);

    Cliente findByCpf(String cpf);

    Cliente findByCelular(String celular);

    void deleteById(Long id);

    void deleteByNome(String nome);
}
