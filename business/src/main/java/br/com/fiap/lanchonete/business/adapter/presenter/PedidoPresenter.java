package br.com.fiap.lanchonete.business.adapter.presenter;

import br.com.fiap.lanchonete.business.common.dto.PedidoDto;
import br.com.fiap.lanchonete.business.common.mapper.PedidoMapper;
import br.com.fiap.lanchonete.business.core.domain.Pedido;
import java.util.List;
import lombok.NoArgsConstructor;

/**
 * Classe utilizada para formatar as classes de dominio da aplicacao
 * para a classe que sera enviada para fora do core da aplicacao.
 *
 * Desta forma aqui sera tratada apenas a conversao do dominio para o DTO.
 */
@NoArgsConstructor
public class PedidoPresenter {
    private static final PedidoMapper MAPPER = PedidoMapper.INSTANCE;

    public PedidoDto toDto(Pedido pedido) {
        return MAPPER.toDto(pedido);
    }

    public List<PedidoDto> mapToDto(List<Pedido> list) {
        return MAPPER.mapToDto(list);
    }
}