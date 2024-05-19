package io.bootify.library.rest;

import io.bootify.library.model.BookThemeDTO;
import io.bootify.library.service.BookThemeService;
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
@RequestMapping(value = "/api/bookThemes", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookThemeResource {

    private final BookThemeService bookThemeService;

    public BookThemeResource(final BookThemeService bookThemeService) {
        this.bookThemeService = bookThemeService;
    }

    @GetMapping
    public ResponseEntity<List<BookThemeDTO>> getAllBookThemes() {
        return ResponseEntity.ok(bookThemeService.findAll());
    }

    @GetMapping("/{idBookTheme}")
    public ResponseEntity<BookThemeDTO> getBookTheme(
            @PathVariable(name = "idBookTheme") final Integer idBookTheme) {
        return ResponseEntity.ok(bookThemeService.get(idBookTheme));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createBookTheme(
            @RequestBody @Valid final BookThemeDTO bookThemeDTO) {
        final Integer createdIdBookTheme = bookThemeService.create(bookThemeDTO);
        return new ResponseEntity<>(createdIdBookTheme, HttpStatus.CREATED);
    }

    @PutMapping("/{idBookTheme}")
    public ResponseEntity<Integer> updateBookTheme(
            @PathVariable(name = "idBookTheme") final Integer idBookTheme,
            @RequestBody @Valid final BookThemeDTO bookThemeDTO) {
        bookThemeService.update(idBookTheme, bookThemeDTO);
        return ResponseEntity.ok(idBookTheme);
    }

    @DeleteMapping("/{idBookTheme}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteBookTheme(
            @PathVariable(name = "idBookTheme") final Integer idBookTheme) {
        bookThemeService.delete(idBookTheme);
        return ResponseEntity.noContent().build();
    }

}
