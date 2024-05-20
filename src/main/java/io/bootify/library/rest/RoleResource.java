package io.bootify.library.rest;

import io.bootify.library.model.RoleDTO;
import io.bootify.library.service.RoleService;
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
@RequestMapping(value = "/api/roles", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleResource {

    private final RoleService roleService;

    public RoleResource(final RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @GetMapping("/{idRole}")
    public ResponseEntity<RoleDTO> getRole(@PathVariable(name = "idRole") final Integer idRole) {
        return ResponseEntity.ok(roleService.get(idRole));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createRole(@RequestBody @Valid final RoleDTO roleDTO) {
        final Integer createdIdRole = roleService.create(roleDTO);
        return new ResponseEntity<>(createdIdRole, HttpStatus.CREATED);
    }

    @PutMapping("/{idRole}")
    public ResponseEntity<Integer> updateRole(@PathVariable(name = "idRole") final Integer idRole,
            @RequestBody @Valid final RoleDTO roleDTO) {
        roleService.update(idRole, roleDTO);
        return ResponseEntity.ok(idRole);
    }

    @DeleteMapping("/{idRole}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRole(@PathVariable(name = "idRole") final Integer idRole) {
        final ReferencedWarning referencedWarning = roleService.getReferencedWarning(idRole);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        roleService.delete(idRole);
        return ResponseEntity.noContent().build();
    }

}
