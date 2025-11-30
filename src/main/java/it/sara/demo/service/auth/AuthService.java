package it.sara.demo.service.auth;

import it.sara.demo.exception.GenericException;
import it.sara.demo.web.user.request.LoginRequest;
import it.sara.demo.web.user.response.LoginResponse;

public interface AuthService {

    /**
     * Authenticates the user based on the provided login request and returns a login response.
     *
     * @param request the login request containing the username and role to authenticate
     * @return a {@link LoginResponse} containing the authentication token, username, and role
     * @throws GenericException if there is an error during the login process
     */
    LoginResponse login(LoginRequest request) throws GenericException;
}
