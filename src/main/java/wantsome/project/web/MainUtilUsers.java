package wantsome.project.web;

import spark.Request;
import spark.Response;
import wantsome.project.db.dao.HistoryDAO;
import wantsome.project.db.dao.ItemDAO;
import wantsome.project.db.dto.HistoryDTO;
import wantsome.project.db.dto.HistoryState;
import wantsome.project.db.dto.ItemDTO;
import wantsome.project.db.dto.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static spark.Spark.halt;
import static wantsome.project.db.dto.State.*;
import static wantsome.project.web.MainUtilAdmin.checkLoggedAdmin;

public class MainUtilUsers {

    public static String showMainPageUsers(Request req, Response res) {
        getLoggedInUserId(req, res);
        String searchValue = req.queryParamOrDefault("search", "");
        String searchBy = req.queryParamOrDefault("searchBy", "");

        String itemsTable = getItemsTableWithFilter(searchBy, searchValue);

        StringBuilder main = new StringBuilder();
        main.append(
                "<HEAD><link rel=\"stylesheet\" type=\"text/css\" href=\"main.css\"></HEAD>" +
                        "<h1 style=\"text-align:center; color: #3d5c5c; \">LIBRARY</h1><br>" +
                        "    <body><div class=\"row left\">" +


                        "<form method='post'>" +
                        "<p style=\"color:3d5c5c; font-size:20px;\">" +
                        "<input type=\"text\" id=\"search\" name=\"search\" value='" + searchValue + "'>&nbsp;" +
                        "<button type=\"submit\" class=\"buttonSearch\">Search</button><br>" +

                        "<label for=\"search\" style=\"color:3d5c5c; font-size:25px;\">Search by: </label><br>" +
                        "  &nbsp;&nbsp;&nbsp; <input type=\"radio\" name=\"searchBy\" id=\"NAME\" value=\"NAME\"");
        if (searchBy == null || searchBy.isEmpty() || searchBy.equals("NAME")) {
            main.append(" checked>");
        } else {
            main.append(">");
        }
        main.append(" Name<br>" +
                "  &nbsp;&nbsp;&nbsp; <input type=\"radio\" name=\"searchBy\" id=\"AUTHOR\" value=\"AUTHOR\"");
        if (searchBy.equals("AUTHOR")) {
            main.append(" checked>");
        } else {
            main.append(">");
        }
        main.append("Author<br>" +
                "  &nbsp;&nbsp;&nbsp; <input type=\"radio\" name=\"searchBy\" id=\"TYPE\" value=\"TYPE\"");
        if (searchBy.equals("TYPE")) {
            main.append(" checked>");
        } else {
            main.append(">");
        }
        main.append("Type<br>" +

                "</p>" +
                "</form>" +
                "<a  id=\"v-pills-home-tab\" data-toggle=\"pill\" href=\"/donations\" role=\"tab\" aria-controls=\"v-pills-home\" aria-selected=\"false\">" +
                "<button type=\"button\" class=\"buttonSearch\">Donate to library </button></a><br></br>" +

                "<a  id=\"v-pills-home-tab\" data-toggle=\"pill\" href=\"/history\" role=\"tab\" aria-controls=\"v-pills-home\" aria-selected=\"false\">" +
                "<button type=\"button\" class=\"buttonSearch\">View history</button></a><br></br>" +

                "              </div>" +
                "              <div class=\"row right\"> " +

                "<table cellspacing=\"35\">" +

                itemsTable +

                "</table>" +
                "<a  href='/logout' class='logout'>" +
                "  <button type=\"button\" class='logout' >Log out! </button>" +
                "</a>" +
                "    </div>" +
                "<body>");

        return String.valueOf(main);
    }

    private static String getItemsTableWithFilter(String searchBy, String searchValue) {
        if (!searchValue.isEmpty()) {
            if ("NAME".equals(searchBy)) {
                return initialBuild(ItemDAO.getByNameContaining(searchValue));
            }
            if ("AUTHOR".equals(searchBy)) {
                return initialBuild(ItemDAO.getByAuthor(searchValue));
            }
            if ("TYPE".equals(searchBy)) {
                return initialBuild(ItemDAO.getByType(searchValue));
            }
        }
        return initialBuild(ItemDAO.getAllAvailable());
    }

