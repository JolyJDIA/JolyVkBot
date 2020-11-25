package jolyjdia.vk.sdk.callback;

public class ClientResponse {
    private final int statusCode;
    private final String content;

    public ClientResponse(int statusCode, String content) {
        this.statusCode = statusCode;
        this.content = content;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getContent() {
        return content;
    }

}
