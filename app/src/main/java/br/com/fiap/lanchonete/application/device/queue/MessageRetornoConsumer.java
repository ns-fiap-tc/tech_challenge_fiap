package br.com.fiap.lanchonete.application.device.queue;

import br.com.fiap.lanchonete.business.adapter.controller.OrdemServicoController;
import br.com.fiap.lanchonete.business.adapter.controller.PedidoController;
import br.com.fiap.lanchonete.business.common.queue.MessageConsumer;
import br.com.fiap.lanchonete.business.core.domain.OrdemServico;
import br.com.fiap.lanchonete.business.core.domain.OrdemServicoStatus;
import br.com.fiap.lanchonete.business.core.domain.PedidoStatus;
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

    //private final PedidoUseCasesImpl pedidoUseCasesImpl;
    //private final OrdemServicoUseCases ordemServicoService;
    private final PedidoController pedidoController;
    private final OrdemServicoController ordemServicoController;

    @RabbitListener(queues = {"cozinhaRetorno", "bebidaRetorno", "sobremesaRetorno"})
    public void receive(@Payload OrdemServico os) {
        log.info("retorno da OS finalizada: " + os);
        List<OrdemServico> ordensServico = null;
        synchronized (this) {
            ordensServico = ordemServicoController.findByPedidoIdStatus(
                    os.getPedidoId(),
                    OrdemServicoStatus.FINALIZADO);
        }
        if (ordensServico == null || ordensServico.isEmpty()) {
            pedidoController.updateStatus(os.getPedidoId(), PedidoStatus.PRONTO);
        }
    }
}