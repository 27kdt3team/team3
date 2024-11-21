package com.team3.scvs.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity // JPA 엔터티 클래스임을 지정
@Data // 롬복을 사용해 Getter, Setter, ToString, EqualsAndHashCode 생성
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 매개변수로 받는 생성자 자동 생성
@Table(name = "users") // 데이터베이스 테이블 이름 지정
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성 전략
    @Column(name = "user_id") // 데이터베이스의 user_id 열과 매핑
    private Long userId;

    @Column(name = "email", nullable = false, unique = true) // email 열과 매핑, 고유값 설정
    private String email;

    @Column(name = "password", nullable = false) // password 열과 매핑
    private String password;

    @Column(name = "nickname", nullable = false, unique = true) // nickname 열과 매핑, 고유값 설정
    private String nickname;
}
