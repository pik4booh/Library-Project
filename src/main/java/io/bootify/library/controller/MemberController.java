package io.bootify.library.controller;

import io.bootify.library.domain.CopyBook;
import io.bootify.library.domain.TypeMember;
import io.bootify.library.model.MemberDTO;
import io.bootify.library.repos.CopyBookRepository;
import io.bootify.library.repos.TypeMemberRepository;
import io.bootify.library.service.MemberService;
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
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final TypeMemberRepository typeMemberRepository;

    public MemberController(final MemberService memberService,
            final TypeMemberRepository typeMemberRepository) {
        this.memberService = memberService;
        this.typeMemberRepository = typeMemberRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("typeMemberValues", typeMemberRepository.findAll(Sort.by("idTypeMember"))
                .stream()
                .collect(CustomCollectors.toSortedMap(TypeMember::getIdTypeMember, TypeMember::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("members", memberService.findAll());
        return "member/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("member") final MemberDTO memberDTO) {
        return "member/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("member") @Valid final MemberDTO memberDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "member/add";
        }
        memberService.create(memberDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("member.create.success"));
        return "redirect:/members";
    }

    @GetMapping("/edit/{idMember}")
    public String edit(@PathVariable(name = "idMember") final Integer idMember, final Model model) {
        model.addAttribute("member", memberService.get(idMember));
        return "member/edit";
    }

    @PostMapping("/edit/{idMember}")
    public String edit(@PathVariable(name = "idMember") final Integer idMember,
            @ModelAttribute("member") @Valid final MemberDTO memberDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "member/edit";
        }
        memberService.update(idMember, memberDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("member.update.success"));
        return "redirect:/members";
    }

    @PostMapping("/delete/{idMember}")
    public String delete(@PathVariable(name = "idMember") final Integer idMember,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = memberService.getReferencedWarning(idMember);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            memberService.delete(idMember);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("member.delete.success"));
        }
        return "redirect:/members";
    }

    @GetMapping("/copyBooks/{idMember}")
    public String copyBooks(@PathVariable(name = "idMember") final int idMember, final Model model) {
        model.addAttribute("member", memberService.get(idMember));
        try {
            //Get all Loaned Books not returned by this member
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "member/copyBooks";
    }

}
