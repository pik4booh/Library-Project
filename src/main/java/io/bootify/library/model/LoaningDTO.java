package io.bootify.library.model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;


public class LoaningDTO {

    private Integer idLoaning;

    @NotNull
    private LocalDateTime loaningDate;

    @NotNull
    private LocalDateTime expectedReturnDate;

    private Integer member;

    private Integer copyBook;

    private Integer typeLoaning;

    @LoaningReturnLoaningUnique
    private Long returnLoaning;

    public Integer getIdLoaning() {
        return idLoaning;
    }

    public void setIdLoaning(final Integer idLoaning) {
        this.idLoaning = idLoaning;
    }

    public LocalDateTime getLoaningDate() {
        return loaningDate;
    }

    public void setLoaningDate(final LocalDateTime loaningDate) {
        this.loaningDate = loaningDate;
    }

    public LocalDateTime getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public void setExpectedReturnDate(final LocalDateTime expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }

    public Integer getMember() {
        return member;
    }

    public void setMember(final Integer member) {
        this.member = member;
    }

    public Integer getCopyBook() {
        return copyBook;
    }

    public void setCopyBook(final Integer copyBook) {
        this.copyBook = copyBook;
    }

    public Integer getTypeLoaning() {
        return typeLoaning;
    }

    public void setTypeLoaning(final Integer typeLoaning) {
        this.typeLoaning = typeLoaning;
    }

    public Long getReturnLoaning() {
        return returnLoaning;
    }

    public void setReturnLoaning(final Long returnLoaning) {
        this.returnLoaning = returnLoaning;
    }

}
