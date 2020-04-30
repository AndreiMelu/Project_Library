package wantsome.project.db.ddl;

import wantsome.project.db.DbManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class RoleDDL {

    public static void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS ROLE (" +
                "ROLE VARCHAR PRIMARY KEY" +
                ")";

        try (Connection conn = DbManager.getConnection();
             Statement st = conn.createStatement()) {

            st.execute(sql);
        }
    }

    public static void dropTable() {
        String str = "DROP TABLE IF EXISTS ROLE";
        try (Connection conn = DbManager.getConnection();
             Statement st = conn.createStatement()) {

            st.execute(str);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
