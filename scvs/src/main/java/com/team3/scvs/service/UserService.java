package com.team3.scvs.service;

import com.team3.scvs.dto.UserDTO;
import com.team3.scvs.entity.UserEntity;
import com.team3.scvs.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 앞단어 40개
    private final String[] FIRST_PARTS = {
            "맑은", "따듯한", "차가운", "상냥한", "활발한", "조용한", "친절한", "빛나는", "행복한", "부드러운", "똑똑한", "멋진",
            "따스한", "강렬한", "산뜻한", "순수한", "사랑스러운", "우아한", "용감한", "재밌는", "평화로운", "아늑한", "열정적인", "상쾌한",
            "청량한", "활기찬", "고요한", "기분좋은", "은은한", "매혹적인", "온화한", "자유로운", "포근한", "잔잔한", "아름다운", "순탄한",
            "선명한", "신비로운", "따사로운", "감미로운"
    };
    // 뒷단어 40개
    private final String[] SECOND_PARTS = {
            "나무", "돌고래", "기린", "구름", "바다", "산", "별", "호수", "강", "태양", "달", "숲",
            "하늘", "물고기", "꽃", "폭포", "구름", "폭풍", "안개", "별빛", "새", "바람", "자연", "모래",
            "석양", "조약돌", "대지", "별자리", "불꽃", "운석", "늑대", "해변", "성운", "하늘길", "비", "빛",
            "마을", "폭풍우", "비바람", "정원", "우주", "산호", "석양", "안개꽃"
    };

    //닉네임 랜덤생성 앞단어 + 뒷단어 + 난수(1~1000)으로 생성 ex)맑은나무444
    public String generateRandomNickname() {
        Random random = new Random();
        String randomNickname; // 랜덤 닉네임 변수
        int maxAttempts = 100; // 최대 시도 횟수
        int attempts = 0; // 시도 횟수 0으로 초기화

        do {
            String firstPart = FIRST_PARTS[random.nextInt(FIRST_PARTS.length)];
            String secondPart = SECOND_PARTS[random.nextInt(SECOND_PARTS.length)];
            int randomNumber = random.nextInt(1000) + 1; // 1~1000 난수
            attempts++; // 시도 1회 증가

            if (attempts >= maxAttempts) { // 시도가 100회이상이 되면
                throw new RuntimeException("닉네임 생성 실패: 중복을 피할 수 없습니다."); // 에러발생
            }
            randomNickname = firstPart + secondPart + randomNumber;
        } while (isNicknameDuplicate(randomNickname)); // DB에서 중복된 닉네임이 있으면 true 반복


        return randomNickname;
    }


    // email이 db에 있는지 중복체크
    public boolean isEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    // nickname이 db에 있는지 중복체크
    public boolean isNicknameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    // UserDTO에 담긴 내용을 UserEntity에 저장해서 DB에 저장
    public void registerUser(UserDTO userDTO) {

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword())); // 비밀번호 암호화
        userEntity.setNickname(userDTO.getNickname());
        log.info(userEntity.getEmail());
        userRepository.save(userEntity);

    }
    public UserDTO getUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        // UserEntity를 UserDTO로 변환
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setNickname(userEntity.getNickname());
        // 비밀번호는 보안 상 포함하지 않음
        return userDTO;
    }


}
