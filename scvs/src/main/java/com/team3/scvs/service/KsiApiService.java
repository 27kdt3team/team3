package com.team3.scvs.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.entity.EntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KsiApiService {
    @Value("${ksi.base.url}")
    private String BASE_URL;

    @Value("${ksi.app.key}")
    private String APP_KEY;

    @Value("${ksi.app.secret}")
    private String APP_SECRET;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static String accessToken;

    public String getAccessToken() throws Exception {
        String url = BASE_URL + "/oauth2/tokenP";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(url);
            request.setEntity(EntityBuilder.create()
                    .setContentType(ContentType.APPLICATION_JSON)
                    .setText("{\"grant_type\": \"client_credentials\", \"appkey\": \"" + APP_KEY + "\", \"appsecret\": \"" + APP_SECRET + "\"}")
                    .build());

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                JsonNode responseJson = objectMapper.readTree(response.getEntity().getContent());
                return responseJson.get("access_token").asText();
            }
        }
    }

    public String revokeAccessToken() throws Exception {
        String url = BASE_URL + "/oauth2/revokeP";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(url);
            request.setEntity(EntityBuilder.create()
                    .setContentType(ContentType.APPLICATION_JSON)
                    .setText("{\"appkey\": \"" + APP_KEY + "\", \"appsecret\": \"" + APP_SECRET + "\", \"token\": \"" + accessToken +"\"}")
                    .build());

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                JsonNode responseJson = objectMapper.readTree(response.getEntity().getContent());
                return responseJson.get("access_token").asText();
            }
        }
    }

    public JsonNode getKoreaIndex(String fid) throws Exception {
        if(accessToken == null || accessToken.isBlank()){
            accessToken = getAccessToken();
        }
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
}


