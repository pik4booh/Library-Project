package io.bootify.library.service;

import io.bootify.library.domain.Loaning;
import io.bootify.library.domain.TypeLoaning;
import io.bootify.library.model.TypeLoaningDTO;
import io.bootify.library.repos.LoaningRepository;
import io.bootify.library.repos.TypeLoaningRepository;
import io.bootify.library.util.NotFoundException;
import io.bootify.library.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TypeLoaningService {

    private final TypeLoaningRepository typeLoaningRepository;
    private final LoaningRepository loaningRepository;

    public TypeLoaningService(final TypeLoaningRepository typeLoaningRepository,
            final LoaningRepository loaningRepository) {
        this.typeLoaningRepository = typeLoaningRepository;
        this.loaningRepository = loaningRepository;
    }

    public List<TypeLoaningDTO> findAll() {
        final List<TypeLoaning> typeLoanings = typeLoaningRepository.findAll(Sort.by("idTypeLoaning"));
        return typeLoanings.stream()
                .map(typeLoaning -> mapToDTO(typeLoaning, new TypeLoaningDTO()))
                .toList();
    }

    public TypeLoaningDTO get(final Integer idTypeLoaning) {
        return typeLoaningRepository.findById(idTypeLoaning)
                .map(typeLoaning -> mapToDTO(typeLoaning, new TypeLoaningDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final TypeLoaningDTO typeLoaningDTO) {
        final TypeLoaning typeLoaning = new TypeLoaning();
        mapToEntity(typeLoaningDTO, typeLoaning);
        return typeLoaningRepository.save(typeLoaning).getIdTypeLoaning();
    }

    public void update(final Integer idTypeLoaning, final TypeLoaningDTO typeLoaningDTO) {
        final TypeLoaning typeLoaning = typeLoaningRepository.findById(idTypeLoaning)
                .orElseThrow(NotFoundException::new);
        mapToEntity(typeLoaningDTO, typeLoaning);
        typeLoaningRepository.save(typeLoaning);
    }

    public void delete(final Integer idTypeLoaning) {
        typeLoaningRepository.deleteById(idTypeLoaning);
    }

    private TypeLoaningDTO mapToDTO(final TypeLoaning typeLoaning,
            final TypeLoaningDTO typeLoaningDTO) {
        typeLoaningDTO.setIdTypeLoaning(typeLoaning.getIdTypeLoaning());
        typeLoaningDTO.setName(typeLoaning.getName());
        return typeLoaningDTO;
    }

    private TypeLoaning mapToEntity(final TypeLoaningDTO typeLoaningDTO,
            final TypeLoaning typeLoaning) {
        typeLoaning.setName(typeLoaningDTO.getName());
        return typeLoaning;
    }

    public ReferencedWarning getReferencedWarning(final Integer idTypeLoaning) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final TypeLoaning typeLoaning = typeLoaningRepository.findById(idTypeLoaning)
                .orElseThrow(NotFoundException::new);
        final Loaning typeLoaningLoaning = loaningRepository.findFirstByTypeLoaning(typeLoaning);
        if (typeLoaningLoaning != null) {
            referencedWarning.setKey("typeLoaning.loaning.typeLoaning.referenced");
            referencedWarning.addParam(typeLoaningLoaning.getIdLoaning());
            return referencedWarning;
        }
        return null;
    }

}
