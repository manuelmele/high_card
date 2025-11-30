package it.sara.demo.web.user.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import it.sara.demo.service.user.criteria.CriteriaGetUsers;
import it.sara.demo.web.request.GenericRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest extends GenericRequest {
    @Size(max = 50, message = "Username cannot exceed 50 characters")
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ0-9' ]*$",
            message = "Username contains invalid characters")
    private String username;

    /**
     * Represents the role assigned to a user in a login request.
     * The possible values are:
     * - USER: Represents a standard user.
     * - ADMIN: Represents an administrative user.
     */
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @NotNull(message = "Role is required")
    private Role role;

    @Getter
    public enum Role {
        USER("USER"),
        ADMIN("ADMIN");

        private final String value;

        Role(String value) {
            this.value = value;
        }

        @JsonCreator
        public static Role from(String value) throws InvalidFormatException {
            if (value == null || value.isBlank()) {
                return null;
            }
            try {
                return Role.valueOf(value.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new InvalidFormatException(
                        null,
                        "Invalid role. Accepted values are: USER, ADMIN",
                        value,
                        CriteriaGetUsers.OrderType.class
                );
            }
        }
    }
}