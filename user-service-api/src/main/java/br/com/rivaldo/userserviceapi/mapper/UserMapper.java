package br.com.rivaldo.userserviceapi.mapper;

import br.com.rivaldo.models.requests.CreateUserRequest;
import br.com.rivaldo.models.requests.UpdateUserRequest;
import br.com.rivaldo.models.responses.UserResponse;
import br.com.rivaldo.userserviceapi.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE,
        nullValueCheckStrategy = ALWAYS
)
public interface UserMapper {

//    @Mapping(target = "profiles", source = "profile")
    UserResponse fromEntity(final User entity);

    @Mapping(target = "id", ignore = true)
    User fromRequest(CreateUserRequest createUserRequest);

    @Mapping(target = "id", ignore = true)
    User update(UpdateUserRequest updateUserRequest, @MappingTarget User entity);
}
