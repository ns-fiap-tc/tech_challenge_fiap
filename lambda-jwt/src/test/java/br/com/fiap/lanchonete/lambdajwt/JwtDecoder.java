package br.com.fiap.lanchonente.lambdajwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

public class JwtDecoder {

    public static final String TOKEN_REF = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJMYW5jaG9uZXRlQXBwIiwic3ViIjoiVXNlckF1dGhlbnRpY2F0aW9uIiwiY3BmIjoiMTIzNDU2Nzg5IiwiZXhwIjoxNzQ2OTI0ODc5fQ.toFe9qCHojy9IVm4IKcO-f-HTbST1oGTis2U9uvgUAU";
    public static final String JWT_SECRET = "iiJOalPElFV2eKu2kMojY/MmiaQBNU3X/NaiuwIySpc="; // Ideally retrieved from AWS Secrets Manager

    public static String extractCpf(String token) {
        try {
            // Define JWT verifier
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWT_SECRET))
                    .withIssuer("LanchoneteApp")
                    .build();

            // Decode & verify token
            DecodedJWT jwt = verifier.verify(token);

            // Extract SSN claim
            return jwt.getClaim("cpf").asString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Invalid Token";
        }
    }

    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJMYW5jaG9uZXRlQXBwIiwic3ViIjoiVXNlckF1dGhlbnRpY2F0aW9uIiwiY3BmIjoiMTIzNDU2Nzg5IiwiZXhwIjoxNzQ2OTI0ODc5fQ.toFe9qCHojy9IVm4IKcO-f-HTbST1oGTis2U9uvgUAU"; // Replace with actual token
        System.out.println("Extracted CPF: " + extractCpf(token));
    }
}
