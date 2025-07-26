package br.com.fiap.lanchonete.business.common.queue;

import br.com.fiap.lanchonete.business.core.domain.OrdemServico;

public interface MessageConsumer {
    void receive(OrdemServico os) throws InterruptedException;
}