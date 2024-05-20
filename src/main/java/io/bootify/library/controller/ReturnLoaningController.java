package io.bootify.library.controller;

import io.bootify.library.domain.Loaning;
import io.bootify.library.model.ReturnLoaningDTO;
import io.bootify.library.repos.LoaningRepository;
import io.bootify.library.service.ReturnLoaningService;
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
@RequestMapping("/returnLoanings")
public class ReturnLoaningController {

    private final ReturnLoaningService returnLoaningService;
    private final LoaningRepository loaningRepository;

    public ReturnLoaningController(final ReturnLoaningService returnLoaningService,
            final LoaningRepository loaningRepository) {
        this.returnLoaningService = returnLoaningService;
        this.loaningRepository = loaningRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("loaningValues", loaningRepository.findAll(Sort.by("idLoaning"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Loaning::getIdLoaning, Loaning::getIdLoaning)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("returnLoanings", returnLoaningService.findAll());
        return "returnLoaning/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("returnLoaning") final ReturnLoaningDTO returnLoaningDTO) {
        return "returnLoaning/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("returnLoaning") @Valid final ReturnLoaningDTO returnLoaningDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "returnLoaning/add";
        }
        returnLoaningService.create(returnLoaningDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("returnLoaning.create.success"));
        return "redirect:/returnLoanings";
    }

    @GetMapping("/edit/{idReturnLoaning}")
    public String edit(@PathVariable(name = "idReturnLoaning") final Integer idReturnLoaning,
            final Model model) {
        model.addAttribute("returnLoaning", returnLoaningService.get(idReturnLoaning));
        return "returnLoaning/edit";
    }

    @PostMapping("/edit/{idReturnLoaning}")
    public String edit(@PathVariable(name = "idReturnLoaning") final Integer idReturnLoaning,
            @ModelAttribute("returnLoaning") @Valid final ReturnLoaningDTO returnLoaningDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "returnLoaning/edit";
        }
        returnLoaningService.update(idReturnLoaning, returnLoaningDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("returnLoaning.update.success"));
        return "redirect:/returnLoanings";
    }

    @PostMapping("/delete/{idReturnLoaning}")
    public String delete(@PathVariable(name = "idReturnLoaning") final Integer idReturnLoaning,
            final RedirectAttributes redirectAttributes) {
        returnLoaningService.delete(idReturnLoaning);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("returnLoaning.delete.success"));
        return "redirect:/returnLoanings";
    }

    @GetMapping("/return/{idLoaning}/{idMember}")
    public String returnLoan(@PathVariable(name = "idLoaning") final int idLoaning, @PathVariable(name = "idMember") final int idMember, RedirectAttributes redirectAttributes)
    {
        try {
            int sanctionStatus = returnLoaningService.returnLoan(idLoaning);
            if (sanctionStatus == 1){
                redirectAttributes.addFlashAttribute("Information", "Sanction has been applied to the member due to late return of the book. And the book has been returned successfully");
            }else{
                redirectAttributes.addFlashAttribute("Information", "Book has been returned successfully");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/members/copyBooks/" + idMember;
        }
        return "redirect:/members/copyBooks/" + idMember;
    }

}
