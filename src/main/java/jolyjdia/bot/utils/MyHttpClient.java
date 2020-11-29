package jolyjdia.bot.utils;

import jolyjdia.vk.sdk.client.TransportClient;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import static jolyjdia.bot.Bot.ASYNC_POOL;

public class MyHttpClient implements TransportClient {
    private final HttpClient httpClient = HttpClient.newBuilder()
            .executor(ASYNC_POOL)
            .version(HttpClient.Version.HTTP_2)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(20))
            .build();
    private static final String FORM_CONTENT_TYPE = "application/x-www-form-urlencoded";

    @Override
    public CompletableFuture<HttpResponse<String>> get(String url) {
        return get(url, FORM_CONTENT_TYPE);
    }
    @Override
    public CompletableFuture<HttpResponse<String>> get(String url, String contentType) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", contentType)
                .GET()
                .build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    @Override
    public CompletableFuture<HttpResponse<String>> post(String url, String body) {
       return post(url, body, FORM_CONTENT_TYPE);
    }
    @Override
    public CompletableFuture<HttpResponse<String>> post(String url, String body, String contentType) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", contentType)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    @Override
    public CompletableFuture<HttpResponse<String>> post(String url, String fileName, File file) {
        return null;
    }



    @Override
    public CompletableFuture<HttpResponse<String>> post(String url) {
        return post(url, null);
    }

    @Override
    public CompletableFuture<HttpResponse<String>> delete(String url) {
        return delete(url, FORM_CONTENT_TYPE);
    }

    @Override
    public CompletableFuture<HttpResponse<String>> delete(String url, String body) {
        return delete(url, null, FORM_CONTENT_TYPE);
    }

    @Override
    public CompletableFuture<HttpResponse<String>> delete(String url, String body, String contentType) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", contentType)
                .DELETE()
                .build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }
}
