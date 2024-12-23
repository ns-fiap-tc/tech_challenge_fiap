package br.com.fiap.lanchonete.domain.port.output.persistence;

import br.com.fiap.lanchonete.domain.model.Cliente;

public interface ClienteRepository {

    Cliente save(Cliente cliente);

    Cliente findByEmail(String email);
}
