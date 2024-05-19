package io.bootify.library.rest;

import io.bootify.library.model.AuthorDTO;
import io.bootify.library.service.AuthorService;
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
@RequestMapping(value = "/api/authors", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorResource {

    private final AuthorService authorService;

    public AuthorResource(final AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        return ResponseEntity.ok(authorService.findAll());
    }

    @GetMapping("/{idAuthor}")
    public ResponseEntity<AuthorDTO> getAuthor(
            @PathVariable(name = "idAuthor") final Integer idAuthor) {
        return ResponseEntity.ok(authorService.get(idAuthor));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createAuthor(@RequestBody @Valid final AuthorDTO authorDTO) {
        final Integer createdIdAuthor = authorService.create(authorDTO);
        return new ResponseEntity<>(createdIdAuthor, HttpStatus.CREATED);
    }

    @PutMapping("/{idAuthor}")
    public ResponseEntity<Integer> updateAuthor(
            @PathVariable(name = "idAuthor") final Integer idAuthor,
            @RequestBody @Valid final AuthorDTO authorDTO) {
        authorService.update(idAuthor, authorDTO);
        return ResponseEntity.ok(idAuthor);
    }

    @DeleteMapping("/{idAuthor}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAuthor(
            @PathVariable(name = "idAuthor") final Integer idAuthor) {
        final ReferencedWarning referencedWarning = authorService.getReferencedWarning(idAuthor);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        authorService.delete(idAuthor);
        return ResponseEntity.noContent().build();
    }

}
