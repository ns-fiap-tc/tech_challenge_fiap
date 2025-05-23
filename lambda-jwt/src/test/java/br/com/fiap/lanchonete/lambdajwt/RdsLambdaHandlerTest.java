package br.com.fiap.lanchonete.lambdajwt;

import br.com.fiap.lanchonente.lambdajwt.RdsLambdaHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RdsLambdaHandlerTest {

    @Mock
    private Context context;

    @Mock
    private LambdaLogger logger;

    @BeforeEach
    public void setup() {
        Mockito.when(context.getFunctionName()).thenReturn("handleRequest");
        Mockito.doAnswer(
                call -> {
                    System.out.println((String) call.getArgument(0));
                    return null;
                }
        ).when(logger).log(Mockito.anyString());

        Mockito.when(context.getLogger()).thenReturn(logger);
    }

    @Test
    public void testHandler() {
        RdsLambdaHandler handler = new RdsLambdaHandler();
        String result = handler.handleRequest("123456789", context);
        Assertions.assertEquals("", result);
    }
}
