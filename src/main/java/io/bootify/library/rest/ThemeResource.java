package io.bootify.library.rest;

import io.bootify.library.model.ThemeDTO;
import io.bootify.library.service.ThemeService;
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
@RequestMapping(value = "/api/themes", produces = MediaType.APPLICATION_JSON_VALUE)
public class ThemeResource {

    private final ThemeService themeService;

    public ThemeResource(final ThemeService themeService) {
        this.themeService = themeService;
    }

    @GetMapping
    public ResponseEntity<List<ThemeDTO>> getAllThemes() {
        return ResponseEntity.ok(themeService.findAll());
    }

    @GetMapping("/{idTheme}")
    public ResponseEntity<ThemeDTO> getTheme(
            @PathVariable(name = "idTheme") final Integer idTheme) {
        return ResponseEntity.ok(themeService.get(idTheme));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createTheme(@RequestBody @Valid final ThemeDTO themeDTO) {
        final Integer createdIdTheme = themeService.create(themeDTO);
        return new ResponseEntity<>(createdIdTheme, HttpStatus.CREATED);
    }

    @PutMapping("/{idTheme}")
    public ResponseEntity<Integer> updateTheme(
            @PathVariable(name = "idTheme") final Integer idTheme,
            @RequestBody @Valid final ThemeDTO themeDTO) {
        themeService.update(idTheme, themeDTO);
        return ResponseEntity.ok(idTheme);
    }

    @DeleteMapping("/{idTheme}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTheme(@PathVariable(name = "idTheme") final Integer idTheme) {
        final ReferencedWarning referencedWarning = themeService.getReferencedWarning(idTheme);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        themeService.delete(idTheme);
        return ResponseEntity.noContent().build();
    }

}
