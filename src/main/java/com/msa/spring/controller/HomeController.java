package com.msa.spring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {

	@RequestMapping("/")
	public String home() {
		return "Hello, Spring Boot!!";
	}
}
