package io.bootify.library.controller;

import io.bootify.library.domain.Book;
import io.bootify.library.domain.TypeMember;
import io.bootify.library.model.BookMemberDTO;
import io.bootify.library.repos.BookRepository;
import io.bootify.library.repos.TypeMemberRepository;
import io.bootify.library.service.BookMemberService;
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
@RequestMapping("/bookMembers")
public class BookMemberController {

    private final BookMemberService bookMemberService;
    private final BookRepository bookRepository;
    private final TypeMemberRepository typeMemberRepository;

    public BookMemberController(final BookMemberService bookMemberService,
            final BookRepository bookRepository, final TypeMemberRepository typeMemberRepository) {
        this.bookMemberService = bookMemberService;
        this.bookRepository = bookRepository;
        this.typeMemberRepository = typeMemberRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("bookValues", bookRepository.findAll(Sort.by("idBook"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Book::getIdBook, Book::getTitle)));
        model.addAttribute("typeMemberValues", typeMemberRepository.findAll(Sort.by("idTypeMember"))
                .stream()
                .collect(CustomCollectors.toSortedMap(TypeMember::getIdTypeMember, TypeMember::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("bookMembers", bookMemberService.findAll());
        return "bookMember/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("bookMember") final BookMemberDTO bookMemberDTO) {
        return "bookMember/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("bookMember") @Valid final BookMemberDTO bookMemberDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "bookMember/add";
        }

        try {
            bookMemberService.create(bookMemberDTO);
            return "redirect:/bookMembers";

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());

            return "redirect:/bookMembers/add";

        }
        
    }

    @GetMapping("/edit/{idBookMember}")
    public String edit(@PathVariable(name = "idBookMember") final Integer idBookMember,
            final Model model) {
        model.addAttribute("bookMember", bookMemberService.get(idBookMember));
        return "bookMember/edit";
    }

    @PostMapping("/edit/{idBookMember}")
    public String edit(@PathVariable(name = "idBookMember") final Integer idBookMember,
            @ModelAttribute("bookMember") @Valid final BookMemberDTO bookMemberDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "bookMember/edit";
        }
        bookMemberService.update(idBookMember, bookMemberDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("bookMember.update.success"));
        return "redirect:/bookMembers";
    }

    @PostMapping("/delete/{idBookMember}")
    public String delete(@PathVariable(name = "idBookMember") final Integer idBookMember,
            final RedirectAttributes redirectAttributes) {
        bookMemberService.delete(idBookMember);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("bookMember.delete.success"));
        return "redirect:/bookMembers";
    }

}
