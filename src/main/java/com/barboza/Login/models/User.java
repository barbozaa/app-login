package com.barboza.Login.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.Collection;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "user")
public class User {
    @Id
    private String id;

    @NonNull
    private String name;

    @NonNull
    private String username;

    @JsonIgnore
    private String password;

    @NonNull
    private String email;

    @NonNull
    private Collection<Role> roles = new ArrayList<>();
}
