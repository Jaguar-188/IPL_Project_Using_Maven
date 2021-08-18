package com.ipl.test;

import com.ipl.test.model.*;

import java.io.*;

import java.util.*;
import org.json.JSONObject;
import org.json.JSONArray;

public class Ipl {

	private static final String matchesPath = "./data/matches.csv";
	private static final String deliveriesPath = "./data/deliveries.csv";
	public static final String matchesPlayedPerYearFilePath = "./jsonResults/matchesPlayedPerYear.json";
	public static final String matchesWonPerTeamPerYearFilePath = "./jsonResults/matchesWonPerTeamPerYear.json";
	public static final String extraRunsConcededPerTeamFilePath = "./jsonResults/extraRunsConcededPerTeam.json";
	public static final String topTenEconomicalBowlersFilePath = "./jsonResults/topTenEconomicalBowlers.json";

	private static final int matchId = 0;
	private static final int season = 1;
	private static final int winner = 10;

	private static final int deliveryId = 0;
	private static final int bowlingTeam = 3;
	private static final int ball = 5;
	private static final int bowler = 8;
	private static final int extraRuns = 16;
	private static final int totalRuns = 17;

	public static void main(String[] args) {

		List<Match> matches = getMatchData();

		List<Delivery> deliveries = getDeliveryData();

		HashMap<String, Integer> matchesPlayedPerYear = matchesPlayedPerYear(matches);
		JSONObject matchesPlayedPerYearJson = new JSONObject(matchesPlayedPerYear);
		JSONArray matchesPlayedPerYearArray = new JSONArray();
		System.out.println("Matches Played Per Year:");
		System.out.println(matchesPlayedPerYearJson);
		matchesPlayedPerYearArray.put(matchesPlayedPerYearJson);
		storeDataToJsonFile(matchesPlayedPerYearFilePath, matchesPlayedPerYearArray);

		System.out.println("\n");

		HashMap<String, HashMap<String, Integer>> matchesWonPerTeamPerYear = matchesWonPerTeamPerYear(matches);
		JSONObject matchesWonPerTeamPerYearJson = new JSONObject(matchesWonPerTeamPerYear);
		JSONArray matchesWonPerTeamPerYearArray = new JSONArray();
		System.out.println("Matches Won Per Tear Per Year:");
		System.out.println(matchesWonPerTeamPerYearJson);
		matchesWonPerTeamPerYearArray.put(matchesWonPerTeamPerYearJson);
		storeDataToJsonFile(matchesWonPerTeamPerYearFilePath, matchesWonPerTeamPerYearArray);

		System.out.println("\n");

		String year = "2016";
		HashMap<String, Integer> extraRunsConcededPerTeam = extraRunsConcededPerTeam(matches, deliveries, year);
		JSONObject extraRunsConcededPerTeamJson = new JSONObject(extraRunsConcededPerTeam);
		JSONArray extraRunsConcededPerTeamArray = new JSONArray();
		System.out.println("Extra Runs Conceded Per Team AT Year 2016:");
		System.out.println(extraRunsConcededPerTeamJson);
		extraRunsConcededPerTeamArray.put(extraRunsConcededPerTeamJson);
		storeDataToJsonFile(extraRunsConcededPerTeamFilePath, extraRunsConcededPerTeamArray);

		System.out.println("\n");

		String season = "2015";
		HashMap<String, Double> topTenEconomicalBowlers = topTenEconomicalBowlers(matches, deliveries, season);
		JSONObject topTenEconomicalBowlersJson = new JSONObject(topTenEconomicalBowlers);
		JSONArray topTenEconomicalBowlersArray = new JSONArray();
		System.out.println("Top Ten Economical Bowlers AT Year 2015:");
		System.out.println(topTenEconomicalBowlersJson);
		topTenEconomicalBowlersArray.put(topTenEconomicalBowlersJson);
		storeDataToJsonFile(topTenEconomicalBowlersFilePath, topTenEconomicalBowlersArray);

	}

	public static HashMap<String, Integer> matchesPlayedPerYear(List<Match> matches)
	{
		HashMap<String, Integer> matchesPlayedPerYear = new HashMap<String, Integer>();

		for (Match match : matches) {
			String Season = match.getSeason();
			if (matchesPlayedPerYear.containsKey(Season)) {
				matchesPlayedPerYear.put(Season, matchesPlayedPerYear.get(Season) + 1);
			} else {
				matchesPlayedPerYear.put(Season, 1);
			}
		}
		return matchesPlayedPerYear;
	}

	public static HashMap<String, HashMap<String, Integer>> matchesWonPerTeamPerYear(List<Match> matches) 
	{
		HashMap<String, HashMap<String, Integer>> matchesWonPerTeamPerYear = new HashMap<String, HashMap<String, Integer>>();

		for (Match match : matches) {
			String Season = match.getSeason();
			String Winner = match.getWinner();
			if (matchesWonPerTeamPerYear.containsKey(Season)) {
				if (matchesWonPerTeamPerYear.get(Season).containsKey(Winner)) {
					matchesWonPerTeamPerYear.get(Season).put(Winner,
							matchesWonPerTeamPerYear.get(Season).get(Winner) + 1);
				} else {
					matchesWonPerTeamPerYear.get(Season).put(Winner, 1);
				}
			} else {
				matchesWonPerTeamPerYear.put(Season, new HashMap<String, Integer>());
				matchesWonPerTeamPerYear.get(Season).put(Winner, 1);
			}
		}
		return matchesWonPerTeamPerYear;
	}

