package io.bootify.library.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class ReturnLoaningDTO {

    private Long idReturnLoaning;

    @NotNull
    @Size(max = 255)
    private String returnDate;

    public Long getIdReturnLoaning() {
        return idReturnLoaning;
    }

    public void setIdReturnLoaning(final Long idReturnLoaning) {
        this.idReturnLoaning = idReturnLoaning;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(final String returnDate) {
        this.returnDate = returnDate;
    }

}
