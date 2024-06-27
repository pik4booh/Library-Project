package io.bootify.library.service;

import io.bootify.library.domain.Book;
import io.bootify.library.domain.BookMember;
import io.bootify.library.domain.CopyBook;
import io.bootify.library.domain.Loaning;
import io.bootify.library.domain.Member;
import io.bootify.library.domain.TypeLoaning;
import io.bootify.library.domain.TypeMember;
import io.bootify.library.model.LoaningDTO;
import io.bootify.library.repos.BookMemberRepository;
import io.bootify.library.repos.CopyBookRepository;
import io.bootify.library.repos.LoaningRepository;
import io.bootify.library.repos.MemberRepository;
import io.bootify.library.repos.TypeLoaningRepository;
import io.bootify.library.util.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LoaningService {

    private final LoaningRepository loaningRepository;
    private final MemberRepository memberRepository;
    private final CopyBookRepository copyBookRepository;
    private final TypeLoaningRepository typeLoaningRepository;
    private final BookMemberRepository bookMemberRepository;

    public LoaningService(final LoaningRepository loaningRepository,
            final MemberRepository memberRepository, final CopyBookRepository copyBookRepository,
            final TypeLoaningRepository typeLoaningRepository, final BookMemberRepository bookMemberRepository) {
        this.loaningRepository = loaningRepository;
        this.memberRepository = memberRepository;
        this.copyBookRepository = copyBookRepository;
        this.typeLoaningRepository = typeLoaningRepository;
        this.bookMemberRepository = bookMemberRepository;
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

    public int createLoaning(LoaningDTO loaningDTO) throws Exception {
      
            Optional<Member> optionalMember  = memberRepository.findById(loaningDTO.getMember());
            Member member = optionalMember.orElseThrow(() -> new NotFoundException("Membre non trouvé"));
            checkLoaningPermission(loaningDTO, member);
            int daysToAdd = member.getTypeMember().getNbLoaningDays();
            LocalDateTime expectedReturnDate =  loaningDTO.getLoaningDate().plusDays(daysToAdd);
            System.out.println("Expected return date : " + expectedReturnDate);
            loaningDTO.setExpectedReturnDate(expectedReturnDate);
            int id = create(loaningDTO);
            return 0;
    }

    public void checkLoaningPermission(LoaningDTO loaningDTO, Member member) throws Exception
    {
        //Get the member type of the actual member who made a request
        TypeMember typeMember = member.getTypeMember();
        //Get the copy book instance by id and throws exception if not found
        CopyBook copyBook = copyBookRepository.findById(loaningDTO.getCopyBook()).orElseThrow(() -> new NotFoundException("Exemplaire non trouvé"));
        //Get book associated with the copy book to get the specific permission for this book
        Book book = copyBook.getBook();

        //find book member by book and member and throws exception if not found
        BookMember bookMember = bookMemberRepository.findFirstByBookAndTypeMember(book, typeMember);
        if(bookMember == null)
        {
            throw new Exception("Ce livre n'a pas de permission défini pour ce type de membre");
        }
        //Get the loaning type requested by the member
        TypeLoaning typeLoaning = loaningDTO.getTypeLoaning() == null ? null : typeLoaningRepository.findById(loaningDTO.getTypeLoaning())
                .orElseThrow(() -> new NotFoundException("Type d'emprunt non trouvé"));

        //Check if the member has the permission to loan this book on the spot or take away
        if(typeLoaning.getName().contains("Spot"))
        {
            int bookSpotPermission = bookMember.getOnTheSpot();
            if(bookSpotPermission == 0)
            {
                throw new Exception("Ce type de membre ne peut pas lire ce livre sur place");
            }
        }else{
            int bookPermission = bookMember.getTakeAway();
            if(bookPermission == 0)
            {
                throw new Exception("Ce type de membre ne peut pas emporter ce livre");
            }
        }
        System.out.println("Check permission successfull");
    }

}
