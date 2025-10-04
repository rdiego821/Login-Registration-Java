package co.com.bancolombia.model.shared.common.value;

import java.util.regex.Pattern;

public class Password extends AbstractString {
    private static final int MIN_LENGTH = 8;

    public Password(String value) {
        super(value, MIN_LENGTH);
    }

    @Override
    public String getValue() { return value; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Password password = (Password) obj;
        return value.equals(password.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
