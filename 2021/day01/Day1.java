package com.aoc.solution;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Day1 {

	private Day1() {

	}

	public static void main(String[] args) throws IOException {
		List<Integer> depthMeasurements = Files.readAllLines(Paths.get("input-day1.txt")).stream()
				.map(Integer::parseInt).collect(Collectors.toList());
		Integer firstNumber = depthMeasurements.get(0);

		int increaseCount = 0;
		int decreaseCount = 0;

		for (Integer depth : depthMeasurements) {
			if (depth > firstNumber) {
				increaseCount += 1;
			}
			if (depth < firstNumber) {
				decreaseCount += 1;
			}
			firstNumber = depth;
		}

		System.out.println("Depth Increase Frequency " + increaseCount);
		System.out.println("Depth Decrease Frequency " + decreaseCount);

		firstNumber = depthMeasurements.get(0);
		Integer secondNumber = depthMeasurements.get(1);
		Integer thirdNumber = depthMeasurements.get(2);
		Integer firstSum = firstNumber + secondNumber + thirdNumber;
		Integer secondSum = 0;
		increaseCount = 0;
		decreaseCount = 0;
		
		// Sliding Window
		// Window Size 3
		for (int i = 3; i < depthMeasurements.size(); i++) {
            firstNumber = secondNumber;
            secondNumber = thirdNumber;
            thirdNumber = depthMeasurements.get(i);
            secondSum = firstNumber + secondNumber + thirdNumber;
            if (secondSum > firstSum) {
				increaseCount += 1;
			}
			if (firstSum > secondSum) {
				decreaseCount += 1;
			}
			firstSum = secondSum;
		}
		
		System.out.println("Depth Increase Frequency for Sliding Window " + increaseCount);
		System.out.println("Depth Decrease Frequency for Sliding Window " + decreaseCount);
	}

}
