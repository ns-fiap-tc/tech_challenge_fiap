package br.com.fiap.lanchonete.application.device.rest.filter;

/**
 * Classe utilizada para transportar o CPF do cliente que se identificou para a classe PedidoApi, utilizando o classe ThreadLocal
 * para garantir que o objeto seja transportado na Thread correta.
 */
public class RequestContext {
    private static final ThreadLocal<String> currentToken = new ThreadLocal<>();

    public static String getCurrentToken() {
        return currentToken.get();
    }

    public static void setCurrentToken(String token) {
        currentToken.set(token);
    }

    public static void clear() {
        currentToken.remove();
    }
}
