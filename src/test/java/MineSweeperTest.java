import com.mine.sweeper.Grid;
import com.mine.sweeper.SweeperGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Rajani Desu
 */
public class MineSweeperTest {

    private Grid grid;
    private SweeperGame game;
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        scanner = new Scanner(System.in);
        grid = new Grid(4, 3); // 4x4 grid with 3 mines
        game = new SweeperGame(scanner);
    }

    @Test
    void testMinePlacement() {
        int mineCount = 0;
        char[][] gridData = grid.getGrid();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (gridData[i][j] == 'M') {
                    mineCount++;
                }
            }
        }
        assertEquals(3, mineCount);
    }


    @Test
    void testUncoverMine() {
        char[][] gridData = grid.getGrid();
        int gameRow = -1, gameColumn = -1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (gridData[i][j] == 'M') {
                    gameRow = i;
                    gameColumn = j;
                    break;
                }
            }
        }
        assertTrue(grid.uncover(gameRow, gameColumn));
        assertEquals('M', grid.getDisplayGrid()[gameRow][gameColumn]);
    }

    @Test
    void testUncoverFloodFill() {
        Grid testGrid = new Grid(3, 0);
        testGrid.uncover(1, 1);
        char[][] displayGrid = testGrid.getDisplayGrid();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals('0', displayGrid[i][j]);
            }
        }
    }

    @Test
    void testWinCondition() {
        Grid testGrid = new Grid(2, 1);
        char[][] gridData = testGrid.getGrid();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (gridData[i][j] != 'M') {
                    testGrid.uncover(i, j);
                }
            }
        }
        assertTrue(testGrid.hasWon());
    }

    @Test
    void testInputParse() {
        int[] coords = game.parseInput("B2", 3);
        assertArrayEquals(new int[]{1, 1}, coords);
    }

    @Test
    void testInvalidInputFormat() {
        assertThrows(IllegalArgumentException.class, () -> game.parseInput("1", 3), "Invalid format");
        assertThrows(IllegalArgumentException.class, () -> game.parseInput("AX", 3));
    }

}
