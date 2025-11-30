package it.sara.demo.service.auth.impl;

import it.sara.demo.exception.GenericException;
import it.sara.demo.service.auth.JwtUtil;
import it.sara.demo.web.user.request.LoginRequest;
import it.sara.demo.web.user.response.LoginResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static it.sara.demo.web.user.request.LoginRequest.Role.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthServiceImpl authServiceImpl;

    @Test
    public void testLoginWithValidRequestAndUserRole() throws GenericException {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setUsername("validUsername");
        request.setRole(USER);
        String expectedToken = "mockedToken";
        when(jwtUtil.generateToken(request.getUsername(), Map.of("role", USER.getValue()))).thenReturn(expectedToken);

        // Act
        LoginResponse response = authServiceImpl.login(request);

        // Assert
        assertEquals(expectedToken, response.getToken());
        assertEquals(request.getUsername(), response.getUsername());
        assertEquals(USER, response.getRole());
        verify(jwtUtil, times(1)).generateToken(request.getUsername(), Map.of("role", USER.getValue()));
    }

    @Test
    public void testLoginWithValidRequestAndAdminRole() throws GenericException {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setUsername("adminUsername");
        request.setRole(LoginRequest.Role.ADMIN);

        // Act
        LoginResponse response = authServiceImpl.login(request);

        // Assert
        assertEquals(request.getUsername(), response.getUsername());
        assertEquals(LoginRequest.Role.ADMIN, response.getRole());
    }

    @Test
    public void testLoginWithNullUsername() {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setUsername(null);
        request.setRole(USER);

        // Act & Assert
        GenericException exception = assertThrows(GenericException.class, () -> authServiceImpl.login(request));
        assertEquals(500, exception.getStatus().getCode());
        assertEquals("Invalid username", exception.getStatus().getMessage());
        verify(jwtUtil, never()).generateToken(anyString(), anyMap());
    }

    @Test
    public void testLoginWithNullRoleDefaultsToUser() throws GenericException {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setUsername("defaultUser");
        request.setRole(null);

        // Act
        LoginResponse response = authServiceImpl.login(request);

        // Assert
        assertEquals(request.getUsername(), response.getUsername());
        assertEquals(USER, response.getRole());
    }
}