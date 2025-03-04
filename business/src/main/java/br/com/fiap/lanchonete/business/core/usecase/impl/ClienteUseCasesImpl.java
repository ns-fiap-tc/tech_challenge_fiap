package br.com.fiap.lanchonete.business.core.usecase.impl;

import br.com.fiap.lanchonete.business.adapter.gateway.ClienteGateway;
import br.com.fiap.lanchonete.business.core.domain.Cliente;
import br.com.fiap.lanchonete.business.core.domain.CpfUtils;
import br.com.fiap.lanchonete.business.core.domain.ValidacaoEnum;
import br.com.fiap.lanchonete.business.core.exception.ValidacaoException;
import br.com.fiap.lanchonete.business.core.exception.ValidacaoNotFoundException;
import br.com.fiap.lanchonete.business.core.exception.ValidacaoRuntimeException;
import br.com.fiap.lanchonete.business.core.usecase.ClienteUseCases;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClienteUseCasesImpl implements ClienteUseCases {
    private final ClienteGateway gateway;

    @Override
    public Cliente save(Cliente cliente) {
        try {
            if(!CpfUtils.isValid(cliente.getCpf())) {
                throw new ValidacaoException(ValidacaoEnum.CPF_INVALIDO);
            }

            if(Objects.nonNull(gateway.findByCpf(cliente.getCpf()))) {
                throw new ValidacaoException(ValidacaoEnum.CPF_JA_CADASTRADO);
            }

            return gateway.save(cliente);
        }catch (ValidacaoException e){
            throw e;
        }catch (Exception e){
            throw new ValidacaoRuntimeException(ValidacaoEnum.NAO_IDENTIFICADO);
        }
    }

    @Override
    public Cliente findById(Long id) {
        return gateway.findById(id);
    }

    @Override
    public Cliente findByEmail(String email) {
        try {
            Cliente cliente = gateway.findByEmail(email);
            if(Objects.isNull(cliente)) {
                throw new ValidacaoNotFoundException(ValidacaoEnum.CLIENTE_NAO_ENCONTRADO);
            }
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
            Cliente cliente = gateway.findByCpf(cpf);
            if(Objects.isNull(cliente)) {
                throw new ValidacaoNotFoundException(ValidacaoEnum.CLIENTE_NAO_ENCONTRADO);
            }
            return cliente;
        }catch (ValidacaoNotFoundException e){
            throw e;
        }catch (Exception e){
            throw new ValidacaoRuntimeException(ValidacaoEnum.NAO_IDENTIFICADO);
        }
    }

    @Override
    public List<Cliente> findAll() {
        return gateway.findAll();
    }

    @Override
    public void deleteByCpf(String cpf) {
        try {
            if(Objects.isNull(gateway.findByCpf(cpf))) {
                throw new ValidacaoNotFoundException(ValidacaoEnum.CLIENTE_NAO_ENCONTRADO);
            }
            gateway.deleteByCpf(cpf);
        }catch (ValidacaoNotFoundException e){
            throw e;
        }catch (Exception e){
            throw new ValidacaoRuntimeException(ValidacaoEnum.NAO_IDENTIFICADO);
        }
    }

    @Override
    public void deleteByEmail(String email) {
        try {
            if(Objects.isNull(gateway.findByEmail(email))) {
                throw new ValidacaoNotFoundException(ValidacaoEnum.CLIENTE_NAO_ENCONTRADO);
            }
            gateway.deleteByEmail(email);
        }catch (ValidacaoNotFoundException e){
            throw e;
        }catch (Exception e){
            throw new ValidacaoRuntimeException(ValidacaoEnum.NAO_IDENTIFICADO);
        }
    }
}