package com.application.books.controllers;

import org.springframework.web.bind.annotation.*;
import com.application.books.models.Book;
import com.application.books.models.BookDTO;
import com.application.books.services.BookService;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
  private final BookService bookService;

  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping
  public List<Book> getAllBooks() {
    return bookService.findAll();
  }

  @GetMapping("/{id}")
  public Book getBookWithoutAuthor(@PathVariable Long id) {
    Book book = bookService.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    book.setAuthor(null);
    return book;
  }

  @GetMapping("/{id}/details")
  public BookDTO getBookWithAuthor(@PathVariable Long id) {
    return bookService.getBookWithAuthor(id);
  }

  @PostMapping
  public Book addBook(@RequestBody Book book) {
    return bookService.save(book);
  }

  @PutMapping("/{id}")
  public Book updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
    Book book = bookService.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    book.setTitle(updatedBook.getTitle());
    book.setAuthor(updatedBook.getAuthor());
    return bookService.save(book);
  }

  @DeleteMapping("/{id}")
  public void deleteBook(@PathVariable Long id) {
    bookService.deleteById(id);
  }
}