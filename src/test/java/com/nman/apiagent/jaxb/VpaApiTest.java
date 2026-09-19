package com.nman.apiagent.jaxb;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class VpaApiTest {

    public static void main(String[] args) {

        RestTemplate restTemplate =
                new RestTemplate();

        String url =
                "http://localhost:8080/api/vpa";

        String json = """
                {
                    "transactionId": "TXN10001",
                    "vpa": "hari@axisbank",
                    "accountId": "ACC001"
                }
                """;

        HttpHeaders headers =
                new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_JSON
        );

        HttpEntity<String> request =
                new HttpEntity<>(
                        json,
                        headers
                );

        String response =
                restTemplate.postForObject(
                        url,
                        request,
                        String.class
                );

        System.out.println(
                "Response: " + response
        );
    }
}