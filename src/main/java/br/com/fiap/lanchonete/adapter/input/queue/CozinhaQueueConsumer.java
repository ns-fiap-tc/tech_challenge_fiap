package br.com.fiap.lanchonete.adapter.input.queue;

import br.com.fiap.lanchonete.adapter.output.producer.RabbitMqMessageProducer;
import br.com.fiap.lanchonete.domain.model.OrdemServico;
import br.com.fiap.lanchonete.domain.model.OrdemServicoStatus;
import br.com.fiap.lanchonete.domain.port.input.queue.MessageConsumer;
import br.com.fiap.lanchonete.domain.usecase.OrdemServicoUseCases;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@CommonsLog
@RequiredArgsConstructor
@Component
public class CozinhaQueueConsumer implements MessageConsumer {

    private final OrdemServicoUseCases ordemServicoService;
    private final RabbitMqMessageProducer messageProducer;

    @RabbitListener(queues = {"cozinha"})
    public void receive(@Payload OrdemServico os) {
        os.setStatus(OrdemServicoStatus.PRODUCAO);
        ordemServicoService.updateStatus(os.getId(), OrdemServicoStatus.PRODUCAO);
        log.info("OS recebida: " + os);
        try {
            Thread.sleep(os.getTempoPreparo() * os.getQuantidade() * 1000);
        } catch (InterruptedException e) {
        }
        os.setStatus(OrdemServicoStatus.FINALIZADO);
        ordemServicoService.updateStatus(os.getId(), OrdemServicoStatus.FINALIZADO);
        log.info("OS finalizada: " + os);
        messageProducer.send("cozinhaRetorno", os);
    }
}
