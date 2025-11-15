package br.com.rivaldo.userserviceapi.controller.impl;

import br.com.rivaldo.models.responses.UserResponse;
import br.com.rivaldo.userserviceapi.controller.UserController;
import br.com.rivaldo.userserviceapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<UserResponse> findById(String id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }

}
