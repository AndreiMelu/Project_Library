package wantsome.project.db.dao;

import wantsome.project.db.DbManager;
import wantsome.project.db.dto.HistoryDTO;
import wantsome.project.db.dto.HistoryState;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HistoryDAO {

    public static void insert(HistoryDTO history) {
        String sql = "INSERT INTO HISTORY (ITEM_ID, USER_ID, STATE, LENT_DATE, RETURN_DATE)  VALUES(?, ?, ?, ?,?);";
        try (Connection conn = DbManager.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, history.getItemId());
            pst.setInt(2, history.getUserId());
            pst.setString(3, history.getState().toString());
            pst.setDate(4, Date.valueOf(history.getLentDate()));
            pst.setDate(5, null);
            pst.execute();
        } catch (SQLException e) {
            System.out.println("Error inserting in db item: " + history.getId() + "! \n" + e.getMessage());
        }
    }


    public static HistoryDTO getHistoryById(int id) {

        String sql = "SELECT * FROM HISTORY " +
                "WHERE ID  = ? ";
        List<HistoryDTO> historyList = new ArrayList<>();

        try (Connection conn = DbManager.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet result = st.executeQuery()) {
                while (result.next()) {
                    return extractLineFromResult(result);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<HistoryDTO> getHistoryByUserOrdered(int userId) {
        List<HistoryDTO> historyList = new ArrayList<>();
        String sql = "SELECT * FROM HISTORY " +
                "WHERE USER_ID=?" +
                "ORDER BY LENT_DATE DESC ";

        try (Connection conn = DbManager.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, userId);

            try (ResultSet result = st.executeQuery()) {
                while (result.next()) {
                    historyList.add(extractLineFromResult(result));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historyList;
    }


    public static void update(int id, String state) {
        String sql = "UPDATE HISTORY SET " +
                "RETURN_DATE = ?, STATE = ?" +
                "WHERE ID  = ?";

        try (Connection conn = DbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(LocalDate.now()));
            ps.setString(2, state);
            ps.setInt(3, id);
            ps.execute();
            System.out.println("Execute update successfully");

        } catch (SQLException e) {
            System.err.println("Error while updating line: " + id + "! " + e.getMessage());
        }
    }


    //UTIL
    private static HistoryDTO extractLineFromResult(ResultSet rs) throws SQLException {

        int id = rs.getInt("ID");
        int itemId = rs.getInt("ITEM_ID");
        int userId = rs.getInt("USER_ID");
        HistoryState state = HistoryState.valueOf(rs.getString("STATE"));
        LocalDate lentDate = rs.getDate("LENT_DATE").toLocalDate();
        LocalDate returnDate;
        if (rs.getDate("RETURN_DATE") == null) {
            returnDate = null;
        } else {
            returnDate = rs.getDate("RETURN_DATE").toLocalDate();
        }

        return new HistoryDTO(id, itemId, userId, state, lentDate, returnDate);
    }
}
