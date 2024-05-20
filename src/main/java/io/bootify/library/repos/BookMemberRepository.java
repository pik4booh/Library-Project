package io.bootify.library.repos;

import io.bootify.library.domain.Book;
import io.bootify.library.domain.BookMember;
import io.bootify.library.domain.TypeMember;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookMemberRepository extends JpaRepository<BookMember, Integer> {

    BookMember findFirstByBook(Book book);

    BookMember findFirstByTypeMember(TypeMember typeMember);

}
