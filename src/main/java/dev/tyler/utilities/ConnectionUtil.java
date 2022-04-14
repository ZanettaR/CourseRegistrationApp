package dev.tyler.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    public static Connection createConnection(){
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://zanetta-db.cnoezlpdmwcu.us-east-1.rds.amazonaws.com/registration?user=postgres&password=IuzSAi5eeIowaSbGT7Mk");

            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
