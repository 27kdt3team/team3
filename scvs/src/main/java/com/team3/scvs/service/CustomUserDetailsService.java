package com.team3.scvs.service;

import com.team3.scvs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 사용자 정보 가져오기
        return userRepository.findByEmail(username)
                .map(user -> User.builder()
                        .username(user.getEmail())
                        .password(user.getPassword())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
    }

    // 로그인된 사용자의 userId를 가져오는 메서드
    public Long getLoggedInUserId() {
        // SecurityContextHolder에서 인증된 사용자 이메일 가져오기
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // 이메일로 userId 조회
        return userRepository.findByEmail(username)
                .map(user -> user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("로그인된 사용자의 정보를 찾을 수 없습니다."));
    }

}

