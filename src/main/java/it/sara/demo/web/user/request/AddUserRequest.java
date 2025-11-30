package it.sara.demo.web.user.request;

import it.sara.demo.web.request.GenericRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest extends GenericRequest {
    // Only letters, spaces, and apostrophes are allowed (no numbers, hyphens, commas, dots, parentheses, etc.)
    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    @Pattern(regexp = "^([A-Za-zÀ-ÖØ-öø-ÿ' ]+)?$", message = "First name contains invalid characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    @Pattern(regexp = "^([A-Za-zÀ-ÖØ-öø-ÿ' ]+)?$", message = "Last name contains invalid characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    private String email;

    // Italian mobile phone: starts with +39, then 3XX followed by 6-7 digits, optional spaces
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+39\\s?3\\d{2}\\s?\\d{6,7}$", message = "Phone number must be valid")
    private String phoneNumber;
}
