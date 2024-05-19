package io.bootify.library.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class TypeMemberDTO {

    private Integer idTypeMember;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private Integer nbLoaningDays;

    @NotNull
    private Integer coeffSanction;

    public Integer getIdTypeMember() {
        return idTypeMember;
    }

    public void setIdTypeMember(final Integer idTypeMember) {
        this.idTypeMember = idTypeMember;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getNbLoaningDays() {
        return nbLoaningDays;
    }

    public void setNbLoaningDays(final Integer nbLoaningDays) {
        this.nbLoaningDays = nbLoaningDays;
    }

    public Integer getCoeffSanction() {
        return coeffSanction;
    }

    public void setCoeffSanction(final Integer coeffSanction) {
        this.coeffSanction = coeffSanction;
    }

}
