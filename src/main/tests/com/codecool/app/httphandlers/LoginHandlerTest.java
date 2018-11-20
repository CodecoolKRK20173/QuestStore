package com.codecool.app.httphandlers;

import com.codecool.app.cookies.CookieHelper;
import com.codecool.app.dao.DAOAccounts;
import com.codecool.app.dao.SQLImpl.DAOAccountsSQL;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginHandlerTest {



    @Test
    void testHandleRequestGetShouldPassWith200ResponseCode() throws IOException{
        DAOAccounts daoAccounts = null;
        CookieHelper cookieHelper = new CookieHelper("sessionId");
        LoginHandler loginHandler = new LoginHandler(daoAccounts, cookieHelper);
        HttpExchange httpExchange = new HttpExchange() {
            Integer responseCode = 0;
            @Override
            public Headers getRequestHeaders() {
                return null;
            }

            @Override
            public Headers getResponseHeaders() {
                return null;
            }

            @Override
            public URI getRequestURI() {
                return null;
            }

            @Override
            public String getRequestMethod() {
                return "GET";
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
                return null;
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
        };
        loginHandler.handle(httpExchange);
        assertEquals(200, httpExchange.getResponseCode());
    }
}