package br.com.fiap.lanchonete.adapter.output.producer;

import br.com.fiap.lanchonete.domain.model.OrdemServico;
import br.com.fiap.lanchonete.domain.port.output.queue.MessageProducer;
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
