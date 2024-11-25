package com.application.books.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.application.books.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {}
