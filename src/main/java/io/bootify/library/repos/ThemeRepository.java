package io.bootify.library.repos;

import io.bootify.library.domain.Theme;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ThemeRepository extends JpaRepository<Theme, Integer> {
}
