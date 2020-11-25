package jolyjdia.vk.sdk.client;

import jolyjdia.vk.sdk.callback.ClientResponse;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public interface TransportClient {//todo:

    CompletableFuture<ClientResponse> get(String url);

    CompletableFuture<ClientResponse> post(String url, String body);

    CompletableFuture<ClientResponse> post(String url, String fileName, File file);

    CompletableFuture<ClientResponse> post(String url, String body, String contentType);

    CompletableFuture<ClientResponse> get(String url, String contentType);

    CompletableFuture<ClientResponse> post(String url);

    CompletableFuture<ClientResponse> delete(String url);

    CompletableFuture<ClientResponse> delete(String url, String body);

    CompletableFuture<ClientResponse> delete(String url, String body, String contentType);
}
