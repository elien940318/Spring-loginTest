package boomrabbit.logintest.service;

import boomrabbit.logintest.mvc.domain.Member;
import boomrabbit.logintest.mvc.domain.MemberStatus;
import boomrabbit.logintest.mvc.repository.MemberRepository;
import boomrabbit.logintest.mvc.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    public MemberService memberService;
    @Autowired
    public MemberRepository memberRepository;

    @Test
    void joinTest(){
        //Given
        Member member = createMember("gch03915","1234","010-1234-1111");
        //when
        Long id = memberService.join(member);
        //then
        assertEquals(member,memberRepository.findMember(id));
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