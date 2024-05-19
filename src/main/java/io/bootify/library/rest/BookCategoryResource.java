package io.bootify.library.rest;

import io.bootify.library.model.BookCategoryDTO;
import io.bootify.library.service.BookCategoryService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/bookCategories", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookCategoryResource {

    private final BookCategoryService bookCategoryService;

    public BookCategoryResource(final BookCategoryService bookCategoryService) {
        this.bookCategoryService = bookCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<BookCategoryDTO>> getAllBookCategories() {
        return ResponseEntity.ok(bookCategoryService.findAll());
    }

    @GetMapping("/{idBookCategory}")
    public ResponseEntity<BookCategoryDTO> getBookCategory(
            @PathVariable(name = "idBookCategory") final Integer idBookCategory) {
        return ResponseEntity.ok(bookCategoryService.get(idBookCategory));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createBookCategory(
            @RequestBody @Valid final BookCategoryDTO bookCategoryDTO) {
        final Integer createdIdBookCategory = bookCategoryService.create(bookCategoryDTO);
        return new ResponseEntity<>(createdIdBookCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{idBookCategory}")
    public ResponseEntity<Integer> updateBookCategory(
            @PathVariable(name = "idBookCategory") final Integer idBookCategory,
            @RequestBody @Valid final BookCategoryDTO bookCategoryDTO) {
        bookCategoryService.update(idBookCategory, bookCategoryDTO);
        return ResponseEntity.ok(idBookCategory);
    }

    @DeleteMapping("/{idBookCategory}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteBookCategory(
            @PathVariable(name = "idBookCategory") final Integer idBookCategory) {
        bookCategoryService.delete(idBookCategory);
        return ResponseEntity.noContent().build();
    }

}
