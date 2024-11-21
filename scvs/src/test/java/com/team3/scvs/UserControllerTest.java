package com.team3.scvs;

import com.team3.scvs.controller.UserController;
import com.team3.scvs.entity.User;
import com.team3.scvs.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testGetUserDetailsWithValidId() throws Exception {
        // Mock 데이터 설정
        User mockUser = new User(1L, "abs@abs.com", "qwer1234", "nick");
        Mockito.when(userService.getUserById(1L)).thenReturn(Optional.of(mockUser));

        // /user/details 요청 실행 및 검증
        mockMvc.perform(get("/user/details?id=1")
                        .requestAttr("isLoggedIn", true)) // 로그인 상태 추가
                .andExpect(status().isOk()) // HTTP 상태 코드 200 확인
                .andExpect(view().name("user/user-details")) // 반환된 뷰 이름 확인
                .andExpect(model().attributeExists("user")) // 모델에 user 속성 존재 확인
                .andExpect(model().attribute("user", mockUser)); // 모델의 user 데이터 검증
    }

    @Test
    void testGetUserDetailsWithInvalidId() throws Exception {
        // Mock 데이터 설정: 없는 ID로 요청
        Mockito.when(userService.getUserById(999L)).thenReturn(Optional.empty());

        // /user/details 요청 실행 및 검증
        mockMvc.perform(get("/user/details?id=999")
                        .requestAttr("isLoggedIn", true)) // 로그인 상태 추가
                .andExpect(status().isOk()) // HTTP 상태 코드 200 확인
                .andExpect(view().name("user/user-details")) // 반환된 뷰 이름 확인
                .andExpect(model().attributeExists("error")) // 모델에 error 속성 존재 확인
                .andExpect(model().attribute("error", "사용자를 찾을 수 없습니다.")); // 에러 메시지 내용 검증
    }
}


