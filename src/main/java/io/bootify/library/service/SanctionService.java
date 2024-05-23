package io.bootify.library.service;

import io.bootify.library.domain.CopyBook;
import io.bootify.library.domain.Member;
import io.bootify.library.domain.Sanction;
import io.bootify.library.model.SanctionDTO;
import io.bootify.library.repos.CopyBookRepository;
import io.bootify.library.repos.MemberRepository;
import io.bootify.library.repos.SanctionRepository;
import io.bootify.library.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SanctionService {

    private final SanctionRepository sanctionRepository;
    private final MemberRepository memberRepository;
    private final CopyBookRepository copyBookRepository;

    public SanctionService(final SanctionRepository sanctionRepository,
            final MemberRepository memberRepository, final CopyBookRepository copyBookRepository) {
        this.sanctionRepository = sanctionRepository;
        this.memberRepository = memberRepository;
        this.copyBookRepository = copyBookRepository;
    }

    public List<SanctionDTO> findAll() {
        final List<Sanction> sanctions = sanctionRepository.findAll(Sort.by("idSanction"));
        return sanctions.stream()
                .map(sanction -> mapToDTO(sanction, new SanctionDTO()))
                .toList();
    }

    public SanctionDTO get(final Integer idSanction) {
        return sanctionRepository.findById(idSanction)
                .map(sanction -> mapToDTO(sanction, new SanctionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final SanctionDTO sanctionDTO) {
        final Sanction sanction = new Sanction();
        mapToEntity(sanctionDTO, sanction);
        return sanctionRepository.save(sanction).getIdSanction();
    }

    public void update(final Integer idSanction, final SanctionDTO sanctionDTO) {
        final Sanction sanction = sanctionRepository.findById(idSanction)
                .orElseThrow(NotFoundException::new);
        mapToEntity(sanctionDTO, sanction);
        sanctionRepository.save(sanction);
    }

    public void delete(final Integer idSanction) {
        sanctionRepository.deleteById(idSanction);
    }

    private SanctionDTO mapToDTO(final Sanction sanction, final SanctionDTO sanctionDTO) {
        sanctionDTO.setIdSanction(sanction.getIdSanction());
        sanctionDTO.setDateBegin(sanction.getDateBegin());
        sanctionDTO.setDateEnd(sanction.getDateEnd());
        sanctionDTO.setMember(sanction.getMember() == null ? null : sanction.getMember().getIdMember());
        sanctionDTO.setCopyBook(sanction.getCopyBook() == null ? null : sanction.getCopyBook().getIdCopyBook());
        return sanctionDTO;
    }

    private Sanction mapToEntity(final SanctionDTO sanctionDTO, final Sanction sanction) {
        sanction.setDateBegin(sanctionDTO.getDateBegin());
        sanction.setDateEnd(sanctionDTO.getDateEnd());
        final Member member = sanctionDTO.getMember() == null ? null : memberRepository.findById(sanctionDTO.getMember())
                .orElseThrow(() -> new NotFoundException("member not found"));
        sanction.setMember(member);
        final CopyBook copyBook = sanctionDTO.getCopyBook() == null ? null : copyBookRepository.findById(sanctionDTO.getCopyBook())
                .orElseThrow(() -> new NotFoundException("copyBook not found"));
        sanction.setCopyBook(copyBook);
        return sanction;
    }

    public void checkMemberSanction(int idMember){

        Sanction sanction = sanctionRepository.findActiveSanctionByMemberId(idMember);
        if(sanction != null){
            throw new RuntimeException("This member has active sanction till " + sanction.getDateEnd() + " and can't loan books.");
        }
    }

    public String sanctionStatus(int idMember){
        Sanction sanction = sanctionRepository.findActiveSanctionByMemberId(idMember);
        if(sanction != null){
            return new String("This member has active sanction till " + sanction.getDateEnd() + " and can't loan books.");
        }else{
            return "N/A";
        }
    }

}
