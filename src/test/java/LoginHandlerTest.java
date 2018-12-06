import com.codecool.app.cookies.CookieHelper;
import com.codecool.app.dao.DAOAccounts;
import com.codecool.app.httphandlers.LoginHandler;
import com.codecool.app.login.AccessLevel;
import com.codecool.app.login.Account;
import com.codecool.app.messages.ErrorMessages;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    private Account testAccount;
    private HttpExchangeHelper postingHelper;
    private CookieHelper cookieHelper;

    @Mock
    private DAOAccounts daoAccounts;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        cookieHelper = new CookieHelper("sessionId");
        loginHandler = new LoginHandler(daoAccounts, cookieHelper);
        postingHelper = new HttpExchangeHelper("POST", testPassword, testUsername);
        testAccount = new Account();
        testAccount.setAccessLevel(AccessLevel.ADMIN);
    }

    @Test
    void testHandlePostRequestShouldAddCookieToResponseHeaderOnGoodCredentials() throws IOException{
        when(daoAccounts.getAccountByNicknameAndPassword(testUsername,testPassword)).thenReturn(testAccount);

        loginHandler.handle(postingHelper);

        assertTrue(postingHelper.getResponseHeaders().containsKey("Set-Cookie"));
    }

    @Test
    void testHandlePostRequestShouldRedirectToLoginPageEmptyAccountFromDataBase() throws IOException{
        when(daoAccounts.getAccountByNicknameAndPassword(testUsername,testPassword)).thenReturn(new Account());

        loginHandler.handle(postingHelper);
        prepareAssertParameters(postingHelper);

        assertAll(
                ()->assertEquals(loginPageAdress, returnedRedirectLocation),
                ()-> assertEquals(redirectResponseCode, returnedResponseCode)
        );

    }


    @Test
    void testHandlePostRequestShouldSetLocationHeaderToAdminPageOnGoodCredentials() throws IOException{
        when(daoAccounts.getAccountByNicknameAndPassword(testUsername,testPassword)).thenReturn(testAccount);

        loginHandler.handle(postingHelper);
        prepareAssertParameters(postingHelper);

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