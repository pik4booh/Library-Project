package io.bootify.library.repos;

import io.bootify.library.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Integer> {
}
