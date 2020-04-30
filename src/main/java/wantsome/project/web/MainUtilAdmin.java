package wantsome.project.web;

import spark.Request;
import spark.Response;
import wantsome.project.db.dao.UserDAO;
import wantsome.project.db.dto.UserDTO;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;

public class MainUtilAdmin {

    public static String showMainPage(Request req, Response res) {
        checkLoggedAdmin(req, res);
        Map<String, Object> model = new HashMap<>();
        model.put("users", UserDAO.getAll());
        return SparkUtil.render(model, "admin_main.vm");
    }

    public static String showRegisterPage(Request req, Response res) {
        checkLoggedAdmin(req, res);
        Map<String, Object> model = new HashMap<>();
        return SparkUtil.render(model, "admin_register.vm");
    }

    public static Response handleRegisterRequest(Request req, Response res) {
        checkLoggedAdmin(req, res);

        UserDTO user = new UserDTO(
                UserDAO.getAll().size() + 1,
                req.queryParams("First_name"),
                req.queryParams("Last_name"),
                req.queryParams("email"),
                req.queryParams("username"),
                req.queryParams("password"),
                Date.valueOf(LocalDate.now()),
                null,
                req.queryParams("role").toUpperCase());
        UserDAO.insert(user);

        res.redirect("/admin/main");
        return res;
    }

    public static Response handleUnregisterRequest(Request req, Response res) {
        checkLoggedAdmin(req, res);
        int id = Integer.parseInt(req.params("id"));
        UserDAO.deleteUserById(id);
        res.redirect("/admin/main");
        return res;
    }

    public static String showUpdateUserPage(Request req, Response res) {
        checkLoggedAdmin(req, res);

        int id = Integer.parseInt(req.params("id"));
        UserDTO user = UserDAO.getById(id);

        Map<String, Object> model = new HashMap<>();
        model.put("username", user.getUsername());
        model.put("role", user.getRole());
        model.put("email", user.getEmail());
        model.put("firstName", user.getFirstName());
        model.put("lastName", user.getLastName());
        return SparkUtil.render(model, "admin_update.vm");
    }

    public static Response handleUpdateUserRequest(Request req, Response res) {
        checkLoggedAdmin(req, res);

        int id = Integer.parseInt(req.params("id"));

        String email = req.queryParams("email");
        String lastName = req.queryParams("lastName");
        String password = req.queryParams("password");

        if (!email.isEmpty()) {
            UserDAO.updateEmailById(id, email);
        }

        if (!lastName.isEmpty()) {
            UserDAO.updateLastNameById(id, lastName);
        }

        if (!password.isEmpty()) {
            UserDAO.updatePasswordById(id, password);
        }

        res.redirect("/admin/main");
        return res;
    }

    public static void checkLoggedAdmin(Request request, Response response) {
        if (request.session().attribute("userId") == null) {
            response.redirect("/login");
            halt();
        } else if (UserDAO.getById(request.session().attribute("userId")).getRole().equalsIgnoreCase("UTILIZATOR")) {
            response.redirect("/library");
        }
    }
}
