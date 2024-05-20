package io.bootify.library.repos;

import io.bootify.library.domain.Book;
import io.bootify.library.domain.BookTheme;
import io.bootify.library.domain.Theme;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookThemeRepository extends JpaRepository<BookTheme, Integer> {

    BookTheme findFirstByTheme(Theme theme);

    BookTheme findFirstByBook(Book book);

}
