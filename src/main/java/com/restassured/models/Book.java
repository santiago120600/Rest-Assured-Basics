package com.restassured.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book {
    Integer id;
    String title;
    String isbn;
    @JsonProperty("author_id")
    Integer authorId;
    @JsonProperty("aisle_number")
    Integer aisle;
    Author author;
}
