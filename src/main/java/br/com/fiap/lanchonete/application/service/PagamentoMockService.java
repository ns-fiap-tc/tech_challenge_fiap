package br.com.fiap.lanchonete.application.service;

import br.com.fiap.lanchonete.adapter.output.producer.RabbitMqMessageProducer;
import br.com.fiap.lanchonete.domain.model.*;
import br.com.fiap.lanchonete.domain.port.output.persistence.PedidoRepository;
import br.com.fiap.lanchonete.domain.usecase.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PagamentoMockService {
    public boolean pagar(){
        return true;
    }
}
