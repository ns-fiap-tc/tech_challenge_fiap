package br.com.fiap.lanchonete.adapter.input.queue;

import br.com.fiap.lanchonete.application.service.PedidoService;
import br.com.fiap.lanchonete.domain.model.OrdemServico;
import br.com.fiap.lanchonete.domain.model.OrdemServicoStatus;
import br.com.fiap.lanchonete.domain.model.PedidoStatus;
import br.com.fiap.lanchonete.domain.port.input.queue.MessageConsumer;
import br.com.fiap.lanchonete.domain.usecase.OrdemServicoUseCases;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@CommonsLog
@RequiredArgsConstructor
@Component
public class MessageRetornoConsumer implements MessageConsumer {

    private final PedidoService pedidoService;
    private final OrdemServicoUseCases ordemServicoService;

    @RabbitListener(queues = {"cozinhaRetorno", "bebidaRetorno", "sobremesaRetorno"})
    public void receive(@Payload OrdemServico os) {
        log.info("retorno da OS finalizada: " + os);
        List<OrdemServico> ordensServico = null;
        synchronized (this) {
            ordensServico = ordemServicoService.findByPedidoIdStatus(
                    os.getPedidoId(),
                    OrdemServicoStatus.FINALIZADO);
        }
        if (ordensServico == null || ordensServico.isEmpty()) {
            pedidoService.updateStatus(os.getPedidoId(), PedidoStatus.PRONTO);
        }
    }
}
