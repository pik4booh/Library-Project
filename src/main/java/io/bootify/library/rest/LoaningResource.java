package io.bootify.library.rest;

import io.bootify.library.model.LoaningDTO;
import io.bootify.library.service.LoaningService;
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
@RequestMapping(value = "/api/loanings", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoaningResource {

    private final LoaningService loaningService;

    public LoaningResource(final LoaningService loaningService) {
        this.loaningService = loaningService;
    }

    @GetMapping
    public ResponseEntity<List<LoaningDTO>> getAllLoanings() {
        return ResponseEntity.ok(loaningService.findAll());
    }

    @GetMapping("/{idLoaning}")
    public ResponseEntity<LoaningDTO> getLoaning(
            @PathVariable(name = "idLoaning") final Integer idLoaning) {
        return ResponseEntity.ok(loaningService.get(idLoaning));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createLoaning(@RequestBody @Valid final LoaningDTO loaningDTO) {
        final Integer createdIdLoaning = loaningService.create(loaningDTO);
        return new ResponseEntity<>(createdIdLoaning, HttpStatus.CREATED);
    }

    @PutMapping("/{idLoaning}")
    public ResponseEntity<Integer> updateLoaning(
            @PathVariable(name = "idLoaning") final Integer idLoaning,
            @RequestBody @Valid final LoaningDTO loaningDTO) {
        loaningService.update(idLoaning, loaningDTO);
        return ResponseEntity.ok(idLoaning);
    }

    @DeleteMapping("/{idLoaning}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteLoaning(
            @PathVariable(name = "idLoaning") final Integer idLoaning) {
        loaningService.delete(idLoaning);
        return ResponseEntity.noContent().build();
    }

}
