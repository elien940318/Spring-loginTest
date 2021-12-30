package boomrabbit.logintest.interceptor;

import boomrabbit.logintest.mvc.domain.Member;
import boomrabbit.logintest.mvc.domain.MemberStatus;
import boomrabbit.logintest.session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

import static boomrabbit.logintest.interceptor.Auth.Role.*;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        String requestURI = request.getRequestURI();
//        String uuid = UUID.randomUUID().toString();
//
//        request.setAttribute(LOG_ID,uuid);

        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod hm = (HandlerMethod) handler;

        Auth auth = hm.getMethodAnnotation(Auth.class);
        //1. @Auth 가 없는 경우는 인증이 별도로 필요없음
        if(auth==null){
            return true;
        }
        //2. @Auth 가 있는 경우에는 세션이 있는지 확인
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession();
        if(session==null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null){
            response.sendRedirect("/login?redirectURL=" + requestURI);
            return false;
        }
        //3. 각 권한 분기처리(팀장,임원,팀원)
        Member member = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
        if(auth.role().equals(LEADER)){
            log.info("@Auth : LEADER");
            MemberStatus status = member.getMemberStatus();
            if(!LEADER.equals(status)){
                log.info("Your don't access here");
            }
        }
        else if(auth.role().equals(MANAGER)){
            log.info("@Auth : MANAGER");
        }
        else if(auth.role().equals(TEAM_MEMBER)){
            log.info("@Auth : TEAM_MEMBER");
        }
        else if(auth.role().equals(USER)){
            log.info("@Auth : USER");
        }
        //log.info("REQUEST [{}][{}][{}]",uuid,requestURI,handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandler[{}]",modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        Object logId = (String)request.getAttribute(LOG_ID);
        log.info("RESPONSE [{}][{}][{}]",logId,requestURI,handler);
        if(ex!=null){
            log.error("afterCompletion error!",ex);
        }
    }
}
