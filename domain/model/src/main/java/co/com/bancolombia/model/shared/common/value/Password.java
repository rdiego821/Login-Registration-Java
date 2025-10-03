package co.com.bancolombia.model.shared.common.value;

import java.util.regex.Pattern;

public class Password extends AbstractString {
    private static final int MIN_LENGTH = 8;

    public Password(String value) {
        super(value, MIN_LENGTH);
    }

    @Override
    public String getValue() { return value; }
}
