package io.bootify.library.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.Set;


@Entity
public class TypeMember {

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
    private Integer idTypeMember;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer nbLoaningDays;

    @Column(nullable = false)
    private Integer coeffSanction;

    @OneToMany(mappedBy = "typeMember")
    private Set<Member> members;

    @OneToMany(mappedBy = "typeMember")
    private Set<BookMember> bookMembers;

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

    public Set<Member> getMembers() {
        return members;
    }

    public void setMembers(final Set<Member> members) {
        this.members = members;
    }

    public Set<BookMember> getBookMembers() {
        return bookMembers;
    }

    public void setBookMembers(final Set<BookMember> bookMembers) {
        this.bookMembers = bookMembers;
    }

}
