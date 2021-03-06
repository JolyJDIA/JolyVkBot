package jolyjdia.vk.sdk.client;

import jolyjdia.bot.utils.StringBind;
import jolyjdia.vk.sdk.actors.Actor;
import jolyjdia.vk.sdk.objects.enums.EnumParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class AbstractQueryBuilder<T> extends ApiRequest<T> {//final class
    private final Map<String, String> params = new HashMap<>();
    private final String method;

    public AbstractQueryBuilder(VkApiClient client, String method, Actor actor) {
        super(client.getApiEndpoint() + method, client.getTransportClient());
        this.method = method;
        version(VkApiClient.getVersion());
        accessToken(actor.getAccessToken());
    }
    public AbstractQueryBuilder(VkApiClient client, String method) {
        super(client.getApiEndpoint() + method, client.getTransportClient());
        this.method = method;
        version(VkApiClient.getVersion());
    }
    protected void version(String value) {
        unsafeParam("v", value);
    }
    protected void accessToken(String value) {
        unsafeParam("access_token", value);
    }
    public void unsafeParam(String key, String value) {
        params.put(key, value);
    }

    public void unsafeParam(String key, int value) {
        unsafeParam(key, Integer.toString(value));
    }

    public void unsafeParam(String key, boolean value) {
        unsafeParam(key, boolAsParam(value));
    }

    public void unsafeParam(String key, Collection<?> value) {
        unsafeParam(key, StringBind.toArray(value));
    }

    @SafeVarargs
    public final <U> void unsafeParam(String key, U... value) {
        unsafeParam(key, StringBind.toArray(value));
    }

    public void unsafeParam(String key, int[] value) {
        unsafeParam(key, StringBind.toArray(value));
    }

    public void unsafeParam(String key, double value) {
        unsafeParam(key, Double.toString(value));
    }

    public void unsafeParam(String key, float value) {
        unsafeParam(key, Float.toString(value));
    }

    public void unsafeParam(String key, EnumParam value) {
        unsafeParam(key, value.getValue());
    }

    public void unsafeParam(String key, EnumParam... fields) {
        unsafeParam(key, StringBind.toArrayEnum(fields));
    }

    public void unsafeParam(String key, List<EnumParam> fields) {
        unsafeParam(key, StringBind.toArrayEnum(fields));
    }
    public Map<String, String> build() {
        return Collections.unmodifiableMap(params);
    }

    public String getMethod() {
        return method;
    }
    @Override
    protected String getBody() {
        return mapToGetString(params);
    }
    private static String mapToGetString(Map<String, String> params) {
        return params.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + (entry.getValue() != null ? escape(entry.getValue()) : ""))
                .collect(Collectors.joining("&"));
    }
    private static String escape(String data) {
        return URLEncoder.encode(data, StandardCharsets.UTF_8);
    }
    private static String boolAsParam(boolean param) {
        return param ? "1" : "0";
    }
}
