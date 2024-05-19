package io.bootify.library.controller;

import io.bootify.library.model.TypeLoaningDTO;
import io.bootify.library.service.TypeLoaningService;
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
@RequestMapping("/typeLoanings")
public class TypeLoaningController {

    private final TypeLoaningService typeLoaningService;

    public TypeLoaningController(final TypeLoaningService typeLoaningService) {
        this.typeLoaningService = typeLoaningService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("typeLoanings", typeLoaningService.findAll());
        return "typeLoaning/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("typeLoaning") final TypeLoaningDTO typeLoaningDTO) {
        return "typeLoaning/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("typeLoaning") @Valid final TypeLoaningDTO typeLoaningDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "typeLoaning/add";
        }
        typeLoaningService.create(typeLoaningDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("typeLoaning.create.success"));
        return "redirect:/typeLoanings";
    }

    @GetMapping("/edit/{idTypeLoaning}")
    public String edit(@PathVariable(name = "idTypeLoaning") final Integer idTypeLoaning,
            final Model model) {
        model.addAttribute("typeLoaning", typeLoaningService.get(idTypeLoaning));
        return "typeLoaning/edit";
    }

    @PostMapping("/edit/{idTypeLoaning}")
    public String edit(@PathVariable(name = "idTypeLoaning") final Integer idTypeLoaning,
            @ModelAttribute("typeLoaning") @Valid final TypeLoaningDTO typeLoaningDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "typeLoaning/edit";
        }
        typeLoaningService.update(idTypeLoaning, typeLoaningDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("typeLoaning.update.success"));
        return "redirect:/typeLoanings";
    }

    @PostMapping("/delete/{idTypeLoaning}")
    public String delete(@PathVariable(name = "idTypeLoaning") final Integer idTypeLoaning,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = typeLoaningService.getReferencedWarning(idTypeLoaning);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            typeLoaningService.delete(idTypeLoaning);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("typeLoaning.delete.success"));
        }
        return "redirect:/typeLoanings";
    }

}
