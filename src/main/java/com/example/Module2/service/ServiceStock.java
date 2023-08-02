package com.example.Module2.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ServiceStock {

	private static final String authMicroserviceUrl = "http://localhost:8082";

	private static final String YAHOO_FINANCE_API_URL = "https://yahoo-finance127.p.rapidapi.com";

	private static final String xKey = "7e59f69744mshaa527e44b79a29fp1c6d42jsna899af6f420e";
	private static final String xHost = "yahoo-finance127.p.rapidapi.com";
	@Autowired
	private RestTemplate restTemplate;

	public ServiceStock(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public Object getAllData(String stock, String jwtToken) {
		try {
			// Send a request to the authentication microservice to validate the access token
			HttpHeaders authHeaders = new HttpHeaders();
			authHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);
			HttpEntity<Void> authRequest = new HttpEntity<>(authHeaders);
			ResponseEntity<Boolean> authResponse = restTemplate.exchange(authMicroserviceUrl + "/api/validateToken", HttpMethod.GET, authRequest, Boolean.class);
			System.out.println(authResponse.getBody());
			System.out.println("Authentication microservice response: " + authResponse.getStatusCode() + " " + authResponse.getBody());


			// Check if the access token is valid
			if (!authResponse.getBody()) {
				System.out.println("Error come");
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid access token");
			}

			// Send a request to the Yahoo Finance API to retrieve stock quotes
			HttpHeaders yahooFinanceApiHeaders = new HttpHeaders();
			yahooFinanceApiHeaders.set("X-RapidAPI-Key", xKey);
			yahooFinanceApiHeaders.set("X-RapidAPI-Host", xHost);
			String apiUrl = YAHOO_FINANCE_API_URL + "/multi-quote/" + stock;
			ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, new HttpEntity<>(yahooFinanceApiHeaders), String.class);

			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(response.getBody());
			return root;
		} catch (HttpServerErrorException x) {

			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid access token");

		}
		catch (ResponseStatusException z){
			return "INVALID ACCESS";

		}
		catch (Exception e) {
			log.error("Exception while calling API", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Exception while calling API");
		}

	}
}