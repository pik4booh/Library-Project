package io.bootify.library.repos;

import io.bootify.library.domain.CopyBook;
import io.bootify.library.domain.Member;
import io.bootify.library.domain.Sanction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SanctionRepository extends JpaRepository<Sanction, Integer> {

    Sanction findFirstByMember(Member member);

    Sanction findFirstByCopyBook(CopyBook copyBook);

}
