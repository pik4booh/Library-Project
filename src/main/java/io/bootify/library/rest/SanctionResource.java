package io.bootify.library.rest;

import io.bootify.library.model.SanctionDTO;
import io.bootify.library.service.SanctionService;
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
@RequestMapping(value = "/api/sanctions", produces = MediaType.APPLICATION_JSON_VALUE)
public class SanctionResource {

    private final SanctionService sanctionService;

    public SanctionResource(final SanctionService sanctionService) {
        this.sanctionService = sanctionService;
    }

    @GetMapping
    public ResponseEntity<List<SanctionDTO>> getAllSanctions() {
        return ResponseEntity.ok(sanctionService.findAll());
    }

    @GetMapping("/{idSanction}")
    public ResponseEntity<SanctionDTO> getSanction(
            @PathVariable(name = "idSanction") final Integer idSanction) {
        return ResponseEntity.ok(sanctionService.get(idSanction));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createSanction(
            @RequestBody @Valid final SanctionDTO sanctionDTO) {
        final Integer createdIdSanction = sanctionService.create(sanctionDTO);
        return new ResponseEntity<>(createdIdSanction, HttpStatus.CREATED);
    }

    @PutMapping("/{idSanction}")
    public ResponseEntity<Integer> updateSanction(
            @PathVariable(name = "idSanction") final Integer idSanction,
            @RequestBody @Valid final SanctionDTO sanctionDTO) {
        sanctionService.update(idSanction, sanctionDTO);
        return ResponseEntity.ok(idSanction);
    }

    @DeleteMapping("/{idSanction}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSanction(
            @PathVariable(name = "idSanction") final Integer idSanction) {
        sanctionService.delete(idSanction);
        return ResponseEntity.noContent().build();
    }

}
