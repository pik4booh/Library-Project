package io.bootify.library.controller;

import io.bootify.library.model.AuthorDTO;
import io.bootify.library.service.AuthorService;
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
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(final AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("authors", authorService.findAll());
        return "author/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("author") final AuthorDTO authorDTO) {
        return "author/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("author") @Valid final AuthorDTO authorDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "author/add";
        }
        authorService.create(authorDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("author.create.success"));
        return "redirect:/authors";
    }

    @GetMapping("/edit/{idAuthor}")
    public String edit(@PathVariable(name = "idAuthor") final Integer idAuthor, final Model model) {
        model.addAttribute("author", authorService.get(idAuthor));
        return "author/edit";
    }

    @PostMapping("/edit/{idAuthor}")
    public String edit(@PathVariable(name = "idAuthor") final Integer idAuthor,
            @ModelAttribute("author") @Valid final AuthorDTO authorDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "author/edit";
        }
        authorService.update(idAuthor, authorDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("author.update.success"));
        return "redirect:/authors";
    }

    @PostMapping("/delete/{idAuthor}")
    public String delete(@PathVariable(name = "idAuthor") final Integer idAuthor,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = authorService.getReferencedWarning(idAuthor);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            authorService.delete(idAuthor);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("author.delete.success"));
        }
        return "redirect:/authors";
    }

}
