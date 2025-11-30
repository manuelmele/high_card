package it.sara.demo.web.user.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import it.sara.demo.service.user.criteria.CriteriaGetUsers;
import it.sara.demo.web.request.GenericRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUsersRequest extends GenericRequest {
    // Optional search query, letters, numbers, spaces allowed, max 100 chars
    @Size(max = 100, message = "Query cannot exceed 100 characters")
    private String query;

    // Pagination offset >= 0
    @Min(value = 0, message = "Offset cannot be negative")
    private int offset;

    // Pagination limit > 0 and reasonable upper bound
    @Min(value = 1, message = "Limit must be at least 1")
    @Max(value = 100, message = "Limit cannot exceed 100")
    private int limit;

    /**
     * Specifies the ordering type to fetch users.
     * Possible values:
     * - BY_FIRSTNAME: Orders by the user's first name in ascending order.
     * - BY_FIRSTNAME_DESC: Orders by the user's first name in descending order.
     * - BY_LASTNAME: Orders by the user's last name in ascending order.
     * - BY_LASTNAME_DESC: Orders by the user's last name in descending order.
     */
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @NotNull(message = "Order type is required")
    private CriteriaGetUsers.OrderType order;
}
