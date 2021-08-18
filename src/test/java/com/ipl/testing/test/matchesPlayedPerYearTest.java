package com.ipl.testing.test;

import com.ipl.test.*;
import com.ipl.test.model.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class matchesPlayedPerYearTest {
	
	List<Match> matches = Ipl.getMatchData();
	List<Match> sublistMatches = matches.subList(0, 10);

	@Test
	@DisplayName("Test to check for valid data..")
	void testForValidData() {
		
		HashMap<String, Integer> matchesPlayedPerYear = new HashMap<>();
		
		matchesPlayedPerYear.put("2017", 10);

		assertEquals(Ipl.matchesPlayedPerYear(sublistMatches), matchesPlayedPerYear,"This should not throw an error when valid data is passed.");
	}
	
	@Test
	@DisplayName("Test to check if returned data is null or not")
	void testForNotNull() {
		
		assertNotNull(Ipl.matchesPlayedPerYear(matches),"This should throw an error when Invalid data is passed.");
	}
	
	@Test
	@DisplayName("Test to check whether the size of data is right or not")
	void testToCheckSizeOfData() {
		
		int sizeOfMatch = Ipl.getMatchData().size();
		
		assertEquals(matches.size(), sizeOfMatch);
	}
	
	@Test
	@DisplayName("Test to check if null data is passed")
	void testToCheckNullInput() {
		
		assertThrows(NullPointerException.class, () -> Ipl.matchesPlayedPerYear(null),"This should throw an NullPointerException");
	}
	
	@Test
	@DisplayName("Test to check return type of function is same as data type of input data")
	void testToCheckReturnType() {
		
		String actual = "HashMap";
		
		assertEquals(Ipl.matchesPlayedPerYear(matches).getClass().getSimpleName(), actual);
	}

}
