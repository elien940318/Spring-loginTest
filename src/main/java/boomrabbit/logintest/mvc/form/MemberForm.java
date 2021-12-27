package boomrabbit.logintest.mvc.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "회원 아이디는 필수입니다.")
    private String memberId;

    private String password;
    private String phoneNumber;
}
