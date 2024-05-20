package io.bootify.library.repos;

import io.bootify.library.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
