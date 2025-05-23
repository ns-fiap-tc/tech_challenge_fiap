package br.com.fiap.lanchonete.business.adapter.presenter;

import br.com.fiap.lanchonete.business.common.dto.ClienteDto;
import br.com.fiap.lanchonete.business.common.mapper.ClienteMapper;
import br.com.fiap.lanchonete.business.core.domain.Cliente;
import java.util.List;

public class ClientePresenter {
    private static final ClienteMapper MAPPER = ClienteMapper.INSTANCE;

    public ClienteDto toDto(Cliente cliente) {
        return MAPPER.toDto(cliente);
    }

    public Cliente toDomain(ClienteDto dto) {
        return MAPPER.toDomain(dto);
    }

    public List<ClienteDto> mapToDto(List<Cliente> list) {
        return MAPPER.mapToDto(list);
    }
}