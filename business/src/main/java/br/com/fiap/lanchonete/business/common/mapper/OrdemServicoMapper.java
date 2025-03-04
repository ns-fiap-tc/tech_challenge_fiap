package br.com.fiap.lanchonete.business.common.mapper;

import br.com.fiap.lanchonete.business.common.dto.OrdemServicoDto;
import br.com.fiap.lanchonete.business.core.domain.OrdemServico;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrdemServicoMapper {
    OrdemServicoMapper INSTANCE = Mappers.getMapper(OrdemServicoMapper.class);

    OrdemServico toDomain(OrdemServicoDto dto);
    OrdemServicoDto toDto(OrdemServico ordemServico);
    List<OrdemServicoDto> mapToDto(List<OrdemServico> list);
    List<OrdemServico> mapToDomain(List<OrdemServicoDto> list);
}