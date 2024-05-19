package io.bootify.library.service;

import io.bootify.library.domain.Loaning;
import io.bootify.library.domain.Member;
import io.bootify.library.domain.Sanction;
import io.bootify.library.domain.TypeMember;
import io.bootify.library.model.MemberDTO;
import io.bootify.library.repos.LoaningRepository;
import io.bootify.library.repos.MemberRepository;
import io.bootify.library.repos.SanctionRepository;
import io.bootify.library.repos.TypeMemberRepository;
import io.bootify.library.util.NotFoundException;
import io.bootify.library.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final TypeMemberRepository typeMemberRepository;
    private final LoaningRepository loaningRepository;
    private final SanctionRepository sanctionRepository;

    public MemberService(final MemberRepository memberRepository,
            final TypeMemberRepository typeMemberRepository,
            final LoaningRepository loaningRepository,
            final SanctionRepository sanctionRepository) {
        this.memberRepository = memberRepository;
        this.typeMemberRepository = typeMemberRepository;
        this.loaningRepository = loaningRepository;
        this.sanctionRepository = sanctionRepository;
    }

    public List<MemberDTO> findAll() {
        final List<Member> members = memberRepository.findAll(Sort.by("idMember"));
        return members.stream()
                .map(member -> mapToDTO(member, new MemberDTO()))
                .toList();
    }

    public MemberDTO get(final Integer idMember) {
        return memberRepository.findById(idMember)
                .map(member -> mapToDTO(member, new MemberDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final MemberDTO memberDTO) {
        final Member member = new Member();
        mapToEntity(memberDTO, member);
        return memberRepository.save(member).getIdMember();
    }

    public void update(final Integer idMember, final MemberDTO memberDTO) {
        final Member member = memberRepository.findById(idMember)
                .orElseThrow(NotFoundException::new);
        mapToEntity(memberDTO, member);
        memberRepository.save(member);
    }

    public void delete(final Integer idMember) {
        memberRepository.deleteById(idMember);
    }

    private MemberDTO mapToDTO(final Member member, final MemberDTO memberDTO) {
        memberDTO.setIdMember(member.getIdMember());
        memberDTO.setName(member.getName());
        memberDTO.setBirth(member.getBirth());
        memberDTO.setIdentifiant(member.getIdentifiant());
        memberDTO.setAddress(member.getAddress());
        memberDTO.setDateRegister(member.getDateRegister());
        memberDTO.setTypeMember(member.getTypeMember() == null ? null : member.getTypeMember().getIdTypeMember());
        memberDTO.setTypeMemberName(member.getTypeMember() == null ? null : member.getTypeMember().getName());
        return memberDTO;
    }

    private Member mapToEntity(final MemberDTO memberDTO, final Member member) {
        member.setName(memberDTO.getName());
        member.setBirth(memberDTO.getBirth());
        member.setIdentifiant(memberDTO.getIdentifiant());
        member.setAddress(memberDTO.getAddress());
        member.setDateRegister(memberDTO.getDateRegister());
        final TypeMember typeMember = memberDTO.getTypeMember() == null ? null : typeMemberRepository.findById(memberDTO.getTypeMember())
                .orElseThrow(() -> new NotFoundException("typeMember not found"));
        member.setTypeMember(typeMember);
        return member;
    }

    public ReferencedWarning getReferencedWarning(final Integer idMember) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Member member = memberRepository.findById(idMember)
                .orElseThrow(NotFoundException::new);
        final Loaning memberLoaning = loaningRepository.findFirstByMember(member);
        if (memberLoaning != null) {
            referencedWarning.setKey("member.loaning.member.referenced");
            referencedWarning.addParam(memberLoaning.getIdLoaning());
            return referencedWarning;
        }
        final Sanction memberSanction = sanctionRepository.findFirstByMember(member);
        if (memberSanction != null) {
            referencedWarning.setKey("member.sanction.member.referenced");
            referencedWarning.addParam(memberSanction.getIdSanction());
            return referencedWarning;
        }
        return null;
    }

}
