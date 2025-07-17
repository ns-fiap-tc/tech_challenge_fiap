package br.com.fiap.lanchonete.infrastructure.config;

import br.com.fiap.lanchonete.application.device.rest.filter.JwtTokenFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
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
    @Value("${jwt.key.value}")
    private String jwtKeyValue;

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

    /**
     * Filtro utilizado para validar o JWT se existe nas requisicoes, se eh valido e se para as requisicoes da PedidoApi
     * existe o cpf para ser utilizado na criacao do pedido.
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean jwtTokenFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setName("jwtTokenFilter");
        registrationBean.setFilter(new JwtTokenFilter(new ObjectMapper(),this.jwtKeyValue));
        registrationBean.addUrlPatterns("/pedido-service/v1/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}