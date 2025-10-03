package co.com.bancolombia.model.signup.model;

import co.com.bancolombia.model.shared.common.value.Email;
import co.com.bancolombia.model.shared.common.value.Password;
import co.com.bancolombia.model.shared.exception.BusinessException;
import co.com.bancolombia.model.shared.exception.ConstantBusinessException;
import co.com.bancolombia.model.signup.value.Name;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
public class SignUpInformation {
    private final Email email;
    private final Password password;
    private final Name name;

    public SignUpInformation(Builder builder) {
        this.email = builder.email;
        this.password = builder.password;
        this.name = builder.name;

        validateMandatoryFields();
    }

    private void validateMandatoryFields() {
        if (email == null) {
            throw new BusinessException(ConstantBusinessException.MISSING_REQUIRED_FIELDS_EXCEPTION,
                    "Email information is required");
        }
        if (password == null) {
            throw new BusinessException(ConstantBusinessException.MISSING_REQUIRED_FIELDS_EXCEPTION,
                    "Password information is required");
        }
        if (name == null) {
            throw new BusinessException(ConstantBusinessException.MISSING_REQUIRED_FIELDS_EXCEPTION,
                    "Name information is required");
        }
    }

    public static class Builder {
        private Email email;
        private Password password;
        private Name name;

        public Builder email(String email) {
            nonNullCheck(email);
            this.email = new Email(email);
            return this;
        }

        public Builder password(String password) {
            nonNullCheck(password);
            this.password = new Password(password);
            return this;
        }

        public Builder name(String name) {
            this.name = new Name(name);
            return this;
        }

        public SignUpInformation build() {
            return new SignUpInformation(this);
        }

        private void nonNullCheck(Object... values) {
            if (Arrays.stream(values).anyMatch(Objects::isNull)) {
                throwMissingRequiredFields("Builder functions must be called with non-null values or non-empty lists");
            }
        }

        private void throwMissingRequiredFields(String message) {
            throw new BusinessException(ConstantBusinessException.MISSING_REQUIRED_FIELDS_EXCEPTION,
                    message);
        }
    }

}