    private static String initialBuild(List<ItemDTO> items) {
        if (items.isEmpty()) {
            return "";
        }

        StringBuilder tblItem = new StringBuilder();
        tblItem.append("<tr>");
        AtomicInteger count = new AtomicInteger();
        AtomicReference<String> author = new AtomicReference<>("");
        items.forEach(p -> {
            if (p.getAuthor() != null) {
                author.set(p.getAuthor());
            } else {
                author.set("");
            }
            if (count.get() % 4 != 0) {
                tblItem.append(String.format("<td>" +
                                "<p>%s</p> " +
                                "<p>%s</p> " +
                                "<p>%s</p> " +
                                "<p>%s</p> " +
                                "<p>%s </p>" +
                                "</td>"
                        , "<strong>Name: </strong>" + p.getName(), "<strong>Author: </strong>" + author, "<strong>Type: </strong>" + p.getType(),
                        "<strong>State: </strong>" + p.getState(),
                        "<a href='/lent/" + p.getItemId() + "'><button id=\"lent\" class=\"button\" type=\"button\">Lent </button></a>"));
            } else {
                tblItem.append(
                        String.format(
                                "</tr><tr><td>" +
                                        "<p>%s</p> " +
                                        "<p>%s</p> " +
                                        "<p>%s</p> " +
                                        "<p>%s</p> " +
                                        "<p>%s </p>" +
                                        "</td>"
                                , "<strong>Name: </strong>" + p.getName(), "<strong>Author: </strong>" + author, "<strong>Type: </strong>" + p.getType(),
                                "<strong>State: </strong>" + p.getState(),
                                "<a href='/lent/" + p.getItemId() + "'><button id=\"lent\" class=\"button\" type=\"button\">Lent </button></a>"));
            }
            count.getAndIncrement();
        });

        tblItem.append("</tr>");
        return tblItem.toString();
    }

    public static String showDonationPage(Request req, Response res) {
        getLoggedInUserId(req, res);
        return "<HEAD><link rel=\"stylesheet\" type=\"text/css\" href=\"donate.css\"></HEAD>" +


                "<h1 style=\"text-align:center;  color: #3d5c5c;\">Donate a media library</h1>" +
                "<div class=\"form\">" +
                "    <br>" +
                "    <form name='form' method='post'>" +
                "        <table align=\"center\">" +
                "            <tr>" +
                "                <td  color: #fff;>Name:</td>" +
                "                <td>" +
                "                    <input type='text' placeholder ='Name' name='name' value= ''required style=\"width: 195px;\">" +
                "                </td>" +
                "            </tr>" +
                "            <tr>" +
                "                <td color: #fff;>Author:</td>" +
                "                <td>" +
                "                    <input type='text' placeholder ='Author' name='Author' value= '' style=\"width: 195px;\">" +
                "                </td>" +
                "            </tr>" +
                "            <tr>" +
                "                <td color: #fff;>Type:</td>" +
                "                <td>" +
                "                    <input type=\"radio\" name=\"type\" id=\"BOOK\" value=\"BOOK\" checked>Book &nbsp;" +
                "                    <input type=\"radio\" name=\"type\" id=\"CD\" value=\"CD\">CD &nbsp;" +
                "                    <input type=\"radio\" name=\"type\" id=\"DVD\" value=\"DVD\">DVD" +
                "                </td>" +
                "            </tr>" +
                "<tr>" +
                "<td colspan ='2' align=\"center\" id='submit'>" +
                "<input type='submit' class='button' value='Donate'>" +
                "<br><br><br><a href='/library'><button type=\"button\" class=\"button\">Back to Library</button></a><br>" +
                "</td>" +
                "</tr>" +
                "        </table>" +
                "        <br>" +
                "    </form>" +
                "</div>";

    }

    public static Response handleDonation(Request req, Response res) {
        ItemDTO item = buildItem(
                req.queryParams("name"),
                req.queryParams("author"),
                req.queryParams("type").toUpperCase());

        ItemDAO.insert(item);
        res.redirect("/library");
        return res;
    }

    public static String showHistoryPage(Request request, Response response) {
        StringBuilder sql = new StringBuilder();
        int userId = getLoggedInUserId(request, response);
        AtomicReference<String> returnDate = new AtomicReference<>("");
        sql.append("<HEAD><link rel=\"stylesheet\" type=\"text/css\" href=\"history.css\"></HEAD>" +
                "<h1 style=\"text-align:center; color: #3d5c5c;\">Your history</h1>" +

                "    <div class=\"row left\">" +
                "<br><br><br><a href='/library'><button type=\"button\" class=\"button\">Back to Library</button></a><br>" +
                "    </div>" +
                "    <div class=\"row right\"> " +
                "<br><br><br><br>" +
                "<table font color=\"#fff\">");
        if (!HistoryDAO.getHistoryByUserOrdered(userId).isEmpty()) {
            sql.append("<tr>" +
                    "<th>Item Name</th>" +
                    "<th>Lent Date</th>" +
                    "<th>Return Date</th>" +
                    "<th>Actual Status</th>" +
                    "<th>Return item</th>" +
                    "<th>Lost?</th>" +
                    "<th>Destroyed?</th>" +
                    "</tr>");
        }

        HistoryDAO.getHistoryByUserOrdered(userId).forEach(p -> {
            if (p.getReturnDate() != null) {
                returnDate.set(p.getReturnDate().toString());
            } else {
                returnDate.set("");
            }
            sql.append(String.format("<tr> <td>%s</td> <td>%s</td> <td>%s</td> <td>%s</td> <td>%s</td> <td>%s</td> <td>%s</td> </tr>",
                    ItemDAO.getById(p.getItemId()).getName(), p.getLentDate(), returnDate, p.getState(),
                    "<a href='/return/" + p.getId() + "'>Return</a>",
                    "<a href='/lost/" + p.getId() + "'>Mark as lost</a>",
                    "<a href='/destroyed/" + p.getId() + "'>Mark as destroyed</a>"));
        });
        sql.append("</table>" +
                "</div>");
        return sql + "<br>";
    }

