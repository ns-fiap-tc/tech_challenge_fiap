package br.com.fiap.lanchonete.application.device.queue;

import br.com.fiap.lanchonete.business.adapter.controller.OrdemServicoController;
import br.com.fiap.lanchonete.business.common.queue.MessageConsumer;
import br.com.fiap.lanchonete.business.core.domain.OrdemServico;
import br.com.fiap.lanchonete.business.core.domain.OrdemServicoStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@CommonsLog
@RequiredArgsConstructor
@Component
public class BebidaQueueConsumer implements MessageConsumer {

    private final OrdemServicoController ordemServicoController;
    private final RabbitMqMessageProducer messageProducer;

    @RabbitListener(queues = {"bebida"})
    public void receive(@Payload OrdemServico os) throws InterruptedException {
        os.setStatus(OrdemServicoStatus.PRODUCAO);
        ordemServicoController.updateStatus(os.getId(), OrdemServicoStatus.PRODUCAO);
        log.info("OS recebida: " + os);
        Thread.sleep((long) os.getTempoPreparo() * os.getQuantidade() * 1000);
        os.setStatus(OrdemServicoStatus.FINALIZADO);
        ordemServicoController.updateStatus(os.getId(), OrdemServicoStatus.FINALIZADO);
        log.info("OS finalizada: " + os);
        messageProducer.send("bebidaRetorno", os);
    }
}