package br.com.rivaldo.userserviceapi.service;

import br.com.rivaldo.models.responses.UserResponse;
import br.com.rivaldo.userserviceapi.mapper.UserMapper;
import br.com.rivaldo.userserviceapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse findById(final String id) {
        return userMapper.fromEntity(userRepository.findById(id).orElse(null));
    }

}
