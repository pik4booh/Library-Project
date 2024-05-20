package io.bootify.library.repos;

import io.bootify.library.domain.Loaning;
import io.bootify.library.domain.ReturnLoaning;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReturnLoaningRepository extends JpaRepository<ReturnLoaning, Integer> {

    ReturnLoaning findFirstByLoaning(Loaning loaning);

    boolean existsByLoaningIdLoaning(Integer idLoaning);

}
