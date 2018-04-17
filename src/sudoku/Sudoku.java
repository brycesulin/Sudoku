package sudoku;

import java.io.*;
import java.util.Scanner;

public class Sudoku {

    public static String filename;

    public static void main(String[] args) throws IOException {
        if (0 < args.length) {
            filename = args[0];
            File file = new File(filename);
            Scanner sc = new Scanner(file);
            PrintStream out = new PrintStream(new FileOutputStream(filename + ".solved"));
            System.setOut(out);

            int[][] sudoku = new int[9][9];

            while (sc.hasNext()) {
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {

                        sudoku[i][j] = sc.nextInt();
                    }
                }
            }
            new Sudoku(sudoku).solveSudoku();
        }
    }

    private int sudoku[][];
    private int n = 9;

    public Sudoku(int sudoku[][]) {
        this.sudoku = sudoku;
    }

    public void solveSudoku() throws FileNotFoundException {

        if (!backtrackSolveSudoku()) {
            System.out.println("Sudoku quiz can't be solved.");
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(sudoku[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean canWeInsertX(int i, int j, int x) {

        // x used in Row?
        for (int jj = 0; jj < n; jj++) {
            if (sudoku[i][jj] == x) {
                return false;
            }
        }

        // x used in Column?
        for (int ii = 0; ii < n; ii++) {
            if (sudoku[ii][j] == x) {
                return false;
            }
        }

        // x used in sudoku 3x3 table?
        int tableRow = i - i % 3;
        int tableColumn = j - j % 3;

        for (int ii = 0; ii < 3; ii++) {
            for (int jj = 0; jj < 3; jj++) {
                if (sudoku[tableRow + ii][tableColumn + jj] == x) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean backtrackSolveSudoku() {
        int i = 0, j = 0;
        boolean emptyCell = false;

        for (int ii = 0; ii < n && !emptyCell; ii++) {
            for (int jj = 0; jj < n && !emptyCell; jj++) {
                if (sudoku[ii][jj] == 0) {
                    emptyCell = true;
                    i = ii;
                    j = jj;
                }
            }
        }

        if (!emptyCell) {
            return true;
        }

        for (int x = 1; x < 10; x++) {

            if (canWeInsertX(i, j, x)) {
                sudoku[i][j] = x;

                if (backtrackSolveSudoku()) {
                    return true;
                }
                sudoku[i][j] = 0;
            }
        }
        return false;
    }
}