package wantsome.project.db;

import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbManager {

    private static final String DB_FILE = "database.db";

    public static Connection getConnection() throws SQLException {
        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true); //enable FK support (disabled by default)
        config.setDateStringFormat("yyyy-MM-dd HH:mm:ss");

        return DriverManager.getConnection("jdbc:sqlite:" + DB_FILE, config.toProperties());
    }
}
