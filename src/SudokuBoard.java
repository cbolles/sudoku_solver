import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;

public class SudokuBoard {

    private int DIM;
    private int[][] board;
    private int boxSize;

    public SudokuBoard(String filename) {
        this.board = fromFile(filename);
        this.DIM = board.length;
        this.boxSize = (int) Math.sqrt(DIM);
    }

    public SudokuBoard(SudokuBoard original) {
        this.DIM = original.DIM;
        this.boxSize = (int) Math.sqrt(DIM);
        this.board = new int[DIM][DIM];
        for(int row = 0; row < DIM; row++)
            for(int col = 0; col < DIM; col++)
                this.board[row][col] = original.getValue(row, col);
    }

    private int[] readLine(String input) {
        int[] line = new int[input.length()];
        for(int i = 0; i < input.length(); i++)
                line[i] = Integer.parseInt(input.substring(i, i+1));
        return line;
    }

    public boolean rowValid(int row) {
        List<Integer> rowContents = new ArrayList<>();
        for(int col = 0; col < DIM; col++) {
            if (board[row][col] != 0) {
                if(rowContents.contains(board[row][col]))
                    return false;
                else
                    rowContents.add(board[row][col]);
            }
        }
        return true;
    }

    public boolean colValid(int col) {
        List<Integer> colContents = new ArrayList<>();
        for(int row = 0; row < DIM; row++) {
            if (board[row][col] != 0) {
                if(colContents.contains(board[row][col]))
                    return false;
                else
                    colContents.add(board[row][col]);
            }
        }
        return true;
    }

    public boolean boxValid(int startRow, int startCol) {
        List<Integer> boxContents = new ArrayList<>();
        for(int row = startRow; row < startRow + boxSize; row ++) {
            for(int col = startCol; col < startCol + boxSize; col++) {
                if(board[row][col] != 0) {
                    if (boxContents.contains(board[row][col]))
                        return false;
                    else
                        boxContents.add(board[row][col]);
                }
            }
        }
        return true;
    }

    public boolean isValid() {
        for(int row = 0; row < DIM; row++)
            if(!rowValid(row))
                return false;
        for(int col = 0; col < DIM; col++)
            if(!colValid(col))
                return false;
        for(int startRow = 0; startRow < DIM; startRow+=boxSize)
            for(int startCol = 0; startCol < DIM; startCol+=boxSize)
                if(!boxValid(startRow, startCol))
                    return false;
        return true;
    }

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

    public void setCoord(int row, int col, int val) {
        board[row][col] = val;
    }

    public int getValue(int row, int col) {
        return board[row][col];
    }

    public int getDIM() {
        return DIM;
    }

    private int[][] fromFile(String filename) {
        try (Scanner in = new Scanner(new File(filename));) {
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
