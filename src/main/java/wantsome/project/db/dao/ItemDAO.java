package wantsome.project.db.dao;

import wantsome.project.db.DbManager;
import wantsome.project.db.dto.ItemDTO;
import wantsome.project.db.dto.State;
import wantsome.project.db.dto.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static wantsome.project.db.dto.State.AVAILABLE;
import static wantsome.project.db.dto.State.LENT;

public class ItemDAO {

    //INSERT
    public static void insert(ItemDTO item) {
        String sql = "INSERT INTO ITEMS(NAME, AUTHOR, STATE, TYPE) VALUES(?, ?, ?, ?);";
        try (Connection conn = DbManager.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, item.getName());
            pst.setString(2, item.getAuthor());
            pst.setString(3, item.getState().name());
            pst.setString(4, item.getType().name());
            pst.execute();
        } catch (SQLException e) {
            System.out.println("Error inserting in db item: " + item.getName() + "! \n" + e.getMessage());
        }
    }


    //SELECT
    public static List<ItemDTO> getAll() {
        String sql = "SELECT * FROM ITEMS";
        List<ItemDTO> items = new ArrayList<>();

        try (Connection conn = DbManager.getConnection();
             PreparedStatement st = conn.prepareStatement(sql);
             ResultSet result = st.executeQuery()) {
            while (result.next()) {
                items.add(extractItemFromResult(result));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public static List<ItemDTO> getAllAvailable() {
        String sql = "SELECT * FROM ITEMS WHERE STATE = ?";
        List<ItemDTO> items = new ArrayList<>();

        try (Connection conn = DbManager.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, AVAILABLE.toString());
            ResultSet result = st.executeQuery();

            while (result.next()) {
                items.add(extractItemFromResult(result));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    public static List<ItemDTO> getAllLent() {
        String sql = "SELECT * FROM ITEMS WHERE STATE = ?";
        List<ItemDTO> items = new ArrayList<>();

        try (Connection conn = DbManager.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, LENT.toString());
            ResultSet result = st.executeQuery();

            while (result.next()) {
                items.add(extractItemFromResult(result));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    //GetBy:
    public static ItemDTO getById(int id) {

        String sql = "SELECT * FROM ITEMS WHERE ITEM_ID = ?";

        try (Connection conn = DbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractItemFromResult(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading item with id " + id + "! \n" + e.getMessage());
        }
        return null;
    }

    public static List<ItemDTO> getByNameContaining(String name) {


        String sql = "SELECT * FROM ITEMS WHERE STATE ='AVAILABLE' AND UPPER(NAME) LIKE UPPER(?)";
        List<ItemDTO> items = new ArrayList<>();
        try (Connection conn = DbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                items.add(extractItemFromResult(result));
            }
        } catch (SQLException e) {
            System.err.println("Error loading item with name: " + name + "! \n" + e.getMessage());
        }
        return items;
    }

    public static List<ItemDTO> getByAuthor(String author) {

        String sql = "SELECT * FROM ITEMS WHERE STATE ='AVAILABLE' AND UPPER(AUTHOR) LIKE UPPER(?)";
        List<ItemDTO> items = new ArrayList<>();
        try (Connection conn = DbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + author + "%");
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                items.add(extractItemFromResult(result));
            }
        } catch (SQLException e) {
            System.err.println("Error loading items from author: " + author + "! \n" + e.getMessage());
        }
        if (items == null)
            return new ArrayList<>();
        return items;
    }

    public static List<ItemDTO> getByType(String type) {

        String sql = "SELECT * FROM ITEMS WHERE UPPER(TYPE) = UPPER(?)";
        List<ItemDTO> items = new ArrayList<>();

        try (Connection conn = DbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, type);
            ResultSet result = ps.executeQuery();

            while (result.next()) {
                items.add(extractItemFromResult(result));
            }
        } catch (SQLException e) {
            System.err.println("Error loading items: \n" + e.getMessage());
        }
        return items;
    }


    //UPDATE
    public static void update(int id, State state) {

        String sql = "UPDATE ITEMS " +
                "SET STATE = ? " +
                "WHERE ITEM_ID  = ?";

        try (Connection conn = DbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, state.toString());
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error while updating note " + id + " : " + e.getMessage());
        }
    }


    //UTIL
    private static ItemDTO extractItemFromResult(ResultSet rs) throws SQLException {

        int id = rs.getInt("ITEM_ID");
        String name = rs.getString("NAME");
        String author = rs.getString("AUTHOR");
        State state = State.valueOf(rs.getString("STATE"));
        Type type = Type.valueOf(rs.getString("TYPE"));

        return new ItemDTO(id, name, author, state, type);
    }
}
