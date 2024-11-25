package com.application.books.models;

public class BookDTO {
  private Long id;
  private String title;
  private AuthorDTO author;

  // Constructor
  public BookDTO(Long id, String title, AuthorDTO author) {
      this.id = id;
      this.title = title;
      this.author = author;
  }

  // Getters and Setters
  public Long getId() {
      return id;
  }

  public void setId(Long id) {
      this.id = id;
  }

  public String getTitle() {
      return title;
  }

  public void setTitle(String title) {
      this.title = title;
  }

  public AuthorDTO getAuthor() {
      return author;
  }

  public void setAuthor(AuthorDTO author) {
      this.author = author;
  }
}
