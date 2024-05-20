package io.bootify.library.service;

import io.bootify.library.domain.Book;
import io.bootify.library.domain.BookCategory;
import io.bootify.library.domain.Category;
import io.bootify.library.model.BookCategoryDTO;
import io.bootify.library.repos.BookCategoryRepository;
import io.bootify.library.repos.BookRepository;
import io.bootify.library.repos.CategoryRepository;
import io.bootify.library.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class BookCategoryService {

    private final BookCategoryRepository bookCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;

    public BookCategoryService(final BookCategoryRepository bookCategoryRepository,
            final CategoryRepository categoryRepository, final BookRepository bookRepository) {
        this.bookCategoryRepository = bookCategoryRepository;
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
    }

    public List<BookCategoryDTO> findAll() {
        final List<BookCategory> bookCategories = bookCategoryRepository.findAll(Sort.by("idBookCategory"));
        return bookCategories.stream()
                .map(bookCategory -> mapToDTO(bookCategory, new BookCategoryDTO()))
                .toList();
    }

    public BookCategoryDTO get(final Integer idBookCategory) {
        return bookCategoryRepository.findById(idBookCategory)
                .map(bookCategory -> mapToDTO(bookCategory, new BookCategoryDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final BookCategoryDTO bookCategoryDTO) {
        final BookCategory bookCategory = new BookCategory();
        mapToEntity(bookCategoryDTO, bookCategory);
        return bookCategoryRepository.save(bookCategory).getIdBookCategory();
    }

    public void update(final Integer idBookCategory, final BookCategoryDTO bookCategoryDTO) {
        final BookCategory bookCategory = bookCategoryRepository.findById(idBookCategory)
                .orElseThrow(NotFoundException::new);
        mapToEntity(bookCategoryDTO, bookCategory);
        bookCategoryRepository.save(bookCategory);
    }

    public void delete(final Integer idBookCategory) {
        bookCategoryRepository.deleteById(idBookCategory);
    }

    private BookCategoryDTO mapToDTO(final BookCategory bookCategory,
            final BookCategoryDTO bookCategoryDTO) {
        bookCategoryDTO.setIdBookCategory(bookCategory.getIdBookCategory());
        bookCategoryDTO.setCategory(bookCategory.getCategory() == null ? null : bookCategory.getCategory().getIdCategory());
        bookCategoryDTO.setBook(bookCategory.getBook() == null ? null : bookCategory.getBook().getIdBook());
        return bookCategoryDTO;
    }

    private BookCategory mapToEntity(final BookCategoryDTO bookCategoryDTO,
            final BookCategory bookCategory) {
        final Category category = bookCategoryDTO.getCategory() == null ? null : categoryRepository.findById(bookCategoryDTO.getCategory())
                .orElseThrow(() -> new NotFoundException("category not found"));
        bookCategory.setCategory(category);
        final Book book = bookCategoryDTO.getBook() == null ? null : bookRepository.findById(bookCategoryDTO.getBook())
                .orElseThrow(() -> new NotFoundException("book not found"));
        bookCategory.setBook(book);
        return bookCategory;
    }

}
