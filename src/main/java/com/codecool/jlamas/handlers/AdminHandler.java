package com.codecool.jlamas.handlers;

import com.codecool.jlamas.controllers.CityController;
import com.codecool.jlamas.controllers.CookieController;
import com.codecool.jlamas.controllers.GroupController;
import com.codecool.jlamas.controllers.MentorController;
import com.codecool.jlamas.database.SessionDAO;
import com.codecool.jlamas.database.UserDAO;
import com.codecool.jlamas.exceptions.InvalidCityDataException;
import com.codecool.jlamas.exceptions.InvalidGroupDataException;
import com.codecool.jlamas.exceptions.InvalidUserDataException;
import com.codecool.jlamas.models.account.Codecooler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class AdminHandler extends AbstractHandler implements HttpHandler {

    private static final String PROFILE = "templates/admin/admin.twig";
    private static final String LIST = "templates/admin/admin_list.twig";
    private static final String MENTOR_FORM = "templates/admin/admin_mentor_form.twig";
    private static final String CITY_FORM = "templates/admin/admin_city_form.twig";
    private static final String GROUP_FORM = "templates/admin/admin_group_form.twig";

    private Map<String, Callable> getCommands = new HashMap<String, Callable>();
    private Map<String, Callable> postCommands = new HashMap<String, Callable>();
    private Codecooler admin;
    private SessionDAO session = new SessionDAO();
    private CookieController cookieController = new CookieController();
    private Response responseCode = new Response();


    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();
        HttpCookie cookie = cookieController.getCookie(httpExchange);

        if (cookie != null) {
            this.admin = session.getUserByCookie(httpExchange);
            String userType = new UserDAO().getType(admin.getLogin().getValue());

            if (admin != null && userType.equals("admin")) {
                if (method.equals("GET")) {
                    response = this.findCommand(httpExchange, getCommands);
                }

                if (method.equals("POST")) {
                    response = this.findCommand(httpExchange, postCommands);
                }

                responseCode.sendOKResponse(response, httpExchange);

            } else {
                session.removeCookieFromDb(cookie);
                responseCode.sendRedirectResponse(httpExchange, "/");
            }
        } else {
            responseCode.sendRedirectResponse(httpExchange, "/");
        }
    }

    protected void addGetCommands (HttpExchange httpExchange) {
        this.getCommands.put("/admin", () -> {return this.displayProfile();} );
        this.getCommands.put("/admin/mentors/list", () -> {return this.displayMentors();} );
        this.getCommands.put("/admin/mentors/add", () -> {return this.displayMentorForm(null, null); } );
        this.getCommands.put("/admin/mentors/list/edit/.+", () -> {return this.displayMentorForm(httpExchange, null); } );
        this.getCommands.put("/admin/mentors/list/remove/.+", () -> { return this.removeMentor(httpExchange);} );
        this.getCommands.put("/admin/cities/list", () -> {return this.displayCities();} );
        this.getCommands.put("/admin/cities/add", () -> {return this.displayCityForm(null, null, null); } );
        this.getCommands.put("/admin/cities/list/remove/.+", () -> {return this.removeCity(httpExchange);} );
        this.getCommands.put("/admin/cities/list/edit/.+", () -> {return this.displayCityForm(httpExchange, null, null); } );
        this.getCommands.put("/admin/groups/add", () -> {return this.displayGroupForm(null, null); } );
        this.getCommands.put("/admin/groups/list", () -> {return this.displayGroups();} );
        this.getCommands.put("/admin/groups/list/remove/.+", () -> {return this.removeGroup(httpExchange);} );
        this.getCommands.put("/admin/groups/list/edit/.+", () -> {return this.displayGroupForm(null, null); } );
    }

    protected void addPostCommands (HttpExchange httpExchange) {
        postCommands.put("/admin/mentors/add", () -> { return this.addMentor(httpExchange);} );
        postCommands.put("/admin/mentors/list/edit/.+", () -> { return this.editMentor(httpExchange);} );
        postCommands.put("/admin/cities/add", () -> { return this.addCity(httpExchange);} );
        postCommands.put("/admin/cities/list/edit/[0-9]+", () -> { return this.editCity(httpExchange);} );
        postCommands.put("/admin/groups/add", () -> { return this.addGroup(httpExchange);} );
        postCommands.put("/admin/groups/list/edit/[0-9]+", () -> { return this.editGroup(httpExchange);} );

    }

    private String displayProfile() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(PROFILE);
        JtwigModel model = JtwigModel.newModel();

        model.with("login", "student");
        model.with("admin", this.admin);

        return template.render(model);
    }

    private String displayMentors() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(LIST);
        JtwigModel model = JtwigModel.newModel();

        model.with("login", "student");
        model.with("mentors", new MentorController().getAllMentors());

        return template.render(model);
    }

    private String displayGroups() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(LIST);
        JtwigModel model = JtwigModel.newModel();

        // instead of value 'student' login from cookie
        model.with("login", "student");
        model.with("groups", new GroupController().getAllGroups());

        return template.render(model);
    }

    private String displayCities() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(LIST);
        JtwigModel model = JtwigModel.newModel();

        // instead of value 'student' login from cookie
        model.with("login", "student");
        model.with("cities", new CityController().getAll());

        return template.render(model);
    }

    private String displayMentorForm(HttpExchange httpExchange, Map<String, String> inputs) {
        // where inputs is a html parsed inputs passed in retake if there was an exception catch (while adding to db)
        JtwigTemplate template = JtwigTemplate.classpathTemplate(MENTOR_FORM);
        JtwigModel model = JtwigModel.newModel();

        // instead of value 'student' login from cookie
        model.with("login", "student");

        if (inputs == null && httpExchange != null) {
            model.with("mentor", new MentorController().getMentor(this.parseStringFromURL(httpExchange)));
        }
        else if (inputs != null) {
            model.with("name", inputs.get("name"));
            model.with("surname", inputs.get("surname"));
        }
        model.with("groups", new GroupController().getAllGroups());

        return template.render(model);
    }

    private String displayCityForm(HttpExchange httpExchange, Map<String, String> inputs, String errmsg) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(CITY_FORM);
        JtwigModel model = JtwigModel.newModel();

        // instead of value 'student' login from cookie
        model.with("login", "student");

        if (httpExchange != null && httpExchange != null) {
            model.with("city", new CityController().getCity(this.parseIntFromURL(httpExchange)));
        }
        else if (inputs != null) {
            model.with("name", inputs.get("name"));
            model.with("shortname", inputs.get("shortname"));
        }
        model.with("msg", errmsg);

        return template.render(model);
    }

    private String displayGroupForm(HttpExchange httpExchange, String errmsg) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(GROUP_FORM);
        JtwigModel model = JtwigModel.newModel();

        CityController cityController = new CityController();
        GroupController groupController = new GroupController();

        // instead of value 'student' login from cookie
        model.with("login", "student");

        if (httpExchange != null) {
            model.with("group", new GroupController().getGroup(this.parseIntFromURL(httpExchange)));
        }
        model.with("errmsg", errmsg);
        model.with("cities", cityController.getAll());
        model.with("years", groupController.getYears());
        model.with("numbers", groupController.getAvailableGroupNumbers());

        return template.render(model);
    }

    private String addMentor(HttpExchange httpExchange) throws IOException {
        Map<String, String> inputs = this.parseUserInputsFromHttp(httpExchange);

        MentorController ctrl = new MentorController();
        try {
            ctrl.createMentorFromMap(inputs);
        } catch (InvalidUserDataException e) {
            return this.displayMentorForm(null, inputs);
        }

        return this.displayMentors();
    }

    private String editMentor(HttpExchange httpExchange) throws IOException {
        Map<String, String> inputs = this.parseUserInputsFromHttp(httpExchange);

        MentorController ctrl = new MentorController();
        try {
            ctrl.editMentorFromMap(inputs, this.parseStringFromURL(httpExchange));
        } catch (InvalidUserDataException e) {
            return this.displayMentorForm(httpExchange, inputs);
        }

        return this.displayMentors();
    }

    private String removeMentor(HttpExchange httpExchange) throws IOException {
        MentorController mentorController = new MentorController();
        mentorController.removeMentor(this.parseStringFromURL(httpExchange));

        return this.displayMentors();
    }

    private String addCity(HttpExchange httpExchange) throws IOException {
        Map<String, String> inputs = this.parseUserInputsFromHttp(httpExchange);

        CityController ctrl = new CityController();
        try {
            ctrl.createCityFromMap(inputs);
        } catch (InvalidCityDataException e) {
            return this.displayCityForm(null, inputs, e.getMessage());
        }

        return this.displayCities();
    }

    private String editCity(HttpExchange httpExchange) throws IOException {
        Map<String, String> inputs = this.parseUserInputsFromHttp(httpExchange);

        CityController ctrl = new CityController();
        try {
            ctrl.editCityFromMap(this.parseIntFromURL(httpExchange), inputs);
        } catch (InvalidCityDataException e) {
            return this.displayCityForm(null, inputs, e.getMessage());
        }

        return this.displayCities();
    }

    private String removeCity(HttpExchange httpExchange) throws IOException {
        CityController cityController = new CityController();
        cityController.removeCity(this.parseIntFromURL(httpExchange));

        return this.displayCities();
    }

    private String addGroup(HttpExchange httpExchange) throws IOException {
        Map<String, String> inputs = this.parseUserInputsFromHttp(httpExchange);

        GroupController ctrl = new GroupController();
        try {
            ctrl.createGroupFromMap(inputs);
        } catch(InvalidGroupDataException e) {
            return this.displayGroupForm(null, e.getMessage());
        }
        return this.displayGroups();
    }

    private String editGroup(HttpExchange httpExchange) throws IOException {
        Map<String, String> inputs = this.parseUserInputsFromHttp(httpExchange);

        GroupController ctrl = new GroupController();
        try {
            ctrl.editGroupFromMap(inputs, this.parseIntFromURL(httpExchange));
        } catch(InvalidGroupDataException e) {
            return this.displayGroupForm(httpExchange, e.getMessage());
        }
        return this.displayGroups();
    }

    private String removeGroup(HttpExchange httpExchange) {
        GroupController ctrl = new GroupController();
        ctrl.removeGroup(this.parseIntFromURL(httpExchange));

        return this.displayGroups();
    }

}