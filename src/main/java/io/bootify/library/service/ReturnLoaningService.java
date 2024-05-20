package io.bootify.library.service;

import io.bootify.library.domain.Loaning;
import io.bootify.library.domain.ReturnLoaning;
import io.bootify.library.model.ReturnLoaningDTO;
import io.bootify.library.repos.LoaningRepository;
import io.bootify.library.repos.ReturnLoaningRepository;
import io.bootify.library.util.NotFoundException;
import io.bootify.library.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ReturnLoaningService {

    private final ReturnLoaningRepository returnLoaningRepository;
    private final LoaningRepository loaningRepository;

    public ReturnLoaningService(final ReturnLoaningRepository returnLoaningRepository,
            final LoaningRepository loaningRepository) {
        this.returnLoaningRepository = returnLoaningRepository;
        this.loaningRepository = loaningRepository;
    }

    public List<ReturnLoaningDTO> findAll() {
        final List<ReturnLoaning> returnLoanings = returnLoaningRepository.findAll(Sort.by("idReturnLoaning"));
        return returnLoanings.stream()
                .map(returnLoaning -> mapToDTO(returnLoaning, new ReturnLoaningDTO()))
                .toList();
    }

    public ReturnLoaningDTO get(final Long idReturnLoaning) {
        return returnLoaningRepository.findById(idReturnLoaning)
                .map(returnLoaning -> mapToDTO(returnLoaning, new ReturnLoaningDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ReturnLoaningDTO returnLoaningDTO) {
        final ReturnLoaning returnLoaning = new ReturnLoaning();
        mapToEntity(returnLoaningDTO, returnLoaning);
        return returnLoaningRepository.save(returnLoaning).getIdReturnLoaning();
    }

    public void update(final Long idReturnLoaning, final ReturnLoaningDTO returnLoaningDTO) {
        final ReturnLoaning returnLoaning = returnLoaningRepository.findById(idReturnLoaning)
                .orElseThrow(NotFoundException::new);
        mapToEntity(returnLoaningDTO, returnLoaning);
        returnLoaningRepository.save(returnLoaning);
    }

    public void delete(final Long idReturnLoaning) {
        returnLoaningRepository.deleteById(idReturnLoaning);
    }

    private ReturnLoaningDTO mapToDTO(final ReturnLoaning returnLoaning,
            final ReturnLoaningDTO returnLoaningDTO) {
        returnLoaningDTO.setIdReturnLoaning(returnLoaning.getIdReturnLoaning());
        returnLoaningDTO.setReturnDate(returnLoaning.getReturnDate());
        return returnLoaningDTO;
    }

    private ReturnLoaning mapToEntity(final ReturnLoaningDTO returnLoaningDTO,
            final ReturnLoaning returnLoaning) {
        returnLoaning.setReturnDate(returnLoaningDTO.getReturnDate());
        return returnLoaning;
    }

    public ReferencedWarning getReferencedWarning(final Long idReturnLoaning) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final ReturnLoaning returnLoaning = returnLoaningRepository.findById(idReturnLoaning)
                .orElseThrow(NotFoundException::new);
        final Loaning returnLoaningLoaning = loaningRepository.findFirstByReturnLoaning(returnLoaning);
        if (returnLoaningLoaning != null) {
            referencedWarning.setKey("returnLoaning.loaning.returnLoaning.referenced");
            referencedWarning.addParam(returnLoaningLoaning.getIdLoaning());
            return referencedWarning;
        }
        return null;
    }

}
