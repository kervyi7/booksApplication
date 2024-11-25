package com.application.books.models;

public class AuthorDTO {
  private Long id;
  private String name;
  private String surname;

  // Constructor
  public AuthorDTO(Long id, String name, String surname) {
    this.id = id;
    this.name = name;
    this.surname = surname;
  }

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }
}
