package com.user.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Transient;
import java.time.LocalDateTime;

@Document(collection = "users")
@Getter
@Setter
public class User {

    @Transient  
    public static final String SEQUENCE_NAME = "user_sequence";  // Auto-increment sequence

    @Id
    private String id;
    private String name;
    private String email;
    private int age;
    private String gender;
    private String city;
    private String state;
    private String country;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User() {}

    public User(String id, String name, String email, int age, String gender, String city, String state, String country) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.city = city;
        this.state = state;
        this.country = country;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

}
