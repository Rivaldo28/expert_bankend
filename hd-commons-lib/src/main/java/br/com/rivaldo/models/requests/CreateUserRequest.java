package br.com.rivaldo.models.requests;

import br.com.rivaldo.models.enums.ProfileEnum;
import lombok.With;

import java.util.Set;

@With
public record CreateUserRequest(
        String name,
        String email,
        String password,
        Set<ProfileEnum> profiles
) {}
