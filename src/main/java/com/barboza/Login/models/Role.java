package com.barboza.Login.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "role")
public class Role {
    @Id
    @Field("_Id")
    private String id;
    private String name;
}
