
import com.codecool.app.dao.DAOMentors;
import com.codecool.app.dao.SQLImpl.DAOMentorsSQL;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class TestAdminDaoSQL {

    @Test
    public void testGetMentorIsNotNull() throws SQLException {
        DAOMentorsSQL testAdminDaoSQL = new DAOMentorsSQL();
        Connection connection = mock(Connection.class);
        testAdminDaoSQL.connection = connection;
        ResultSet resultSet = mock(ResultSet.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(Mockito.anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString(Mockito.anyString())).thenReturn("Admin");
        when(resultSet.getString(Mockito.anyString())).thenReturn("SurAdmin");
        when(resultSet.getString(Mockito.anyString())).thenReturn("test@email.com");
        Assert.assertNotEquals(null, testAdminDaoSQL.getMentor(1));
    }


    @Test
    public void testSQLException() {
        DAOMentorsSQL testAdminDaoSQL = new DAOMentorsSQL();
        Connection connection = mock(Connection.class);
        testAdminDaoSQL.connection = connection;

    }
}
