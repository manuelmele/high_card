package it.sara.demo.web.user.response;

import it.sara.demo.web.user.request.LoginRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    public String token;
    public String username;
    public LoginRequest.Role role;

    public LoginResponse(String token, String username, LoginRequest.Role role) {
        this.token = token;
        this.username = username;
        this.role = role;
    }

    @Override
    public String toString() {
        return String.format("token: %s, username: %s, role: %s", token, username, role.getValue());
    }
}