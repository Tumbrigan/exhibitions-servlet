package com.kucher.command;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static Map<String, Command> map;

    private CommandFactory() {
    }

    static {
        map = new HashMap<>();
        map.put("registration", new GetRegistrationPageCommand());
        map.put("register-user", new RegisterNewUserCommand());
        map.put("login", new GetLoginPageCommand());
        map.put("logout", new LogoutCommand());
        map.put("home", new LoginIntoSystemCommand());
        map.put("user/home", new GetUserHomeCommand());
        map.put("admin/users", new GetUsersPageCommand());
        map.put("admin/home", new GetAdminHomeCommand());
        map.put("admin/exhibitions", new GetExhibitionsPageCommand());
        map.put("admin/create-exhibition", new CreateExhibitionCommand());
        map.put("admin/delete-exhibition", new DeleteExhibitionCommand());
        map.put("admin/deactivate-exhibition", new CancelExhibitionCommand());
        map.put("admin/activate-exhibition", new ActivateExhibitionCommand());


    }

    public static Command getCommand(String command) {
        return map.get(command);
    }
}
