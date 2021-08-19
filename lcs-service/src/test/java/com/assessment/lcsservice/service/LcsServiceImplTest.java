package com.assessment.lcsservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.assessment.lcsservice.vo.InputData;
import com.assessment.lcsservice.vo.LongestCommonSubstrings;
import com.assessment.lcsservice.vo.StringValue;

@SpringBootTest
public class LcsServiceImplTest {

	@Autowired
	LcsService lcsService;

	@Test
	public void findLcs_Case1() throws Exception {

		InputData inputData = new InputData();
		List<StringValue> listOfValues = new ArrayList<StringValue>();

		listOfValues.add(new StringValue("comcast"));
		listOfValues.add(new StringValue("comcast1"));
		listOfValues.add(new StringValue("comcast2"));
		inputData.setSetOfStrings(listOfValues);

		LongestCommonSubstrings lcs = lcsService.findLcs(inputData);
		assertEquals("comcast", lcs.getLcs().get(0).getValue());

	}

}
