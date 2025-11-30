package it.sara.demo.service.util;

import static it.sara.demo.service.util.Constants.EMAIL_REGEX;
import static it.sara.demo.service.util.Constants.ITALIAN_PHONE_REGEX;
import static java.util.Optional.ofNullable;

public class ValidationUtil {

    public static boolean isValidEmail(String email) {
        return ofNullable(email).map(e -> e.matches(EMAIL_REGEX)).orElse(false);
    }

    public static boolean isValidItalianPhone(String phone) {
        return ofNullable(phone).map(p -> p.matches(ITALIAN_PHONE_REGEX)).orElse(false);
    }
}
