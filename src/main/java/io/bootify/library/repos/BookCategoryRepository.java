package io.bootify.library.repos;

import io.bootify.library.domain.Book;
import io.bootify.library.domain.BookCategory;
import io.bootify.library.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookCategoryRepository extends JpaRepository<BookCategory, Integer> {

    BookCategory findFirstByCategory(Category category);

    BookCategory findFirstByBook(Book book);

}
