package io.bootify.library.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.time.LocalDateTime;
import java.util.Set;


@Entity
public class Member {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer idMember;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime birth;

    @Column(nullable = false)
    private String identifiant;

    @Column
    private String address;

    @Column(nullable = false)
    private LocalDateTime dateRegister;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_member_id")
    private TypeMember typeMember;

    @OneToMany(mappedBy = "member")
    private Set<Loaning> loanings;

    @OneToMany(mappedBy = "member")
    private Set<Sanction> sanctions;

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

    public TypeMember getTypeMember() {
        return typeMember;
    }

    public void setTypeMember(final TypeMember typeMember) {
        this.typeMember = typeMember;
    }

    public Set<Loaning> getLoanings() {
        return loanings;
    }

    public void setLoanings(final Set<Loaning> loanings) {
        this.loanings = loanings;
    }

    public Set<Sanction> getSanctions() {
        return sanctions;
    }

    public void setSanctions(final Set<Sanction> sanctions) {
        this.sanctions = sanctions;
    }

}
