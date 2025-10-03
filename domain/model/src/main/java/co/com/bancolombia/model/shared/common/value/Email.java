package co.com.bancolombia.model.shared.common.value;

import java.util.regex.Pattern;

public class Email extends AbstractString {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]{1,30}@[a-zA-Z0-9.-]{1,61}$");

    public Email(String value) {
        super(value, EMAIL_PATTERN);
    }

    @Override
    public String getValue() { return value; }
}
