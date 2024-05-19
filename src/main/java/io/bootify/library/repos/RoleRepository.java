package io.bootify.library.repos;

import io.bootify.library.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Integer> {
}
