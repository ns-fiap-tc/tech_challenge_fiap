package br.com.fiap.lanchonente.lambdajwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class CognitoTokenValidator {
    private static final String COGNITO_JWKS_URL = "https://cognito-idp.<region>.amazonaws.com/<user-pool-id>/.well-known/jwks.json";

    public static boolean verifyToken(String token) {
        try {
            Map<String, Object> jwks = new ObjectMapper().readValue(new URL(COGNITO_JWKS_URL), Map.class);
            Map<String, Object> keyMap = (Map<String, Object>) jwks.get("keys");

            DecodedJWT jwt = JWT.decode(token);
            Algorithm algorithm = null; //Algorithm.RSA256(null, jwt.getKeyId());
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("https://cognito-idp.<region>.amazonaws.com/<user-pool-id>")
                    .build();
            verifier.verify(token);

            return true;
        } catch (JWTVerificationException | IOException e) {
            e.printStackTrace();
            return false;
        /*} catch (StreamReadException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
         */
        }
    }
}
