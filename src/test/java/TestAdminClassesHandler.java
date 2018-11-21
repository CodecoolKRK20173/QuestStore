import com.codecool.app.cookies.CookieHelper;
import com.codecool.app.dao.AppDAOs;
import com.codecool.app.dao.DAOAccounts;
import com.codecool.app.dao.DAOClasses;
import com.codecool.app.dao.SQLImpl.AppDAOsSQL;
import com.codecool.app.httphandlers.admin.AdminClassesHandler;
import com.codecool.app.login.Account;
import com.codecool.app.user.model.Class;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpPrincipal;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class TestAdminClassesHandler {
    private CookieHelper cookieHelperMock;
    private Headers headers;
    private String sessionId;
    private HttpCookie httpCookie;
    private Optional<HttpCookie> optionalHttpCookie;
    private Account account;
    private DAOAccounts daoAccounts;
    private HttpExchange httpExchangeMock;
    private List<Class> listClass;
    private AppDAOs appDAOsMock;
    private Class classRoom;
    private DAOClasses daoClasses;

    @BeforeEach
    public void initializingBeforeTesting() {
        createMocks();
        setReturns();
    }

    @Test
    public void testUnathorizedAttempt() throws IOException {
        createMocks();
        setReturns();
        AdminClassesHandler adminClassesHandler = new AdminClassesHandler(appDAOsMock, cookieHelperMock);
        adminClassesHandler.handle(httpExchangeMock);
        assertEquals("/", httpExchangeMock.getResponseHeaders().get("Location").get(0));

    }

    @Test
    public void testAuthorizedAttempt() throws IOException{
        createMocks();
        setReturns();
        HttpExchangeMock httpExchange = new HttpExchangeMock(headers);
        when(cookieHelperMock.getSessionIdCookie(httpExchange)).thenReturn(optionalHttpCookie);
        when(appDAOsMock.getDAOAccounts().isValidUserType(sessionId, "ADMIN")).thenReturn(true);
        AdminClassesHandler adminClassesHandler = new AdminClassesHandler(appDAOsMock, cookieHelperMock);
        adminClassesHandler.handle(httpExchange);
        assertEquals(true, httpExchange.getTestDone());
    }


    private void createMocks() {
        //Mocks
        this.sessionId = "12345";
        this.headers = new Headers();
        this.cookieHelperMock = mock(CookieHelper.class);
        this.httpCookie = new HttpCookie("SESSION_ID", "12345");
        this.optionalHttpCookie= Optional.of(httpCookie);
        this.account = mock(Account.class);
        this.daoAccounts = mock(DAOAccounts.class);
        this.httpExchangeMock = mock(HttpExchange.class);
        this.listClass = new ArrayList<>();
        this.appDAOsMock = mock(AppDAOs.class);
        this.classRoom = mock(Class.class);
        this.daoClasses = mock(DAOClasses.class);
        listClass.add(classRoom);

    }

    private void setReturns() {
        //Appdaos thenReturns
        when(appDAOsMock.getDAOClasses()).thenReturn(daoClasses);
        when(appDAOsMock.getDAOAccounts()).thenReturn(daoAccounts);
        when(appDAOsMock.getDAOAccounts().getAccountBySessionId(sessionId)).thenReturn(account);
        when(appDAOsMock.getDAOClasses().getAllClasses()).thenReturn(listClass);
        when(appDAOsMock.getDAOAccounts().getAccountBySessionId(sessionId).getNickname()).thenReturn("Admin");
        when(httpExchangeMock.getResponseHeaders()).thenReturn(headers);

        //HttpExchange thenReturn
        when(httpExchangeMock.getRequestMethod()).thenReturn("GET");

        //Class thenReturn
        when(classRoom.getName()).thenReturn("test-Name");

        //Account thenReturn
        when(account.getNickname()).thenReturn("Admin");

        //CookieHelper thenReturn
        when(cookieHelperMock.getSessionIdCookie(httpExchangeMock)).thenReturn(optionalHttpCookie);

        when(daoClasses.getAllClasses()).thenReturn(listClass);
    }

}
