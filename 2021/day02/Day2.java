package com.aoc.solution;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day2 {

	private static final String FORWARD = "forward";
	private static final String DOWN = "down";
	private static final String UP = "up";

	private Day2() {

	}

	public static void main(String[] args) throws IOException {
		List<String> directions = Files.readAllLines(Paths.get("input-day2.txt"));
		Integer horizontalPosition = 0;
		Integer depth = 0;
		Integer aim = 0;
		for (String direction : directions) {
			Integer distanceUnits = Integer.parseInt(direction.split(" ")[1].trim());
			if (direction.startsWith(FORWARD)) {
				horizontalPosition += distanceUnits;
			}
			if (direction.startsWith(DOWN)) {
				depth += distanceUnits;
			}
			if (direction.startsWith(UP)) {
				depth -= distanceUnits;
			}
		}
		System.out.println("Horizontal Position :: " + horizontalPosition);
		System.out.println("Depth :: " + depth);
		System.out.println("Horizontal Position X Depth :: "+horizontalPosition * depth);
		
		//Part 2
		horizontalPosition = 0;
		depth = 0;
		aim = 0;
		for (String direction : directions) {
			Integer distanceUnits = Integer.parseInt(direction.split(" ")[1].trim());
			if (direction.startsWith(FORWARD)) {
				horizontalPosition += distanceUnits;
				depth += aim * distanceUnits;
			}
			if (direction.startsWith(DOWN)) {
				aim += distanceUnits;
			}
			if (direction.startsWith(UP)) {
				aim -= distanceUnits;
			}
		}
		
		System.out.println("Horizontal Position :: " + horizontalPosition);
		System.out.println("Depth :: " + depth);
		System.out.println("Aim :: " + aim);
		System.out.println("Horizontal Position X Depth :: "+horizontalPosition * depth);
	}

}
