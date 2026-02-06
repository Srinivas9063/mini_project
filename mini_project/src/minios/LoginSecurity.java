package minios;
class LoginSecurity extends Security {

    private User1 user1;

    LoginSecurity(User1 user) {
        this.user1 = user;
    }

    @Override
    boolean authenticate(String username, String password) {
        return user1.validate(username, password);
    }
}
