package br.com.fiap.lanchonete.infrastructure.utils;

import jakarta.annotation.PostConstruct;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MensagensUtils {
    private static MessageSource MESSAGE_SOURCE;

    @Autowired private MessageSource resourceBundle;

    @PostConstruct
    public void init(){
        MESSAGE_SOURCE = resourceBundle;
    }

    public String getMessage(String key,Object... args){
        Locale locale = LocaleContextHolder.getLocale();
        return resourceBundle.getMessage(key,args,locale);
    }

    public static String getMessage(String key){
        Locale locale = LocaleContextHolder.getLocale();
        return MESSAGE_SOURCE.getMessage(key,null,locale);
    }

    public static <E extends Enum<E>> String getEnumLabel(IEnumLabel<E> e ,String... params){
        Locale locale = LocaleContextHolder.getLocale();
        String messageKey = "enum."+e.getClass().getSimpleName()+ "." + ((Enum) e).name();
        return MESSAGE_SOURCE.getMessage(messageKey,params,locale);
    }

    public static <E extends Enum<E>> String getEnumLabel(IEnumLabel<E> e){
        Locale locale = LocaleContextHolder.getLocale();
        String messageKey = "enum."+e.getClass().getSimpleName()+ "." + ((Enum) e).name();
        return MESSAGE_SOURCE.getMessage(messageKey,null,locale);
    }
}