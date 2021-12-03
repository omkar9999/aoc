package com.aoc.solution;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day3 {

	private Day3() {

	}

	public static void main(String[] args) throws IOException {

		List<String> initialNumberList = Files.readAllLines(Paths.get("input-day3.txt"));
		Map<Integer, Integer[]> solutionMap = findFrequencyMap(initialNumberList);

		StringBuffer gammaRate = new StringBuffer();
		StringBuffer epsilonRate = new StringBuffer();
		solutionMap.forEach((column, solution) -> {
			gammaRate.append(solution[2]);
			Integer negatedValue = solution[2] == 0 ? 1 : 0;
			epsilonRate.append(negatedValue);
		});

		int gammaRateInDecimal = Integer.parseInt(gammaRate.toString(), 2);
		int epsilonRateInDecimal = Integer.parseInt(epsilonRate.toString(), 2);

		System.out.println("gammaRate in binary " + gammaRate);
		System.out.println("epsilonRate in binary " + epsilonRate);
		System.out.println("gammaRate in decimal " + gammaRateInDecimal);
		System.out.println("epsilonRate in decimal " + epsilonRateInDecimal);
		System.out.println("Final Answer :: " + gammaRateInDecimal * epsilonRateInDecimal);

		// Part 2
		List<String> oxygenGeneratorRatingList = findOxygenGeneratorRating(0,initialNumberList);
		System.out.println(oxygenGeneratorRatingList);
		
		List<String> co2ScrubberRatingList = findCO2ScrubberRating(0,initialNumberList);
		System.out.println(co2ScrubberRatingList);
		
		int oxygenGeneratorRating = Integer.parseInt(oxygenGeneratorRatingList.get(0).toString(), 2);
		int co2ScrubberRating = Integer.parseInt(co2ScrubberRatingList.get(0).toString(), 2);
		
		int lifeSupportRating = oxygenGeneratorRating * co2ScrubberRating;
		System.out.println("Life Support Rating of Submarine :: " + lifeSupportRating);

	}

	private static Map<Integer, Integer[]> findFrequencyMap(List<String> numbers) {
		Map<Integer, Integer[]> solutionMap = new HashMap<>();
		numbers.forEach(number -> {
			char[] numberChars = number.toCharArray();
			for (int i = 0; i < numberChars.length; i++) {
				Integer[] columnFrequencyForColumn = solutionMap.get(i);
				int value = Integer.parseInt(String.valueOf(numberChars[i]));
				if (null == columnFrequencyForColumn) {
					Integer[] columnFrequencyToInitialize = new Integer[4];
					if (value == 0) {
						columnFrequencyToInitialize[0] = 1;
						columnFrequencyToInitialize[1] = 0;
					} else {
						columnFrequencyToInitialize[1] = 1;
						columnFrequencyToInitialize[0] = 0;
					}
					columnFrequencyToInitialize[2] = value;
					solutionMap.put(i, columnFrequencyToInitialize);
				} else {
					if (value == 0) {
						columnFrequencyForColumn[0] = columnFrequencyForColumn[0] + 1;
					} else {
						columnFrequencyForColumn[1] = columnFrequencyForColumn[1] + 1;
					}
					if (columnFrequencyForColumn[0] > columnFrequencyForColumn[1]) {
						columnFrequencyForColumn[2] = 0;
					} else {
						columnFrequencyForColumn[2] = 1;
					}
					// 4th position checks if frequencies are same
					if(columnFrequencyForColumn[0] == columnFrequencyForColumn[1]) {
						columnFrequencyForColumn[3] = 1;
					}else {
						columnFrequencyForColumn[3] = 0;
					}
					solutionMap.put(i, columnFrequencyForColumn);
				}
			}
		});
		return solutionMap;
	}

	private static List<String> findOxygenGeneratorRating(int currentStartingPosition, List<String> currentList) {
		if (currentList.size() == 1) {
			return currentList;
		}
		if (currentStartingPosition + 1 > currentList.get(0).length()) {
			return currentList;
		}
		Map<Integer, Integer[]> solutionMap = findFrequencyMap(currentList);
		Integer[] currentFrequencies = solutionMap.get(currentStartingPosition);
		Integer maxFreqBitForTheCurrentPosition = currentFrequencies[2];
		if(currentFrequencies[3] == 1) {
			maxFreqBitForTheCurrentPosition = 1;
		}
		List<String> filteredList = filterTheListBasedOnTheBitAndPosition(currentStartingPosition, maxFreqBitForTheCurrentPosition, currentList);
		return findOxygenGeneratorRating(currentStartingPosition + 1, filteredList);
	}
	
	private static List<String> findCO2ScrubberRating(int currentStartingPosition, List<String> currentList) {
		if (currentList.size() == 1) {
			return currentList;
		}
		if (currentStartingPosition + 1 > currentList.get(0).length()) {
			return currentList;
		}
		Map<Integer, Integer[]> solutionMap = findFrequencyMap(currentList);
		Integer[] currentFrequencies = solutionMap.get(currentStartingPosition);
		Integer maxFreqBitForTheCurrentPosition = currentFrequencies[2];
		Integer minFreqBitForTheCurrentPosition = maxFreqBitForTheCurrentPosition == 0 ? 1 : 0;
		if(currentFrequencies[3] == 1) {
			minFreqBitForTheCurrentPosition = 0;
		}
		List<String> filteredList = filterTheListBasedOnTheBitAndPosition(currentStartingPosition, minFreqBitForTheCurrentPosition, currentList);
		return findCO2ScrubberRating(currentStartingPosition + 1, filteredList);
	}

	private static List<String> filterTheListBasedOnTheBitAndPosition(int currentStartingPosition, Integer bitToSelect,
			List<String> currentList) {
		List<String> finalList = new ArrayList<>();
		currentList.forEach(number -> {
			char[] numberChars = number.toCharArray();
			char decisiveCharacter =  numberChars[currentStartingPosition];
			int value = Integer.parseInt(String.valueOf(decisiveCharacter));
			if(value == bitToSelect) {
				finalList.add(number);
			}
		});
		return finalList;
	}

}
