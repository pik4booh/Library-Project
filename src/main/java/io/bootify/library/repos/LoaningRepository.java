package io.bootify.library.repos;

import io.bootify.library.domain.CopyBook;
import io.bootify.library.domain.Loaning;
import io.bootify.library.domain.Member;
import io.bootify.library.domain.ReturnLoaning;
import io.bootify.library.domain.TypeLoaning;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface LoaningRepository extends JpaRepository<Loaning, Integer> {

    Loaning findFirstByMember(Member member);

    Loaning findFirstByCopyBook(CopyBook copyBook);

    Loaning findFirstByTypeLoaning(TypeLoaning typeLoaning);
    Loaning findFirstByReturnLoaning(ReturnLoaning returnLoaning);

    @Query(value = "SELECT lo.* FROM public.loaning lo " +
    "LEFT JOIN public.return_loaning rl ON lo.id_loaning = rl.loaning_id " +
    "WHERE lo.member_id = :idMember AND rl.loaning_id IS NULL", nativeQuery = true)
    List<Loaning> findActiveLoaningByMember(@Param("idMember") int idMember);

    Loaning findFirstByReturnLoaning(ReturnLoaning returnLoaning);

    boolean existsByReturnLoaningIdReturnLoaning(Long idReturnLoaning);

}
