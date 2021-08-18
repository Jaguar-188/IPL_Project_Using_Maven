package com.ipl.testing.test;
import com.ipl.test.model.*;
import static org.junit.jupiter.api.Assertions.*;
import com.ipl.test.*;

import java.util.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class matchesWonPerTeamPerYearTest {
	
	List<Match> matches = Ipl.getMatchData();
	List<Match> sublistMatches = matches.subList(219, 249);

	@Test
	@DisplayName("Test to check for valid data..")
	void testForValidData() {
		HashMap<String, Integer> winner1 = new HashMap<String, Integer>();
		HashMap<String, Integer> winner2 = new HashMap<String, Integer>();
		HashMap<String, HashMap<String, Integer>> matchesPlayedPerTeamPerYear = new HashMap<String, HashMap<String, Integer>>();
		
		winner1.put("Deccan Chargers", 3);
		winner1.put("Mumbai Indians", 3);
		winner1.put("Chennai Super Kings", 4);
		winner1.put("Royal Challengers Bangalore", 2);
		winner1.put("Delhi Daredevils", 1);
		winner1.put("Kolkata Knight Riders", 2);
		winner2.put("Chennai Super Kings", 2);
		winner2.put("Rajasthan Royals", 2);
		winner2.put("Royal Challengers Bangalore", 1);
		winner2.put("Mumbai Indians", 2);
		winner2.put("Pune Warriors", 2);
		winner2.put("Kolkata Knight Riders", 2);
		winner2.put("Kings XI Punjab", 2);
		winner2.put("Deccan Chargers", 1);
		winner2.put("Kochi Tuskers Kerala", 1);
		matchesPlayedPerTeamPerYear.put("2010", winner1);
		matchesPlayedPerTeamPerYear.put("2011", winner2);
		
		assertEquals(Ipl.matchesWonPerTeamPerYear(sublistMatches), matchesPlayedPerTeamPerYear,"This should not throw an error when valid data is passed.");
	}
	
	@Test
	@DisplayName("Test to check if returned data is null or not")
	void testForNotNull() {
		
		assertNotNull(Ipl.matchesWonPerTeamPerYear(matches),"This should throw an error when Invalid data is passed.");
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
		
		assertThrows(NullPointerException.class, () -> Ipl.matchesWonPerTeamPerYear(null),"This should throw an NullPointerException");
	}
	
	@Test
	@DisplayName("Test to check return type of function is same as data type of input data")
	void testToCheckReturnType() {
		
		String actual = "HashMap";
		
		assertEquals(Ipl.matchesWonPerTeamPerYear(matches).getClass().getSimpleName(), actual);
	}
	
}
