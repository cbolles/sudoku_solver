import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

/**
 * Class that represents the Sudoku board. The board is represented by a 2-D array of ints in which each int is a value
 * in the board. Empty values are represented by 0's.
 * @author Collin Bolles
 * 12/16/2018
 */
public class SudokuBoard {

    /** The square dimenstion of the board, for most Sudoku boards this will be 9 **/
    private int DIM;

    /** The 2-D array of values in the board **/
    private int[][] board;

    /** The size of the sudoku box, square root of the DIM **/
    private int boxSize;

    /**
     * Makes use of a file to parse in the board. The board should be square and have valid values ranging from
     * 1 to the length of the board. Empty values are represented as spaces. See boards/example_1.txt for an example.
     * @precondition filename points to an existing and valid board file
     * @param filename The location of the file containing the board to parse in
     */
    public SudokuBoard(String filename) {
        this.board = fromFile(filename);
        this.DIM = board.length;
        this.boxSize = (int) Math.sqrt(DIM);
    }

    /**
     * Copy constructor that recreates the values in the original
     * @precondition original is not null
     * @param original SudokuBoard to copy
     */
    public SudokuBoard(SudokuBoard original) {
        this.DIM = original.DIM;
        this.boxSize = original.boxSize;
        this.board = new int[DIM][DIM];
        for(int row = 0; row < DIM; row++)
            for(int col = 0; col < DIM; col++)
                this.board[row][col] = original.getValue(row, col);
    }

    /**
     * Handles reading in a single string and parsing the values into a 1-D array of ints
     * @param input String representation of the values in a Sudoku row
     * @return 1-D array of values representing the Sudoku moves
     */
    private int[] readLine(String input) {
        int[] line = new int[input.length()];
        for(int i = 0; i < input.length(); i++)
                line[i] = Integer.parseInt(input.substring(i, i+1));
        return line;
    }

    /**
     * Handles returning a 2-D array of values from a filename. Reads in the file line by line and constructs the
     * Sudoku board.
     * @param filename Location of the file to parse
     * @return 2-D array representing Sudoku Board
     */
    private int[][] fromFile(String filename) {
        try (Scanner in = new Scanner(new File(filename))) {
            String line = in.nextLine();
            int DIM = line.length();
            int[][] board = new int[DIM][DIM];
            board[0] = readLine(line);
            for(int i = 1; i < DIM; i++)
                board[i] = readLine(in.nextLine());
            return board;
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Checks if a given row is valid. A valid row is a row with no repeated values. ex) 1, 5, 6, 7, 4, 3, 2
     * @param row The index of the row to validate
     * @return If the row is valid
     */
    private boolean rowValid(int row) {
        boolean[] contents = new boolean[DIM+1];
        for(int col = 0; col < DIM; col++) {
            if (board[row][col] != 0) {
                if(contents[board[row][col]])
                    return false;
                else
                    contents[board[row][col]] = true;
            }
        }
        return true;
    }

    /**
     * Checks if a given column is valid. A valid column is a column with no repeated values. ex) 1, 5, 6, 7, 4, 3, 2
     * @param col The index of the column to validate
     * @return If the column is valid
     */
    private boolean colValid(int col) {
        boolean[] contents = new boolean[DIM+1];
        for(int row = 0; row < DIM; row++) {
            if(board[row][col] != 0) {
                if(contents[board[row][col]])
                    return false;
                else
                    contents[board[row][col]] = true;
            }
        }
        return true;
    }

    /**
     * Checks the validity of a square region of the board. A valid box is a box with not repeated values.
     * ex) 1, 5, 6, 7, 4, 3, 2
     * @param startRow Start row of the box
     * @param startCol Start column of the box
     * @return If the box is valid
     */
    private boolean boxValid(int startRow, int startCol) {
        boolean[] contents = new boolean[DIM+1];
        for(int row = startRow; row < startRow + boxSize; row ++) {
            for(int col = startCol; col < startCol + boxSize; col++) {
                if(board[row][col] != 0) {
                    if(contents[board[row][col]])
                        return false;
                    else
                        contents[board[row][col]] = true;
                }
            }
        }
        return true;
    }

    /**
     * Checks the validity if each row, column, and box.
     * @return If each row, column, and box is valid
     */
    public boolean isValid() {
        for(int i = 0; i < DIM; i++) {
            if(!rowValid(i))
                return false;
            if(!colValid(i))
                return false;
        }
        for(int startRow = 0; startRow < DIM; startRow+=boxSize)
            for(int startCol = 0; startCol < DIM; startCol+=boxSize)
                if(!boxValid(startRow, startCol))
                    return false;
        return true;
    }

    /**
     * Returns an array which has the row (index 0), and column (index 1) of the next available empty coordinate (value
     * of 0 in the board).
     * @return Array with row and column of the next empty value on the Sudoku board
     */
    public int[] getEmptyCoord() {
        int[] coords = new int[2];
        for(int row = 0; row < DIM; row++)
            for(int col = 0; col < DIM; col++)
                if(board[row][col] == 0) {
                    coords[0] = row;
                    coords[1] = col;
                    return coords;
                }
        return null;
    }

    /**
     * Allows the ability to set a given row and column of the board to a specific value.
     * @param row Row in board to change
     * @param col Column in the board to change
     * @param val Value to set the board to
     */
    public void setCoord(int row, int col, int val) {
        board[row][col] = val;
    }

    /**
     * Get the value at a given row and column of the board
     * @param row Row to get from
     * @param col Column to get from
     * @return Value at the row and column
     */
    public int getValue(int row, int col) {
        return board[row][col];
    }

    /**
     * Returns the square dimension of the board
     * @return
     */
    public int getDIM() {
        return DIM;
    }

    /**
     * Represents the board as a string of values separated by spaces. Zeros are replaced with underscores.
     * @return String representation of the board
     */
    public String toString() {
        String result = "";
        for(int row = 0; row < DIM; row++) {
            for (int col = 0; col < DIM; col++) {
                if(board[row][col] == 0)
                    result += "_ ";
                else
                    result += board[row][col] + " ";
            }
            result += "\n";
        }
        return result.trim();
    }
}
