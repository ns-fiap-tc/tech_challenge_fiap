package br.com.fiap.lanchonete.adapter.output.persistence.mapper;

import br.com.fiap.lanchonete.adapter.output.persistence.entity.PagamentoEntity;
import br.com.fiap.lanchonete.domain.model.Pagamento;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PagamentoMapper {
    PagamentoMapper INSTANCE = Mappers.getMapper(PagamentoMapper.class);

    Pagamento toDomain(PagamentoEntity entity);
    PagamentoEntity toEntity(Pagamento pagamento);
    List<Pagamento> map(List<PagamentoEntity> entities);
}
