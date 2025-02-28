package br.com.fiap.lanchonete.application.adapter.queue;

import br.com.fiap.lanchonete.business.common.queue.MessageProducer;
import br.com.fiap.lanchonete.business.core.domain.OrdemServico;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RabbitMqMessageProducer implements MessageProducer {

    @Value("${spring.rabbitmq.exchange}")
    private String exchangeName;

    private final RabbitTemplate rabbitTemplate;

    public void send(String queueName, OrdemServico ordemServico) {
        rabbitTemplate.convertAndSend(exchangeName, queueName, ordemServico);
    }
}