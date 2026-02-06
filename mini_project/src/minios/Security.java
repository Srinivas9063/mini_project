package minios;
public abstract class Security {
    abstract boolean authenticate(String username, String password);
}