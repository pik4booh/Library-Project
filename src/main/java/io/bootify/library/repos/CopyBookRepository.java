package io.bootify.library.repos;

import io.bootify.library.domain.Book;
import io.bootify.library.domain.CopyBook;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CopyBookRepository extends JpaRepository<CopyBook, Integer> {

    CopyBook findFirstByBook(Book book);
    CopyBook findById(int idCopyBook);

}
