package io.bootify.library.rest;

import io.bootify.library.model.CopyBookDTO;
import io.bootify.library.service.CopyBookService;
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
@RequestMapping(value = "/api/copyBooks", produces = MediaType.APPLICATION_JSON_VALUE)
public class CopyBookResource {

    private final CopyBookService copyBookService;

    public CopyBookResource(final CopyBookService copyBookService) {
        this.copyBookService = copyBookService;
    }

    @GetMapping
    public ResponseEntity<List<CopyBookDTO>> getAllCopyBooks() {
        return ResponseEntity.ok(copyBookService.findAll());
    }

    @GetMapping("/{idCopyBook}")
    public ResponseEntity<CopyBookDTO> getCopyBook(
            @PathVariable(name = "idCopyBook") final Integer idCopyBook) {
        return ResponseEntity.ok(copyBookService.get(idCopyBook));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createCopyBook(
            @RequestBody @Valid final CopyBookDTO copyBookDTO) {
        final Integer createdIdCopyBook = copyBookService.create(copyBookDTO);
        return new ResponseEntity<>(createdIdCopyBook, HttpStatus.CREATED);
    }

    @PutMapping("/{idCopyBook}")
    public ResponseEntity<Integer> updateCopyBook(
            @PathVariable(name = "idCopyBook") final Integer idCopyBook,
            @RequestBody @Valid final CopyBookDTO copyBookDTO) {
        copyBookService.update(idCopyBook, copyBookDTO);
        return ResponseEntity.ok(idCopyBook);
    }

    @DeleteMapping("/{idCopyBook}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCopyBook(
            @PathVariable(name = "idCopyBook") final Integer idCopyBook) {
        final ReferencedWarning referencedWarning = copyBookService.getReferencedWarning(idCopyBook);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        copyBookService.delete(idCopyBook);
        return ResponseEntity.noContent().build();
    }

}