	public static HashMap<String, Integer> extraRunsConcededPerTeam(List<Match> matches, List<Delivery> deliveries,String year)
	{
		List<String> matchDataFiltered = new ArrayList<String>();
		HashMap<String, Integer> extraRunsConceded = new HashMap<String, Integer>();
		if (deliveries.isEmpty()) {
			throw new EmptyStackException();
		}
		for (Match match : matches) {
			if (match.getSeason().equals(year)) {
				matchDataFiltered.add(match.getMatchId());
			}
		}
		for (String id : matchDataFiltered) {
			String idFromMatch = id;
			for (Delivery delivery : deliveries) {
				String idFromDelivery = delivery.getMatchId();
				if (idFromMatch.equals(idFromDelivery)) {
					String bowlingTeam = delivery.getBowlingTeam();
					String extraRuns = delivery.getExtraRuns();
					if (extraRunsConceded.containsKey(bowlingTeam)) {
						extraRunsConceded.put(bowlingTeam,
								extraRunsConceded.get(bowlingTeam) + Integer.parseInt(extraRuns));
					} else {
						extraRunsConceded.put(bowlingTeam, Integer.parseInt(extraRuns));
					}
				}
			}
		}
		return extraRunsConceded;
	}

	public static HashMap<String, Double> topTenEconomicalBowlers(List<Match> matches, List<Delivery> deliveries,String season) 
	{
		List<String> matchDataFiltered = new ArrayList<String>();
		HashMap<String, Double> totalRunsByEachBowler = new HashMap<String, Double>();
		HashMap<String, Double> totalBowlsByEachBowler = new HashMap<String, Double>();
		HashMap<String, Double> topEconomicalBowlers = new HashMap<String, Double>();
		HashMap<String, Double> topTenEconomicalBowlers = new HashMap<String, Double>();
		if (matches.isEmpty()) {
			throw new EmptyStackException();
		}
		for (Match match : matches) {
			if (match.getSeason().equals(season)) {
				matchDataFiltered.add(match.getMatchId());
			}
		}

		for (String id : matchDataFiltered) {
			String idFromMatch = id;
			for (Delivery delivery : deliveries) {
				String idFromDelivery = delivery.getMatchId();
				if (idFromMatch.equals(idFromDelivery)) {
					String bowler = delivery.getBowler();
					String totalRuns = delivery.getTotalRuns();
					String totalBalls = delivery.getBall();
					if (totalRunsByEachBowler.containsKey(bowler)) {
						totalRunsByEachBowler.put(bowler,
								totalRunsByEachBowler.get(bowler) + Double.parseDouble(totalRuns));
						totalBowlsByEachBowler.put(bowler,
								totalBowlsByEachBowler.get(bowler) + Double.parseDouble(totalBalls));
					} else {
						totalRunsByEachBowler.put(bowler, Double.parseDouble(totalRuns));
						totalBowlsByEachBowler.put(bowler, Double.parseDouble(totalBalls));
					}
				}

			}

		}

		for (Map.Entry<String, Double> runs : totalRunsByEachBowler.entrySet()) {
			for (Map.Entry<String, Double> balls : totalBowlsByEachBowler.entrySet()) {
				if (runs.getKey().equals(balls.getKey())) {
					topEconomicalBowlers.put(runs.getKey(), runs.getValue() / (balls.getValue() / 6));
				}
			}
		}

		List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(topEconomicalBowlers.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
			public int compare(Map.Entry<String, Double> c1, Map.Entry<String, Double> c2) {
				return c1.getValue().compareTo(c2.getValue());
			}
		});

		int temp = 0;
		for (Map.Entry<String, Double> entry : list) {
			temp++;
			if (temp == 10) {
				break;
			}
			topTenEconomicalBowlers.put(entry.getKey(), entry.getValue());
		}
		return topTenEconomicalBowlers;
	}

	public static List<Match> getMatchData() {
		List<Match> matches = new ArrayList<Match>();

		try {
			String line = "";
			String data[];
			int count = 0;
			FileReader file = new FileReader(matchesPath);
			BufferedReader br = new BufferedReader(file);
			while ((line = br.readLine()) != null) {
				Match match = new Match();
				if (count != 0) {
					data = line.split(",");
					match.setMatchId(data[matchId]);
					match.setSeason(data[season]);
					match.setWinner(data[winner]);
					matches.add(match);
				}
				count = count + 1;
			}
			br.close();
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return matches;
	}

	public static List<Delivery> getDeliveryData() {
		List<Delivery> deliveries = new ArrayList<Delivery>();

		try {
			String line = "";
			String data[];
			int count = 0;
			FileReader file = new FileReader(deliveriesPath);
			BufferedReader br = new BufferedReader(file);
			while ((line = br.readLine()) != null) {
				Delivery delivery = new Delivery();
				if (count != 0) {
					data = line.split(",");
					delivery.setMatchId(data[deliveryId]);
					delivery.setBowlingTeam(data[bowlingTeam]);
					delivery.setBall(data[ball]);
					delivery.setBowler(data[bowler]);
					delivery.setExtraRuns(data[extraRuns]);
					delivery.setTotalRuns(data[totalRuns]);
					deliveries.add(delivery);
				}
				count = count + 1;
			}
			br.close();
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deliveries;
	}

	public static void storeDataToJsonFile(String filePath, JSONArray jsonArray) {
		try {
			FileWriter file = new FileWriter(filePath);
			file.write(jsonArray.toString());
			System.out.println("Successfully Write data to file..");
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
