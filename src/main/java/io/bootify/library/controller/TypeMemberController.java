package io.bootify.library.controller;

import io.bootify.library.model.TypeMemberDTO;
import io.bootify.library.service.TypeMemberService;
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
@RequestMapping("/typeMembers")
public class TypeMemberController {

    private final TypeMemberService typeMemberService;

    public TypeMemberController(final TypeMemberService typeMemberService) {
        this.typeMemberService = typeMemberService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("typeMembers", typeMemberService.findAll());
        return "typeMember/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("typeMember") final TypeMemberDTO typeMemberDTO) {
        return "typeMember/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("typeMember") @Valid final TypeMemberDTO typeMemberDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "typeMember/add";
        }
        typeMemberService.create(typeMemberDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("typeMember.create.success"));
        return "redirect:/typeMembers";
    }

    @GetMapping("/edit/{idTypeMember}")
    public String edit(@PathVariable(name = "idTypeMember") final Integer idTypeMember,
            final Model model) {
        model.addAttribute("typeMember", typeMemberService.get(idTypeMember));
        return "typeMember/edit";
    }

    @PostMapping("/edit/{idTypeMember}")
    public String edit(@PathVariable(name = "idTypeMember") final Integer idTypeMember,
            @ModelAttribute("typeMember") @Valid final TypeMemberDTO typeMemberDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "typeMember/edit";
        }
        typeMemberService.update(idTypeMember, typeMemberDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("typeMember.update.success"));
        return "redirect:/typeMembers";
    }

    @PostMapping("/delete/{idTypeMember}")
    public String delete(@PathVariable(name = "idTypeMember") final Integer idTypeMember,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = typeMemberService.getReferencedWarning(idTypeMember);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            typeMemberService.delete(idTypeMember);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("typeMember.delete.success"));
        }
        return "redirect:/typeMembers";
    }

}
