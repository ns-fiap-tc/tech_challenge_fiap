package br.com.fiap.lanchonete.application.device.rest.filter;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * FilterCHain utilizado para identificar se o JWT nao expirou (é valido) e se contem o CPF a ser utilizado para identificar
 * o cliente no pedido.
 */
public class JwtTokenFilter extends OncePerRequestFilter
{
    //private static final String JWT_SECRET = "f6UZe3w8OacPa9ccturVD4RJLSwClGqrvjwEr7Tbv2E="; // Ideally retrieved from AWS Secrets Manager

    private final ObjectMapper mapper;
    private String jwtSecret;

    public JwtTokenFilter(ObjectMapper mapper) {
        this.mapper = mapper;
        try {
            this.jwtSecret = this.getJwtSecret();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(this.jwtSecret))
                    .withIssuer("LanchoneteApp")
                    .build();

            // Decode & verify token
            String jwtToken = authHeader.substring(7); // Remove "Bearer " prefix

            DecodedJWT jwt = null;
            String cpf = null;

            try {
                jwt = verifier.verify(jwtToken);

                // Extract CPF claim
                cpf = jwt.getClaim("cpf").asString();
            } catch (Exception ex) {
                this.mountResponseError(request, response, ex.getMessage());
                return;
            }

            if (StringUtils.isNotBlank(cpf)) {
                RequestContext.setCurrentToken(cpf);
            }
        } else {
            this.mountResponseError(request, response, "Authorization token not found.");
            return;
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Metodo utilizada para montar o objeto de resposta em caso de falha.
     * @param request
     * @param response
     * @param erropMessage
     * @throws IOException
     */
    private void mountResponseError(HttpServletRequest request, HttpServletResponse response, String erropMessage) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(
                mapper.writer()
                        .with(SerializationFeature.INDENT_OUTPUT)
                        .writeValueAsString(
                                ResponseErrorDto.builder()
                                        .status(HttpStatus.UNAUTHORIZED.value())
                                        .error(erropMessage)
                                        .path(request.getServletPath())
                                        .timestamp(new Date())
                                        .build()
                        )
        );
    }

    /**
     * Metodo que busca o token do secrets manager.
     * @return
     * @throws JsonProcessingException
     */
    private String getJwtSecret() throws JsonProcessingException {
        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard().build();
        GetSecretValueRequest request = new GetSecretValueRequest().withSecretId("jwt-secret-key");
        GetSecretValueResult result = client.getSecretValue(request);
        Map<String, String> secretMap = this.mapper.readValue(result.getSecretString(), Map.class);
        return secretMap.get("jwt-key");
    }
}
