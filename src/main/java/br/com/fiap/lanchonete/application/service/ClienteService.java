package br.com.fiap.lanchonete.application.service;

import br.com.fiap.lanchonete.adapter.input.exception.ValidacaoException;
import br.com.fiap.lanchonete.adapter.input.exception.ValidacaoNotFoundException;
import br.com.fiap.lanchonete.adapter.input.exception.ValidacaoRuntimeException;
import br.com.fiap.lanchonete.domain.model.Cliente;
import br.com.fiap.lanchonete.domain.model.ValidacaoEnum;
import br.com.fiap.lanchonete.domain.port.output.persistence.ClienteRepository;
import br.com.fiap.lanchonete.domain.usecase.ClienteUseCases;
import java.util.List;
import java.util.Objects;

import br.com.fiap.lanchonete.utils.CpfUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClienteService implements ClienteUseCases {

    private final ClienteRepository repository;

    @Override
    public Cliente save(Cliente cliente) {
        try {
            if(!CpfUtils.isValid(cliente.getCpf()))
                throw new ValidacaoException(ValidacaoEnum.CPF_INVALIDO);

            if(Objects.nonNull(repository.findByCpf(cliente.getCpf())))
                throw new ValidacaoException(ValidacaoEnum.CPF_JA_CADASTRADO);

            return repository.save(cliente);
        }catch (ValidacaoException e){
            throw e;
        }catch (Exception e){
            throw new ValidacaoRuntimeException(ValidacaoEnum.NAO_IDENTIFICADO);
        }
    }

    @Override
    public Cliente findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Cliente findByEmail(String email) {
        try {
            Cliente cliente = repository.findByEmail(email);
            if(Objects.isNull(cliente)) throw new ValidacaoNotFoundException(ValidacaoEnum.CLIENTE_NAO_ENCONTRADO);
            return cliente;
        }catch (ValidacaoNotFoundException e){
            throw e;
        }catch (Exception e){
            throw new ValidacaoRuntimeException(ValidacaoEnum.NAO_IDENTIFICADO);
        }
    }

    @Override
    public Cliente findByCpf(String cpf) {
        try {
            Cliente cliente = repository.findByCpf(cpf);
            if(Objects.isNull(cliente)) throw new ValidacaoNotFoundException(ValidacaoEnum.CLIENTE_NAO_ENCONTRADO);
            return cliente;
        }catch (ValidacaoNotFoundException e){
            throw e;
        }catch (Exception e){
            throw new ValidacaoRuntimeException(ValidacaoEnum.NAO_IDENTIFICADO);
        }
    }

    @Override
    public List<Cliente> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteByCpf(String cpf) {
        try {
            if(Objects.isNull(repository.findByCpf(cpf))) throw new ValidacaoNotFoundException(ValidacaoEnum.CLIENTE_NAO_ENCONTRADO);
            repository.deleteByCpf(cpf);
        }catch (ValidacaoNotFoundException e){
            throw e;
        }catch (Exception e){
            throw new ValidacaoRuntimeException(ValidacaoEnum.NAO_IDENTIFICADO);
        }
    }

    @Override
    public void deleteByEmail(String email) {
        try {
            if(Objects.isNull(repository.findByEmail(email))) throw new ValidacaoNotFoundException(ValidacaoEnum.CLIENTE_NAO_ENCONTRADO);
            repository.deleteByEmail(email);
        }catch (ValidacaoNotFoundException e){
            throw e;
        }catch (Exception e){
            throw new ValidacaoRuntimeException(ValidacaoEnum.NAO_IDENTIFICADO);
        }
    }
}
