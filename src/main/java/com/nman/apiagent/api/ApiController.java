package com.nman.apiagent.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

	@GetMapping("/")
	public String sampleTest() {
		return "Hello AI World";

	}

}
