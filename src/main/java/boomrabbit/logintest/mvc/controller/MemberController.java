package boomrabbit.logintest.mvc.controller;

import boomrabbit.logintest.mvc.domain.MemberStatus;
import boomrabbit.logintest.mvc.form.MemberForm;
import boomrabbit.logintest.mvc.domain.Member;
import boomrabbit.logintest.mvc.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        log.info("MemberController : createForm");
        model.addAttribute("memberForm",new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm memberForm, BindingResult result){
        log.info("MemberController : create");
        if(result.hasErrors()){
            return "members/createMemberForm";
        }
        Member member = new Member();
        member.setMemberId(memberForm.getMemberId());
        member.setPassword(memberForm.getPassword());
        member.setPhoneNumber(memberForm.getPhoneNumber());
        member.setMemberStatus(MemberStatus.USER);  //의존하게 되는 문제점.....
        memberService.join(member);
        return "redirect:/";
    }

}
