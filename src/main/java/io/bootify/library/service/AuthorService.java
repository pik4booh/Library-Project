package io.bootify.library.service;

import io.bootify.library.domain.Author;
import io.bootify.library.domain.Book;
import io.bootify.library.model.AuthorDTO;
import io.bootify.library.repos.AuthorRepository;
import io.bootify.library.repos.BookRepository;
import io.bootify.library.util.NotFoundException;
import io.bootify.library.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorService(final AuthorRepository authorRepository,
            final BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public List<AuthorDTO> findAll() {
        final List<Author> authors = authorRepository.findAll(Sort.by("idAuthor"));
        return authors.stream()
                .map(author -> mapToDTO(author, new AuthorDTO()))
                .toList();
    }

    public AuthorDTO get(final Integer idAuthor) {
        return authorRepository.findById(idAuthor)
                .map(author -> mapToDTO(author, new AuthorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final AuthorDTO authorDTO) {
        final Author author = new Author();
        mapToEntity(authorDTO, author);
        return authorRepository.save(author).getIdAuthor();
    }

    public void update(final Integer idAuthor, final AuthorDTO authorDTO) {
        final Author author = authorRepository.findById(idAuthor)
                .orElseThrow(NotFoundException::new);
        mapToEntity(authorDTO, author);
        authorRepository.save(author);
    }

    public void delete(final Integer idAuthor) {
        authorRepository.deleteById(idAuthor);
    }

    private AuthorDTO mapToDTO(final Author author, final AuthorDTO authorDTO) {
        authorDTO.setIdAuthor(author.getIdAuthor());
        authorDTO.setName(author.getName());
        return authorDTO;
    }

    private Author mapToEntity(final AuthorDTO authorDTO, final Author author) {
        author.setName(authorDTO.getName());
        return author;
    }

    public ReferencedWarning getReferencedWarning(final Integer idAuthor) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Author author = authorRepository.findById(idAuthor)
                .orElseThrow(NotFoundException::new);
        final Book authorBook = bookRepository.findFirstByAuthor(author);
        if (authorBook != null) {
            referencedWarning.setKey("author.book.author.referenced");
            referencedWarning.addParam(authorBook.getIdBook());
            return referencedWarning;
        }
        return null;
    }

}
