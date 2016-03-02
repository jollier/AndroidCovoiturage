package model;

/**
 * Created by jollier on 02/03/2016.
 */
public class UserConnect {

    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user2) {
        user = user2;
    }

    public UserConnect() {
    }
}
