package com.codecool.app.httphandlers;

import com.codecool.app.cookies.CookieHelper;
import com.codecool.app.dao.DAOAccounts;
import com.codecool.app.login.AccessLevel;
import com.codecool.app.login.Account;
import com.codecool.app.messages.ErrorMessages;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class LoginHandlerTest {

    private LoginHandler loginHandler;
    private String loginPageAdress = "/";
    private String adminPageAdress = "/admin/profile";
    private int standardResponseCode = 200;
    private int redirectResponseCode = 303;
    private String returnedRedirectLocation;
    private int returnedResponseCode;
    private String testUsername = "tName";
    private String testPassword = "tPass";

    @Mock
    private DAOAccounts daoAccounts;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        CookieHelper cookieHelper = new CookieHelper("sessionId");
        loginHandler = new LoginHandler(daoAccounts, cookieHelper);
    }

    @Test
    void testHandlePostRequestShouldPassOnRetrievingEmptyAccountFromDataBase() throws IOException{
        when(daoAccounts.getAccountByNicknameAndPassword(testUsername,testPassword)).thenReturn(new Account());
        HttpExchange httpExchange = new HttpExchangeHelper("POST", testPassword, testUsername);

        loginHandler.handle(httpExchange);

        assertTrue(httpExchange.getResponseHeaders().containsKey("Location"));
    }

    @Test
    void testHandleRequestPostShouldSetLocationHeaderToLoginPageOnWrongCredentials() throws IOException{
        when(daoAccounts.getAccountByNicknameAndPassword(testUsername,testPassword)).thenThrow(new NoSuchElementException(new ErrorMessages().getUSER_NOT_REGISTERED_MESSAGE()));
        HttpExchange httpExchange = new HttpExchangeHelper("POST", testPassword, testUsername);

        loginHandler.handle(httpExchange);
        prepareAssertParameters(httpExchange);

        assertAll(
                ()->assertEquals(loginPageAdress, returnedRedirectLocation),
                ()->assertEquals(redirectResponseCode, returnedResponseCode)
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
                ()->assertEquals(adminPageAdress, returnedRedirectLocation),
                ()-> assertEquals(redirectResponseCode, returnedResponseCode)
        );
    }

    @Test
    void testHandleRequestGetShouldPassWith200ResponseCode() throws IOException{
        HttpExchange httpExchange = new HttpExchangeHelper("GET", testPassword, testUsername);

        loginHandler.handle(httpExchange);

        assertEquals(standardResponseCode, httpExchange.getResponseCode());
    }

    private void prepareAssertParameters(HttpExchange httpExchange) {
        returnedRedirectLocation = httpExchange.getResponseHeaders().get("Location").get(0);
        returnedResponseCode = httpExchange.getResponseCode();
    }
}