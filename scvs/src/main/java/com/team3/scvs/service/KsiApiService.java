package com.team3.scvs.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team3.scvs.util.DateUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.entity.EntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class KsiApiService {
    @Value("${ksi.base.url}")
    private String BASE_URL;

    @Value("${ksi.app.key}")
    private String APP_KEY;

    @Value("${ksi.app.secret}")
    private String APP_SECRET;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String accessToken;

    @PostConstruct
    public void getAccessToken() throws Exception {
        String url = BASE_URL + "/oauth2/tokenP";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(url);
            request.setEntity(EntityBuilder.create()
                    .setContentType(ContentType.APPLICATION_JSON)
                    .setText("{\"grant_type\": \"client_credentials\", \"appkey\": \"" + APP_KEY + "\", \"appsecret\": \"" + APP_SECRET + "\"}")
                    .build());

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                JsonNode responseJson = objectMapper.readTree(response.getEntity().getContent());
                accessToken = responseJson.get("access_token").asText();
                System.out.println("KIS API access token : " + accessToken);
            }
        }
    }

    @PreDestroy
    public void revokeAccessToken() throws Exception {
        String url = BASE_URL + "/oauth2/revokeP";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(url);
            request.setEntity(EntityBuilder.create()
                    .setContentType(ContentType.APPLICATION_JSON)
                    .setText("{\"appkey\": \"" + APP_KEY + "\", \"appsecret\": \"" + APP_SECRET + "\", \"token\": \"" + accessToken +"\"}")
                    .build());

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                JsonNode responseJson = objectMapper.readTree(response.getEntity().getContent());
                if(responseJson.get("code").asText().equals("200")){
                    System.out.println("KIS API access token destroyed.");
                }
            }
        }
    }

    public JsonNode getKoreaIndex(String fid) throws Exception {
        String url = BASE_URL + "/uapi/domestic-stock/v1/quotations/inquire-index-price";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url + "?FID_COND_MRKT_DIV_CODE=U&FID_INPUT_ISCD="+fid);
            request.addHeader("content-type", "application/json");
            request.addHeader("authorization", "Bearer " + accessToken);
            request.addHeader("appkey", APP_KEY);
            request.addHeader("appsecret", APP_SECRET);
            request.addHeader("tr_id", "FHPUP02100000");
            request.addHeader("custtype", "P");

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                return objectMapper.readTree(response.getEntity().getContent());
            }
        }
    }

    public JsonNode getUsaIndex(String marketCode, String iscd) throws Exception {
        String url = BASE_URL + "/uapi/overseas-price/v1/quotations/inquire-daily-chartprice";
        String todayDate = DateUtil.getTodayDate();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url + "?FID_COND_MRKT_DIV_CODE=" + marketCode +
                    "&FID_INPUT_ISCD="+ iscd + "&FID_INPUT_DATE_1=" + todayDate +
                    "&FID_INPUT_DATE_2=" + todayDate + "&FID_PERIOD_DIV_CODE=D");
            request.addHeader("content-type", "application/json");
            request.addHeader("authorization", "Bearer " + accessToken);
            request.addHeader("appkey", APP_KEY);
            request.addHeader("appsecret", APP_SECRET);
            request.addHeader("tr_id", "FHKST03030100");

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                return objectMapper.readTree(response.getEntity().getContent());
            }
        }
    }
}