    private static int getLoggedInUserId(Request request, Response response) {
        Integer userId = request.session().attribute("userId");
        if (userId == null) {
            response.redirect("/login");
            halt();
        }
        return userId;
    }

    private static ItemDTO buildItem(String name, String author, String type) {
        return new ItemDTO(
                ItemDAO.getAll().size() + 1,
                name,
                author,
                AVAILABLE,
                Type.valueOf(type));
    }

    public static Response handleReturnItem(Request req, Response res) {
        int id = Integer.parseInt(req.params("id"));
        HistoryDAO.update(id, HistoryState.RETURNED.toString());
        ItemDAO.update(HistoryDAO.getHistoryById(id).getItemId(), AVAILABLE);
        res.redirect("/history");
        return res;
    }

    public static Response handleLostItem(Request req, Response res) {
        int id = Integer.parseInt(req.params("id"));
        HistoryDAO.update(id, HistoryState.LOST.toString());
        ItemDAO.update(HistoryDAO.getHistoryById(id).getItemId(), LOST);
        res.redirect("/history");
        return res;
    }

    public static Response handleDestroyedItem(Request req, Response res) {
        int id = Integer.parseInt(req.params("id"));
        HistoryDAO.update(id,HistoryState.DESTROYED.toString());
        ItemDAO.update(HistoryDAO.getHistoryById(id).getItemId(), DESTROYED);
        res.redirect("/history");
        return res;
    }

    public static Response handleLentItem(Request req, Response res) {
        int userId = getLoggedInUserId(req, res);
        int itemId = Integer.parseInt(req.params("id"));
        HistoryDAO.insert(new HistoryDTO(1, itemId, userId, HistoryState.LENT, LocalDate.now(), null));
        ItemDAO.update(itemId, LENT);
        res.redirect("/library");
        return res;
    }

    public static void checkLoggedInUser(Request request, Response response) {
        if (request.session().attribute("userId") == null) {
            response.redirect("/login");
            halt();
        }
    }

    public static String showLibraryToAdmin(Request req, Response res) {
        checkLoggedAdmin(req, res);

        StringBuilder main = new StringBuilder();
        main.append(
                "<HEAD><link rel=\"stylesheet\" type=\"text/css\" href=\"main.css\"></HEAD>" +
                        "<h1 style=\"text-align:center; color: #3d5c5c; \">LIBRARY</h1><br>" +
                        "    <body><div class=\"row left\">" +
                        "<br>" +
                        "<a id=\"v-pills-home-tab\" data-toggle=\"pill\" href=\"/admin/main\" role=\"tab\" aria-controls=\"v-pills-home\" aria-selected=\"false\">" +
                        "<button type=\"button\" class=\"buttonSearch\">Back to admin page</button></a><br></br>" +
                        "<p style=\"color: #3d5c5c; font-size: 16px;\">Total items in library: <strong>" + ItemDAO.getAll().size() + "</strong></p>" +
                        "<p style=\"color: #3d5c5c; font-size: 16px;\">Total available items in library: <strong>" + ItemDAO.getAllAvailable().size() + "</strong></p>" +
                        "<p style=\"color: #3d5c5c; font-size: 16px;\">Total lent items in library: <strong>" + ItemDAO.getAllLent().size() + "</strong></p>" +

                        "              </div>" +
                        "              <div class=\"row right\"> " +

                        "<table cellspacing=\"35\">" +
                        buildForAdminView(ItemDAO.getAll()) +
                        "</table>" +
                        "    </div>" +
                        "<body>");

        return String.valueOf(main);
    }

    private static String buildForAdminView(List<ItemDTO> items) {
        if (items.isEmpty()) {
            return "";
        }

        StringBuilder tblItem = new StringBuilder();
        tblItem.append("<tr>");
        AtomicInteger count = new AtomicInteger();
        AtomicReference<String> author = new AtomicReference<>("");
        items.forEach(p -> {
            if (p.getAuthor() != null) {
                author.set(p.getAuthor());
            } else {
                author.set("");
            }
            if (count.get() % 4 != 0) {
                tblItem.append(String.format("<td>" +
                                "<p>%s</p> " +
                                "<p>%s</p> " +
                                "<p>%s</p> " +
                                "<p>%s</p> " +
                                "</td>"
                        , "<strong>Name: </strong>" + p.getName(), "<strong>Author: </strong>" + author, "<strong>Type: </strong>" + p.getType(),
                        "<strong>State: </strong>" + p.getState()));

            } else {
                tblItem.append(
                        String.format(
                                "</tr><tr><td>" +
                                        "<p>%s</p> " +
                                        "<p>%s</p> " +
                                        "<p>%s</p> " +
                                        "<p>%s</p> " +
                                        "</td>"
                                , "<strong>Name: </strong>" + p.getName(), "<strong>Author: </strong>" + author, "<strong>Type: </strong>" + p.getType(),
                                "<strong>State: </strong>" + p.getState()));
            }
            count.getAndIncrement();
        });

        tblItem.append("</tr>");
        return tblItem.toString();
    }
}
