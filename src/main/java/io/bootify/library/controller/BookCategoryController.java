package io.bootify.library.controller;

import io.bootify.library.domain.Book;
import io.bootify.library.domain.Category;
import io.bootify.library.model.BookCategoryDTO;
import io.bootify.library.repos.BookRepository;
import io.bootify.library.repos.CategoryRepository;
import io.bootify.library.service.BookCategoryService;
import io.bootify.library.util.CustomCollectors;
import io.bootify.library.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/bookCategories")
public class BookCategoryController {

    private final BookCategoryService bookCategoryService;
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;

    public BookCategoryController(final BookCategoryService bookCategoryService,
            final CategoryRepository categoryRepository, final BookRepository bookRepository) {
        this.bookCategoryService = bookCategoryService;
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("categoryValues", categoryRepository.findAll(Sort.by("idCategory"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Category::getIdCategory, Category::getName)));
        model.addAttribute("bookValues", bookRepository.findAll(Sort.by("idBook"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Book::getIdBook, Book::getTitle)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("bookCategories", bookCategoryService.findAll());
        return "bookCategory/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("bookCategory") final BookCategoryDTO bookCategoryDTO) {
        return "bookCategory/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("bookCategory") @Valid final BookCategoryDTO bookCategoryDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "bookCategory/add";
        }
        bookCategoryService.create(bookCategoryDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("bookCategory.create.success"));
        return "redirect:/bookCategories";
    }

    @GetMapping("/edit/{idBookCategory}")
    public String edit(@PathVariable(name = "idBookCategory") final Integer idBookCategory,
            final Model model) {
        model.addAttribute("bookCategory", bookCategoryService.get(idBookCategory));
        return "bookCategory/edit";
    }

    @PostMapping("/edit/{idBookCategory}")
    public String edit(@PathVariable(name = "idBookCategory") final Integer idBookCategory,
            @ModelAttribute("bookCategory") @Valid final BookCategoryDTO bookCategoryDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "bookCategory/edit";
        }
        bookCategoryService.update(idBookCategory, bookCategoryDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("bookCategory.update.success"));
        return "redirect:/bookCategories";
    }

    @PostMapping("/delete/{idBookCategory}")
    public String delete(@PathVariable(name = "idBookCategory") final Integer idBookCategory,
            final RedirectAttributes redirectAttributes) {
        bookCategoryService.delete(idBookCategory);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("bookCategory.delete.success"));
        return "redirect:/bookCategories";
    }

}
