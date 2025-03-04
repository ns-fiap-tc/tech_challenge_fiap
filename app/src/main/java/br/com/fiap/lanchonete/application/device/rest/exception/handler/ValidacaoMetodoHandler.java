package br.com.fiap.lanchonete.application.device.rest.exception.handler;

import br.com.fiap.lanchonete.business.common.dto.ErroDto;
import br.com.fiap.lanchonete.business.common.dto.ErrosDto;
import br.com.fiap.lanchonete.business.common.dto.ValidacaoDto;
import br.com.fiap.lanchonete.business.core.domain.ValidacaoEnum;
import br.com.fiap.lanchonete.infrastructure.utils.MensagensUtils;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ValidacaoMetodoHandler {
    private final String caminhoCampoProperties = ".campo.generico";
    private final char separatorNodes = '.';

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler({BindException.class})
    public ErrosDto bindException(BindException ex){
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        List<ValidacaoDto> listValidationsDTO = new ArrayList<>();

        if(fieldErrors.isEmpty()){
            result.getAllErrors().forEach(
                    objectError -> {
                        ValidacaoDto validacaoDto = new ValidacaoDto();
                        validacaoDto.setCodes(objectError.getCodes());
                        validacaoDto.setField(objectError.getObjectName());
                        listValidationsDTO.add(validacaoDto);
                    }
            );
            return processaCamposErros(listValidationsDTO,ex);
        }

        fieldErrors.forEach(
                objectError -> {
                    ValidacaoDto validacaoDto = new ValidacaoDto();
                    validacaoDto.setCodes(objectError.getCodes());
                    validacaoDto.setField(objectError.getObjectName());
                    listValidationsDTO.add(validacaoDto);
                }
        );
        return processaCamposErros(listValidationsDTO,ex);
    }

    private ErrosDto processaCamposErros(List<ValidacaoDto> listValidacaoDto, BindException ex){
        List<ErroDto> erros = new ArrayList<>();

        final String caminhoObjetoProperties = ".objeto.generico";
        final String caminhoListaProperties = ".lista.generico";
        final int qtdeNodesParaObj = 2;
        final String identificadorListas = "[";

        for (ValidacaoDto validacaoDto : listValidacaoDto){
            String caminhoCampo = validacaoDto.getCodes()[0].substring(validacaoDto.getCodes()[0].lastIndexOf(separatorNodes));
            String nomeCampo = caminhoCampo.substring(1,caminhoCampo.length());

            boolean possuiLista = validacaoDto.getCodes()[0].contains(identificadorListas);
            boolean possuiObjetoEmCascata = validacaoDto.getCodes()[0].chars().filter(ch-> ch == separatorNodes).count() > qtdeNodesParaObj;

            if(possuiObjetoEmCascata && !possuiLista){
                preencherErro(erros, validacaoDto,caminhoCampo,nomeCampo,caminhoObjetoProperties,ex);
                continue;
            }

            if(possuiLista){
                preencherErro(erros, validacaoDto,caminhoCampo,nomeCampo,caminhoListaProperties,ex);
            }

            ErroDto erroDto = new ErroDto();
            erroDto.setCodigo(ValidacaoEnum.ENTRADA_DE_DADOS_INVALIDA.getCodigo());
            String mensagemProperties = obterMensagem(validacaoDto.getCodes()[0], caminhoCampoProperties,caminhoCampo);
            adicionarErro(erros, erroDto,String.format(mensagemProperties,nomeCampo),ex);
        }
        return ErrosDto.builder().erros(erros).build();
    }

    private void preencherErro(List<ErroDto> erros, ValidacaoDto validacao, String caminhoCampo, String nomeCampo, String caminhoProperties, BindException ex){
        ErroDto erro = new ErroDto();
        erro.setCodigo(ValidacaoEnum.ENTRADA_DE_DADOS_INVALIDA.getCodigo());

        String caminhoCampoCompleto = validacao.getField().replaceAll("[^A-Za-z.]","");
        int posicaoInicial = caminhoCampoCompleto.lastIndexOf(separatorNodes);
        String nomeGrupo = posicaoInicial != -1
                ? caminhoCampoCompleto.substring(0,posicaoInicial)
                : caminhoCampoCompleto;

        nomeCampo = nomeCampo.replace("[]","");
        nomeGrupo = nomeGrupo.replace("."," > ");

        String mensagemListaProperties;

        if(nomeCampo.equals(nomeGrupo)) caminhoProperties = ".campo.generico";

        mensagemListaProperties = obterMensagem(validacao.getCodes()[0],caminhoProperties,caminhoCampo);

        adicionarErro(erros,erro,String.format(mensagemListaProperties,nomeCampo,nomeGrupo),ex);
    }

    private String obterMensagem(String fieldErro, String caminhoProperties, String caminhoCampo){
        String parametroProperties = fieldErro.substring(0,fieldErro.indexOf(separatorNodes)) + caminhoProperties;
        String mensagemProperties = MensagensUtils.getMessage(parametroProperties);
        String mensagemPropertiesCompleto = MensagensUtils.getMessage(fieldErro);

        if(parametroProperties.equals(mensagemProperties) && mensagemPropertiesCompleto.equals(fieldErro)){
            return MensagensUtils.getMessage(fieldErro.substring(0,fieldErro.indexOf(separatorNodes))+caminhoCampo);
        }

        if(!parametroProperties.equals(mensagemProperties)) return mensagemProperties;

        return mensagemPropertiesCompleto;
    }

    private void adicionarErro(List<ErroDto> erros, ErroDto novoErro, String mensagem, BindException ex){
        novoErro.setMensagem(mensagem);
        String mensagemErroLog = String.format("Ocorreu uma validação nos campos de entrada de uma requisição: %s " +
                "Mensagem Original %s: ",novoErro.getMensagem(),ex.getMessage());
        log.warn(mensagemErroLog);
        erros.add(novoErro);
    }
}