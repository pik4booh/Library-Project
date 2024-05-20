package io.bootify.library.repos;

import io.bootify.library.domain.Book;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("SELECT b FROM Book b JOIN b.categories c WHERE " +
           "(:title IS NULL OR b.title LIKE %:title%) AND " +
           "(:author IS NULL OR b.author LIKE %:author%) AND " +
           "(:releaseDate1 IS NULL OR b.releaseDate >= :releaseDate1) AND " +
           "(:releaseDate2 IS NULL OR b.releaseDate <= :releaseDate2) AND " +
           "(COALESCE(:categoryIds) IS NULL OR c.id IN :categoryIds)")
    List<Book> findBooksByCriteria(
            @Param("title") String title,
            @Param("author") String author,
            @Param("releaseDate1") LocalDate releaseDate1,
            @Param("releaseDate2") LocalDate releaseDate2,
            @Param("categoryIds") List<Long> categoryIds);
}
