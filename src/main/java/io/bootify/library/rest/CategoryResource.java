package io.bootify.library.rest;

import io.bootify.library.model.CategoryDTO;
import io.bootify.library.service.CategoryService;
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
@RequestMapping(value = "/api/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryResource {

    private final CategoryService categoryService;

    public CategoryResource(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/{idCategory}")
    public ResponseEntity<CategoryDTO> getCategory(
            @PathVariable(name = "idCategory") final Integer idCategory) {
        return ResponseEntity.ok(categoryService.get(idCategory));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createCategory(
            @RequestBody @Valid final CategoryDTO categoryDTO) {
        final Integer createdIdCategory = categoryService.create(categoryDTO);
        return new ResponseEntity<>(createdIdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{idCategory}")
    public ResponseEntity<Integer> updateCategory(
            @PathVariable(name = "idCategory") final Integer idCategory,
            @RequestBody @Valid final CategoryDTO categoryDTO) {
        categoryService.update(idCategory, categoryDTO);
        return ResponseEntity.ok(idCategory);
    }

    @DeleteMapping("/{idCategory}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable(name = "idCategory") final Integer idCategory) {
        final ReferencedWarning referencedWarning = categoryService.getReferencedWarning(idCategory);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        categoryService.delete(idCategory);
        return ResponseEntity.noContent().build();
    }

}
