package com.example.welcomeservice.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student implements Serializable {

    private Long id;
    private String name;
    private String email;
    private Integer age;
    private String address;
    private String course;
    private String status; // PENDING, PROCESSED
}
