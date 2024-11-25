package com.application.books;

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
import com.application.books.models.Book;
import com.application.books.services.AuthorService;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    private Author author;

    @BeforeEach
    public void setUp() {
        author = new Author(1L, "Alice", "Wonder", null);
    }

    @Test
    public void testGetAllAuthors() throws Exception {
        List<Author> authors = List.of(author);
        when(authorService.findAll()).thenReturn(authors);
        mockMvc.perform(get("/author"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Alice"))
                .andExpect(jsonPath("$[0].surname").value("Wonder"));
    }

    @Test
    public void testGetAuthorWithoutBooks() throws Exception {
        when(authorService.findById(1L)).thenReturn(Optional.of(author));
        mockMvc.perform(get("/author/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.surname").value("Wonder"))
                .andExpect(jsonPath("$.books").doesNotExist());
    }

    @Test
    public void testGetAuthorWithBooks() throws Exception {
        List<Book> books = List.of(new Book(1L, "Wonderland", author));
        author.setBooks(books);
        when(authorService.findById(1L)).thenReturn(Optional.of(author));
        mockMvc.perform(get("/author/{id}/details", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.surname").value("Wonder"))
                .andExpect(jsonPath("$.books[0].title").value("Wonderland"));
    }

    @Test
    public void testAddAuthor() throws Exception {
        Author newAuthor = new Author(null, "Bob", "Smith", null);
        when(authorService.save(any(Author.class))).thenReturn(newAuthor);
        mockMvc.perform(post("/author")
                .contentType("application/json")
                .content("{\"name\":\"Bob\",\"surname\":\"Smith\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bob"))
                .andExpect(jsonPath("$.surname").value("Smith"));
    }

    @Test
    public void testUpdateAuthor() throws Exception {
        Author updatedAuthor = new Author(1L, "Alice", "Wonderland", null);
        when(authorService.findById(1L)).thenReturn(Optional.of(author));
        when(authorService.save(any(Author.class))).thenReturn(updatedAuthor);
        mockMvc.perform(put("/author/{id}", 1L)
                .contentType("application/json")
                .content("{\"name\":\"Alice\",\"surname\":\"Wonderland\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.surname").value("Wonderland"));
    }

    @Test
    public void testDeleteAuthor() throws Exception {
        when(authorService.findById(1L)).thenReturn(Optional.of(author));
        mockMvc.perform(delete("/author/{id}", 1L))
                .andExpect(status().isOk());
    }
}
