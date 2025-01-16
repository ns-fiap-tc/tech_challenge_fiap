package br.com.fiap.lanchonete.domain.port.output.queue;

import br.com.fiap.lanchonete.domain.model.OrdemServico;

public interface MessageProducer {
    void send(String queueName, OrdemServico ordemServico);
}
