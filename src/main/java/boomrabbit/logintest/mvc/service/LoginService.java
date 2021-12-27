package boomrabbit.logintest.mvc.service;

import boomrabbit.logintest.mvc.domain.Member;
import boomrabbit.logintest.mvc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    /**
     * login
     * 입력한 id 와 password 를 비교
     */
    public Member login(String memberid, String password){
        return memberRepository.findLoginId(memberid)
                .filter(m->m.getPassword().equals(password))
                .orElse(null);
    }
}
