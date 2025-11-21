package br.com.rivaldo.models.requests;

import br.com.rivaldo.models.enums.ProfileEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.With;

import java.util.Set;

@With
public record UpdateUserRequest(
        @Schema(description = "User name" , example = "Rivaldo Souza")
        @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
        String name,

        @Schema(description = "User email", example = "rivaldo@mail.com")
        @Email(message = "Invalid email")
        @Size(min = 6, max = 50, message = "Email must contain between 3 and 50 characters")
        String email,

        @Schema(description = "User password", example = "123456")
        @Size(min = 6, max = 20, message = "Password must contain between 6 and 20 characters")
        String password,

        @Schema(description = "User profiles", example = "[\"ROLE_ADMIN\", \"ROLE_CUSTOMER\", \"ROLE_TECHNICIAN\"]")
        Set<ProfileEnum> profiles
) {}
