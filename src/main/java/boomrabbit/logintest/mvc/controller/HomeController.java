package boomrabbit.logintest.mvc.controller;

import boomrabbit.logintest.mvc.domain.Member;
import boomrabbit.logintest.mvc.repository.MemberRepository;
import boomrabbit.logintest.session.SessionConst;
import boomrabbit.logintest.session.SessionManger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManger sessionManger;

    //@RequestMapping("/")
    public String home(){
        log.info("home");
        return "home";
    }

    //@GetMapping("/")
    public String homeLogin(@CookieValue(name="memberId",required = false) Long memberId, Model model){
        if(memberId==null){
            return "home";
        }
        Member loginMember = memberRepository.findMember(memberId);
        if(loginMember==null){
            return "home";
        }
        model.addAttribute("member",loginMember);
        return "loginHome";
    }
    //@GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model){

        //세션 관리자에 저장된 회원 정보 조회
        Member member = (Member)sessionManger.getSession(request);
        //로그인
        if(member==null){
            return "home";
        }
        model.addAttribute("member",member);
        return "loginHome";
    }
    //@GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model){

        HttpSession session = request.getSession(false);
        if(session==null){
            return "home";
        }
        //세션 관리자에 저장된 회원 정보 조회
        Member member = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
        //로그인
        if(member==null){
            return "home";
        }
        model.addAttribute("member",member);
        return "loginHome";
    }
    @GetMapping("/")
    public String homeLoginV3Spring(@SessionAttribute(name=SessionConst.LOGIN_MEMBER,required = false) Member member, Model model){

        //로그인
        if(member==null){
            return "home";
        }
        model.addAttribute("member",member);
        return "loginHome";
    }
}
