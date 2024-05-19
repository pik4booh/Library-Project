package io.bootify.library.repos;

import io.bootify.library.domain.Librarian;
import io.bootify.library.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LibrarianRepository extends JpaRepository<Librarian, Integer> {

    Librarian findFirstByRole(Role role);

    Librarian findByNameAndPwd(String name, String pwd);

}
