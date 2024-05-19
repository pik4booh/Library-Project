package io.bootify.library.controller;

import io.bootify.library.model.RoleDTO;
import io.bootify.library.service.RoleService;
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
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(final RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "role/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("role") final RoleDTO roleDTO) {
        return "role/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("role") @Valid final RoleDTO roleDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "role/add";
        }
        roleService.create(roleDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("role.create.success"));
        return "redirect:/roles";
    }

    @GetMapping("/edit/{idRole}")
    public String edit(@PathVariable(name = "idRole") final Integer idRole, final Model model) {
        model.addAttribute("role", roleService.get(idRole));
        return "role/edit";
    }

    @PostMapping("/edit/{idRole}")
    public String edit(@PathVariable(name = "idRole") final Integer idRole,
            @ModelAttribute("role") @Valid final RoleDTO roleDTO, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "role/edit";
        }
        roleService.update(idRole, roleDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("role.update.success"));
        return "redirect:/roles";
    }

    @PostMapping("/delete/{idRole}")
    public String delete(@PathVariable(name = "idRole") final Integer idRole,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = roleService.getReferencedWarning(idRole);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            roleService.delete(idRole);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("role.delete.success"));
        }
        return "redirect:/roles";
    }

}
