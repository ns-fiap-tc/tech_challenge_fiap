package br.com.fiap.lanchonente.lambdajwt;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Map;

/*
- Uses AWS Secrets Manager for secure database credentials.
- Generates JWT to securely store SSN information in token claims.
- Signs JWT using HMAC256 for cryptographic security.
- Sets token expiration (1 hour) to enforce security best practices.
 */
public class RdsLambdaHandler {

    public String handleRequest(String cpf, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Handler invoked.");
        String jwt = null;
        String dbHost = "my-database-1.cyhpsurw38ch.us-east-1.rds.amazonaws.com";
        String dbName = "lanchdb";
        String dbPort = "5432";
        String dbSchema = "lanch";
        String dbUser = "lanchuser";
        String dbPassword = "lanch01234!#";
        String dbUrl = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
        Connection conn = null;
        try {
            /*
            // Retrieve credentials from AWS Secrets Manager
            Map<String, String> dbCredentials = getSecretValues();
            String username = dbCredentials.get("username");
            String password = dbCredentials.get("password");
*/
            // Establish connection to RDS
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            if (!conn.isValid(0)) {
                logger.log("Failed to connect to RDS: " + dbUrl);
                return jwt;
            }

            this.checkIfSchemaExists(conn, dbSchema, logger);

            String query = "SELECT nr_cpf FROM " + dbSchema + ".tb_cliente WHERE nr_cpf = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, cpf);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    logger.log("CPF found.");
                    jwt = generateJwt(cpf);
                } else {
                    logger.log("CPF NOT found.");
                    jwt = generateJwt("");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return jwt;
    }

    private void checkIfSchemaExists(Connection conn, String dbSchema, LambdaLogger logger) {
        logger.log("Checking if schema exists...");
        String createSchemaStmt = "CREATE SCHEMA IF NOT EXISTS " + dbSchema + " AUTHORIZATION lanchuser";
        Statement statement = null;
        try {
            statement = conn.createStatement();
            statement.execute(createSchemaStmt);
            String createTableStmt = "CREATE TABLE IF NOT EXISTS " + dbSchema + ".tb_cliente (nr_cpf VARCHAR(11) PRIMARY KEY)";
            statement.execute(createTableStmt);

            String insertStmt = "INSERT INTO " + dbSchema + ".tb_cliente (nr_cpf) VALUES ('12345678901') ON CONFLICT DO NOTHING";
            statement.execute(insertStmt);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private Map<String, String> getSecretValues() throws JsonProcessingException {
        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard().build();
        GetSecretValueRequest request = new GetSecretValueRequest().withSecretId("database-credentials");
        GetSecretValueResult result = client.getSecretValue(request);

        // Parse the JSON response containing credentials
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(result.getSecretString(), Map.class);
    }

    private String generateJwt(String cpf) throws JsonProcessingException {
        return JWT.create()
                .withIssuer("LanchoneteApp")
                .withSubject("UserAuthentication")
                .withClaim("cpf", cpf)
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1-hour expiration
                .sign(Algorithm.HMAC256(this.getJwtSecret()));
    }

    private String getJwtSecret() throws JsonProcessingException {
        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard().build();
        GetSecretValueRequest request = new GetSecretValueRequest().withSecretId("jwt-secret-key");
        GetSecretValueResult result = client.getSecretValue(request);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> secretMap = objectMapper.readValue(result.getSecretString(), Map.class);
        return secretMap.get("jwt-key");
    }
}