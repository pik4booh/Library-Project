package io.bootify.library.repos;

import io.bootify.library.domain.CopyBook;
import io.bootify.library.domain.Loaning;
import io.bootify.library.domain.Member;
import io.bootify.library.domain.ReturnLoaning;
import io.bootify.library.domain.TypeLoaning;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LoaningRepository extends JpaRepository<Loaning, Integer> {

    Loaning findFirstByMember(Member member);

    Loaning findFirstByCopyBook(CopyBook copyBook);

    Loaning findFirstByTypeLoaning(TypeLoaning typeLoaning);
    Loaning findFirstByReturnLoaning(ReturnLoaning returnLoaning);

}
