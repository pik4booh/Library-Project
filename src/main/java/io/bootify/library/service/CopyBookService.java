package io.bootify.library.service;

import io.bootify.library.domain.Book;
import io.bootify.library.domain.CopyBook;
import io.bootify.library.domain.Loaning;
import io.bootify.library.domain.Sanction;
import io.bootify.library.model.CopyBookDTO;
import io.bootify.library.repos.BookRepository;
import io.bootify.library.repos.CopyBookRepository;
import io.bootify.library.repos.LoaningRepository;
import io.bootify.library.repos.SanctionRepository;
import io.bootify.library.util.NotFoundException;
import io.bootify.library.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CopyBookService {

    private final CopyBookRepository copyBookRepository;
    private final BookRepository bookRepository;
    private final LoaningRepository loaningRepository;
    private final SanctionRepository sanctionRepository;

    public CopyBookService(final CopyBookRepository copyBookRepository,
            final BookRepository bookRepository, final LoaningRepository loaningRepository,
            final SanctionRepository sanctionRepository) {
        this.copyBookRepository = copyBookRepository;
        this.bookRepository = bookRepository;
        this.loaningRepository = loaningRepository;
        this.sanctionRepository = sanctionRepository;
    }

    public List<CopyBookDTO> findAll() {
        final List<CopyBook> copyBooks = copyBookRepository.findAll(Sort.by("idCopyBook"));
        return copyBooks.stream()
                .map(copyBook -> mapToDTO(copyBook, new CopyBookDTO()))
                .toList();
    }

    public CopyBookDTO get(final Integer idCopyBook) {
        return copyBookRepository.findById(idCopyBook)
                .map(copyBook -> mapToDTO(copyBook, new CopyBookDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final CopyBookDTO copyBookDTO) {
        final CopyBook copyBook = new CopyBook();
        mapToEntity(copyBookDTO, copyBook);
        return copyBookRepository.save(copyBook).getIdCopyBook();
    }

    public void update(final Integer idCopyBook, final CopyBookDTO copyBookDTO) {
        final CopyBook copyBook = copyBookRepository.findById(idCopyBook)
                .orElseThrow(NotFoundException::new);
        mapToEntity(copyBookDTO, copyBook);
        copyBookRepository.save(copyBook);
    }

    public void delete(final Integer idCopyBook) {
        copyBookRepository.deleteById(idCopyBook);
    }

    private CopyBookDTO mapToDTO(final CopyBook copyBook, final CopyBookDTO copyBookDTO) {
        copyBookDTO.setIdCopyBook(copyBook.getIdCopyBook());
        copyBookDTO.setLanguage(copyBook.getLanguage());
        copyBookDTO.setIsbn(copyBook.getIsbn());
        copyBookDTO.setEditDate(copyBook.getEditDate());
        copyBookDTO.setPageNumber(copyBook.getPageNumber());
        copyBookDTO.setCover(copyBook.getCover());
        copyBookDTO.setBook(copyBook.getBook() == null ? null : copyBook.getBook().getIdBook());
        return copyBookDTO;
    }

    private CopyBook mapToEntity(final CopyBookDTO copyBookDTO, final CopyBook copyBook) {
        copyBook.setLanguage(copyBookDTO.getLanguage());
        copyBook.setIsbn(copyBookDTO.getIsbn());
        copyBook.setEditDate(copyBookDTO.getEditDate());
        copyBook.setPageNumber(copyBookDTO.getPageNumber());
        copyBook.setCover(copyBookDTO.getCover());
        final Book book = copyBookDTO.getBook() == null ? null : bookRepository.findById(copyBookDTO.getBook())
                .orElseThrow(() -> new NotFoundException("book not found"));
        copyBook.setBook(book);
        return copyBook;
    }

    public ReferencedWarning getReferencedWarning(final Integer idCopyBook) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final CopyBook copyBook = copyBookRepository.findById(idCopyBook)
                .orElseThrow(NotFoundException::new);
        final Loaning copyBookLoaning = loaningRepository.findFirstByCopyBook(copyBook);
        if (copyBookLoaning != null) {
            referencedWarning.setKey("copyBook.loaning.copyBook.referenced");
            referencedWarning.addParam(copyBookLoaning.getIdLoaning());
            return referencedWarning;
        }
        final Sanction copyBookSanction = sanctionRepository.findFirstByCopyBook(copyBook);
        if (copyBookSanction != null) {
            referencedWarning.setKey("copyBook.sanction.copyBook.referenced");
            referencedWarning.addParam(copyBookSanction.getIdSanction());
            return referencedWarning;
        }
        return null;
    }

}
