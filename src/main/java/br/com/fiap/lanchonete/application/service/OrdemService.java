package br.com.fiap.lanchonete.application.service;

import br.com.fiap.lanchonete.domain.port.output.persistence.OrdemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrdemService {

    private final OrdemRepository ordemRepository;
}
