package io.bootify.library.controller;

import io.bootify.library.model.ReturnLoaningDTO;
import io.bootify.library.service.ReturnLoaningService;
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
@RequestMapping("/returnLoanings")
public class ReturnLoaningController {

    private final ReturnLoaningService returnLoaningService;

    public ReturnLoaningController(final ReturnLoaningService returnLoaningService) {
        this.returnLoaningService = returnLoaningService;
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
    public String edit(@PathVariable(name = "idReturnLoaning") final Long idReturnLoaning,
            final Model model) {
        model.addAttribute("returnLoaning", returnLoaningService.get(idReturnLoaning));
        return "returnLoaning/edit";
    }

    @PostMapping("/edit/{idReturnLoaning}")
    public String edit(@PathVariable(name = "idReturnLoaning") final Long idReturnLoaning,
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
    public String delete(@PathVariable(name = "idReturnLoaning") final Long idReturnLoaning,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = returnLoaningService.getReferencedWarning(idReturnLoaning);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            returnLoaningService.delete(idReturnLoaning);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("returnLoaning.delete.success"));
        }
        return "redirect:/returnLoanings";
    }

}