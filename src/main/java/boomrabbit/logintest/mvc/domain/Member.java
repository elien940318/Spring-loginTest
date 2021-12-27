package boomrabbit.logintest.mvc.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name="ID")
    private Long id;

    /**
     * 아이디
     */
    private String memberId;

    /**
     * 비밀번호
     */
    private String password;

    /**
     * Member 권한 (회원가입시 처음은 무조건 LEVEL1)
     */
    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;

    /**
     * 전화번호(휴대폰 번호)
     */
    private String phoneNumber;
}
