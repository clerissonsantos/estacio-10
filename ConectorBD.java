package cadastrobd.model.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConectorBD {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=bancoLoja;encrypt=true;trustServerCertificate=true;";
        String user = "lojaa";
        String password = "lojaa"; 

        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    public static PreparedStatement getPrepared(Connection conn, String sql) throws SQLException {
        return conn.prepareStatement(sql);
    }
}
