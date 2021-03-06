package com.warthur.oauth2.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by warth on 2018/3/30.
 */
@RestController
@RequestMapping("/api")
public class HttpController {

	@GetMapping("/product/{id}")
	public String getProduct(@PathVariable String id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return "product id : " + id;
	}

	@GetMapping("/order/{id}")
	public String getOrder(@PathVariable String id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return "order id : " + id;
	}
}
