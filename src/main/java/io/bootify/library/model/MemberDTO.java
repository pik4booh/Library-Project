package io.bootify.library.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;


public class MemberDTO {

    private Integer idMember;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private LocalDateTime birth;

    @NotNull
    @Size(max = 255)
    private String identifiant;

    @Size(max = 255)
    private String address;

    @NotNull
    private LocalDateTime dateRegister;

    private Integer typeMember;

    private String typeMemberName;

    public Integer getIdMember() {
        return idMember;
    }

    public void setIdMember(final Integer idMember) {
        this.idMember = idMember;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public LocalDateTime getBirth() {
        return birth;
    }

    public void setBirth(final LocalDateTime birth) {
        this.birth = birth;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(final String identifiant) {
        this.identifiant = identifiant;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public LocalDateTime getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(final LocalDateTime dateRegister) {
        this.dateRegister = dateRegister;
    }

    public Integer getTypeMember() {
        return typeMember;
    }

    public void setTypeMember(final Integer typeMember) {
        this.typeMember = typeMember;
    }

    public String getTypeMemberName() {
        return typeMemberName;
    }

    public void setTypeMemberName(final String typeMemberName) {
        this.typeMemberName = typeMemberName;
    }

}
