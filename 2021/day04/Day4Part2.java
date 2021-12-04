package com.aoc.solution;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day4Part2 {

	private Day4Part2() {

	}

	public static void main(String[] args) throws IOException {
		List<String> initialNumberList = Files.readAllLines(Paths.get("input-day4.txt"));

		String drawSequence = initialNumberList.get(0);

		List<Integer[][]> boards = loadBoards(initialNumberList);
		List<Integer[][]> boardsThatDidNotWinYet = boards;
		for (String drawnNumberString : Arrays.asList(drawSequence.split(","))) {
			System.out.println("*****************************"+drawnNumberString);
			Integer drawnNumber = Integer.parseInt(drawnNumberString.trim());
			markBoardsAfterDraw(boardsThatDidNotWinYet, drawnNumber);
			boardsThatDidNotWinYet = checkFirstComletedRows(boardsThatDidNotWinYet,drawnNumber);
			if (!boardsThatDidNotWinYet.isEmpty()) {
				boardsThatDidNotWinYet = checkFirstVerticalComletedLines(boardsThatDidNotWinYet,drawnNumber);
				//if (wonBoard != null) {
					//System.out.println("A Board has WON!! **** Number Drawn ** " + drawnNumber);
					//System.out.println(Arrays.deepToString(wonBoard));
					//calculateFinalAnswer(wonBoard, drawnNumber);
					//break;
				//}
			} 
			System.out.println("boardsThatDidNotWinYet "+ boardsThatDidNotWinYet.size());
			if(boardsThatDidNotWinYet.size() == 1) {
				System.out.println(Arrays.deepToString(boardsThatDidNotWinYet.get(0)));
			}
		}
	}

	private static void calculateFinalAnswer(Integer[][] wonBoard, Integer drawnNumber) {
		Integer sum = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (wonBoard[i][j] != -1) {
					sum += wonBoard[i][j];
				}
			}
		}
		System.out.println("Sum of unmarked numbers for board that won " + sum);
		System.out.println("Final Answer :: " + sum * drawnNumber);
		System.out.println("************************************************");
	}

	private static List<Integer[][]> loadBoards(List<String> initialNumberList) {
		List<Integer[][]> boards = new ArrayList<>();
		Integer[][] board = null;
		int totalBoards = initialNumberList.size() / 6;
		for (int z = 0; z < totalBoards; z++) {
			boards.add(new Integer[5][5]);
		}
		int l = -1;
		for (int i = 1; i < initialNumberList.size(); i++) {
			String line = initialNumberList.get(i);
			// Skip blank lines
			if (!line.isBlank()) {
				l++;
				if (l % 5 == 0) {
					board = boards.get(l / 5);
				}
				List<String> boardLine = Arrays.asList(line.split(" "));
				int k = 0;
				for (int j = 0; j < boardLine.size(); j++) {
					String boardLineNumber = boardLine.get(j).trim();
					if (!boardLineNumber.isBlank()) {
						Integer num = Integer.parseInt(boardLineNumber);
						board[l % 5][k++] = num;
					}
				}
			}
		}
		return boards;
	}

	private static List<Integer[][]> markBoardsAfterDraw(List<Integer[][]> boards, Integer drawnNumber) {
		boards.forEach(board -> {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					if (board[i][j] == drawnNumber) {
						board[i][j] = -1; // -1 represents matched number
					}
				}
			}
		});
		return boards;
	}

	private static List<Integer[][]> checkFirstComletedRows(List<Integer[][]> boards,Integer drawnNumber) {
		List<Integer[][]> wonBoards = new ArrayList<>();
		List<Integer[][]> boardsThatDidNotWinYet = new ArrayList<>();
		boolean matchedRowsFound = false;
		for (Integer[][] board : boards) {
			for (int i = 0; i < 5; i++) {
				Integer[] row = getRow(board, i);
				matchedRowsFound = Arrays.asList(row).stream().allMatch(j -> j == -1);
				if (matchedRowsFound) {
					calculateFinalAnswer(board, drawnNumber);
					wonBoards.add(board);
					break;
				}
			}
			if(!matchedRowsFound) {
				boardsThatDidNotWinYet.add(board);
			}
		}
		return boardsThatDidNotWinYet;
	}

	private static List<Integer[][]> checkFirstVerticalComletedLines(List<Integer[][]> boards,Integer drawnNumber) {
		List<Integer[][]> wonBoards = new ArrayList<>();
		List<Integer[][]> boardsThatDidNotWinYet = new ArrayList<>();
		boolean matchedColumnFound = false;
		for (Integer[][] board : boards) {
			for (int i = 0; i < 5; i++) {
				Integer[] column = getColumn(board, i);
				matchedColumnFound = Arrays.asList(column).stream().allMatch(j -> j == -1);
				if (matchedColumnFound) {
					//System.out.println("A Board has WON!! **** Number Drawn ** " + drawnNumber);
					//System.out.println(Arrays.deepToString(board));
					wonBoards.add(board);
					calculateFinalAnswer(board, drawnNumber);
					break;
				}
			}
			if(!matchedColumnFound) {
				boardsThatDidNotWinYet.add(board);
			}
		}
		return boardsThatDidNotWinYet;
	}

	private static Integer[] getColumn(Integer[][] array, int index) {
		Integer[] column = new Integer[array[0].length];
		for (int i = 0; i < column.length; i++) {
			column[i] = array[i][index];
		}
		return column;
	}

	private static Integer[] getRow(Integer[][] array, int index) {
		Integer[] row = new Integer[array[0].length];
		for (int i = 0; i < row.length; i++) {
			row[i] = array[index][i];
		}
		return row;
	}

}
