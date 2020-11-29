package jolyjdia.vk.sdk.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jolyjdia.bot.utils.StringBind;
import jolyjdia.vk.sdk.exceptions.ClientException;
import jolyjdia.vk.sdk.exceptions.ExceptionMapper;
import jolyjdia.vk.sdk.objects.base.Error;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;

public abstract class ApiRequest<T> {
    private final TransportClient client;
    private final String url;
    private final Type type;

    protected ApiRequest(String url, TransportClient client) {
        this.client = client;
        this.url = url;
        ParameterizedType superclass = ((ParameterizedType) this.getClass().getGenericSuperclass());
        if (superclass == null) {
            throw new InternalError();
        }
        Type[] types = superclass.getActualTypeArguments();
        if (types.length == 0) {
            throw new InternalError();
        }
        this.type = types[0];
    }
    public CompletableFuture<T> execute() {
        return executeAsString().thenApply(textResponse -> {
            JsonObject json = JsonParser.parseString(textResponse).getAsJsonObject();
            if (json.has("error")) {
                JsonElement errorElement = json.get("error");
                Error error = StringBind.GSON.fromJson(errorElement, Error.class);
                if (error != null) {
                    throw ExceptionMapper.parseException(error);
                }
            }

            JsonElement response = json;
            if (json.has("response")) {
                response = json.get("response");
            }

            return StringBind.GSON.fromJson(response, type);
        });
    }

    public CompletableFuture<String> executeAsString() {
        return client.post(url, getBody()).thenApply(r -> {
            int code; String body = r.body();
            if ((code = r.statusCode()) != 200) {
                throw new ClientException("Internal API server error. Wrong status code: " + code + ". Content: " + body);
            }
            if (!r.headers().map().containsKey("content-type")) {
                throw new ClientException("No content type header");
            }
            return body;
        });
    }

    protected abstract String getBody();
}