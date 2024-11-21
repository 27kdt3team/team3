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

    private static String NASDAQ_MARKETCODE = "N";
    private static String SNP500_MARKETCODE = "N";
    private static String EXCHANGE_MARKETCODE = "X";

    private static String NASDAQ_ISCD = "COMP";
    private static String SNP500_ISCD = "SPX";
    private static String EXCHANGE_ISCD = "FX@KRW";

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

    @GetMapping("/api/nasdaq")
    public JsonNode getNasdaqIndex(){
        try{
            return ksiApiService.getUsaIndex(NASDAQ_MARKETCODE,NASDAQ_ISCD);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/api/snp500")
    public JsonNode getSnp500Index(){
        try{
            return ksiApiService.getUsaIndex(SNP500_MARKETCODE,SNP500_ISCD);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/api/exchange")
    public JsonNode getExchangeIndex(){
        try{
            return ksiApiService.getUsaIndex(EXCHANGE_MARKETCODE,EXCHANGE_ISCD);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
