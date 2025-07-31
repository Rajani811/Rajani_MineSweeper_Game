package com.mine.sweeper;

import java.util.Random;

/**
 * @author Rajani Desu
 */


public class Grid {
    private int size;
    private char[][] grid; // Actual grid with mines ('M') and numbers
    private char[][] displayGrid; // Grid shown to player
    private int numMines;
    private int uncoveredCount;

    public Grid(int size, int numMines) {
        this.size = size;
        this.numMines = numMines;
        this.grid = new char[size][size];
        this.displayGrid = new char[size][size];
        this.uncoveredCount = 0;
        initializeGrids();
        placeMines();
        calculateNumbers();
    }

    private void initializeGrids() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = '0';
                displayGrid[i][j] = '_';
            }
        }
    }

    private void placeMines() {
        Random random = new Random();
        int minesPlaced = 0;
        while (minesPlaced < numMines) {
            int row = random.nextInt(size);
            int col = random.nextInt(size);
            if (grid[row][col] != 'M') {
                grid[row][col] = 'M';
                minesPlaced++;
            }
        }
    }

    public void calculateNumbers() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == 'M') continue;
                int count = 0;
                for (int di = -1; di <= 1; di++) {
                    for (int dj = -1; dj <= 1; dj++) {
                        if (di == 0 && dj == 0) continue;
                        int ni = i + di;
                        int nj = j + dj;
                        if (ni >= 0 && ni < size && nj >= 0 && nj < size && grid[ni][nj] == 'M') {
                            count++;
                        }
                    }
                }
                grid[i][j] = (char) (count + '0');
            }
        }
    }

    public char[][] getDisplayGrid() {
        return displayGrid;
    }

    public char[][] getGrid() {
        return grid;
    }

    public boolean uncover(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            return false;
        }
        if (displayGrid[row][col] != '_') {
            return false;
        }
        if (grid[row][col] == 'M') {
            displayGrid[row][col] = 'M';
            return true; // Hit a mine
        }
        floodFill(row, col);
        return false;
    }

    private void floodFill(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size || displayGrid[row][col] != '_') {
            return;
        }
        displayGrid[row][col] = grid[row][col];
        uncoveredCount++;
        if (grid[row][col] == '0') {
            for (int di = -1; di <= 1; di++) {
                for (int dj = -1; dj <= 1; dj++) {
                    if (di == 0 && dj == 0) continue;
                    floodFill(row + di, col + dj);
                }
            }
        }
    }

    public boolean hasWon() {
        return uncoveredCount == size * size - numMines;
    }

    public void display() {
        System.out.println("Here is your minefield:");
        System.out.print("  ");
        for (int j = 0; j < size; j++) {
            System.out.print(" " + (j + 1) + " ");
        }
        System.out.println();
        for (int i = 0; i < size; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < size; j++) {
                System.out.print(" " + displayGrid[i][j] + " ");
            }
            System.out.println();
        }
    }
    public int getSize() {
        return size;
    }
}