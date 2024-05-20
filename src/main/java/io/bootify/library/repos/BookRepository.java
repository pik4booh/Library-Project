package io.bootify.library.repos;

import io.bootify.library.domain.Book;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("SELECT b.* " +
              "FROM public.book AS b "+
              "JOIN public.book_category AS bc ON b.id_book = bc.book_id "+
              "JOIN public.category AS c ON c.id_category = bc.category_id "+
              "WHERE title LIKE %:title% "
              )
       List<Book> findBooksByCriteria(
            @Param("title") String title
            );
}
