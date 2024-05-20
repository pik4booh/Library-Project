package io.bootify.library.controller;

import io.bootify.library.model.CategoryDTO;
import io.bootify.library.service.CategoryService;
import io.bootify.library.util.ReferencedWarning;
import io.bootify.library.util.WebUtils;
import jakarta.validation.Valid;
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
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "category/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("category") final CategoryDTO categoryDTO) {
        return "category/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("category") @Valid final CategoryDTO categoryDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "category/add";
        }
        categoryService.create(categoryDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("category.create.success"));
        return "redirect:/categories";
    }

    @GetMapping("/edit/{idCategory}")
    public String edit(@PathVariable(name = "idCategory") final Integer idCategory,
            final Model model) {
        model.addAttribute("category", categoryService.get(idCategory));
        return "category/edit";
    }

    @PostMapping("/edit/{idCategory}")
    public String edit(@PathVariable(name = "idCategory") final Integer idCategory,
            @ModelAttribute("category") @Valid final CategoryDTO categoryDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "category/edit";
        }
        categoryService.update(idCategory, categoryDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("category.update.success"));
        return "redirect:/categories";
    }

    @PostMapping("/delete/{idCategory}")
    public String delete(@PathVariable(name = "idCategory") final Integer idCategory,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = categoryService.getReferencedWarning(idCategory);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            categoryService.delete(idCategory);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("category.delete.success"));
        }
        return "redirect:/categories";
    }

}
