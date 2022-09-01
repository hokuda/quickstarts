package com.redhat.example;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

public class RestrictHandler implements HttpHandler {
    private final String RESTRICTED_URI_PATTERN="/auth/realms/.+/login-actions/required-action.+CONFIGURE_TOTP.+";
    private final String ALLOWED_IPADDR_PATTERN="/127.0.0.1:.+";
    private HttpHandler next;

    public RestrictHandler(HttpHandler next) {
        this.next = next;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        String uri = exchange.getRequestURI() + "?" + exchange.getQueryString();
        String src = exchange.getSourceAddress().toString();

        System.err.println("handleRequest() is invoked. URI:" + uri + " peer:" + src);

        if (uri.matches(RESTRICTED_URI_PATTERN) & !src.matches(ALLOWED_IPADDR_PATTERN)) {
            System.err.println("denied access to " + uri + " from " + src);
            exchange.setStatusCode(403);
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
            exchange.getResponseSender().send("OTP registration is forbidden from outside of intranet");
            exchange.endExchange();
        }
        else next.handleRequest(exchange);
    }
}
