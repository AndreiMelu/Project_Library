package wantsome.project.db.dao;

import wantsome.project.db.DbManager;
import wantsome.project.db.dto.RoleDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class RoleDAO {

    public static List<RoleDTO> getAll() {
        String sql = "SELECT * FROM ROLE";
        List<RoleDTO> roles = new ArrayList<>();

        try (Connection conn = DbManager.getConnection();
             PreparedStatement st = conn.prepareStatement(sql);
             ResultSet result = st.executeQuery()) {
            while (result.next()) {
                RoleDTO role = new RoleDTO(result.getString("role"));
                roles.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roles;
    }

    public static void insert(RoleDTO role) {
        String sql = "INSERT INTO ROLE(ROLE)  VALUES(?)";
        try (Connection conn = DbManager.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, role.getRole());
            st.execute();
        } catch (SQLException e) {
            System.out.println("Error inserting in db role" + role + " : " + e.getMessage());
        }
    }

    public static void delete(String role) {
        String sql = "DELETE FROM ROLE WHERE ROLE = ?";
        try (Connection conn = DbManager.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, role);
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
