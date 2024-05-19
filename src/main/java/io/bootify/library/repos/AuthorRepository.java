package io.bootify.library.repos;

import io.bootify.library.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
