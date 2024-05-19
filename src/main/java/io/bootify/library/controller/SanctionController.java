package io.bootify.library.controller;

import io.bootify.library.domain.CopyBook;
import io.bootify.library.domain.Member;
import io.bootify.library.model.SanctionDTO;
import io.bootify.library.repos.CopyBookRepository;
import io.bootify.library.repos.MemberRepository;
import io.bootify.library.service.SanctionService;
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
@RequestMapping("/sanctions")
public class SanctionController {

    private final SanctionService sanctionService;
    private final MemberRepository memberRepository;
    private final CopyBookRepository copyBookRepository;

    public SanctionController(final SanctionService sanctionService,
            final MemberRepository memberRepository, final CopyBookRepository copyBookRepository) {
        this.sanctionService = sanctionService;
        this.memberRepository = memberRepository;
        this.copyBookRepository = copyBookRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("memberValues", memberRepository.findAll(Sort.by("idMember"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Member::getIdMember, Member::getName)));
        model.addAttribute("copyBookValues", copyBookRepository.findAll(Sort.by("idCopyBook"))
                .stream()
                .collect(CustomCollectors.toSortedMap(CopyBook::getIdCopyBook, CopyBook::getIsbn)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("sanctions", sanctionService.findAll());
        return "sanction/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("sanction") final SanctionDTO sanctionDTO) {
        return "sanction/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("sanction") @Valid final SanctionDTO sanctionDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "sanction/add";
        }
        sanctionService.create(sanctionDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("sanction.create.success"));
        return "redirect:/sanctions";
    }

    @GetMapping("/edit/{idSanction}")
    public String edit(@PathVariable(name = "idSanction") final Integer idSanction,
            final Model model) {
        model.addAttribute("sanction", sanctionService.get(idSanction));
        return "sanction/edit";
    }

    @PostMapping("/edit/{idSanction}")
    public String edit(@PathVariable(name = "idSanction") final Integer idSanction,
            @ModelAttribute("sanction") @Valid final SanctionDTO sanctionDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "sanction/edit";
        }
        sanctionService.update(idSanction, sanctionDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("sanction.update.success"));
        return "redirect:/sanctions";
    }

    @PostMapping("/delete/{idSanction}")
    public String delete(@PathVariable(name = "idSanction") final Integer idSanction,
            final RedirectAttributes redirectAttributes) {
        sanctionService.delete(idSanction);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("sanction.delete.success"));
        return "redirect:/sanctions";
    }

}
