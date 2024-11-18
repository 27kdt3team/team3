package com.team3.scvs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class DBConnTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void testSelectTable(){
        String sql = "SELECT * FROM test";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);

        assertThat(maps).isNotNull();
    }
}
