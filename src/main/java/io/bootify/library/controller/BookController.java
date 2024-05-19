package io.bootify.library.controller;

import io.bootify.library.domain.Author;
import io.bootify.library.model.BookDTO;
import io.bootify.library.repos.AuthorRepository;
import io.bootify.library.service.BookService;
import io.bootify.library.util.CustomCollectors;
import io.bootify.library.util.ReferencedWarning;
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
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorRepository authorRepository;

    public BookController(final BookService bookService, final AuthorRepository authorRepository) {
        this.bookService = bookService;
        this.authorRepository = authorRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("authorValues", authorRepository.findAll(Sort.by("idAuthor"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Author::getIdAuthor, Author::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("books", bookService.findAll());
        return "book/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("book") final BookDTO bookDTO) {
        return "book/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("book") @Valid final BookDTO bookDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "book/add";
        }
        bookService.create(bookDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("book.create.success"));
        return "redirect:/books";
    }

    @GetMapping("/edit/{idBook}")
    public String edit(@PathVariable(name = "idBook") final Integer idBook, final Model model) {
        model.addAttribute("book", bookService.get(idBook));
        return "book/edit";
    }

    @PostMapping("/edit/{idBook}")
    public String edit(@PathVariable(name = "idBook") final Integer idBook,
            @ModelAttribute("book") @Valid final BookDTO bookDTO, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "book/edit";
        }
        bookService.update(idBook, bookDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("book.update.success"));
        return "redirect:/books";
    }

    @PostMapping("/delete/{idBook}")
    public String delete(@PathVariable(name = "idBook") final Integer idBook,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = bookService.getReferencedWarning(idBook);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            bookService.delete(idBook);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("book.delete.success"));
        }
        return "redirect:/books";
    }

}
