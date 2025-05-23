package br.com.fiap.lanchonente.lambdajwt;

import java.util.HashMap;
import java.util.Map;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthFlowType;

public class CognitoAuthService {
    private static final String USER_POOL_ID = "your-user-pool-id";
    private static final String CLIENT_ID = "your-app-client-id";

    private final CognitoIdentityProviderClient cognitoClient;

    public CognitoAuthService() {
        this.cognitoClient = CognitoIdentityProviderClient.builder()
                .region(Region.US_EAST_1)
                .build();
    }

    public String authenticateUser(String cpf) {
        Map<String, String> authParams = new HashMap<>();
        authParams.put("USERNAME", cpf);

        AdminInitiateAuthRequest authRequest = AdminInitiateAuthRequest.builder()
                .userPoolId(USER_POOL_ID)
                .clientId(CLIENT_ID)
                .authFlow(AuthFlowType.CUSTOM_AUTH)
                .authParameters(authParams)
                .build();

        AdminInitiateAuthResponse authResponse = cognitoClient.adminInitiateAuth(authRequest);

        return authResponse.authenticationResult().idToken(); // Cognito-issued JWT
    }
}
