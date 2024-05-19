package io.bootify.library.rest;

import io.bootify.library.model.BookDTO;
import io.bootify.library.service.BookService;
import io.bootify.library.util.ReferencedException;
import io.bootify.library.util.ReferencedWarning;
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
@RequestMapping(value = "/api/books", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookResource {

    private final BookService bookService;

    public BookResource(final BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/{idBook}")
    public ResponseEntity<BookDTO> getBook(@PathVariable(name = "idBook") final Integer idBook) {
        return ResponseEntity.ok(bookService.get(idBook));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createBook(@RequestBody @Valid final BookDTO bookDTO) {
        final Integer createdIdBook = bookService.create(bookDTO);
        return new ResponseEntity<>(createdIdBook, HttpStatus.CREATED);
    }

    @PutMapping("/{idBook}")
    public ResponseEntity<Integer> updateBook(@PathVariable(name = "idBook") final Integer idBook,
            @RequestBody @Valid final BookDTO bookDTO) {
        bookService.update(idBook, bookDTO);
        return ResponseEntity.ok(idBook);
    }

    @DeleteMapping("/{idBook}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteBook(@PathVariable(name = "idBook") final Integer idBook) {
        final ReferencedWarning referencedWarning = bookService.getReferencedWarning(idBook);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        bookService.delete(idBook);
        return ResponseEntity.noContent().build();
    }

}
