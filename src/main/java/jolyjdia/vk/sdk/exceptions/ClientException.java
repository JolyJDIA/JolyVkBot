package jolyjdia.vk.sdk.exceptions;

public class ClientException extends RuntimeException {
    private static final long serialVersionUID = 77558875745745547L;

    public ClientException(String message) {
        super(message);
    }
}
