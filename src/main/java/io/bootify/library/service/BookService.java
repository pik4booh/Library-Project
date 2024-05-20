package io.bootify.library.service;

import io.bootify.library.domain.Book;
import io.bootify.library.domain.BookCategory;
import io.bootify.library.domain.BookMember;
import io.bootify.library.domain.BookTheme;
import io.bootify.library.domain.CopyBook;
import io.bootify.library.model.BookDTO;
import io.bootify.library.repos.BookCategoryRepository;
import io.bootify.library.repos.BookMemberRepository;
import io.bootify.library.repos.BookRepository;
import io.bootify.library.repos.BookThemeRepository;
import io.bootify.library.repos.CopyBookRepository;
import io.bootify.library.util.NotFoundException;
import io.bootify.library.util.ReferencedWarning;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookThemeRepository bookThemeRepository;
    private final CopyBookRepository copyBookRepository;
    private final BookMemberRepository bookMemberRepository;
    private final BookCategoryRepository bookCategoryRepository;

    public BookService(final BookRepository bookRepository,
            final BookThemeRepository bookThemeRepository,
            final CopyBookRepository copyBookRepository,
            final BookMemberRepository bookMemberRepository,
            final BookCategoryRepository bookCategoryRepository
            ) {
        this.bookRepository = bookRepository;
        this.bookThemeRepository = bookThemeRepository;
        this.copyBookRepository = copyBookRepository;
        this.bookMemberRepository = bookMemberRepository;
        this.bookCategoryRepository = bookCategoryRepository;
    }

    public List<BookDTO> findAll() {
        final List<Book> books = bookRepository.findAll(Sort.by("idBook"));
        return books.stream()
                .map(book -> mapToDTO(book, new BookDTO()))
                .toList();
    }

    public BookDTO get(final Integer idBook) {
        return bookRepository.findById(idBook)
                .map(book -> mapToDTO(book, new BookDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final BookDTO bookDTO) {
        final Book book = new Book();
        mapToEntity(bookDTO, book);
        return bookRepository.save(book).getIdBook();
    }

    public void update(final Integer idBook, final BookDTO bookDTO) {
        final Book book = bookRepository.findById(idBook)
                .orElseThrow(NotFoundException::new);
        mapToEntity(bookDTO, book);
        bookRepository.save(book);
    }

    public void delete(final Integer idBook) {
        bookRepository.deleteById(idBook);
    }

    private BookDTO mapToDTO(final Book book, final BookDTO bookDTO) {
        bookDTO.setIdBook(book.getIdBook());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setSummary(book.getSummary());
        bookDTO.setCollection(book.getCollection());
        bookDTO.setCoteNumber(book.getCoteNumber());
        bookDTO.setReleaseDate(book.getReleaseDate());
        bookDTO.setAuthor(book.getAuthor());
        return bookDTO;
    }

    private Book mapToEntity(final BookDTO bookDTO, final Book book) {
        book.setTitle(bookDTO.getTitle());
        book.setSummary(bookDTO.getSummary());
        book.setCollection(bookDTO.getCollection());
        book.setCoteNumber(bookDTO.getCoteNumber());
        book.setReleaseDate(bookDTO.getReleaseDate());
        book.setAuthor(bookDTO.getAuthor());
        return book;
    }

    public ReferencedWarning getReferencedWarning(final Integer idBook) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Book book = bookRepository.findById(idBook)
                .orElseThrow(NotFoundException::new);
        final BookTheme bookBookTheme = bookThemeRepository.findFirstByBook(book);
        if (bookBookTheme != null) {
            referencedWarning.setKey("book.bookTheme.book.referenced");
            referencedWarning.addParam(bookBookTheme.getIdBookTheme());
            return referencedWarning;
        }
        final CopyBook bookCopyBook = copyBookRepository.findFirstByBook(book);
        if (bookCopyBook != null) {
            referencedWarning.setKey("book.copyBook.book.referenced");
            referencedWarning.addParam(bookCopyBook.getIdCopyBook());
            return referencedWarning;
        }
        final BookMember bookBookMember = bookMemberRepository.findFirstByBook(book);
        if (bookBookMember != null) {
            referencedWarning.setKey("book.bookMember.book.referenced");
            referencedWarning.addParam(bookBookMember.getIdBookMember());
            return referencedWarning;
        }
        final BookCategory bookBookCategory = bookCategoryRepository.findFirstByBook(book);
        if (bookBookCategory != null) {
            referencedWarning.setKey("book.bookCategory.book.referenced");
            referencedWarning.addParam(bookBookCategory.getIdBookCategory());
            return referencedWarning;
        }
        return null;
    }

}
