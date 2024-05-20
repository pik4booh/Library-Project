package io.bootify.library.repos;

import io.bootify.library.domain.CopyBook;
import io.bootify.library.domain.Member;
import io.bootify.library.domain.Sanction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface SanctionRepository extends JpaRepository<Sanction, Integer> {

    Sanction findFirstByMember(Member member);

    Sanction findFirstByCopyBook(CopyBook copyBook);

    @Query(value = "SELECT * FROM public.\"sanction\" sa\r\n" + //
                "WHERE sa.member_id = :idMember\r\n" + //
                "AND date_end = (\r\n" + //
                "\tSELECT MAX(sa.date_end) FROM public.\"sanction\" sa\r\n" + //
                "\tWHERE sa.member_id = :idMember\r\n" + //
                "\tAND date_end > CURRENT_DATE\r\n" + //
                ")", nativeQuery = true)
    Sanction findActiveSanctionByMemberId(@Param("idMember") int idMember);

}
