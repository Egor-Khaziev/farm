package shell.api.utils;

public class AuthMessage {
    private boolean auth;
    private String token;

    public AuthMessage() {
    }

    public String getToken() {
        return token;
    }

    public boolean isAuth() {
        return auth;
    }

    public AuthMessage(boolean auth, String token) {
        this.auth = auth;
        this.token = token;
    }
}
