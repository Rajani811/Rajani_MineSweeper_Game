package com.mine.sweeper;

import java.util.Scanner;
/**
 * @author Rajani Desu
 */

public class MineSweeper {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SweeperGame game = new SweeperGame(scanner);
        game.startPlay();
        scanner.close();
    }
}