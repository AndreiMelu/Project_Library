package wantsome.project.db.dao;

import wantsome.project.db.DbManager;
import wantsome.project.db.dto.UserDTO;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class UserDAO {

    public static List<UserDTO> getAll() {
        String sql = "SELECT * FROM USER";
        List<UserDTO> users = new ArrayList<>();

        try (Connection conn = DbManager.getConnection();
             PreparedStatement st = conn.prepareStatement(sql);
             ResultSet result = st.executeQuery()) {
            while (result.next()) {
                UserDTO user = new UserDTO(result.getInt("id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("email"),
                        result.getString("username"),
                        result.getString("password"),
                        result.getDate("created_at"),
                        result.getDate("updated_at"),
                        result.getString("role")
                );
                users.add(user);

            }
        } catch (SQLException e) {
            System.out.println("Error loading all users : " + e.getMessage());
        }
        return users;
    }

    public static List<UserDTO> getAllByRole(String role) {
        String sql = "SELECT * FROM USER WHERE ROLE = ?";
        List<UserDTO> users = new ArrayList<>();

        try (Connection conn = DbManager.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, role);
            ResultSet result = st.executeQuery();

            while (result.next()) {
                UserDTO user = new UserDTO(result.getInt("id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("email"),
                        result.getString("username"),
                        result.getString("password"),
                        result.getDate("created_at"),
                        result.getDate("updated_at"),
                        result.getString("role")
                );
                users.add(user);

            }
        } catch (SQLException e) {
            System.out.println("Error loading all users : " + e.getMessage());
        }
        return users;
    }

    public static UserDTO getById(int id) {
        String sql = "SELECT * FROM USER WHERE ID = ?";

        try (Connection conn = DbManager.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet result = st.executeQuery()) {
                if (result.next()) {
                    return new UserDTO(result.getInt("id"),
                            result.getString("first_name"),
                            result.getString("last_name"),
                            result.getString("email"),
                            result.getString("username"),
                            result.getString("password"),
                            result.getDate("created_at"),
                            result.getDate("updated_at"),
                            result.getString("role"));
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error loading user with id " + id + " : " + ex.getMessage());
        }
        return null;
    }


    public static UserDTO getByUsername(String username, String password) {
        String sql = "SELECT * FROM USER WHERE USERNAME = ? AND PASSWORD = ?";

        try (Connection conn = DbManager.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, username);
            st.setString(2, password);

            try (ResultSet result = st.executeQuery()) {
                if (result.next()) {
                    return new UserDTO(result.getInt("id"),
                            result.getString("first_name"),
                            result.getString("last_name"),
                            result.getString("email"),
                            result.getString("username"),
                            result.getString("password"),
                            result.getDate("created_at"),
                            result.getDate("updated_at"),
                            result.getString("role"));
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error loading user with id " + username + " : " + ex.getMessage());
        }
        return null;
    }

    public static void insert(UserDTO user) {
        String sql = "INSERT INTO USER(FIRST_NAME, LAST_NAME, EMAIL, USERNAME, PASSWORD, CREATED_AT, UPDATED_AT, ROLE) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection conn = DbManager.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, user.getFirstName());
            st.setString(2, user.getLastName());
            st.setString(3, user.getEmail());
            st.setString(4, user.getUsername());
            st.setString(5, user.getPassword());
            st.setDate(6, (Date) user.getCreatedAt());
            st.setDate(7, (Date) user.getUpdatedAt());
            st.setString(8, user.getRole());
            st.execute();


        } catch (SQLException e) {
            System.out.println("Error inserting in db user " + user + " : " + e.getMessage());
        }
    }

    private static void deleteById(int id) {
        String sql = "DELETE FROM USER WHERE ID = ?";
        try (Connection conn = DbManager.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUserById(int id) {
        int count = 0;
        if (getById(id).getRole().equals("UTILIZATOR")) {
            deleteById(id);
            System.out.println("Utilizatorul cu id ul " + id + " a fost sters cu succes!");
        } else if (getById(id).getRole().equals("ADMINISTRATOR")) {
            for (UserDTO u : getAllByRole("ADMINISTRATOR")) {
                count++;
            }
            if (count > 1) {
                deleteById(id);
            } else {
                System.out.println("Nu se poate sterge utilizatorul cu id-ul " + id + " ! Este ultimul administrator!");
            }
        }
    }


    public static void updateEmailById(int id, String email) {
        String sql = "UPDATE USER SET EMAIL = ?, UPDATED_AT = ? WHERE ID = ?";

        try (Connection conn = DbManager.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, email);
            st.setDate(2, Date.valueOf(LocalDate.now()));
            st.setInt(3, id);
            int count = st.executeUpdate();

            System.out.println(count);

        } catch (SQLException e) {
            System.err.println("Error while updating user " + " : " + e.getMessage());
        }
    }

    public static void updatePasswordById(int id, String password) {
        String sql = "UPDATE USER SET PASSWORD = ?, UPDATED_AT = ? WHERE ID = ?";

        try (Connection conn = DbManager.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, password);
            st.setDate(2, Date.valueOf(LocalDate.now()));
            st.setInt(3, id);
            int count = st.executeUpdate();

            System.out.println(count);

        } catch (SQLException e) {
            System.err.println("Error while updating user " + " : " + e.getMessage());
        }
    }

    public static void updateLastNameById(int id, String name) {
        String sql = "UPDATE USER SET LAST_NAME = ?, UPDATED_AT = ? WHERE ID = ?";

        try (Connection conn = DbManager.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, name);
            st.setDate(2, Date.valueOf(LocalDate.now()));
            st.setInt(3, id);
            int count = st.executeUpdate();

            System.out.println(count);

        } catch (SQLException e) {
            System.err.println("Error while updating user " + " : " + e.getMessage());
        }
    }
}
