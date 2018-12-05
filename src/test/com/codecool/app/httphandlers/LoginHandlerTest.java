package com.codecool.app.httphandlers;

import com.codecool.app.cookies.CookieHelper;
import com.codecool.app.dao.DAOAccounts;
import com.codecool.app.login.AccessLevel;
import com.codecool.app.login.Account;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

import static junit.framework.TestCase.assertTrue;

public class LoginHandlerTest {

    private Account testAccount;
    private String testUsername = "testName";
    private String testPassword = "testPass";
    private String loginPageAdress = "/";
    private String adminPageAdress = "/admin";
    private int standardResponseCode = 200;
    private int redirectResponseCode = 303;
    private String returnedRedirectLocation;
    private int returnedResponseCode;
    private CookieHelper cookieHelper;
    private LoginHandler loginHandler;
    private HttpExchange httpExchange;

    @Mock private DAOAccounts myDao;


//    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void testConnection() throws IOException {
        MockitoAnnotations.initMocks(this);
        cookieHelper = new CookieHelper("sessionId");
        loginHandler = new LoginHandler(myDao, cookieHelper);
        testAccount = new Account();
        testAccount.setAccessLevel(AccessLevel.ADMIN);
        httpExchange = new HttpRequestCreator("POST", testPassword, testUsername);
        loginHandler.handle(httpExchange);
        assertTrue(httpExchange.getResponseHeaders().containsKey("Set-Cookie"));

    }

}