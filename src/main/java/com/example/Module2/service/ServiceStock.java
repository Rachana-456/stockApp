package com.example.Module2.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ServiceStock {


	private static final String xKey = "604eb06a6fmsh4a9cb1338be9f31p1a4a4ajsnb12737650e76";
	private static final String xHost = "yahoo-finance127.p.rapidapi.com";
	@Autowired
	private RestTemplate restTemplate;
	public Object getAllData(String stock) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("X-RapidAPI-Key", xKey);
			headers.set("X-RapidAPI-Host", xHost);
			String apiUrl ="https://yahoo-finance127.p.rapidapi.com/multi-quote/"+stock;


			ResponseEntity<String> response = restTemplate.exchange(apiUrl,HttpMethod.GET,new HttpEntity<>(headers),String.class);
//			log.info("Output is",response.getBody());

//			System.out.println(response.getBody());
//			return response.getBody();
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(response.getBody());
			return root;
		}
		catch(Exception e) {
			log.error("Something wrong",e);
			throw new ResponseStatusException(
				HttpStatus.INTERNAL_SERVER_ERROR,
				"EXCEPTION WHILE CALLING API",
				e
				);

		}
	}
}
