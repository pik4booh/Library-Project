package io.bootify.library.repos;

import io.bootify.library.domain.Book;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BookRepository extends JpaRepository<Book, Integer> {
       @Query(value = "SELECT b.* " +
       "FROM public.book AS b " +
       "JOIN public.book_category AS bc ON b.id_book = bc.book_id " +
       "JOIN public.category AS c ON c.id_category = bc.category_id " +
       "WHERE (COALESCE(:title, '') = '' OR b.title LIKE %:title%) " +
       "AND (COALESCE(:author, '') = '' OR b.author LIKE %:author%) " +
       "AND (COALESCE(:releaseDate1, '') = '' OR b.release_date BETWEEN :releaseDate1 AND :releaseDate2) " +
       "AND (COALESCE(:categoryIds, '') = '' OR c.name IN (:categoryIds)) ",
       nativeQuery = true)
       List<Book> findBooksByCriteria(@Param("title") String title,
              @Param("author") String author,
              @Param("releaseDate1") LocalDate releaseDate1,
              @Param("releaseDate2") LocalDate releaseDate2,
              @Param("categoryIds") String categoryIds
       );

}
