package io.bootify.library.service;

import io.bootify.library.domain.CopyBook;
import io.bootify.library.domain.Loaning;
import io.bootify.library.domain.Member;
import io.bootify.library.domain.TypeLoaning;
import io.bootify.library.model.LoaningDTO;
import io.bootify.library.repos.CopyBookRepository;
import io.bootify.library.repos.LoaningRepository;
import io.bootify.library.repos.MemberRepository;
import io.bootify.library.repos.TypeLoaningRepository;
import io.bootify.library.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LoaningService {

    private final LoaningRepository loaningRepository;
    private final MemberRepository memberRepository;
    private final CopyBookRepository copyBookRepository;
    private final TypeLoaningRepository typeLoaningRepository;

    public LoaningService(final LoaningRepository loaningRepository,
            final MemberRepository memberRepository, final CopyBookRepository copyBookRepository,
            final TypeLoaningRepository typeLoaningRepository) {
        this.loaningRepository = loaningRepository;
        this.memberRepository = memberRepository;
        this.copyBookRepository = copyBookRepository;
        this.typeLoaningRepository = typeLoaningRepository;
    }

    public List<LoaningDTO> findAll() {
        final List<Loaning> loanings = loaningRepository.findAll(Sort.by("idLoaning"));
        return loanings.stream()
                .map(loaning -> mapToDTO(loaning, new LoaningDTO()))
                .toList();
    }

    public LoaningDTO get(final Integer idLoaning) {
        return loaningRepository.findById(idLoaning)
                .map(loaning -> mapToDTO(loaning, new LoaningDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final LoaningDTO loaningDTO) {
        final Loaning loaning = new Loaning();
        mapToEntity(loaningDTO, loaning);
        return loaningRepository.save(loaning).getIdLoaning();
    }

    public void update(final Integer idLoaning, final LoaningDTO loaningDTO) {
        final Loaning loaning = loaningRepository.findById(idLoaning)
                .orElseThrow(NotFoundException::new);
        mapToEntity(loaningDTO, loaning);
        loaningRepository.save(loaning);
    }

    public void delete(final Integer idLoaning) {
        loaningRepository.deleteById(idLoaning);
    }

    private LoaningDTO mapToDTO(final Loaning loaning, final LoaningDTO loaningDTO) {
        loaningDTO.setIdLoaning(loaning.getIdLoaning());
        loaningDTO.setLoaningDate(loaning.getLoaningDate());
        loaningDTO.setExpectedReturnDate(loaning.getExpectedReturnDate());
        loaningDTO.setMember(loaning.getMember() == null ? null : loaning.getMember().getIdMember());
        loaningDTO.setCopyBook(loaning.getCopyBook() == null ? null : loaning.getCopyBook().getIdCopyBook());
        loaningDTO.setTypeLoaning(loaning.getTypeLoaning() == null ? null : loaning.getTypeLoaning().getIdTypeLoaning());
        return loaningDTO;
    }

    private Loaning mapToEntity(final LoaningDTO loaningDTO, final Loaning loaning) {
        loaning.setLoaningDate(loaningDTO.getLoaningDate());
        loaning.setExpectedReturnDate(loaningDTO.getExpectedReturnDate());
        final Member member = loaningDTO.getMember() == null ? null : memberRepository.findById(loaningDTO.getMember())
                .orElseThrow(() -> new NotFoundException("member not found"));
        loaning.setMember(member);
        final CopyBook copyBook = loaningDTO.getCopyBook() == null ? null : copyBookRepository.findById(loaningDTO.getCopyBook())
                .orElseThrow(() -> new NotFoundException("copyBook not found"));
        loaning.setCopyBook(copyBook);
        final TypeLoaning typeLoaning = loaningDTO.getTypeLoaning() == null ? null : typeLoaningRepository.findById(loaningDTO.getTypeLoaning())
                .orElseThrow(() -> new NotFoundException("typeLoaning not found"));
        loaning.setTypeLoaning(typeLoaning);
        return loaning;
    }

}
