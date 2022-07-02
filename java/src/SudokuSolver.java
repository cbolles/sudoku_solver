import java.util.ArrayList;
import java.util.List;

/**
 * Solves a Sudoku board using the backtracking algorithm. The class goes through each empty coordinate and attempts
 * to produce a valid solution through trial and error
 * @author Collin Bolles
 * 12/16/2018
 */
public class SudokuSolver {

    /** Representation of the board **/
    private SudokuBoard board;

    /**
     * Creates a board element using the given file location to a board text file
     * @param filename Location of the sudoku board text file
     * @see SudokuBoard
     */
    public SudokuSolver(String filename) {
        this.board = new SudokuBoard(filename);
    }

    /**
     * Get the original board
     * @return The Sudoku board being solved
     */
    public SudokuBoard getBoard() {
        return board;
    }

    /**
     * Get all the possible children on a given board. A child is the original board with the next empty position filled
     * with a value. Each config has a different value in the previously empty position.
     * @param parent The Sudoku board getting children values from
     * @return List of possible child values
     */
    public List<SudokuBoard> childrenConfigurations(SudokuBoard parent) {
        List<SudokuBoard> configurations = new ArrayList<>();
        int emptyCoord[] = parent.getEmptyCoord();
        for(int i = 1; i <= parent.getDIM(); i++) {
            SudokuBoard config = new SudokuBoard(parent);
            config.setCoord(emptyCoord[0], emptyCoord[1], i);
            configurations.add(config);
        }
        return configurations;
    }

    /**
     * Goes through each possible child that is valid and produces children from there until a valid solution is found.
     * Depth first search approach
     * @param original The original board
     * @return Solved board
     */
    public SudokuBoard solve(SudokuBoard original) {
        if(original.getEmptyCoord() == null && original.isValid())
            return original;
        List<SudokuBoard> childrenBoards = childrenConfigurations(original);
        for(SudokuBoard child: childrenBoards) {
            if(child.isValid()) {
                SudokuBoard solution = solve(child);
                if(solution != null)
                    return solution;
            }
        }
        return null;
    }

    /**
     * Simply runs solve on the original board passed into the constructor
     * @return Solved Sudoku board
     */
    public SudokuBoard solve() {
        return solve(board);
    }

    /**
     * Main method. Expects file location of sudoku board as the only argument.
     * @param args
     */
    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("Usage: java SudukoSolver <suduko file>");
            System.exit(1);
        }
        String filename = args[0];
        SudokuSolver solver = new SudokuSolver(filename);
        System.out.println("Original:");
        System.out.println(solver.getBoard());
        System.out.println("Solved:");
        System.out.println(solver.solve());
    }
}
