package wantsome.project.web;

import spark.Request;
import spark.Response;
import wantsome.project.db.dao.UserDAO;
import wantsome.project.db.dto.UserDTO;
import java.util.HashMap;
import java.util.Map;

public class MainLoginUtil {

    public static String showLoginPage() {
        Map<String, Object> model = new HashMap<>();
        return SparkUtil.render(model, "login.vm");
    }

    public static Response handleLoginRequest(Request req, Response res) {
        String userName = req.queryParams("username");
        String password = req.queryParams("password");

        UserDTO user = UserDAO.getByUsername(userName, password);
        if (user != null) {
            req.session().attribute("userId", user.getId());
            if (user.getRole().equalsIgnoreCase("ADMINISTRATOR")) {
                res.redirect("/admin/main");
            } else {
                res.redirect("/library");
            }
        } else {
            res.redirect("/login");
        }
        return res;
    }

    public static Response handleLogoutRequest(Request req, Response res) {
        req.session().removeAttribute("userId");
        res.redirect("/login");
        return res;
    }
}
