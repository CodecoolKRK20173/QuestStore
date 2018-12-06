package com.codecool.app.httphandlers;

import com.codecool.app.cookies.CookieHelper;
import com.codecool.app.dao.DAOAccounts;
import com.codecool.app.login.AccessLevel;
import com.codecool.app.login.Account;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class LoginHandlerTest {

    private Account testAccount;
    private String testUsername = "testLogin";
    private String testPassword = "testPassword";
    private String loginPageAdress = "/";
    private String adminPageAdress = "/admin/profile";
    private String returnedRedirectLocation;
    private int returnedResponseCode;
    private CookieHelper cookieHelper;
    private LoginHandler loginHandler;
    private HttpExchange httpExchange;

    @Mock private DAOAccounts myDao;


//    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @BeforeEach
    void prepare() {
        MockitoAnnotations.initMocks(this);
        cookieHelper = new CookieHelper("sessionId");
        loginHandler = new LoginHandler(myDao, cookieHelper);
        testAccount = new Account();
        testAccount.setAccessLevel(AccessLevel.ADMIN);
        httpExchange = new HttpRequestCreator("POST", testPassword, testUsername);
    }

    @Test
    public void testIfAccountNotNull() throws IOException {
        when(myDao.getAccountByNicknameAndPassword(testUsername,testPassword)).thenReturn(testAccount);
        loginHandler.handle(httpExchange);
        assertNotNull(testAccount);
    }


    @Test
    public void testCookieWithGetAccountByNicknameAndPassword() throws IOException {
        when(myDao.getAccountByNicknameAndPassword(testUsername,testPassword)).thenReturn(testAccount);
        loginHandler.handle(httpExchange);
        assertTrue(httpExchange.getResponseHeaders().containsKey("Set-Cookie"));
    }

    @Test
    public void testSetID() throws IOException {
        when(myDao.getAccountByNicknameAndPassword(testUsername,testPassword)).thenReturn(testAccount);
        testAccount.setId(1);
        loginHandler.handle(httpExchange);
        Assertions.assertEquals(1, testAccount.getId());
    }

    @Test
    public void testCookiesWithUpdateAccount() throws IOException {
        String newUsername = "testLogin2";
        String newPassword = "testPassword2";
        Account secondUsername = new Account(newUsername, newPassword);
        when(myDao.getAccountByNicknameAndPassword(testUsername,testPassword)).thenReturn(testAccount);
        testAccount.setId(1);
        secondUsername.setAccessLevel(AccessLevel.ADMIN);
        myDao.updateAccount(1, secondUsername);
        loginHandler.handle(httpExchange);
        assertTrue(httpExchange.getResponseHeaders().containsKey("Set-Cookie"));
    }

    @Test
    public void testRedirectingToAdminPage() throws IOException {
        when(myDao.getAccountByNicknameAndPassword(testUsername,testPassword)).thenReturn(testAccount);
        loginHandler.handle(httpExchange);
        returnedRedirectLocation = httpExchange.getResponseHeaders().get("Location").get(0);
        Assertions.assertEquals(adminPageAdress, returnedRedirectLocation);
    }

}