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

    @Query(value = "SELECT t.borrowCount, b.title, b.author, b.summary "+
    "FROM ( "+
        "SELECT c.book_id, COUNT(l.id_loaning) AS borrowCount "+
        "FROM Loaning l "+
        "LEFT JOIN copy_book c ON l.copy_book_id = c.id_copy_book "+
        "GROUP BY c.book_id "+
    ") AS t "+
    "JOIN book b ON t.book_id = b.id_book LIMIT(:lim) ", nativeQuery = true)
    List<Object[]> findMostBorrowedBooks(@Param("lim") int lim);

    @Query(value = "SELECT " + 
    "c.*, " +
    "CASE " +
        "WHEN latest_loan.latest_loan_id IS NULL OR rl.id_return_loaning IS NOT NULL THEN 1 " +
        "ELSE 0 " +
    "END AS available " +
    "FROM copy_book c " +
    "LEFT JOIN ( " +
        "SELECT " +
            "l.copy_book_id, " +
            "MAX(l.id_loaning) AS latest_loan_id " +
        "FROM " + 
            "loaning l " +
        "GROUP BY " +
            "l.copy_book_id " +
    ") latest_loan ON c.id_copy_book = latest_loan.copy_book_id " +
    "LEFT JOIN " +
        "return_loaning rl ON latest_loan.latest_loan_id = rl.loaning_id " +
    "WHERE c.book_id = :idBook " +
    "ORDER BY available DESC ", nativeQuery = true)
    List<Object[]> checkCopyBookAvailability(@Param("idBook") int idBook);

}
