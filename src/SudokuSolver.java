import java.util.ArrayList;
import java.util.List;

public class SudokuSolver {

    private SudokuBoard board;

    public SudokuSolver(String filename) {
        this.board = new SudokuBoard(filename);
    }

    public SudokuBoard getBoard() {
        return board;
    }

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

    public SudokuBoard solve(SudokuBoard original) {
        if(original.getEmptyCoord() == null)
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
        System.out.println(solver.solve(solver.getBoard()));
    }
}
