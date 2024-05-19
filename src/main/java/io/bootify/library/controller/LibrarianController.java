package io.bootify.library.controller;

import io.bootify.library.domain.Role;
import io.bootify.library.model.LibrarianDTO;
import io.bootify.library.repos.RoleRepository;
import io.bootify.library.service.LibrarianService;
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
@RequestMapping("/librarians")
public class LibrarianController {

    private final LibrarianService librarianService;
    private final RoleRepository roleRepository;

    public LibrarianController(final LibrarianService librarianService,
            final RoleRepository roleRepository) {
        this.librarianService = librarianService;
        this.roleRepository = roleRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("roleValues", roleRepository.findAll(Sort.by("idRole"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Role::getIdRole, Role::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("librarians", librarianService.findAll());
        return "librarian/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("librarian") final LibrarianDTO librarianDTO) {
        return "librarian/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("librarian") @Valid final LibrarianDTO librarianDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "librarian/add";
        }
        librarianService.create(librarianDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("librarian.create.success"));
        return "redirect:/librarians";
    }

    @GetMapping("/edit/{idLibrarian}")
    public String edit(@PathVariable(name = "idLibrarian") final Integer idLibrarian,
            final Model model) {
        model.addAttribute("librarian", librarianService.get(idLibrarian));
        return "librarian/edit";
    }

    @PostMapping("/edit/{idLibrarian}")
    public String edit(@PathVariable(name = "idLibrarian") final Integer idLibrarian,
            @ModelAttribute("librarian") @Valid final LibrarianDTO librarianDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "librarian/edit";
        }
        librarianService.update(idLibrarian, librarianDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("librarian.update.success"));
        return "redirect:/librarians";
    }

    @PostMapping("/delete/{idLibrarian}")
    public String delete(@PathVariable(name = "idLibrarian") final Integer idLibrarian,
            final RedirectAttributes redirectAttributes) {
        librarianService.delete(idLibrarian);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("librarian.delete.success"));
        return "redirect:/librarians";
    }

}
