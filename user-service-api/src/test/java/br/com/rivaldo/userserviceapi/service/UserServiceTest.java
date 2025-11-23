package br.com.rivaldo.userserviceapi.service;

import br.com.rivaldo.models.exceptions.ResourceNotFoundException;
import br.com.rivaldo.models.requests.CreateUserRequest;
import br.com.rivaldo.models.requests.UpdateUserRequest;
import br.com.rivaldo.models.responses.UserResponse;
import br.com.rivaldo.userserviceapi.entity.User;
import br.com.rivaldo.userserviceapi.mapper.UserMapper;
import br.com.rivaldo.userserviceapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static br.com.rivaldo.userserviceapi.creator.CreatorUtils.generateMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Test
    void whenCallFindByIdWithValidThenReturnUserResponse() {
        when(repository.findById(anyString())).thenReturn(Optional.of(new User()));
        when(mapper.fromEntity(any(User.class))).thenReturn(generateMock(UserResponse.class));

        final var response = service.findById("123");

        assertNotNull(response);
        assertEquals(UserResponse.class, response.getClass());

        verify(repository, times(1)).findById(anyString());
        verify(mapper, times(1)).fromEntity(any(User.class));
    }

    @Test
    void whenCallFindByIdWithInvalidThenThrowResourceNotFoundException() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());
        try {
            service.findById("123");
        } catch (Exception ex) {
            assertEquals(ResourceNotFoundException.class, ex.getClass());
            assertEquals("Object not found. Id: 123, Type: UserResponse", ex.getMessage());
        }
        verify(repository, times(1)).findById(anyString());
        verify(mapper, times(0)).fromEntity(any(User.class));
    }

    @Test
void whenCallFindAllThenReturnListOfUserResponse() {
        when(repository.findAll()).thenReturn(List.of(new User(), new User()));
        when(mapper.fromEntity(any(User.class))).thenReturn(mock(UserResponse.class));

        final var response = service.findAll();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(UserResponse.class, response.get(0).getClass());

        verify(repository, times(1)).findAll();
        verify(mapper, times(2)).fromEntity(any(User.class));
    }

    @Test
    void whenCallSaveThenSuccess() {
        final var request = generateMock(CreateUserRequest.class);

        when(mapper.fromRequest(any())).thenReturn(new User());
        when(encoder.encode(anyString())).thenReturn("encoded");
        when(repository.save(any(User.class))).thenReturn(new User());
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());

        service.save(request);

        verify(mapper).fromRequest(request);
        verify(encoder).encode(request.password());
        verify(repository).save(any(User.class));
        verify(repository).findByEmail(request.email());
    }

    @Test
    void whenCallSaveWithInvalidEmailThenThrowDataIntegrityViolationException() {
        final var request = generateMock(CreateUserRequest.class);
        final var entity = generateMock(User.class);

        when(repository.findByEmail(anyString())).thenReturn(Optional.of(entity));

        try {
            service.save(request);
        } catch (Exception e) {
            assertEquals(DataIntegrityViolationException.class, e.getClass());
            assertEquals("Email [" + request.email() + "] already exists", e.getMessage());
        }

        verify(repository).findByEmail(request.email());
        verify(mapper, times(0)).fromRequest(request);
        verify(encoder, times(0)).encode(request.password());
        verify(repository, times(0)).save(any(User.class));

    }

    @Test
    void whenCallUpdateThenInvalidIdThenThrowResourceNotFoundException() {
        final var request = generateMock(UpdateUserRequest.class);

        when(repository.findById(anyString())).thenReturn(Optional.empty());

        try {
            service.update("123", request);
        } catch (Exception e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
            assertEquals("Object not found. Id: 123, Type: UserResponse", e.getMessage());
        }
        verify(repository).findById(anyString());
        verify(repository, times(0)).save(any(User.class));
        verify(mapper, times(0)).update(any(), any(User.class));
        verify(encoder, times(0)).encode(request.password());
    }

    @Test
    void whenCallUpdateWithValidParamsThenGetSuccess() {
        // ARRANGE
        final var id = "123";
        final var request = generateMock(UpdateUserRequest.class);
        final var entity = generateMock(User.class);
//        final var expectedResponse = generateMock(UserResponse.class);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(repository.findByEmail(request.email())).thenReturn(Optional.empty());
        when(encoder.encode(anyString())).thenReturn("encoded");
        when(mapper.update(any(), any())).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
//        when(mapper.fromEntity(entity)).thenReturn(expectedResponse);

        // ACT
        final var response = service.update(id, request);

        // ASSERT
//        assertNotNull(response);
//        assertEquals(expectedResponse, response);
        verify(repository).findById(id);
        verify(repository).findByEmail(request.email());
        verify(encoder).encode(request.password());
        verify(mapper, times(1)).fromEntity(entity);
        verify(mapper, times(1)).update(request, entity);
        verify(repository, times(1)).save(entity);
    }

}