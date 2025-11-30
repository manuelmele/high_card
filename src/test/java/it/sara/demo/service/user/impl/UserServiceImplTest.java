package it.sara.demo.service.user.impl;

import it.sara.demo.exception.GenericException;
import it.sara.demo.service.database.UserRepository;
import it.sara.demo.service.database.model.User;
import it.sara.demo.service.user.criteria.CriteriaAddUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testAddUser_MissingFirstName() throws GenericException {
        // Arrange
        CriteriaAddUser criteria = new CriteriaAddUser();
        criteria.setFirstName(null);
        criteria.setLastName("Doe");
        criteria.setEmail("john.doe@example.com");
        criteria.setPhoneNumber("+393456789012");

        // Act & Assert
        GenericException exception = assertThrows(GenericException.class, () -> userService.addUser(criteria));
        assertEquals(400, exception.getStatus().getCode());
        assertEquals("First name is required", exception.getStatus().getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testAddUser_EmptySecondName() throws GenericException {
        // Arrange
        CriteriaAddUser criteria = new CriteriaAddUser();
        criteria.setFirstName("John");
        criteria.setLastName("");
        criteria.setEmail("john.doe@example.com");
        criteria.setPhoneNumber("+393456789012");

        // Act & Assert
        GenericException exception = assertThrows(GenericException.class, () -> userService.addUser(criteria));
        assertEquals(400, exception.getStatus().getCode());
        assertEquals("Last name is required", exception.getStatus().getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testAddUser_InvalidEmail() throws GenericException {
        // Arrange
        CriteriaAddUser criteria = new CriteriaAddUser();
        criteria.setFirstName("John");
        criteria.setLastName("Doe");
        criteria.setEmail("invalid_email");
        criteria.setPhoneNumber("+393456789012");

        // Act & Assert
        GenericException exception = assertThrows(GenericException.class, () -> userService.addUser(criteria));
        assertEquals(500, exception.getStatus().getCode());
        assertEquals("Invalid email address", exception.getStatus().getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testAddUser_InvalidItalianPhone() throws GenericException {
        // Arrange
        CriteriaAddUser criteria = new CriteriaAddUser();
        criteria.setFirstName("John");
        criteria.setLastName("Doe");
        criteria.setEmail("john.doe@example.com");
        criteria.setPhoneNumber("12345");

        // Act & Assert
        GenericException exception = assertThrows(GenericException.class, () -> userService.addUser(criteria));
        assertEquals(500, exception.getStatus().getCode());
        assertEquals("Invalid Italian phone number", exception.getStatus().getMessage());
        verify(userRepository, never()).save(any(User.class));
    }
}