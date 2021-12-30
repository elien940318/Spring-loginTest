package boomrabbit.logintest.mvc.controller;

import boomrabbit.logintest.interceptor.Auth;
import boomrabbit.logintest.mvc.domain.MemberStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static boomrabbit.logintest.interceptor.Auth.Role.*;

@Controller
@Slf4j
public class LevelController {

    @Auth(role = USER)
    @GetMapping("/level/level1")
    public String level1(){
        log.info("level 1");
        return "level/level1";
    }
    @Auth(role=TEAM_MEMBER)
    @GetMapping("/level/level2")
    public String level2(){
        log.info("level 2");
        return "level/level2";
    }
    @Auth(role=MANAGER)
    @GetMapping("/level/level3")
    public String level3(){
        log.info("level 3");
        return "level/level3";
    }
    @Auth(role=LEADER)
    @GetMapping("/level/level4")
    public String level4(){
        log.info("level 4");
        return "level/level4";
    }
}
