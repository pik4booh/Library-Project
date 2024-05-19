package io.bootify.library.rest;

import io.bootify.library.model.LibrarianDTO;
import io.bootify.library.service.LibrarianService;
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
@RequestMapping(value = "/api/librarians", produces = MediaType.APPLICATION_JSON_VALUE)
public class LibrarianResource {

    private final LibrarianService librarianService;

    public LibrarianResource(final LibrarianService librarianService) {
        this.librarianService = librarianService;
    }

    @GetMapping
    public ResponseEntity<List<LibrarianDTO>> getAllLibrarians() {
        return ResponseEntity.ok(librarianService.findAll());
    }

    @GetMapping("/{idLibrarian}")
    public ResponseEntity<LibrarianDTO> getLibrarian(
            @PathVariable(name = "idLibrarian") final Integer idLibrarian) {
        return ResponseEntity.ok(librarianService.get(idLibrarian));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createLibrarian(
            @RequestBody @Valid final LibrarianDTO librarianDTO) {
        final Integer createdIdLibrarian = librarianService.create(librarianDTO);
        return new ResponseEntity<>(createdIdLibrarian, HttpStatus.CREATED);
    }

    @PutMapping("/{idLibrarian}")
    public ResponseEntity<Integer> updateLibrarian(
            @PathVariable(name = "idLibrarian") final Integer idLibrarian,
            @RequestBody @Valid final LibrarianDTO librarianDTO) {
        librarianService.update(idLibrarian, librarianDTO);
        return ResponseEntity.ok(idLibrarian);
    }

    @DeleteMapping("/{idLibrarian}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteLibrarian(
            @PathVariable(name = "idLibrarian") final Integer idLibrarian) {
        librarianService.delete(idLibrarian);
        return ResponseEntity.noContent().build();
    }

}
