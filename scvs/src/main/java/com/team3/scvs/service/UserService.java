package com.team3.scvs.service;

import com.team3.scvs.entity.User;
import com.team3.scvs.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * UserService
 * - UserRepository를 활용하여 비즈니스 로직 처리
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    /**
     * UserService 생성자 (의존성 주입)
     * @param userRepository UserRepository 객체
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * ID로 사용자 조회
     *
     * @param id 사용자 ID
     * @return Optional<User>
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * 이메일로 사용자 조회
     *
     * @param email 사용자 이메일
     * @return Optional<User>
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * 닉네임으로 사용자 조회
     *
     * @param nickname 사용자 닉네임
     * @return Optional<User>
     */
    public Optional<User> getUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    /**
     * 이메일 중복 체크
     *
     * @param email 사용자 이메일
     * @return true: 중복 있음, false: 중복 없음
     */
    public boolean isEmailDuplicate(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    /**
     * 닉네임 중복 체크
     *
     * @param nickname 사용자 닉네임
     * @return true: 중복 있음, false: 중복 없음
     */
    public boolean isNicknameDuplicate(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }

    /**
     * 사용자 정보 업데이트
     *
     * @param user 업데이트할 사용자 정보
     * @return 업데이트된 사용자
     */
    public User updateUser(User user) {
        return userRepository.save(user);
    }
}
