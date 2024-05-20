package io.bootify.library.controller;

import io.bootify.library.domain.Book;
import io.bootify.library.domain.Theme;
import io.bootify.library.model.BookThemeDTO;
import io.bootify.library.repos.BookRepository;
import io.bootify.library.repos.ThemeRepository;
import io.bootify.library.service.BookThemeService;
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
@RequestMapping("/bookThemes")
public class BookThemeController {

    private final BookThemeService bookThemeService;
    private final ThemeRepository themeRepository;
    private final BookRepository bookRepository;

    public BookThemeController(final BookThemeService bookThemeService,
            final ThemeRepository themeRepository, final BookRepository bookRepository) {
        this.bookThemeService = bookThemeService;
        this.themeRepository = themeRepository;
        this.bookRepository = bookRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("themeValues", themeRepository.findAll(Sort.by("idTheme"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Theme::getIdTheme, Theme::getName)));
        model.addAttribute("bookValues", bookRepository.findAll(Sort.by("idBook"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Book::getIdBook, Book::getTitle)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("bookThemes", bookThemeService.findAll());
        return "bookTheme/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("bookTheme") final BookThemeDTO bookThemeDTO) {
        return "bookTheme/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("bookTheme") @Valid final BookThemeDTO bookThemeDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "bookTheme/add";
        }
        bookThemeService.create(bookThemeDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("bookTheme.create.success"));
        return "redirect:/bookThemes";
    }

    @GetMapping("/edit/{idBookTheme}")
    public String edit(@PathVariable(name = "idBookTheme") final Integer idBookTheme,
            final Model model) {
        model.addAttribute("bookTheme", bookThemeService.get(idBookTheme));
        return "bookTheme/edit";
    }

    @PostMapping("/edit/{idBookTheme}")
    public String edit(@PathVariable(name = "idBookTheme") final Integer idBookTheme,
            @ModelAttribute("bookTheme") @Valid final BookThemeDTO bookThemeDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "bookTheme/edit";
        }
        bookThemeService.update(idBookTheme, bookThemeDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("bookTheme.update.success"));
        return "redirect:/bookThemes";
    }

    @PostMapping("/delete/{idBookTheme}")
    public String delete(@PathVariable(name = "idBookTheme") final Integer idBookTheme,
            final RedirectAttributes redirectAttributes) {
        bookThemeService.delete(idBookTheme);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("bookTheme.delete.success"));
        return "redirect:/bookThemes";
    }

}
