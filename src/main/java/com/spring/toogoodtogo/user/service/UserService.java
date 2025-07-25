package com.spring.toogoodtogo.user.service;

import com.spring.toogoodtogo.user.domain.User;
import com.spring.toogoodtogo.user.domain.enums.UserRole;
import com.spring.toogoodtogo.user.dto.LoginRequest;
import com.spring.toogoodtogo.user.dto.SignUpRequest;
import com.spring.toogoodtogo.user.dto.SignUpResponse;
import com.spring.toogoodtogo.user.dto.UserInfoResponse;
import com.spring.toogoodtogo.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public SignUpResponse singUp (SignUpRequest request){
        //이메일 중복 체크
        if(userRepository.existsByEmail(request.email())){
            throw new IllegalArgumentException("Email already exists");
        }
        //String → Enum 변환 (Validation에서 이미 체크했으므로 바로 사용해도 안전)
        UserRole role = UserRole.valueOf(request.role());

        //Password 암호화 필수.
        String encodePwd = passwordEncoder.encode(request.password());

        //User 엔티티 생성
        User user = new User(
                request.email(),
                encodePwd,
                request.name(),
                request.phone(),
                role
        );
        userRepository.save(user);
        return new SignUpResponse(user.getId(), user.getEmail(),user.getName(), user.getRole().name());
  /*      User user = User.builder()
                .email(request.email())
                .password(request.password())
                .name(request.name())
                .phone(request.phone())
                .role(role)
                .build();*/
    }

    public UserInfoResponse getUserInfo(Long userId){
       User user =  userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("User not found"));
       return new UserInfoResponse(
               user.getId(),
               user.getEmail(),
               user.getName(),
               user.getPhone(),
               user.getRole().name()
       );
    }
}
