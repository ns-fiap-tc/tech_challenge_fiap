package br.com.fiap.pagamentomock.infrastructure.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

@Configuration
public class WebConfig {
    private static final String CHARSET_ENCODING = "UTF-8";

    @Bean
    public LocaleResolver localeResolver(){
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(StringUtils.parseLocaleString("pt_BR"));
        return cookieLocaleResolver;
    }

    @Bean
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:msgs/mensagens");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding(CHARSET_ENCODING);
        messageSource.setCacheSeconds(0);
        return messageSource;
    }
}