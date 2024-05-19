package io.bootify.library.service;

import io.bootify.library.domain.BookCategory;
import io.bootify.library.domain.Category;
import io.bootify.library.model.CategoryDTO;
import io.bootify.library.repos.BookCategoryRepository;
import io.bootify.library.repos.CategoryRepository;
import io.bootify.library.util.NotFoundException;
import io.bootify.library.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final BookCategoryRepository bookCategoryRepository;

    public CategoryService(final CategoryRepository categoryRepository,
            final BookCategoryRepository bookCategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.bookCategoryRepository = bookCategoryRepository;
    }

    public List<CategoryDTO> findAll() {
        final List<Category> categories = categoryRepository.findAll(Sort.by("idCategory"));
        return categories.stream()
                .map(category -> mapToDTO(category, new CategoryDTO()))
                .toList();
    }

    public CategoryDTO get(final Integer idCategory) {
        return categoryRepository.findById(idCategory)
                .map(category -> mapToDTO(category, new CategoryDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final CategoryDTO categoryDTO) {
        final Category category = new Category();
        mapToEntity(categoryDTO, category);
        return categoryRepository.save(category).getIdCategory();
    }

    public void update(final Integer idCategory, final CategoryDTO categoryDTO) {
        final Category category = categoryRepository.findById(idCategory)
                .orElseThrow(NotFoundException::new);
        mapToEntity(categoryDTO, category);
        categoryRepository.save(category);
    }

    public void delete(final Integer idCategory) {
        categoryRepository.deleteById(idCategory);
    }

    private CategoryDTO mapToDTO(final Category category, final CategoryDTO categoryDTO) {
        categoryDTO.setIdCategory(category.getIdCategory());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }

    private Category mapToEntity(final CategoryDTO categoryDTO, final Category category) {
        category.setName(categoryDTO.getName());
        return category;
    }

    public ReferencedWarning getReferencedWarning(final Integer idCategory) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Category category = categoryRepository.findById(idCategory)
                .orElseThrow(NotFoundException::new);
        final BookCategory categoryBookCategory = bookCategoryRepository.findFirstByCategory(category);
        if (categoryBookCategory != null) {
            referencedWarning.setKey("category.bookCategory.category.referenced");
            referencedWarning.addParam(categoryBookCategory.getIdBookCategory());
            return referencedWarning;
        }
        return null;
    }

}
