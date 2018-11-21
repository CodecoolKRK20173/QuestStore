package com.codecool.app.httphandlers;

import com.codecool.app.cookies.CookieHelper;
import com.codecool.app.dao.DAOAccounts;
import com.codecool.app.login.AccessLevel;
import com.codecool.app.login.Account;
import com.codecool.app.messages.ErrorMessages;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class LoginHandlerTest {

    private LoginHandler loginHandler;
    private String loginPageAdress = "/";
    private String adminPageAdress = "/admin/profile";
    private int standardResponseCode = 200;
    private int redirectResponseCode = 303;
    private String redirectLocation;
    private int responseCode;
    private String testUsername = "tName";
    private String testPassword = "tPass";

    @Mock
    private DAOAccounts daoAccounts;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        CookieHelper cookieHelper = new CookieHelper("sessionId");
        loginHandler = new LoginHandler(daoAccounts, cookieHelper);
    }

    @Test
    void testHandleRequestPostShouldSetLocationHeaderToLoginPageOnWrongCredentials() throws IOException{
        when(daoAccounts.getAccountByNicknameAndPassword(testUsername,testPassword)).thenThrow(new NoSuchElementException(new ErrorMessages().getUSER_NOT_REGISTERED_MESSAGE()));
        HttpExchange httpExchange = new HttpExchangeHelper("POST", testPassword, testUsername);

        loginHandler.handle(httpExchange);
        prepareAssertParameters(httpExchange);

        assertAll(
                ()->assertEquals(loginPageAdress, redirectLocation),
                ()->assertEquals(redirectResponseCode, responseCode)
        );
    }


    @Test
    void testHandleRequestPostShouldSetLocationHeaderToAdminPageOnGoodCredentials() throws IOException{
        Account testAccount = new Account();
        testAccount.setAccessLevel(AccessLevel.ADMIN);
        when(daoAccounts.getAccountByNicknameAndPassword(testUsername,testPassword)).thenReturn(testAccount);
        HttpExchange httpExchange = new HttpExchangeHelper("POST", testPassword, testUsername);

        loginHandler.handle(httpExchange);
        prepareAssertParameters(httpExchange);

        assertAll(
                ()->assertEquals(adminPageAdress, redirectLocation),
                ()-> assertEquals(redirectResponseCode, responseCode)
        );
    }

    @Test
    void testHandleRequestGetShouldPassWith200ResponseCode() throws IOException{
        HttpExchange httpExchange = new HttpExchangeHelper("GET", testPassword, testUsername);

        loginHandler.handle(httpExchange);

        assertEquals(standardResponseCode, httpExchange.getResponseCode());
    }

    private void prepareAssertParameters(HttpExchange httpExchange) {
        redirectLocation = httpExchange.getResponseHeaders().get("Location").get(0);
        responseCode = httpExchange.getResponseCode();
    }
}