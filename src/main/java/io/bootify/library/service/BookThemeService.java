package io.bootify.library.service;

import io.bootify.library.domain.Book;
import io.bootify.library.domain.BookTheme;
import io.bootify.library.domain.Theme;
import io.bootify.library.model.BookThemeDTO;
import io.bootify.library.repos.BookRepository;
import io.bootify.library.repos.BookThemeRepository;
import io.bootify.library.repos.ThemeRepository;
import io.bootify.library.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class BookThemeService {

    private final BookThemeRepository bookThemeRepository;
    private final ThemeRepository themeRepository;
    private final BookRepository bookRepository;

    public BookThemeService(final BookThemeRepository bookThemeRepository,
            final ThemeRepository themeRepository, final BookRepository bookRepository) {
        this.bookThemeRepository = bookThemeRepository;
        this.themeRepository = themeRepository;
        this.bookRepository = bookRepository;
    }

    public List<BookThemeDTO> findAll() {
        final List<BookTheme> bookThemes = bookThemeRepository.findAll(Sort.by("idBookTheme"));
        return bookThemes.stream()
                .map(bookTheme -> mapToDTO(bookTheme, new BookThemeDTO()))
                .toList();
    }

    public BookThemeDTO get(final Integer idBookTheme) {
        return bookThemeRepository.findById(idBookTheme)
                .map(bookTheme -> mapToDTO(bookTheme, new BookThemeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final BookThemeDTO bookThemeDTO) {
        final BookTheme bookTheme = new BookTheme();
        mapToEntity(bookThemeDTO, bookTheme);
        return bookThemeRepository.save(bookTheme).getIdBookTheme();
    }

    public void update(final Integer idBookTheme, final BookThemeDTO bookThemeDTO) {
        final BookTheme bookTheme = bookThemeRepository.findById(idBookTheme)
                .orElseThrow(NotFoundException::new);
        mapToEntity(bookThemeDTO, bookTheme);
        bookThemeRepository.save(bookTheme);
    }

    public void delete(final Integer idBookTheme) {
        bookThemeRepository.deleteById(idBookTheme);
    }

    private BookThemeDTO mapToDTO(final BookTheme bookTheme, final BookThemeDTO bookThemeDTO) {
        bookThemeDTO.setIdBookTheme(bookTheme.getIdBookTheme());
        bookThemeDTO.setTheme(bookTheme.getTheme() == null ? null : bookTheme.getTheme().getIdTheme());
        bookThemeDTO.setBook(bookTheme.getBook() == null ? null : bookTheme.getBook().getIdBook());
        return bookThemeDTO;
    }

    private BookTheme mapToEntity(final BookThemeDTO bookThemeDTO, final BookTheme bookTheme) {
        final Theme theme = bookThemeDTO.getTheme() == null ? null : themeRepository.findById(bookThemeDTO.getTheme())
                .orElseThrow(() -> new NotFoundException("theme not found"));
        bookTheme.setTheme(theme);
        final Book book = bookThemeDTO.getBook() == null ? null : bookRepository.findById(bookThemeDTO.getBook())
                .orElseThrow(() -> new NotFoundException("book not found"));
        bookTheme.setBook(book);
        return bookTheme;
    }

}
