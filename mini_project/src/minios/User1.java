package minios;
class User1 {

    private String username;
    private String password;

    public void setCredentials(String u, String p) {
        username = u;
        password = p;
    }

    public boolean validate(String u, String p) {
        return username.equals(u) && password.equals(p);
    }

    public String getUsername() {
        return username;
    }
}
