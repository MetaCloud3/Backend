package com.meta.cloud.oauth2.service;

import com.meta.cloud.domain.entity.User;
import com.meta.cloud.repository.UserRepository;
import com.meta.cloud.oauth2.user.OAuth2UserInfo;
import com.meta.cloud.oauth2.user.OAuth2UserInfoFactory;
import com.meta.cloud.oauth2.service.OAuth2UserPrincipal;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                oAuth2UserRequest.getClientRegistration().getRegistrationId(),
                oAuth2User.getAttributes()
        );

        if (oAuth2UserInfo.getEmail().isEmpty()) {
            throw new OAuth2AuthenticationException("Email not found from OAuth2 provider");
        }

        Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return OAuth2UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        User user = User.builder()
                .loginId(oAuth2UserInfo.getId())
                .loginPw("default_password") // OAuth2 로그인에서는 비밀번호를 사용하지 않으므로 기본값 설정
                .name(oAuth2UserInfo.getName())
                .email(oAuth2UserInfo.getEmail())
                .totalStorage(100) // 예시: 기본 총 용량 설정
                .usedStorage(0) // 예시: 초기 사용 용량 설정
                .createdAt(java.time.Instant.now().toString())
                .updatedAt(java.time.Instant.now().toString())
                .build();

        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser = User.builder()
                .loginId(existingUser.getLoginId())
                .loginPw(existingUser.getLoginPw())
                .name(oAuth2UserInfo.getName())
                .email(existingUser.getEmail())
                .totalStorage(existingUser.getTotalStorage())
                .usedStorage(existingUser.getUsedStorage())
                .createdAt(existingUser.getCreatedAt())
                .updatedAt(java.time.Instant.now().toString())
                .build();

        return userRepository.save(existingUser);
    }
}
