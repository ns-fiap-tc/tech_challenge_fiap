package br.com.fiap.lanchonete.infrastructure.utils;

import jakarta.annotation.PostConstruct;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MensagensUtils {
    private static MessageSource messageSource;

    private final MessageSource resourceBundle;

    @PostConstruct
    public void init(){
        messageSource = resourceBundle;
    }

    public String getMessage(String key,Object... args){
        Locale locale = LocaleContextHolder.getLocale();
        return resourceBundle.getMessage(key,args,locale);
    }

    public static String getMessage(String key){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key,null,locale);
    }

    public static <E extends Enum<E>> String getEnumLabel(IEnumLabel<E> e ,String... params){
        Locale locale = LocaleContextHolder.getLocale();
        String messageKey = "enum."+e.getClass().getSimpleName()+ "." + ((Enum) e).name();
        return messageSource.getMessage(messageKey,params,locale);
    }

    public static <E extends Enum<E>> String getEnumLabel(IEnumLabel<E> e){
        Locale locale = LocaleContextHolder.getLocale();
        String messageKey = "enum."+e.getClass().getSimpleName()+ "." + ((Enum) e).name();
        return messageSource.getMessage(messageKey,null,locale);
    }
}