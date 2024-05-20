package io.bootify.library.service;

import io.bootify.library.domain.Loaning;
import io.bootify.library.domain.Member;
import io.bootify.library.domain.ReturnLoaning;
import io.bootify.library.domain.Sanction;
import io.bootify.library.model.ReturnLoaningDTO;
import io.bootify.library.repos.LoaningRepository;
import io.bootify.library.repos.ReturnLoaningRepository;
import io.bootify.library.repos.SanctionRepository;
import io.bootify.library.util.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ReturnLoaningService {

    private final ReturnLoaningRepository returnLoaningRepository;
    private final LoaningRepository loaningRepository;
    private final SanctionRepository sanctionRepository;


    public ReturnLoaningService(final ReturnLoaningRepository returnLoaningRepository,
            final LoaningRepository loaningRepository,
            final SanctionRepository sanctionRepository) {
        this.returnLoaningRepository = returnLoaningRepository;
        this.loaningRepository = loaningRepository;
        this.sanctionRepository = sanctionRepository;
    }

    public List<ReturnLoaningDTO> findAll() {
        final List<ReturnLoaning> returnLoanings = returnLoaningRepository.findAll(Sort.by("idReturnLoaning"));
        return returnLoanings.stream()
                .map(returnLoaning -> mapToDTO(returnLoaning, new ReturnLoaningDTO()))
                .toList();
    }

    public ReturnLoaningDTO get(final Integer idReturnLoaning) {
        return returnLoaningRepository.findById(idReturnLoaning)
                .map(returnLoaning -> mapToDTO(returnLoaning, new ReturnLoaningDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ReturnLoaningDTO returnLoaningDTO) {
        final ReturnLoaning returnLoaning = new ReturnLoaning();
        mapToEntity(returnLoaningDTO, returnLoaning);
        return returnLoaningRepository.save(returnLoaning).getIdReturnLoaning();
    }

    public void update(final Integer idReturnLoaning, final ReturnLoaningDTO returnLoaningDTO) {
        final ReturnLoaning returnLoaning = returnLoaningRepository.findById(idReturnLoaning)
                .orElseThrow(NotFoundException::new);
        mapToEntity(returnLoaningDTO, returnLoaning);
        returnLoaningRepository.save(returnLoaning);
    }

    public void delete(final Integer idReturnLoaning) {
        returnLoaningRepository.deleteById(idReturnLoaning);
    }

    private ReturnLoaningDTO mapToDTO(final ReturnLoaning returnLoaning,
            final ReturnLoaningDTO returnLoaningDTO) {
        returnLoaningDTO.setIdReturnLoaning(returnLoaning.getIdReturnLoaning());
        returnLoaningDTO.setReturnDate(returnLoaning.getReturnDate());
        returnLoaningDTO.setLoaning(returnLoaning.getLoaning() == null ? null : returnLoaning.getLoaning().getIdLoaning());
        return returnLoaningDTO;
    }

    private ReturnLoaning mapToEntity(final ReturnLoaningDTO returnLoaningDTO,
            final ReturnLoaning returnLoaning) {
        returnLoaning.setReturnDate(returnLoaningDTO.getReturnDate());
        final Loaning loaning = returnLoaningDTO.getLoaning() == null ? null : loaningRepository.findById(returnLoaningDTO.getLoaning())
                .orElseThrow(() -> new NotFoundException("loaning not found"));
        returnLoaning.setLoaning(loaning);
        return returnLoaning;
    }

    public boolean loaningExists(final Integer idLoaning) {
        return returnLoaningRepository.existsByLoaningIdLoaning(idLoaning);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int returnLoan(int idLoaning) throws Exception{
        Loaning loaning = loaningRepository.findById(idLoaning).orElseThrow(NotFoundException::new);
        Member member = loaning.getMember();
        boolean isSanctioned = applySanction(loaning, member);
        LocalDateTime currentDate = LocalDateTime.now();
        ReturnLoaning returnLoaning = new ReturnLoaning();
        returnLoaning.setReturnDate(currentDate);
        returnLoaning.setLoaning(loaning);
        returnLoaningRepository.save(returnLoaning);

        if (isSanctioned){
            return 1;
        }
        return 0;

    }

    public boolean applySanction(Loaning loan, Member member) throws Exception{
        LocalDateTime currentDate = LocalDateTime.now();
        if (loan.getExpectedReturnDate().isBefore(currentDate)){
            long numberDaysOfDelay = currentDate.getDayOfYear() - loan.getExpectedReturnDate().getDayOfYear();
            long totalSanctionDays = numberDaysOfDelay * member.getTypeMember().getCoeffSanction();
            LocalDateTime sanctionDateEnd = loan.getExpectedReturnDate().plusDays(totalSanctionDays);

            Sanction sanction = new Sanction();
            sanction.setMember(member);
            sanction.setDateBegin(currentDate);
            sanction.setDateEnd(sanctionDateEnd);
            sanction.setCopyBook(loan.getCopyBook());
            sanctionRepository.save(sanction);
            return true;   
        }
        return false;
    }

}
