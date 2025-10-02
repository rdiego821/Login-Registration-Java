package co.com.bancolombia.api.signup.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @Valid
    @NotNull(message = "email no puede ser nulo")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]{1,30}@[a-zA-Z0-9.-]{1,61}$",
            message = "el formato del email no es valido"
    )
    @JsonProperty("email")
    private String email;

    @Valid
    @NotNull(message = "password no puede ser nulo")
    @Size(min = 8, message = "password con longitud invalida")
    @JsonProperty("password")
    private String password;

    @Valid
    @NotNull(message = "name no puede ser nulo")
    @JsonProperty("name")
    private String name;
}
