package com.team3.scvs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team3.scvs.config.TestSecurityConfig;
import com.team3.scvs.controller.WatchlistController;
import com.team3.scvs.entity.UserWatchlistEntity;
import com.team3.scvs.service.WatchlistService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WatchlistController.class)
@Import(TestSecurityConfig.class) // 테스트용 SecurityConfig 적용
class WatchlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WatchlistService watchlistService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetWatchlistByUser() throws Exception {
        // 가짜 관심 목록 데이터
        UserWatchlistEntity mockWatchlist = new UserWatchlistEntity(1L, 1L);
        Mockito.when(watchlistService.getUserWatchlists(1L)).thenReturn(List.of(mockWatchlist));

        // GET 요청 테스트
        mockMvc.perform(get("/watchlist/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userWatchlistId").value(1))
                .andExpect(jsonPath("$[0].userId").value(1));
    }

    @Test
    void testAddStockToWatchlist() throws Exception {
        // Mock 서비스 동작 설정
        Mockito.doNothing().when(watchlistService).addStockToWatchlist(eq(1L), eq(1001L));

        // POST 요청 테스트
        mockMvc.perform(post("/watchlist/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(1001L)))
                .andExpect(status().isOk())
                .andExpect(content().string("Stock added to watchlist successfully"));
    }

    @Test
    void testDeleteFromWatchlist() throws Exception {
        // Mock 서비스 동작 설정
        Mockito.doNothing().when(watchlistService).deleteWatchlist(eq(1L));

        // DELETE 요청 테스트
        mockMvc.perform(delete("/watchlist/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Watchlist entry deleted successfully"));
    }
}
