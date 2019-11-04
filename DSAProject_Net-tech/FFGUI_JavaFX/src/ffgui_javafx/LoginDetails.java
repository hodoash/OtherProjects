package ffgui_javafx;

import java.util.Objects;

/**
 * This is a class that stores login details(username and password) as an object
 */
public class LoginDetails {
    //declare instance variables
    String username;
    String password;

    public LoginDetails(String name, String pass){
        this.username = name;
        this.password = pass;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoginDetails)) return false;
        LoginDetails that = (LoginDetails) o;
        return Objects.equals(getUsername(), that.getUsername()) &&
                Objects.equals(getPassword(), that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword());
    }
}
