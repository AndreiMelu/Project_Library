package wantsome.project.db.ddl;

import wantsome.project.db.DbManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDDL {

    public static void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS USER (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "FIRST_NAME VARCHAR NOT NULL," +
                "LAST_NAME VARCHAR NOT NULL," +
                "EMAIL VARCHAR NOT NULL," +
                "USERNAME VARCHAR NOT NULL UNIQUE," +
                "PASSWORD VARCHAR NOT NULL," +
                "CREATED_AT DATE NOT NULL," +
                "UPDATED_AT DATE," +
                "ROLE NOT NULL REFERENCES ROLE(ROLE)" +
                ")";

        try (Connection conn = DbManager.getConnection();
             Statement st = conn.createStatement()) {

            st.execute(sql);
        }
    }

    public static void dropTable() {
        String str = "DROP TABLE IF EXISTS USER";

        try (Connection conn = DbManager.getConnection();
             Statement st = conn.createStatement()) {

            st.execute(str);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
