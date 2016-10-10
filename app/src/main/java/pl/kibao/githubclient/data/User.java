package pl.kibao.githubclient.data;

// TODO Use autovalue
public class User {
    private static final String USER_TYPE = "User";
    public int id;
    public String username;
    public String email;
    public String name;
    public String avatarUrl;
    public String type;

    public boolean isUser() {
        return USER_TYPE.equalsIgnoreCase(type);
    }
}
