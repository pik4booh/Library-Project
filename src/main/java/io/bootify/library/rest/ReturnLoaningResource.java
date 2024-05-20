package io.bootify.library.rest;

import io.bootify.library.model.ReturnLoaningDTO;
import io.bootify.library.service.ReturnLoaningService;
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
@RequestMapping(value = "/api/returnLoanings", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReturnLoaningResource {

    private final ReturnLoaningService returnLoaningService;

    public ReturnLoaningResource(final ReturnLoaningService returnLoaningService) {
        this.returnLoaningService = returnLoaningService;
    }

    @GetMapping
    public ResponseEntity<List<ReturnLoaningDTO>> getAllReturnLoanings() {
        return ResponseEntity.ok(returnLoaningService.findAll());
    }

    @GetMapping("/{idReturnLoaning}")
    public ResponseEntity<ReturnLoaningDTO> getReturnLoaning(
            @PathVariable(name = "idReturnLoaning") final Integer idReturnLoaning) {
        return ResponseEntity.ok(returnLoaningService.get(idReturnLoaning));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createReturnLoaning(
            @RequestBody @Valid final ReturnLoaningDTO returnLoaningDTO) {
        final Integer createdIdReturnLoaning = returnLoaningService.create(returnLoaningDTO);
        return new ResponseEntity<>(createdIdReturnLoaning, HttpStatus.CREATED);
    }

    @PutMapping("/{idReturnLoaning}")
    public ResponseEntity<Integer> updateReturnLoaning(
            @PathVariable(name = "idReturnLoaning") final Integer idReturnLoaning,
            @RequestBody @Valid final ReturnLoaningDTO returnLoaningDTO) {
        returnLoaningService.update(idReturnLoaning, returnLoaningDTO);
        return ResponseEntity.ok(idReturnLoaning);
    }

    @DeleteMapping("/{idReturnLoaning}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteReturnLoaning(
            @PathVariable(name = "idReturnLoaning") final Integer idReturnLoaning) {
        returnLoaningService.delete(idReturnLoaning);
        return ResponseEntity.noContent().build();
    }

}
