package br.com.fiap.lanchonete.business.common.queue;

import br.com.fiap.lanchonete.business.core.domain.OrdemServico;

public interface MessageProducer {
    void send(String queueName, OrdemServico ordemServico);
}