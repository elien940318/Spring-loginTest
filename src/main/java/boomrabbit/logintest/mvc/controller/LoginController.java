package boomrabbit.logintest.mvc.controller;

import boomrabbit.logintest.mvc.domain.Member;
import boomrabbit.logintest.mvc.form.LoginForm;
import boomrabbit.logintest.mvc.service.LoginService;
import boomrabbit.logintest.session.SessionConst;
import boomrabbit.logintest.session.SessionManger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final SessionManger sessionManager;

    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("loginForm", new LoginForm());
        return "login/loginForm";
    }
    //@PostMapping("/login")
    public String login(@Valid LoginForm loginForm, BindingResult result, HttpServletResponse response){
        if(result.hasErrors()){
            return "login/loginForm";
        }
        Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
        if(loginMember==null){
            result.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        //로그인처리
        Cookie idCookie = new Cookie("memberId",String.valueOf(loginMember.getId()));
        response.addCookie(idCookie);
        return "redirect:/";
    }
    //@PostMapping("/login")
    public String loginV2(@Valid LoginForm loginForm, BindingResult result, HttpServletResponse response){
        if(result.hasErrors()){
            return "login/loginForm";
        }
        Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
        if(loginMember==null){
            result.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        //로그인처리
        sessionManager.createSession(loginMember,response);
        return "redirect:/";
    }
    @PostMapping("/login")
    public String loginV3(@Valid LoginForm loginForm, BindingResult result, HttpServletRequest request){
        if(result.hasErrors()){
            return "login/loginForm";
        }
        Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
        if(loginMember==null){
            result.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        //로그인처리
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER,loginMember);
        return "redirect:/";
    }

    //@PostMapping("/logout")
    public String logout(HttpServletResponse response){
        expireCookie(response, "memberId");
        return "redirect:/";
    }
    //@PostMapping("/logout")
    public String logoutV2(HttpServletRequest request){
        sessionManager.expire(request);
        return "redirect:/";
    }
    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
