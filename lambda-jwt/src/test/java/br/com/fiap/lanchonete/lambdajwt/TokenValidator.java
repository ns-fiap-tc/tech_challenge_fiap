package br.com.fiap.lanchonente.lambdajwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;

public class TokenValidator {
    private static final String SECRET_KEY = "your-secret-key"; // Consider fetching from AWS Secrets Manager

    public static String extractCpf(String token) {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                    .build()
                    .verify(token);

            // Check expiration time
            Date expiration = jwt.getExpiresAt();
            if (expiration != null && expiration.before(new Date())) {
                System.err.println("Token has expired.");
                return null;
            }

            // Retrieve CPF claim from the token
            return jwt.getClaim("cpf").asString();
        } catch (Exception e) {
            System.err.println("Token validation failed: " + e.getMessage());
            return null;
        }
    }
}
