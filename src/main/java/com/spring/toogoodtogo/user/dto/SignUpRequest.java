package com.spring.toogoodtogo.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식이 아닙니다.")
        String email,

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 6, max = 20, message = "비밀번호는 6~20자여야 합니다.")
        String password,

        @NotBlank(message = "이름은 필수입니다.")
        String name,

        @NotBlank(message = "전화번호는 필수입니다.")
        @Pattern(regexp = "^(010|011|016|017|018|019)[0-9]{7,8}$", message = "전화번호 형식이 올바르지 않습니다.")
        String phone,

        @NotBlank(message = "회원 유형은 필수입니다.")
        @Pattern(regexp = "CUSTOMER|STORE_OWNER", message = "회원 유형이 올바르지 않습니다.")
        String role
) {
}
