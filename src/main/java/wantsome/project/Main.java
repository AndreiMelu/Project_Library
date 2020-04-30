package wantsome.project;

import wantsome.project.db.dao.ItemDAO;
import wantsome.project.db.dao.RoleDAO;
import wantsome.project.db.dao.UserDAO;
import wantsome.project.db.ddl.HistoryDDL;
import wantsome.project.db.ddl.ItemDDL;
import wantsome.project.db.ddl.RoleDDL;
import wantsome.project.db.ddl.UserDDL;
import wantsome.project.db.dto.ItemDTO;
import wantsome.project.db.dto.RoleDTO;
import wantsome.project.db.dto.UserDTO;
import java.sql.SQLException;
import java.time.LocalDate;

import static spark.Spark.*;
import static wantsome.project.db.dto.State.AVAILABLE;
import static wantsome.project.db.dto.State.LOST;
import static wantsome.project.db.dto.Type.*;
import static wantsome.project.web.MainLoginUtil.*;
import static wantsome.project.web.MainUtilAdmin.*;
import static wantsome.project.web.MainUtilUsers.*;

public class Main {

    public static void main(String[] args) throws SQLException {
        createTablesAndSampleData();
        setupRoutes();
    }

    private static void setupRoutes() {
        staticFiles.location("public");

        //Login
        get("/login", (req, res) -> showLoginPage());
        post("/login", (req, res) -> handleLoginRequest(req, res));

        //Logout
        get("/logout", (req, res) -> handleLogoutRequest(req, res) );

        //--- Admin ---//
        get("/admin/main", (req, res) -> showMainPage(req, res));
        get("/main", (req, res) -> showLibraryToAdmin(req, res));

        get("/register", (req, res) -> showRegisterPage(req, res));
        post("/register", (req, res) -> handleRegisterRequest(req, res));

        get("/admin/unregister/:id", (req, res) -> handleUnregisterRequest(req, res));

        get("/admin/update/:id", (req, res) -> showUpdateUserPage(req, res));
        post("/admin/update/:id", (req, res) -> handleUpdateUserRequest(req, res));

        //--- Users ---//
        get("/library", (req, res) -> showMainPageUsers(req, res));
        post("/library", (req, res) -> showMainPageUsers(req, res));

        get("/donations", (req, res) -> showDonationPage(req, res));
        post("/donations", (req, res) -> handleDonation(req, res));

        get("/history", (req, res) -> showHistoryPage(req, res));
        get("/return/:id", (req, res) -> handleReturnItem(req, res));
        get("/lost/:id", (req, res) -> handleLostItem(req, res));
        get("/destroyed/:id", (req, res) -> handleDestroyedItem(req, res));
        get("/lent/:id", (req, res) -> handleLentItem(req, res));

        awaitInitialization();
        System.out.println("\nServer started, url: http://localhost:4567/login (use Ctrl+C to stop it)\n");
    }

    private static void createTablesAndSampleData() throws SQLException {

        RoleDDL.createTable();
        UserDDL.createTable();
        ItemDDL.createTable();
        HistoryDDL.createTable();

        if (RoleDAO.getAll().isEmpty()) {
            RoleDAO.insert(new RoleDTO("ADMINISTRATOR"));
            RoleDAO.insert(new RoleDTO("UTILIZATOR"));
        }
        if (UserDAO.getAll().isEmpty()) {
            UserDAO.insert(new UserDTO(1, "Andreea", "Raducan", "araducan@gmail.com.com", "andreea", "andreea", java.sql.Date.valueOf(LocalDate.now()), null, "ADMINISTRATOR"));
            UserDAO.insert(new UserDTO(2, "Andrei", "Melu", "andrei.melu@yahoo.com", "andrei", "andrei", java.sql.Date.valueOf(LocalDate.now()), null, "ADMINISTRATOR"));

        }
        if (ItemDAO.getAll().isEmpty()) {
            ItemDAO.insert(new ItemDTO(1, "Programare concurenta si paralel-distribuita in JAVA", "Ernest Scheiber", AVAILABLE, BOOK));
            ItemDAO.insert(new ItemDTO(2, "Informatica aplicata. Programare in Java", "Eugen Petac, Cristina Serban", AVAILABLE, BOOK));
            ItemDAO.insert(new ItemDTO(3, "M-am hotărât să devin prost", "Martin Page", AVAILABLE, BOOK));
            ItemDAO.insert(new ItemDTO(4, "De la idee la bani", "Napoleon Hill", AVAILABLE, BOOK));
            ItemDAO.insert(new ItemDTO(5, "Cadranul banilor", "Robert T. Kiyosaki", AVAILABLE, BOOK));
            ItemDAO.insert(new ItemDTO(6, "Povești populare românești", AVAILABLE, BOOK));
            ItemDAO.insert(new ItemDTO(7, " Memento", AVAILABLE, DVD));
            ItemDAO.insert(new ItemDTO(8, "Mr. Nobody", AVAILABLE, DVD));
            ItemDAO.insert(new ItemDTO(9, "Schindler's list", AVAILABLE, DVD));
            ItemDAO.insert(new ItemDTO(1, "Inception", AVAILABLE, DVD));
            ItemDAO.insert(new ItemDTO(2, "The White Album", "The Beatles", AVAILABLE, CD));
            ItemDAO.insert(new ItemDTO(3, "Bad", "Michael Jackson", LOST, CD));
            ItemDAO.insert(new ItemDTO(4, "Thriller", "Michael Jackson", AVAILABLE, CD));
        }
    }

    private static void deleteTables() {
        HistoryDDL.dropTable();
        ItemDDL.dropTable();
        UserDDL.dropTable();
        RoleDDL.dropTable();
    }
}
