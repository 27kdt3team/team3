package com.team3.scvs.service;

import com.team3.scvs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 이메일로 사용자 정보 가져오기
        return userRepository.findByEmail(username)
                .map(userEntity -> {
                    // 사용자 권한을 GrantedAuthority 타입의 리스트로 변환
                    List<GrantedAuthority> authorities = Collections.singletonList(
                            new SimpleGrantedAuthority(userEntity.getUserrole()) // 권한 설정
                    );

                    // userEntity를 UserDetails 변환하여 객체 반환
                    return User.builder()
                            .username(userEntity.getEmail()) // 이메일(DB)을 username(Security)으로 사용
                            .password(userEntity.getPassword()) // 비밀번호
                            .authorities(authorities) // 권한
                            .build();
                })
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
    }

}

