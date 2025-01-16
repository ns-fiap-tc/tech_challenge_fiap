package br.com.fiap.lanchonete.application.service;

import br.com.fiap.lanchonete.domain.model.OrdemServico;
import br.com.fiap.lanchonete.domain.model.OrdemServicoStatus;
import br.com.fiap.lanchonete.domain.port.output.persistence.OrdemServicoRepository;
import br.com.fiap.lanchonete.domain.usecase.OrdemServicoUseCases;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class OrdemServicoService implements OrdemServicoUseCases {
    private final OrdemServicoRepository repository;

    @Override
    public OrdemServico save(OrdemServico ordemServico) {
        return repository.save(ordemServico);
    }

    @Override
    public void updateStatus(Long id, OrdemServicoStatus status) {
        this.repository.updateStatus(id, status, new Date());
    }

    @Override
    public List<OrdemServico> findByPedidoId(Long pedidoId) {
        return repository.findByPedidoId(pedidoId);
    }

    @Override
    public List<OrdemServico> findByPedidoIdStatus(Long pedidoId, OrdemServicoStatus status) {
        return repository.findByPedidoIdStatus(pedidoId, status);
    }

    @Override
    public List<OrdemServico> findByPedidoItemId(Long pedidoItemId) {
        return repository.findByPedidoItemId(pedidoItemId);
    }
}
