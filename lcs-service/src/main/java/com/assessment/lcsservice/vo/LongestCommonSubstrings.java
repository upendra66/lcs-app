package com.assessment.lcsservice.vo;

import java.util.List;

/**
 * Holds the list of String Values.
 * 
 * @author uyadav509
 *
 */
public class LongestCommonSubstrings {
	private List<StringValue> lcs;

	public LongestCommonSubstrings(List<StringValue> lcs) {
		super();
		this.lcs = lcs;
	}

	public List<StringValue> getLcs() {
		return lcs;
	}

	public void setLcs(List<StringValue> lcs) {
		this.lcs = lcs;
	}

}
