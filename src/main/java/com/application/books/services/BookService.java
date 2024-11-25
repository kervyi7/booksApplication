package com.application.books.services;

import org.springframework.stereotype.Service;
import com.application.books.models.AuthorDTO;
import com.application.books.models.Book;
import com.application.books.models.BookDTO;
import com.application.books.repos.BookRepository;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
  private final BookRepository bookRepository;

  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  public Optional<Book> findById(Long id) {
    return bookRepository.findById(id);
  }

  public Book save(Book book) {
    return bookRepository.save(book);
  }

  public void deleteById(Long id) {
    bookRepository.deleteById(id);
  }

  public BookDTO getBookWithAuthor(Long id) {
    Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    AuthorDTO authorDTO = new AuthorDTO(book.getAuthor().getId(), book.getAuthor().getName(),
        book.getAuthor().getSurname());
    return new BookDTO(book.getId(), book.getTitle(), authorDTO);
  }
}
