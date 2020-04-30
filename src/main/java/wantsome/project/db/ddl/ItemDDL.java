package wantsome.project.db.ddl;
import wantsome.project.db.DbManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static wantsome.project.db.dto.State.*;
import static wantsome.project.db.dto.Type.*;

public class ItemDDL {

    public static void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS ITEMS (" +
                "ITEM_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME VARCHAR NOT NULL," +
                "AUTHOR VARCHAR," +
                "STATE VARCHAR CHECK (STATE IN ('" + AVAILABLE + "', '" + LENT + "', '" + LOST + "', '" + DESTROYED + "')) NOT NULL," +
                "TYPE VARCHAR CHECK (TYPE IN ('" + CD + "', '" + DVD + "', '" + BOOK + "')) NOT NULL" +
                ");";

        try (Connection conn = DbManager.getConnection();
             Statement st = conn.createStatement()) {

            st.execute(sql);
        }
    }

    public static void dropTable() {
        String sql = "DROP TABLE IF EXISTS ITEMS";
        try (Connection conn = DbManager.getConnection();
             Statement st = conn.createStatement()) {

            st.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
