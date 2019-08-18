package org.sudoku.poc.sudokupoc.solver;

import com.google.common.base.Preconditions;
import com.google.common.base.Verify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudoku.poc.sudokupoc.Board;
import org.sudoku.poc.sudokupoc.Position;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SudokuSolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(SudokuSolver.class);

    public Board resolve(Board board){
        Preconditions.checkNotNull(board);
        List<Board> liste = resolveTout(board,true);
        if(liste.isEmpty()) {
            LOGGER.info("il y a aucun sudoku");
            return null;
        } else {
            LOGGER.info("il y a {} sudoku", liste.size());
            int nb = 1;
            for (Board b : liste) {
                Verify.verify(b.isResolved(), "sudoku=" + b);
                LOGGER.info("sudoku {}: {}", nb, b);
                nb++;
            }
            Set<Board> set = new HashSet<>(liste);
            LOGGER.info("sudoku nb : {}", set.size());
            if (liste.size() >= 2) {
                Board b, b2;
                b = liste.get(0);
                b2 = liste.get(1);
                LOGGER.info("equals : {}", b.equals(b2));
            }

            return liste.get(0);
        }
    }

    public int nbSolution(Board board){
            Preconditions.checkNotNull(board);
            List<Board> liste = resolveTout(board,false);
            return liste.size();
    }

    public List<Board> resolveTout(Board board, boolean toutesSolutions){
        return getSudokuAlgorithm().resolveTout(board,toutesSolutions);
    }

    public Set<Integer> valeursPossibles(Board board, Position position){
        return getSudokuAlgorithm().valeursPossibles(board,position);
    }

    private SudokuAlgorithm getSudokuAlgorithm(){
        int no;
        no=1;
        no=2;

        if(no==2){
            return new SudokuFastAlgorithmSolver();
        } else {
            return new SudokuBasicSolver();
        }
    }
}
