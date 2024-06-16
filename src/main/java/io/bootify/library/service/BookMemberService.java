package io.bootify.library.service;

import io.bootify.library.domain.Book;
import io.bootify.library.domain.BookMember;
import io.bootify.library.domain.TypeMember;
import io.bootify.library.model.BookMemberDTO;
import io.bootify.library.repos.BookMemberRepository;
import io.bootify.library.repos.BookRepository;
import io.bootify.library.repos.TypeMemberRepository;
import io.bootify.library.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class BookMemberService {

    private final BookMemberRepository bookMemberRepository;
    private final BookRepository bookRepository;
    private final TypeMemberRepository typeMemberRepository;

    public BookMemberService(final BookMemberRepository bookMemberRepository,
            final BookRepository bookRepository, final TypeMemberRepository typeMemberRepository) {
        this.bookMemberRepository = bookMemberRepository;
        this.bookRepository = bookRepository;
        this.typeMemberRepository = typeMemberRepository;
    }

    public List<BookMemberDTO> findAll() {
        final List<BookMember> bookMembers = bookMemberRepository.findAll(Sort.by("idBookMember"));
        return bookMembers.stream()
                .map(bookMember -> mapToDTO(bookMember, new BookMemberDTO()))
                .toList();
    }

    public BookMemberDTO get(final Integer idBookMember) {
        return bookMemberRepository.findById(idBookMember)
                .map(bookMember -> mapToDTO(bookMember, new BookMemberDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final BookMemberDTO bookMemberDTO) throws Exception {
        BookMember existingBookMember = bookMemberRepository.findFirstByBookAndTypeMember(bookMemberDTO.getBook(), bookMemberDTO.getTypeMember());
        if (existingBookMember != null) {
            throw new Exception("Permission already exists for this book and member type");
        }
        final BookMember bookMember = new BookMember();
        mapToEntity(bookMemberDTO, bookMember);
        return bookMemberRepository.save(bookMember).getIdBookMember();
    }

    public void update(final Integer idBookMember, final BookMemberDTO bookMemberDTO) {
        final BookMember bookMember = bookMemberRepository.findById(idBookMember)
                .orElseThrow(NotFoundException::new);
        mapToEntity(bookMemberDTO, bookMember);
        bookMemberRepository.save(bookMember);
    }

    public void delete(final Integer idBookMember) {
        bookMemberRepository.deleteById(idBookMember);
    }

    private BookMemberDTO mapToDTO(final BookMember bookMember, final BookMemberDTO bookMemberDTO) {
        bookMemberDTO.setIdBookMember(bookMember.getIdBookMember());
        bookMemberDTO.setOnTheSpot(bookMember.getOnTheSpot());
        bookMemberDTO.setTakeAway(bookMember.getTakeAway());
        bookMemberDTO.setBook(bookMember.getBook() == null ? null : bookMember.getBook());
        bookMemberDTO.setTypeMember(bookMember.getTypeMember() == null ? null : bookMember.getTypeMember());
        return bookMemberDTO;
    }

    private BookMember mapToEntity(final BookMemberDTO bookMemberDTO, final BookMember bookMember) {
        bookMember.setOnTheSpot(bookMemberDTO.getOnTheSpot());
        bookMember.setTakeAway(bookMemberDTO.getTakeAway());
        final Book book = bookMemberDTO.getBook() == null ? null : bookRepository.findById(bookMemberDTO.getBook().getIdBook())
                .orElseThrow(() -> new NotFoundException("book not found"));
        bookMember.setBook(book);
        final TypeMember typeMember = bookMemberDTO.getTypeMember() == null ? null : typeMemberRepository.findById(bookMemberDTO.getTypeMember().getIdTypeMember())
                .orElseThrow(() -> new NotFoundException("typeMember not found"));
        bookMember.setTypeMember(typeMember);
        return bookMember;
    }

}
