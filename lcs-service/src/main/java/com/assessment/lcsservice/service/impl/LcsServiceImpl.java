package com.assessment.lcsservice.service.impl;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.assessment.lcsservice.service.LcsService;
import com.assessment.lcsservice.vo.InputData;
import com.assessment.lcsservice.vo.LongestCommonSubstrings;
import com.assessment.lcsservice.vo.StringValue;

/**
 * 
 * @author uyadav509
 *
 */
@Service
public class LcsServiceImpl implements LcsService {

	@Override
	public LongestCommonSubstrings findLcs(InputData inputData) {

		String[] inputStrArray = inputData.getSetOfStrings().stream().map(obj -> obj.getValue()).toArray(String[]::new);

		SortedSet<String> sortedLongestCommonSubstrings = findLcsSetFromStringArray(inputStrArray);

		List<StringValue> listOfStringValues = sortedLongestCommonSubstrings.stream().map(str -> new StringValue(str))
				.collect(Collectors.toList());

		LongestCommonSubstrings lcs = new LongestCommonSubstrings(listOfStringValues);

		return lcs;

	}

	/**
	 * This method generates longest common substrings for a given array of stings.
	 * First string of array is used as reference which later used to generate all
	 * possible substrings for it and those substrings are checked in all n-1
	 * strings of array to find the longest common substring. SortedSet is used
	 * store longest common substrings as it maintains uniqueness and sorted order
	 * of strings.
	 * 
	 * @param strArr: String of arrays.
	 * @return SortedSet<String>: SortedSet of longest common substrings.
	 */
	private SortedSet<String> findLcsSetFromStringArray(String strArr[]) {

		int n = strArr.length;

		// First string from string array for reference
		String refStr = strArr[0];

		// Length of reference string
		int len = refStr.length();

		String resultStr = "";

		// Sorted set of longest common substrings, it gives all strings in sorted
		// order.
		SortedSet<String> lcsSet = new TreeSet<String>();

		for (int i = 0; i < len; i++) {
			for (int j = i + 1; j <= len; j++) {

				// Generate all substrings from reference string
				String temSubstr = refStr.substring(i, j);
				int k = 1;
				for (k = 1; k < n; k++) {
					// Check if the generated substring is found in all
					if (!strArr[k].contains(temSubstr))
						break;
				}

				// If current substring is present in all strings and its length is equal to
				// result then add to sorted set
				// and if its length is greater than result the remove all previously
				// found substrings from sorted set and new found common substring
				if (k == n && resultStr.length() == temSubstr.length()) {
					resultStr = temSubstr;
					lcsSet.add(resultStr);

				} else if (k == n && resultStr.length() < temSubstr.length()) {
					resultStr = temSubstr;
					lcsSet.clear();
					lcsSet.add(resultStr);

				}

			}
		}

		return lcsSet;
	}

}
