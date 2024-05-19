package io.bootify.library.model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;


public class SanctionDTO {

    private Integer idSanction;

    @NotNull
    private LocalDateTime dateBegin;

    @NotNull
    private LocalDateTime dateEnd;

    private Integer member;

    private Integer copyBook;

    public Integer getIdSanction() {
        return idSanction;
    }

    public void setIdSanction(final Integer idSanction) {
        this.idSanction = idSanction;
    }

    public LocalDateTime getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(final LocalDateTime dateBegin) {
        this.dateBegin = dateBegin;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(final LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
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

}
