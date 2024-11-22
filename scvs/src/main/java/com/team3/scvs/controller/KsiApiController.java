package com.team3.scvs.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.team3.scvs.service.KsiApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KsiApiController {

    private static String KOSPI_FID = "0001";
    private static String KOSDAQ_FID = "1001";

    @Autowired
    private KsiApiService ksiApiService;

    @GetMapping("/api/kospi")
    public JsonNode getKospiIndex(){
        try{
            return ksiApiService.getKoreaIndex(KOSPI_FID);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/api/kosdaq")
    public JsonNode getKosdaqIndex(){
        try{
            return ksiApiService.getKoreaIndex(KOSDAQ_FID);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
