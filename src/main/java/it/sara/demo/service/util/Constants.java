package it.sara.demo.service.util;

public class Constants {

    // Italian phone regex: +39 prefix, numbers start with 3 (mobile) or 0 (landline)
    public static final String ITALIAN_PHONE_REGEX = "^\\+39\\s?(3\\d{8,9}|0\\d{8,10})$";

    //Email regex
    public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
}
