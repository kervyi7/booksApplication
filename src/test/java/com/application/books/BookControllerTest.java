package com.application.books;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.application.books.models.Author;
import com.application.books.models.AuthorDTO;
import com.application.books.models.Book;
import com.application.books.models.BookDTO;
import com.application.books.services.BookService;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BookService bookService;

  private Book book;
  private Author author;

  @BeforeEach
  public void setUp() {
    author = new Author(1L, "Alice", "Wonder", null);
    book = new Book(1L, "Wonderland", author);
  }

  @Test
  public void testGetAllBooks() throws Exception {
    List<Book> books = List.of(book);
    when(bookService.findAll()).thenReturn(books);
    mockMvc.perform(get("/book"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].title").value("Wonderland"));
  }

  @Test
  public void testGetBookWithoutAuthor() throws Exception {
    when(bookService.findById(1L)).thenReturn(Optional.of(book));
    mockMvc.perform(get("/book/{id}", 1L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.title").value("Wonderland"))
        .andExpect(jsonPath("$.author").doesNotExist());
  }

  @Test
  public void testGetBookWithAuthor() throws Exception {
    when(bookService.getBookWithAuthor(1L))
        .thenReturn(new BookDTO(1L, "Wonderland", new AuthorDTO(1L, "Alice", "Wonder")));
    mockMvc.perform(get("/book/{id}/details", 1L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title").value("Wonderland"))
        .andExpect(jsonPath("$.author.name").value("Alice"))
        .andExpect(jsonPath("$.author.surname").value("Wonder"));
  }

  @Test
  public void testAddBook() throws Exception {
    Author author = new Author(1L, "Alice", "Wonder", null);
    Book book = new Book(null, "Wonderland", author);
    when(bookService.save(any(Book.class))).thenReturn(book);
    mockMvc.perform(post("/book")
        .contentType("application/json")
        .content("{ \"title\": \"Wonderland\", \"author\": { \"id\": 1 } }"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title").value("Wonderland"));
  }

  @Test
  public void testUpdateBook() throws Exception {
    Author author = new Author(1L, "Alice", "Wonder", null);
    Book originalBook = new Book(1L, "Wonderland", author);
    Book updatedBook = new Book(1L, "Wonderland Updated", author);
    when(bookService.findById(1L)).thenReturn(Optional.of(originalBook));
    when(bookService.save(any(Book.class))).thenReturn(updatedBook);
    mockMvc.perform(put("/book/{id}", 1L)
        .contentType("application/json")
        .content("{ \"title\": \"Wonderland Updated\", \"author\": { \"id\": 1 } }"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title").value("Wonderland Updated"));
  }

  @Test
  public void testDeleteBook() throws Exception {
    when(bookService.findById(1L)).thenReturn(Optional.of(book));
    mockMvc.perform(delete("/book/{id}", 1L))
        .andExpect(status().isOk());
  }
}
