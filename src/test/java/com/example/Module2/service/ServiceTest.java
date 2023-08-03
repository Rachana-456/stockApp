package com.example.Module2.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ServiceTest {

    private static final String AUTH_MICROSERVICE_URL = "http://localhost:8082";
    private static final String YAHOO_FINANCE_API_URL = "https://yahoo-finance127.p.rapidapi.com";
    private static final String X_KEY = "7e59f69744mshaa527e44b79a29fp1c6d42jsna899af6f420e";
    private static final String X_HOST = "yahoo-finance127.p.rapidapi.com";

    @Mock
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private ServiceStock serviceStock;

    @Mock
    private AuthService authService;

    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
        authService = new AuthService(restTemplate); // Instantiate AuthService, or inject it if it's a Spring component
        serviceStock = new ServiceStock(restTemplate, authService);
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void testGetAllDataWithValidToken() throws Exception {
        String stock = "AAPL";
        String jwtToken = "valid_token";

        HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);

        mockServer.expect(requestTo(AUTH_MICROSERVICE_URL + "/api/validateToken"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken))
                .andRespond(withSuccess("true", MediaType.APPLICATION_JSON));

        HttpHeaders yahooFinanceApiHeaders = new HttpHeaders();
        yahooFinanceApiHeaders.set("X-RapidAPI-Key", X_KEY);
        yahooFinanceApiHeaders.set("X-RapidAPI-Host", X_HOST);

        mockServer.expect(requestTo(YAHOO_FINANCE_API_URL + "/multi-quote/" + stock))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("X-RapidAPI-Key", X_KEY))
                .andExpect(header("X-RapidAPI-Host", X_HOST))
                .andRespond(withSuccess("{\"test\":\"value\"}", MediaType.APPLICATION_JSON));

        JsonNode expectedResponse = new ObjectMapper().readTree("{\"test\":\"value\"}");
        Object actualResponse = serviceStock.getAllData(stock, jwtToken);

        assertEquals(expectedResponse, actualResponse);

        System.out.println("Expected: " + expectedResponse);
        System.out.println("Actual: " + actualResponse);

        mockServer.verify();
    }

    @Test(expected = ResponseStatusException.class)
    public void testGetAllDataWithInvalidToken() {
        String stock = "AAPL";
        String jwtToken = "invalid_token";

        HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);

        mockServer.expect(requestTo(AUTH_MICROSERVICE_URL + "/api/validateToken"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken))
                .andRespond(withSuccess("false", MediaType.APPLICATION_JSON));

        serviceStock.getAllData(stock, jwtToken);

        mockServer.verify();
    }
}