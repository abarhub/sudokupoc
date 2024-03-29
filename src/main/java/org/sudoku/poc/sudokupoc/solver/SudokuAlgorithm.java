package org.sudoku.poc.sudokupoc.solver;

import org.sudoku.poc.sudokupoc.Board;
import org.sudoku.poc.sudokupoc.Position;

import java.util.List;
import java.util.Set;

public interface SudokuAlgorithm {

    List<Board> resolveTout(Board board, boolean toutesSolutions);

    Set<Integer> valeursPossibles(Board board, Position position);
}
