package io.bootify.library.repos;

import io.bootify.library.domain.Member;
import io.bootify.library.domain.TypeMember;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Integer> {

    Member findFirstByTypeMember(TypeMember typeMember);
    Member findById(int idMember);

}
