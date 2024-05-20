package io.bootify.library.rest;

import io.bootify.library.model.TypeMemberDTO;
import io.bootify.library.service.TypeMemberService;
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
@RequestMapping(value = "/api/typeMembers", produces = MediaType.APPLICATION_JSON_VALUE)
public class TypeMemberResource {

    private final TypeMemberService typeMemberService;

    public TypeMemberResource(final TypeMemberService typeMemberService) {
        this.typeMemberService = typeMemberService;
    }

    @GetMapping
    public ResponseEntity<List<TypeMemberDTO>> getAllTypeMembers() {
        return ResponseEntity.ok(typeMemberService.findAll());
    }

    @GetMapping("/{idTypeMember}")
    public ResponseEntity<TypeMemberDTO> getTypeMember(
            @PathVariable(name = "idTypeMember") final Integer idTypeMember) {
        return ResponseEntity.ok(typeMemberService.get(idTypeMember));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createTypeMember(
            @RequestBody @Valid final TypeMemberDTO typeMemberDTO) {
        final Integer createdIdTypeMember = typeMemberService.create(typeMemberDTO);
        return new ResponseEntity<>(createdIdTypeMember, HttpStatus.CREATED);
    }

    @PutMapping("/{idTypeMember}")
    public ResponseEntity<Integer> updateTypeMember(
            @PathVariable(name = "idTypeMember") final Integer idTypeMember,
            @RequestBody @Valid final TypeMemberDTO typeMemberDTO) {
        typeMemberService.update(idTypeMember, typeMemberDTO);
        return ResponseEntity.ok(idTypeMember);
    }

    @DeleteMapping("/{idTypeMember}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTypeMember(
            @PathVariable(name = "idTypeMember") final Integer idTypeMember) {
        final ReferencedWarning referencedWarning = typeMemberService.getReferencedWarning(idTypeMember);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        typeMemberService.delete(idTypeMember);
        return ResponseEntity.noContent().build();
    }

}
