package io.bootify.library.rest;

import io.bootify.library.model.BookMemberDTO;
import io.bootify.library.service.BookMemberService;
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
@RequestMapping(value = "/api/bookMembers", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookMemberResource {

    private final BookMemberService bookMemberService;

    public BookMemberResource(final BookMemberService bookMemberService) {
        this.bookMemberService = bookMemberService;
    }

    @GetMapping
    public ResponseEntity<List<BookMemberDTO>> getAllBookMembers() {
        return ResponseEntity.ok(bookMemberService.findAll());
    }

    @GetMapping("/{idBookMember}")
    public ResponseEntity<BookMemberDTO> getBookMember(
            @PathVariable(name = "idBookMember") final Integer idBookMember) {
        return ResponseEntity.ok(bookMemberService.get(idBookMember));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createBookMember(
            @RequestBody @Valid final BookMemberDTO bookMemberDTO) throws Exception {
        final Integer createdIdBookMember = bookMemberService.create(bookMemberDTO);
        return new ResponseEntity<>(createdIdBookMember, HttpStatus.CREATED);
    }

    @PutMapping("/{idBookMember}")
    public ResponseEntity<Integer> updateBookMember(
            @PathVariable(name = "idBookMember") final Integer idBookMember,
            @RequestBody @Valid final BookMemberDTO bookMemberDTO) {
        bookMemberService.update(idBookMember, bookMemberDTO);
        return ResponseEntity.ok(idBookMember);
    }

    @DeleteMapping("/{idBookMember}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteBookMember(
            @PathVariable(name = "idBookMember") final Integer idBookMember) {
        bookMemberService.delete(idBookMember);
        return ResponseEntity.noContent().build();
    }

}
