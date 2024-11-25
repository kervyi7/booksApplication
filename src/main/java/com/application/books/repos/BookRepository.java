package com.application.books.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.application.books.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {}