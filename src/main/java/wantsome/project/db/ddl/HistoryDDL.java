package wantsome.project.db.ddl;

import wantsome.project.db.DbManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static wantsome.project.db.dto.HistoryState.*;
import static wantsome.project.db.dto.State.DESTROYED;

public class HistoryDDL {

    public static void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS HISTORY(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ITEM_ID INTEGER NOT NULL REFERENCES ITEMS(ITEM_ID)," +
                "USER_ID INTEGER NOT NULL REFERENCES USER(ID)," +
                "LENT_DATE DATE NOT NULL," +
                "STATE VARCHAR CHECK (STATE IN ('" + RETURNED + "', '" + LENT + "', '" + LOST + "', '" + DESTROYED + "')) NOT NULL," +
                "RETURN_DATE DATE" +
                ");";
        try (Connection conn = DbManager.getConnection();
             Statement st = conn.createStatement()) {
            st.execute(sql);
        }
    }

    public static void dropTable() {
        String sql = "DROP TABLE IF EXISTS HISTORY";
        try (Connection conn = DbManager.getConnection();
             Statement st = conn.createStatement()) {

            st.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
