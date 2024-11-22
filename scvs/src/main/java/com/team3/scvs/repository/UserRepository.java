package com.team3.scvs.repository;

import com.team3.scvs.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);    // 이메일유무(중복확인에 사용) 중복이면 true
    boolean existsByNickname(String nickname); // 닉네임유무(중복확인에 사용) 중복이면 true

    Optional<UserEntity> findByEmail(String email); // UserEntity반환(로그인에 사용)

}
