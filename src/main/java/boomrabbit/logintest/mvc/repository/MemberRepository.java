package boomrabbit.logintest.mvc.repository;

import boomrabbit.logintest.mvc.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findMember(Long id){
        return em.find(Member.class,id);
    }

    public Optional<Member> findLoginId(String memberId){
        return em.createQuery("select m from Member m where m.memberId = :memberId",Member.class)
                .setParameter("memberId",memberId)
                .getResultList()
                .stream()
                .findFirst();
    }
    //로그인 확인 optional + jpa 조합 고민해보기
}
