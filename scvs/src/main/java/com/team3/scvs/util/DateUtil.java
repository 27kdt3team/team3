package com.team3.scvs.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static String getTodayDate(){
        // 오늘 날짜 가져오기
        LocalDate today = LocalDate.now();

        // 포맷 정의 (YYYYMMDD)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // 포맷 적용
        return today.format(formatter);
    }
}
