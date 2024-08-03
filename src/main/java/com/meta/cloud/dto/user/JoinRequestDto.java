package com.meta.cloud.dto.user;

import com.meta.cloud.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JoinRequestDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 5, max = 15, message = "아이디는 5 ~ 15자 이내이어야 합니다")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8 ~ 20자 이내이어야 합니다")
    private String loginPw;

    @NotBlank(message = "이름을 입력해주세요.")
    @Size(max = 50, message = "이름은 최대 50자 입니다")
    private String name;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    public User toEntity(String encodedPw) {
        return User.builder()
                .loginId(loginId)
                .loginPw(encodedPw)
                .name(name)
                .email(email)
                .build();
    }
}
