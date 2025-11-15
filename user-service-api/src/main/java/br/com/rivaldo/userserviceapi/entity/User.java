package br.com.rivaldo.userserviceapi.entity;

import br.com.rivaldo.models.enums.ProfileEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@With
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private Set<ProfileEnum> profiles;
}
