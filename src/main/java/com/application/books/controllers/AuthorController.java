package com.application.books.controllers;

import org.springframework.web.bind.annotation.*;
import com.application.books.models.Author;
import com.application.books.services.AuthorService;
import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
  private final AuthorService authorService;

  public AuthorController(AuthorService authorService) {
    this.authorService = authorService;
  }

  @GetMapping
  public List<Author> getAllAuthors() {
    return authorService.findAll();
  }

  @GetMapping("/{id}")
  public Author getAuthorWithoutBooks(@PathVariable Long id) {
    Author author = authorService.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
    author.setBooks(null);
    return author;
  }

  @GetMapping("/{id}/details")
  public Author getAuthorWithBooks(@PathVariable Long id) {
    Author author = authorService.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
    author.getBooks().size();
    return author;
  }

  @PostMapping
  public Author addAuthor(@RequestBody Author author) {
    return authorService.save(author);
  }

  @PutMapping("/{id}")
  public Author updateAuthor(@PathVariable Long id, @RequestBody Author updatedAuthor) {
    Author author = authorService.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
    author.setName(updatedAuthor.getName());
    author.setSurname(updatedAuthor.getSurname());
    return authorService.save(author);
  }

  @DeleteMapping("/{id}")
  public void deleteAuthor(@PathVariable Long id) {
    authorService.deleteById(id);
  }
}
