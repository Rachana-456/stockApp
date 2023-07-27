package com.example.Module2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Module2.service.ServiceStock;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class Controller {
	
	private final ServiceStock service;
	@GetMapping("/getData")
	 @PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> callApi(@RequestParam String stock){
		return ResponseEntity.ok(service.getAllData(stock));
	}

}
