package wantsome.project.db.dto;

import java.util.Date;
import java.util.Objects;


public class UserDTO {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private Date createdAt;
    private Date updatedAt;
    private String role;

    public UserDTO(int id, String firstName, String lastName, String email, String username, String password, Date createdAt, Date updatedAt, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.role = role;
    }


    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return id == userDTO.id &&
                firstName.equals(userDTO.firstName) &&
                lastName.equals(userDTO.lastName) &&
                email.equals(userDTO.email) &&
                username.equals(userDTO.username) &&
                password.equals(userDTO.password) &&
                createdAt.equals(userDTO.createdAt) &&
                Objects.equals(updatedAt, userDTO.updatedAt) &&
                role.equals(userDTO.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, username, password, createdAt, updatedAt, role);
    }


    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", first_name='" + firstName + '\'' +
                ", last_name='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", created_at=" + createdAt +
                ", updated_at=" + updatedAt +
                ", role='" + role + '\'' +
                '}';
    }
}
