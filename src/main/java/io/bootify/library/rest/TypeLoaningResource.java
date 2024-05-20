package io.bootify.library.rest;

import io.bootify.library.model.TypeLoaningDTO;
import io.bootify.library.service.TypeLoaningService;
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
@RequestMapping(value = "/api/typeLoanings", produces = MediaType.APPLICATION_JSON_VALUE)
public class TypeLoaningResource {

    private final TypeLoaningService typeLoaningService;

    public TypeLoaningResource(final TypeLoaningService typeLoaningService) {
        this.typeLoaningService = typeLoaningService;
    }

    @GetMapping
    public ResponseEntity<List<TypeLoaningDTO>> getAllTypeLoanings() {
        return ResponseEntity.ok(typeLoaningService.findAll());
    }

    @GetMapping("/{idTypeLoaning}")
    public ResponseEntity<TypeLoaningDTO> getTypeLoaning(
            @PathVariable(name = "idTypeLoaning") final Integer idTypeLoaning) {
        return ResponseEntity.ok(typeLoaningService.get(idTypeLoaning));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createTypeLoaning(
            @RequestBody @Valid final TypeLoaningDTO typeLoaningDTO) {
        final Integer createdIdTypeLoaning = typeLoaningService.create(typeLoaningDTO);
        return new ResponseEntity<>(createdIdTypeLoaning, HttpStatus.CREATED);
    }

    @PutMapping("/{idTypeLoaning}")
    public ResponseEntity<Integer> updateTypeLoaning(
            @PathVariable(name = "idTypeLoaning") final Integer idTypeLoaning,
            @RequestBody @Valid final TypeLoaningDTO typeLoaningDTO) {
        typeLoaningService.update(idTypeLoaning, typeLoaningDTO);
        return ResponseEntity.ok(idTypeLoaning);
    }

    @DeleteMapping("/{idTypeLoaning}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTypeLoaning(
            @PathVariable(name = "idTypeLoaning") final Integer idTypeLoaning) {
        final ReferencedWarning referencedWarning = typeLoaningService.getReferencedWarning(idTypeLoaning);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        typeLoaningService.delete(idTypeLoaning);
        return ResponseEntity.noContent().build();
    }

}
