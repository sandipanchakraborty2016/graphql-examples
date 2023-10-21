package com.example.graphqlexamples.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {

    private Integer id;
    private String name;
    private Integer age;

}
