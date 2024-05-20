package io.bootify.library.model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;


public class ReturnLoaningDTO {

    private Integer idReturnLoaning;

    @NotNull
    private LocalDateTime returnDate;

    @ReturnLoaningLoaningUnique
    private Integer loaning;

    public Integer getIdReturnLoaning() {
        return idReturnLoaning;
    }

    public void setIdReturnLoaning(final Integer idReturnLoaning) {
        this.idReturnLoaning = idReturnLoaning;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(final LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getLoaning() {
        return loaning;
    }

    public void setLoaning(final Integer loaning) {
        this.loaning = loaning;
    }

}
