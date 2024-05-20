package io.bootify.library.controller;

import io.bootify.library.model.ThemeDTO;
import io.bootify.library.service.ThemeService;
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
@RequestMapping("/themes")
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(final ThemeService themeService) {
        this.themeService = themeService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("themes", themeService.findAll());
        return "theme/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("theme") final ThemeDTO themeDTO) {
        return "theme/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("theme") @Valid final ThemeDTO themeDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "theme/add";
        }
        themeService.create(themeDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("theme.create.success"));
        return "redirect:/themes";
    }

    @GetMapping("/edit/{idTheme}")
    public String edit(@PathVariable(name = "idTheme") final Integer idTheme, final Model model) {
        model.addAttribute("theme", themeService.get(idTheme));
        return "theme/edit";
    }

    @PostMapping("/edit/{idTheme}")
    public String edit(@PathVariable(name = "idTheme") final Integer idTheme,
            @ModelAttribute("theme") @Valid final ThemeDTO themeDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "theme/edit";
        }
        themeService.update(idTheme, themeDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("theme.update.success"));
        return "redirect:/themes";
    }

    @PostMapping("/delete/{idTheme}")
    public String delete(@PathVariable(name = "idTheme") final Integer idTheme,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = themeService.getReferencedWarning(idTheme);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            themeService.delete(idTheme);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("theme.delete.success"));
        }
        return "redirect:/themes";
    }

}
