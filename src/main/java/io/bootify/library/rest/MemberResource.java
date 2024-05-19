package io.bootify.library.rest;

import io.bootify.library.model.MemberDTO;
import io.bootify.library.service.MemberService;
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
@RequestMapping(value = "/api/members", produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberResource {

    private final MemberService memberService;

    public MemberResource(final MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<MemberDTO>> getAllMembers() {
        return ResponseEntity.ok(memberService.findAll());
    }

    @GetMapping("/{idMember}")
    public ResponseEntity<MemberDTO> getMember(
            @PathVariable(name = "idMember") final Integer idMember) {
        return ResponseEntity.ok(memberService.get(idMember));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createMember(@RequestBody @Valid final MemberDTO memberDTO) {
        final Integer createdIdMember = memberService.create(memberDTO);
        return new ResponseEntity<>(createdIdMember, HttpStatus.CREATED);
    }

    @PutMapping("/{idMember}")
    public ResponseEntity<Integer> updateMember(
            @PathVariable(name = "idMember") final Integer idMember,
            @RequestBody @Valid final MemberDTO memberDTO) {
        memberService.update(idMember, memberDTO);
        return ResponseEntity.ok(idMember);
    }

    @DeleteMapping("/{idMember}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMember(
            @PathVariable(name = "idMember") final Integer idMember) {
        final ReferencedWarning referencedWarning = memberService.getReferencedWarning(idMember);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        memberService.delete(idMember);
        return ResponseEntity.noContent().build();
    }

}
