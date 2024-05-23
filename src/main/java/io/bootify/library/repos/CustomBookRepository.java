package io.bootify.library.repos;

import io.bootify.library.domain.Book;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public interface CustomBookRepository {
    List<Book> findBooksByCriteria(String title, String author, LocalDate releaseDate1, LocalDate releaseDate2, List<String> categories);
}
