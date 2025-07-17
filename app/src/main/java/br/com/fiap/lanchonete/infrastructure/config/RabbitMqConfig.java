package br.com.fiap.lanchonete.infrastructure.config;

import br.com.fiap.lanchonete.categoria.commons.domain.CategoriaTipoEnum;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@CommonsLog
@EnableRabbit
@Configuration
public class RabbitMqConfig implements RabbitListenerConfigurer {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Value("${spring.rabbitmq.exchange}")
    private String exchangeName;

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry(){
        return new RabbitListenerEndpointRegistry();
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Cria as filas dinamicamente, caso nao existam.
     * @return
     */
    @Bean
    public Boolean queueCreation() {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = connectionFactory.createConnection();
            channel = connection.createChannel(false); //transactional
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true);
            if (this.createQueue(channel, "bebida") != null) {
                channel.queueBind(
                        "bebida",  //queueName
                        exchangeName,
                        CategoriaTipoEnum.BEBIDA.name()  //routingKey
                );
            }
            if (this.createQueue(channel, "bebidaRetorno") != null) {
                channel.queueBind(
                        "bebidaRetorno",  //queueName
                        exchangeName,
                        "bebidaRetorno"  //routingKey
                );
            }
            if (this.createQueue(channel, "sobremesa") != null) {
                channel.queueBind(
                        "sobremesa",  //queueName
                        exchangeName,
                        CategoriaTipoEnum.SOBREMESA.name()  //routingKey
                );
            }
            if (this.createQueue(channel, "sobremesaRetorno") != null) {
                channel.queueBind(
                        "sobremesaRetorno",  //queueName
                        exchangeName,
                        "sobremesaRetorno"  //routingKey
                );
            }
            if (this.createQueue(channel, "cozinha") != null) {
                channel.queueBind(
                        "cozinha",  //queueName
                        exchangeName,
                        CategoriaTipoEnum.LANCHE.name()  //routingKey
                );

                channel.queueBind(
                        "cozinha",  //queueName
                        exchangeName,
                        CategoriaTipoEnum.ACOMPANHAMENTO.name()  //routingKey
                );
            }
            if (this.createQueue(channel, "cozinhaRetorno") != null) {
                channel.queueBind(
                        "cozinhaRetorno",  //queueName
                        exchangeName,
                        "cozinhaRetorno"  //routingKey
                );
            }
        } catch (AmqpException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (TimeoutException e) {
                    throw new RuntimeException(e);
                }
            }
            if (connection != null) {
                connection.close();
            }
        }
        return true;
    }

    private AMQP.Queue.DeclareOk createQueue(Channel channel, String queueName) throws IOException {
        AMQP.Queue.DeclareOk declareOk = null;
        try {
            declareOk = channel.queueDeclarePassive(queueName);
        } catch (Exception e) {
            //se der excecao significa que nao encontrou a fila, portanto ignora.
        }
        if (declareOk == null) {
            log.info("Criando a queue " + queueName);
            declareOk = channel.queueDeclare(
                    queueName, //queueName
                    true, //durable
                    false, //exclusive
                    false, //autoDelete
                    null //arguments
            );
        }
        return declareOk;
    }

    @Override
    public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setPrefetchCount(1);
        factory.setConsecutiveActiveTrigger(1);
        factory.setConsecutiveIdleTrigger(1);
        factory.setConnectionFactory(connectionFactory);
        registrar.setContainerFactory(factory);
        registrar.setEndpointRegistry(rabbitListenerEndpointRegistry());
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }
}