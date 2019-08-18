package org.sudoku.poc.sudokupoc.solver.algox;

import org.sudoku.poc.sudokupoc.Board;
import org.sudoku.poc.sudokupoc.util.ConvertBoard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExactCover {

    private static int BOARD_SIZE = 9;
    private static int SUBSECTION_SIZE = 3;
    private static int NO_VALUE = 0;
    private static int CONSTRAINTS = 4;
    private static int MIN_VALUE = 1;
    private static int MAX_VALUE = 9;
    private static int COVER_START_INDEX = 1;

    public List<int[][]> solve(int[][] board) {
        boolean[][] cover = initializeExactCoverBoard(board);
        DancingLinks dlx = new DancingLinks(cover);
        dlx.runSolver();
        return dlx.getResult();
    }

    public List<Board> solve(Board board) {
        int[][] boardTab= ConvertBoard.convertBoard(board);
        boolean[][] cover = initializeExactCoverBoard(boardTab);
        DancingLinks dlx = new DancingLinks(cover);
        dlx.runSolver();
        List<int[][]> res=dlx.getResult();
        List<Board> liste=new ArrayList<>();
        if(res!=null&&!res.isEmpty()){
            for(int[][] tabRes:res){
                liste.add(ConvertBoard.convertTab(tabRes));
            }
        }
        return liste;
    }

    private int getIndex(int row, int column, int num) {
        return (row - 1) * BOARD_SIZE * BOARD_SIZE
                + (column - 1) * BOARD_SIZE + (num - 1);
    }

    private boolean[][] createExactCoverBoard() {
        boolean[][] coverBoard = new boolean
                [BOARD_SIZE * BOARD_SIZE * MAX_VALUE]
                [BOARD_SIZE * BOARD_SIZE * CONSTRAINTS];

        int hBase = 0;
        hBase = checkCellConstraint(coverBoard, hBase);
        hBase = checkRowConstraint(coverBoard, hBase);
        hBase = checkColumnConstraint(coverBoard, hBase);
        checkSubsectionConstraint(coverBoard, hBase);

        return coverBoard;
    }

    private int checkSubsectionConstraint(boolean[][] coverBoard, int hBase) {
        for (int row = COVER_START_INDEX; row <= BOARD_SIZE; row += SUBSECTION_SIZE) {
            for (int column = COVER_START_INDEX; column <= BOARD_SIZE; column += SUBSECTION_SIZE) {
                for (int n = COVER_START_INDEX; n <= BOARD_SIZE; n++, hBase++) {
                    for (int rowDelta = 0; rowDelta < SUBSECTION_SIZE; rowDelta++) {
                        for (int columnDelta = 0; columnDelta < SUBSECTION_SIZE; columnDelta++) {
                            int index = getIndex(row + rowDelta, column + columnDelta, n);
                            coverBoard[index][hBase] = true;
                        }
                    }
                }
            }
        }
        return hBase;
    }

    private int checkColumnConstraint(boolean[][] coverBoard, int hBase) {
        for (int column = COVER_START_INDEX; column <= BOARD_SIZE; column++) {
            for (int n = COVER_START_INDEX; n <= BOARD_SIZE; n++, hBase++) {
                for (int row = COVER_START_INDEX; row <= BOARD_SIZE; row++) {
                    int index = getIndex(row, column, n);
                    coverBoard[index][hBase] = true;
                }
            }
        }
        return hBase;
    }

    private int checkRowConstraint(boolean[][] coverBoard, int hBase) {
        for (int row = COVER_START_INDEX; row <= BOARD_SIZE; row++) {
            for (int n = COVER_START_INDEX; n <= BOARD_SIZE; n++, hBase++) {
                for (int column = COVER_START_INDEX; column <= BOARD_SIZE; column++) {
                    int index = getIndex(row, column, n);
                    coverBoard[index][hBase] = true;
                }
            }
        }
        return hBase;
    }

    private int checkCellConstraint(boolean[][] coverBoard, int hBase) {
        for (int row = COVER_START_INDEX; row <= BOARD_SIZE; row++) {
            for (int column = COVER_START_INDEX; column <= BOARD_SIZE; column++, hBase++) {
                for (int n = COVER_START_INDEX; n <= BOARD_SIZE; n++) {
                    int index = getIndex(row, column, n);
                    coverBoard[index][hBase] = true;
                }
            }
        }
        return hBase;
    }

    private boolean[][] initializeExactCoverBoard(int[][] board) {
        boolean[][] coverBoard = createExactCoverBoard();
        for (int row = COVER_START_INDEX; row <= BOARD_SIZE; row++) {
            for (int column = COVER_START_INDEX; column <= BOARD_SIZE; column++) {
                int n = board[row - 1][column - 1];
                if (n != NO_VALUE) {
                    for (int num = MIN_VALUE; num <= MAX_VALUE; num++) {
                        if (num != n) {
                            Arrays.fill(coverBoard[getIndex(row, column, num)], false);
                        }
                    }
                }
            }
        }
        return coverBoard;
    }
}
