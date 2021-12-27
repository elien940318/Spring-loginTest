package boomrabbit.logintest.service;

import boomrabbit.logintest.mvc.domain.Member;
import boomrabbit.logintest.mvc.domain.MemberStatus;
import boomrabbit.logintest.mvc.service.LoginService;
import boomrabbit.logintest.mvc.service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class LoginServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private LoginService loginService;

    @Test
    void loginCheck(){
        //Given
        Member member = createMember("gch03915","1234","010-1111-2222");
        //when
        memberService.join(member);
        MemberStatus memberStatus = loginService.login(member.getMemberId(), member.getPassword()).getMemberStatus();
        //then
        //System.out.println(memberStatus);
        Assertions.assertEquals(memberStatus,member.getMemberStatus());
    }

    private Member createMember(String memberId, String password,String phoneNumber){
        Member member = new Member();
        member.setMemberId(memberId);
        member.setPassword(password);
        member.setMemberStatus(MemberStatus.USER);
        member.setPhoneNumber(phoneNumber);
        return member;
    }
}