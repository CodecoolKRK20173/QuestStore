

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

public class HttpExchangeHelper extends HttpExchange {
    private Integer responseCode;
    private Headers headers;
    private String requestMethod;
    private String password;
    private String login;

    HttpExchangeHelper(String requestMethod, String password, String login) {
        this.requestMethod = requestMethod;
        this.password = password;
        this.login = login;
        this.headers = new Headers();
    }

    @Override
    public Headers getRequestHeaders() {
        return null;
    }

    @Override
    public Headers getResponseHeaders() {
        return headers;
    }

    @Override
    public URI getRequestURI() {
        return null;
    }

    @Override
    public String getRequestMethod() {
        return requestMethod;
    }

    @Override
    public HttpContext getHttpContext() {
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public InputStream getRequestBody() {
        return new ByteArrayInputStream(String.format("nick=%s&password=%s", login, password).getBytes());
    }

    @Override
    public OutputStream getResponseBody() {
        return new OutputStream() {
            @Override
            public void write(int b) throws IOException {
            }
        };
    }

    @Override
    public void sendResponseHeaders(int i, long l) throws IOException {
        this.responseCode = i;
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return null;
    }

    @Override
    public int getResponseCode() {
        return responseCode;
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return null;
    }

    @Override
    public String getProtocol() {
        return null;
    }

    @Override
    public Object getAttribute(String s) {
        return null;
    }

    @Override
    public void setAttribute(String s, Object o) {

    }

    @Override
    public void setStreams(InputStream inputStream, OutputStream outputStream) {

    }

    @Override
    public HttpPrincipal getPrincipal() {
        return null;
    }
}
