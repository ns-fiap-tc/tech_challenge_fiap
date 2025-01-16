package br.com.fiap.lanchonete.domain.port.input.queue;

import br.com.fiap.lanchonete.domain.model.OrdemServico;

public interface MessageConsumer {
    void receive(OrdemServico os);
}
