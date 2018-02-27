package com.gregspitz.flashcardserver.httphandlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * A base for all HTTP Handlers which will return respond with JSON
 * @param <T> the type of response
 */
public abstract class JsonBaseHttpHandler<T> implements HttpHandler {
    private static final Charset CHARSET = StandardCharsets.UTF_8;
    private static final Gson gson = new Gson();

    private String mJsonResponse;
    private HttpExchange mHttpExchange;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        mHttpExchange = httpExchange;
        logReceivedRequest();
        mJsonResponse = gson.toJson(createResponse(httpExchange));
        sendResponse();
    }

    protected abstract T createResponse(HttpExchange httpExchange);

    private void logReceivedRequest() {
        System.out.println("Request received: " + mHttpExchange.getRequestMethod() +
                " " + mHttpExchange.getRemoteAddress());
    }

    private void sendResponse() throws IOException{
        final byte[] jsonResponseBytes = mJsonResponse.getBytes(CHARSET);
        Headers headers = mHttpExchange.getResponseHeaders();
        headers.set("Content-Type", "application/json; charset=" + CHARSET);
        mHttpExchange.sendResponseHeaders(200, jsonResponseBytes.length);
        OutputStream os = mHttpExchange.getResponseBody();
        os.write(jsonResponseBytes);
        os.close();
    }

    public static Charset getCharset() {
        return CHARSET;
    }
}
