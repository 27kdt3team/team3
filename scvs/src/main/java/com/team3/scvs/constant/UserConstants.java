package com.team3.scvs.constant;

public class UserConstants {

    // 앞단어 40개 (닉네임생성)
    public static final String[] FIRST_PARTS = {
            "맑은", "따듯한", "차가운", "상냥한", "활발한", "조용한", "친절한", "빛나는", "행복한", "부드러운", "똑똑한", "멋진",
            "따스한", "강렬한", "산뜻한", "순수한", "사랑스러운", "우아한", "용감한", "재밌는", "평화로운", "아늑한", "열정적인", "상쾌한",
            "청량한", "활기찬", "고요한", "기분좋은", "은은한", "매혹적인", "온화한", "자유로운", "포근한", "잔잔한", "아름다운", "순탄한",
            "선명한", "신비로운", "따사로운", "감미로운"
    };
    // 뒷단어 40개 (닉네임생성)
    public static final String[] SECOND_PARTS = {
            "나무", "돌고래", "기린", "구름", "바다", "산", "별", "호수", "강", "태양", "달", "숲",
            "하늘", "물고기", "꽃", "폭포", "구름", "폭풍", "안개", "별빛", "새", "바람", "자연", "모래",
            "석양", "조약돌", "대지", "별자리", "불꽃", "운석", "늑대", "해변", "성운", "하늘길", "비", "빛",
            "마을", "폭풍우", "비바람", "정원", "우주", "산호", "석양", "안개꽃"
    };

    //
    public static final int MAX_ATTEMPTS = 100; // 최대 시도 횟수

    public static final String USER_NOT_FOUND_ERROR = "사용자를 찾을 수 없습니다: ";

    private UserConstants() {
        // 인스턴스 생성 방지
    }
}