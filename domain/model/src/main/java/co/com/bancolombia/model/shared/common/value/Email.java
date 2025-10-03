package co.com.bancolombia.model.shared.common.value;

import java.util.regex.Pattern;

public class Email extends AbstractString {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]{1,30}@[a-zA-Z0-9.-]{1,61}$");

    public Email(String value) {
        super(value, EMAIL_PATTERN);
    }

    @Override
    public String getValue() { return value; }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return value.equals(email.value);
    }
}
