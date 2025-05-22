package br.com.fiap.lanchonente.lambdajwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

//Criar o Secret.
public class SecretGenerator {
    public static void main(String[] args) {
        byte[] key = new byte[32]; // 256-bit key
        new SecureRandom().nextBytes(key);
        String jwtSecret = Base64.getEncoder().encodeToString(key);
        System.out.println("Generated JWT Secret: " + jwtSecret);

        //criar o token
        String token = JWT.create()
                .withIssuer("LanchoneteApp")
                .withSubject("UserAuthentication")
                .withClaim("cpf", "123456789012")
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1-hour expiration
                .sign(Algorithm.HMAC256(jwtSecret));

        System.out.println("Token: " + token);
    }
}
