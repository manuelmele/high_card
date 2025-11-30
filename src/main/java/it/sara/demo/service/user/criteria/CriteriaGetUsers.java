package it.sara.demo.service.user.criteria;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import it.sara.demo.service.criteria.GenericCriteria;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriteriaGetUsers extends GenericCriteria {

    private String query;
    private int offset;
    private int limit;
    private OrderType order;

    @Getter
    public enum OrderType {
        BY_FIRSTNAME("by firstName"),
        BY_FIRSTNAME_DESC("by firstName desc"),
        BY_LASTNAME("by lastName"),
        BY_LASTNAME_DESC("by lastName");
        private final String displayName;

        OrderType(String displayName) {
            this.displayName = displayName;
        }

        @JsonCreator
        public static OrderType from(String value) throws InvalidFormatException {
            if (value == null || value.isBlank()) {
                return null;
            }
            try {
                return OrderType.valueOf(value.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new InvalidFormatException(
                        null,
                        "Invalid order type. Accepted values are: BY_FIRSTNAME, BY_FIRSTNAME_DESC, BY_LASTNAME, BY_LASTNAME_DESC",
                        value,
                        OrderType.class
                );
            }
        }
    }

}
