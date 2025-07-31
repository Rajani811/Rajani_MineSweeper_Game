package com.mine.sweeper;

import java.util.Scanner;

public class SweeperGame {
    private Grid grid;
    private Scanner scanner;
    private boolean gameOver;
    private boolean won;

    public SweeperGame(Scanner scanner) {
        this.scanner = scanner;
        this.gameOver = false;
        this.won = false;
    }

    private void initializeGame() {
        int size = 0;
        int numMines = 0;
        while (true) {
            try {
                System.out.print("Enter grid size (e.g., 5 for 5x5 grid): ");
                size = Integer.parseInt(scanner.nextLine());
                if (size < 2) {
                    System.out.println("Grid size must be at least 2.");
                    continue;
                }
                int maxMines = (int) (size * size * 0.35); // Maximum 35% of total squares
                System.out.print("Enter number of mines (1 to " + maxMines + "): ");
                numMines = Integer.parseInt(scanner.nextLine());
                if (numMines < 1 || numMines > maxMines) {
                    System.out.println("Number of mines must be between 1 and " + maxMines + ".");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter valid numbers.");
            }
        }
        this.grid = new Grid(size, numMines);
    }

    public int[] parseInput(String input, int gridSize) throws IllegalArgumentException {
        input = input.trim().toUpperCase();
        if (input.length() < 2) {
            throw new IllegalArgumentException("Invalid input. Use format like A1.");
        }
        int row = input.charAt(0) - 'A'; // Letter maps to row (A=0, B=1, ...)
        int col;
        try {
            col = Integer.parseInt(input.substring(1)) - 1; // Number maps to column (1=0, 2=1, ...)
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input. Use format like A1.");
        }
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            throw new IllegalArgumentException("Invalid coordinates. Column must be A-" + (char)('A' + gridSize - 1) + ", row must be 1-" + gridSize + ".");
        }
        return new int[]{row, col};
    }

    public void startPlay() {
        while (true) {
            gameOver = false;
            won = false;
            initializeGame();
            System.out.println("\nWelcome to Minesweeper!");
            while (!gameOver) {
                grid.display();
                try {
                    System.out.print("Select a square to reveal (e.g., A1): ");
                    String input = scanner.nextLine();
                    int[] coordinates = parseInput(input, grid.getSize());
                    int row = coordinates[0];
                    int col = coordinates[1];
                    if (grid.uncover(row, col)) {
                        gameOver = true;
                        System.out.println("Oh no, you detonated a mine! Game over.");
                    } else {
                        char value = grid.getDisplayGrid()[row][col];
                        if (value != 'M') {
                            int adjacentMines = Character.getNumericValue(value);
                            System.out.println("This square contains " + adjacentMines + " adjacent mine" + (adjacentMines == 1 ? "." : "s."));
                        }
                        if (grid.hasWon()) {
                            gameOver = true;
                            won = true;
                            System.out.println("Congratulations, you have won the game!");
                        }
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println("Press any key to play again...");
            scanner.nextLine(); // Wait for any input
        }
    }
}