package com.assessment.lcsservice.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.lcsservice.config.ValidSchema;
import com.assessment.lcsservice.service.LcsService;
import com.assessment.lcsservice.vo.InputData;
import com.assessment.lcsservice.vo.LongestCommonSubstrings;

@RestController
@Validated
public class LcsController {
	
	 final String SCHEMA_PATH = "classpath:schema.json";

	@Autowired
	LcsService lcsService;

	
	@PostMapping("/lcs")
	@CrossOrigin
	public ResponseEntity<LongestCommonSubstrings> greeting(@ValidSchema(SCHEMA_PATH) InputData inputData) {

		return ResponseEntity.ok(lcsService.findLcs(inputData));
	}

}
