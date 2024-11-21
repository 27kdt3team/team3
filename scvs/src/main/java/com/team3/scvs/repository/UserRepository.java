package com.team3.scvs.repository;

import com.team3.scvs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository
 * - User 엔터티를 관리하는 Repository
 * - JpaRepository를 상속받아 기본 CRUD 메서드 제공
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 이메일을 기준으로 사용자 찾기
     *
     * @param email 사용자 이메일
     * @return Optional<User>
     */
    Optional<User> findByEmail(String email);
    /**
     * 닉네임을 기준으로 사용자 찾기
     *
     * @param nickname 사용자 닉네임
     * @return Optional<User>
     */
    Optional<User> findByNickname(String nickname);
}
