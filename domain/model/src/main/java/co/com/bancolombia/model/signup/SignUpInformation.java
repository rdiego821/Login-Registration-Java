package co.com.bancolombia.model.signup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class SignUpInformation {
    private final String email;
    private final String password;
    private final String name;
}
