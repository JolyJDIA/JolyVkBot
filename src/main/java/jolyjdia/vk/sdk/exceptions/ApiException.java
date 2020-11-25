package jolyjdia.vk.sdk.exceptions;

public class ApiException extends RuntimeException {
    private static final long serialVersionUID = 1118726129966345334L;

    private final String description;
    private final String message;
    private final int code;

    public ApiException(int code, String description, String message) {
        this.description = description;
        this.code = code;
        this.message = message;
    }

    public ApiException(int code, String message) {
        this(code, "Unknown", message);
    }

    public ApiException(String message) {
        this(0, "Unknown", message);
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return description + " (" + code + "): " + message;
    }
}
