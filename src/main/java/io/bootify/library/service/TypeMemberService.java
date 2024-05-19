package io.bootify.library.service;

import io.bootify.library.domain.BookMember;
import io.bootify.library.domain.Member;
import io.bootify.library.domain.TypeMember;
import io.bootify.library.model.TypeMemberDTO;
import io.bootify.library.repos.BookMemberRepository;
import io.bootify.library.repos.MemberRepository;
import io.bootify.library.repos.TypeMemberRepository;
import io.bootify.library.util.NotFoundException;
import io.bootify.library.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TypeMemberService {

    private final TypeMemberRepository typeMemberRepository;
    private final MemberRepository memberRepository;
    private final BookMemberRepository bookMemberRepository;

    public TypeMemberService(final TypeMemberRepository typeMemberRepository,
            final MemberRepository memberRepository,
            final BookMemberRepository bookMemberRepository) {
        this.typeMemberRepository = typeMemberRepository;
        this.memberRepository = memberRepository;
        this.bookMemberRepository = bookMemberRepository;
    }

    public List<TypeMemberDTO> findAll() {
        final List<TypeMember> typeMembers = typeMemberRepository.findAll(Sort.by("idTypeMember"));
        return typeMembers.stream()
                .map(typeMember -> mapToDTO(typeMember, new TypeMemberDTO()))
                .toList();
    }

    public TypeMemberDTO get(final Integer idTypeMember) {
        return typeMemberRepository.findById(idTypeMember)
                .map(typeMember -> mapToDTO(typeMember, new TypeMemberDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final TypeMemberDTO typeMemberDTO) {
        final TypeMember typeMember = new TypeMember();
        mapToEntity(typeMemberDTO, typeMember);
        return typeMemberRepository.save(typeMember).getIdTypeMember();
    }

    public void update(final Integer idTypeMember, final TypeMemberDTO typeMemberDTO) {
        final TypeMember typeMember = typeMemberRepository.findById(idTypeMember)
                .orElseThrow(NotFoundException::new);
        mapToEntity(typeMemberDTO, typeMember);
        typeMemberRepository.save(typeMember);
    }

    public void delete(final Integer idTypeMember) {
        typeMemberRepository.deleteById(idTypeMember);
    }

    private TypeMemberDTO mapToDTO(final TypeMember typeMember, final TypeMemberDTO typeMemberDTO) {
        typeMemberDTO.setIdTypeMember(typeMember.getIdTypeMember());
        typeMemberDTO.setName(typeMember.getName());
        typeMemberDTO.setNbLoaningDays(typeMember.getNbLoaningDays());
        typeMemberDTO.setCoeffSanction(typeMember.getCoeffSanction());
        return typeMemberDTO;
    }

    private TypeMember mapToEntity(final TypeMemberDTO typeMemberDTO, final TypeMember typeMember) {
        typeMember.setName(typeMemberDTO.getName());
        typeMember.setNbLoaningDays(typeMemberDTO.getNbLoaningDays());
        typeMember.setCoeffSanction(typeMemberDTO.getCoeffSanction());
        return typeMember;
    }

    public ReferencedWarning getReferencedWarning(final Integer idTypeMember) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final TypeMember typeMember = typeMemberRepository.findById(idTypeMember)
                .orElseThrow(NotFoundException::new);
        final Member typeMemberMember = memberRepository.findFirstByTypeMember(typeMember);
        if (typeMemberMember != null) {
            referencedWarning.setKey("typeMember.member.typeMember.referenced");
            referencedWarning.addParam(typeMemberMember.getIdMember());
            return referencedWarning;
        }
        final BookMember typeMemberBookMember = bookMemberRepository.findFirstByTypeMember(typeMember);
        if (typeMemberBookMember != null) {
            referencedWarning.setKey("typeMember.bookMember.typeMember.referenced");
            referencedWarning.addParam(typeMemberBookMember.getIdBookMember());
            return referencedWarning;
        }
        return null;
    }

}
