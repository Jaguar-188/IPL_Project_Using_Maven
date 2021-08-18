package com.ipl.testing.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ipl.test.*;
import com.ipl.test.model.*;
import java.util.*;

class topTenEconomicalBowlersTest {
	
	List<Match> matches = Ipl.getMatchData();
	List<Delivery> deliveries = Ipl.getDeliveryData();

//	@Test
//	@DisplayName("Test to check for economy of perticular bowler")
//	void testForEconomyOfPerticularBowler() {
//		
//		String year = "2012";
//		String bowlerName = "SL Malinga";
//		HashMap<String , Double> totalRunsbyBowler = new HashMap<>();
//		HashMap<String , Double> totalBallsByBowler = new HashMap<>();
//		HashMap<String , Double> economyOfBowler = new HashMap<>();
//		HashMap<String , Double> topTeneconomyOfBowler = new HashMap<>();
//		HashMap<String , Double> topTeneconomicalBowlers = new HashMap<>();
//		List<Match> sublistMatches = matches.subList(307,380);
//		for(Match match : sublistMatches)
//		{
//			for(Delivery delivery : deliveries)
//			{
//				if(delivery.getMatchId().equals(match.getMatchId()))
//				{
//					if(totalRunsbyBowler.containsKey(delivery.getBowler()))
//					{
//						totalRunsbyBowler.put(bowlerName, totalRunsbyBowler.get(delivery.getBowler()) + Double.parseDouble(delivery.getTotalRuns()));
//						totalBallsByBowler.put(bowlerName, totalBallsByBowler.get(delivery.getBowler()) + Double.parseDouble(delivery.getBall()));
//					}
//					else
//					{
//						totalRunsbyBowler.put(delivery.getBowler(), Double.parseDouble(delivery.getTotalRuns()));
//						totalBallsByBowler.put(delivery.getBowler(), Double.parseDouble(delivery.getBall()));
//					}
//				}
//			}
//		}
//		
//		for(Map.Entry<String, Double> runs : totalRunsbyBowler.entrySet())
//		{
//			for(Map.Entry<String, Double> balls : totalBallsByBowler.entrySet())
//			{
//				if(runs.getKey().equals(balls.getKey()))
//				{
//					economyOfBowler.put(runs.getKey(),runs.getValue()/(balls.getValue()/6));
//				}
//			}
//		}
//		//System.out.println(economyOfBowler);
//		List<Map.Entry<String, Double>> list = new ArrayList<>(economyOfBowler.entrySet());
//		
//		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
//			public int compare(Map.Entry<String, Double> c1,Map.Entry<String, Double> c2)
//			{
//				return c1.getValue().compareTo(c2.getValue());
//			}
//		});
//		
//		int temp = 0;
//		for(Map.Entry<String, Double> entry : list)
//		{
//			temp++;
//			if(temp == 10)
//			{
//				break;
//			}
//			topTeneconomyOfBowler.put(entry.getKey(), entry.getValue());
//		}
//		System.out.println("++"+topTeneconomyOfBowler);
//		topTeneconomicalBowlers = Ipl.topTenEconomicalBowlers(matches, deliveries, year);
//		System.out.println("--"+topTeneconomicalBowlers);
//		//assertEquals(Ipl.extraRunsConcededPerTeam(sublistMatches, deliveries, year), extraRuns);
//		
//		//assertEquals(Ipl.topTenEconomicalBowlers(matches,deliveries,year), topTenEconomicalBowlersAt2015,"This should not throw an error when valid data is passed.");
//	}
	
	@Test
	@DisplayName("Test to check if returned data is null or not")
	void testForNotNull() {
		
		String year = "2015";
		
		assertNotNull(Ipl.topTenEconomicalBowlers(matches,deliveries,year),"This should throw an error when Invalid data is passed.");
	}
	
	@Test
	@DisplayName("Test to check whether the size of data is right or not")
	void testToCheckSizeOfData() {
		
		int sizeOfMatch = Ipl.getMatchData().size();
		int sizeOfDelivery = Ipl.getDeliveryData().size();
		
		assertEquals(matches.size(), sizeOfMatch);
		assertEquals(deliveries.size(), sizeOfDelivery);
	}
	
	@Test
	@DisplayName("Test to check if null data is passed")
	void testToCheckNullInput() {
		
		assertThrows(NullPointerException.class, () -> Ipl.topTenEconomicalBowlers(null,null,null),"This should throw an NullPointerException");
	}
	
	@Test()
	@DisplayName("Test to check if empty data/list is passed")
	void testForInvalidInput() {
		
		String year = "2015";
		List<Match> empty = new ArrayList<>();
		
		assertThrows(EmptyStackException.class, () -> Ipl.topTenEconomicalBowlers(empty, deliveries,year),"This should throw an Exception");
	}
	
	@Test
	@DisplayName("Test to check return type of function is same as data type of input data")
	void testToCheckReturnType() {
		
		String actual = "HashMap";
		String year = "2015";
		
		assertEquals(Ipl.topTenEconomicalBowlers(matches,deliveries,year).getClass().getSimpleName(), actual);
	}

}
