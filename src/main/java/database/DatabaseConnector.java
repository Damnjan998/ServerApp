package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDateTime;

public class DatabaseConnector {

    private static DatabaseConnector dbInstance;

    private Connection connection;

    public DatabaseConnector(String dbPath) {
        String connectionString = "jdbc:sqlite" + dbPath;
        try {
            connection = DriverManager.getConnection(connectionString);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public DatabaseConnector() {}

    public static DatabaseConnector getInstance(String dbPath) {
        if (dbInstance == null) {
            dbInstance = new DatabaseConnector(dbPath);
        }
        return dbInstance;
    }

    public Connection getConnection() {
        return connection;
    }

    public String findKey(String fileName, String dbPath) {

        return "";
    }

    public void logChange(LocalDateTime timestamp, String fileName) {

    }
}
