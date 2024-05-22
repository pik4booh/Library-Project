package io.bootify.library.controller;

import io.bootify.library.domain.CopyBook;
import io.bootify.library.domain.Member;
import io.bootify.library.domain.TypeLoaning;
import io.bootify.library.model.LoaningDTO;
import io.bootify.library.repos.CopyBookRepository;
import io.bootify.library.repos.MemberRepository;
import io.bootify.library.repos.TypeLoaningRepository;
import io.bootify.library.service.LoaningService;
import io.bootify.library.service.SanctionService;
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
@RequestMapping("/loanings")
public class LoaningController {

    private final LoaningService loaningService;
    private final SanctionService sanctionService;
    private final MemberRepository memberRepository;
    private final CopyBookRepository copyBookRepository;
    private final TypeLoaningRepository typeLoaningRepository;

    public LoaningController(final LoaningService loaningService,
            final SanctionService sanctionService,
            final MemberRepository memberRepository, final CopyBookRepository copyBookRepository,
            final TypeLoaningRepository typeLoaningRepository) {
        this.loaningService = loaningService;
        this.sanctionService = sanctionService;
        this.memberRepository = memberRepository;
        this.copyBookRepository = copyBookRepository;
        this.typeLoaningRepository = typeLoaningRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("memberValues", memberRepository.findAll(Sort.by("idMember"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Member::getIdMember, Member::getName)));
        model.addAttribute("copyBookValues", copyBookRepository.findAll(Sort.by("idCopyBook"))
                .stream()
                .collect(CustomCollectors.toSortedMap(CopyBook::getIdCopyBook, CopyBook::getIsbn)));
        model.addAttribute("typeLoaningValues", typeLoaningRepository.findAll(Sort.by("idTypeLoaning"))
                .stream()
                .collect(CustomCollectors.toSortedMap(TypeLoaning::getIdTypeLoaning, TypeLoaning::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("loanings", loaningService.findAll());
        return "loaning/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("loaning") final LoaningDTO loaningDTO) {
        return "loaning/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("loaning") @Valid final LoaningDTO loaningDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "loaning/add";
        }
        loaningService.create(loaningDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("loaning.create.success"));
        return "redirect:/loanings";
    }

    @GetMapping("/edit/{idLoaning}")
    public String edit(@PathVariable(name = "idLoaning") final Integer idLoaning,
            final Model model) {
        model.addAttribute("loaning", loaningService.get(idLoaning));
        return "loaning/edit";
    }

    @PostMapping("/edit/{idLoaning}")
    public String edit(@PathVariable(name = "idLoaning") final Integer idLoaning,
            @ModelAttribute("loaning") @Valid final LoaningDTO loaningDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "loaning/edit";
        }
        loaningService.update(idLoaning, loaningDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("loaning.update.success"));
        return "redirect:/loanings";
    }

    @PostMapping("/delete/{idLoaning}")
    public String delete(@PathVariable(name = "idLoaning") final Integer idLoaning,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = loaningService.getReferencedWarning(idLoaning);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            loaningService.delete(idLoaning);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("loaning.delete.success"));
        }
        return "redirect:/loanings";
    }

    @GetMapping("/create-form/{idCopyBook}")
    public String create(@ModelAttribute("loaning") final LoaningDTO loaningDTO, @PathVariable("idCopyBook") int idCopyBook, Model model) {
        CopyBook copyBook = copyBookRepository.findById(idCopyBook);
        Book book = copyBook.getBook();
        TypeLoaning[] typeLoanings = typeLoaningRepository.findAll().toArray(TypeLoaning[]::new);
        model.addAttribute("book", book);
        model.addAttribute("copyBook", copyBook);
        model.addAttribute("loaningTypes", typeLoanings);

        return "loaning/create";
    }

    @PostMapping("/create")
    public String createLoan(@ModelAttribute LoaningDTO loaningDTO, RedirectAttributes redirectAttributes)
    {

        //print all loaningDTO attributes
        System.out.println("Member : " + loaningDTO.getMember());
        System.out.println("Loan Type : " + loaningDTO.getTypeLoaning());
        System.out.println("Copy Book : " + loaningDTO.getCopyBook());
        try {
            //check if user in the request has active sanction
            sanctionService.checkMemberSanction(loaningDTO.getMember());
            int id = loaningService.createLoaning(loaningDTO);
            return "redirect:/loanings";

        } catch (Exception e) {
            //Set error message in a model
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/loanings/create-form/" + loaningDTO.getCopyBook();
        }


    }

}
