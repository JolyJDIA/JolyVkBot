package jolyjdia.vk.sdk.client;

import java.io.File;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public interface TransportClient {//todo:

    CompletableFuture<HttpResponse<String>> get(String url);

    CompletableFuture<HttpResponse<String>> post(String url, String body);

    CompletableFuture<HttpResponse<String>> post(String url, String fileName, File file);

    CompletableFuture<HttpResponse<String>> post(String url, String body, String contentType);

    CompletableFuture<HttpResponse<String>> get(String url, String contentType);

    CompletableFuture<HttpResponse<String>> post(String url);

    CompletableFuture<HttpResponse<String>> delete(String url);

    CompletableFuture<HttpResponse<String>> delete(String url, String body);

    CompletableFuture<HttpResponse<String>> delete(String url, String body, String contentType);
}
