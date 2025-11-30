package it.sara.demo.service.auth.impl;

import it.sara.demo.exception.GenericException;
import it.sara.demo.service.auth.AuthService;
import it.sara.demo.service.auth.JwtUtil;
import it.sara.demo.service.util.StringUtil;
import it.sara.demo.web.user.request.LoginRequest;
import it.sara.demo.web.user.response.LoginResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

import static it.sara.demo.web.user.request.LoginRequest.Role.USER;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public LoginResponse login(LoginRequest request) throws GenericException {
        if (StringUtil.isNullOrEmpty(request.getUsername())) {
            throw new GenericException(500, "Invalid username");
        }

        LoginRequest.Role role = Objects.isNull(request.getRole()) ? USER : request.getRole();
        String token = jwtUtil.generateToken(request.getUsername(), Map.of("role", role.getValue()));

        return new LoginResponse(token, request.getUsername(), role);
    }
}
