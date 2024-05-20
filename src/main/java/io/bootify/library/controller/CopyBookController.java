package io.bootify.library.controller;

import io.bootify.library.domain.Book;
import io.bootify.library.model.CopyBookDTO;
import io.bootify.library.repos.BookRepository;
import io.bootify.library.service.CopyBookService;
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
@RequestMapping("/copyBooks")
public class CopyBookController {

    private final CopyBookService copyBookService;
    private final BookRepository bookRepository;

    public CopyBookController(final CopyBookService copyBookService,
            final BookRepository bookRepository) {
        this.copyBookService = copyBookService;
        this.bookRepository = bookRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("bookValues", bookRepository.findAll(Sort.by("idBook"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Book::getIdBook, Book::getTitle)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("copyBooks", copyBookService.findAll());
        return "copyBook/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("copyBook") final CopyBookDTO copyBookDTO) {
        return "copyBook/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("copyBook") @Valid final CopyBookDTO copyBookDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "copyBook/add";
        }
        copyBookService.create(copyBookDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("copyBook.create.success"));
        return "redirect:/copyBooks";
    }

    @GetMapping("/edit/{idCopyBook}")
    public String edit(@PathVariable(name = "idCopyBook") final Integer idCopyBook,
            final Model model) {
        model.addAttribute("copyBook", copyBookService.get(idCopyBook));
        return "copyBook/edit";
    }

    @PostMapping("/edit/{idCopyBook}")
    public String edit(@PathVariable(name = "idCopyBook") final Integer idCopyBook,
            @ModelAttribute("copyBook") @Valid final CopyBookDTO copyBookDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "copyBook/edit";
        }
        copyBookService.update(idCopyBook, copyBookDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("copyBook.update.success"));
        return "redirect:/copyBooks";
    }

    @PostMapping("/delete/{idCopyBook}")
    public String delete(@PathVariable(name = "idCopyBook") final Integer idCopyBook,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = copyBookService.getReferencedWarning(idCopyBook);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            copyBookService.delete(idCopyBook);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("copyBook.delete.success"));
        }
        return "redirect:/copyBooks";
    }

}
