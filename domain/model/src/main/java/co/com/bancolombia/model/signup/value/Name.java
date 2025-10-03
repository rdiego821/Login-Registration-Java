package co.com.bancolombia.model.signup.value;

import co.com.bancolombia.model.shared.common.value.AbstractString;

public class Name extends AbstractString {
    public Name(String inputValue) { super(inputValue); }

    @Override
    public String getValue() { return value; }
}
