package org.sudoku.poc.sudokupoc.solver;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudoku.poc.sudokupoc.Board;
import org.sudoku.poc.sudokupoc.Position;
import org.sudoku.poc.sudokupoc.solver.algox.ExactCover;
import org.sudoku.poc.sudokupoc.util.ConvertBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SudokuFastAlgorithmSolver implements SudokuAlgorithm {

    private static final Logger LOGGER = LoggerFactory.getLogger(SudokuFastAlgorithmSolver.class);

    @Override
    public List<Board> resolveTout(Board board, boolean toutesSolutions) {

        int[][] tab=new int[9][9];

        tab=ConvertBoard.convertBoard(board);

        ExactCover exactCover=new ExactCover();
        List<int[][]> res = exactCover.solve(tab);

        List<Board> list=new ArrayList<>();

        if(res!=null&&!res.isEmpty()){
            for(int[][] tab2:res){
                list.add(ConvertBoard.convertTab(tab2));
            }
        }

        return list;
    }

    @Override
    public Set<Integer> valeursPossibles(Board board, Position position) {
        Preconditions.checkNotNull(board);
        Preconditions.checkNotNull(position);
        Set<Integer> res=new TreeSet<>();
        for(int i=1;i<=9;i++){
            res.add(i);
        }
        if(!res.isEmpty()) {
            for (int i = 0; i < 9; i++) {
                int n = board.get(i, position.getColonne());
                if (n > 0) {
                    res.remove(n);
                }
                if (res.isEmpty()) {
                    break;
                }
            }
        }
        if(!res.isEmpty()) {
            for (int i = 0; i < 9; i++) {
                int n = board.get(position.getLigne(), i);
                if (n > 0) {
                    res.remove(n);
                }
                if(res.isEmpty()) {
                    break;
                }
            }
        }
        if(!res.isEmpty()) {
            int debutLigne, finLigne;
            int debutColonne, finColonne;
            debutLigne = (position.getLigne() / 3) * 3;
            finLigne = ((position.getLigne() / 3) + 1) * 3;
            debutColonne = (position.getColonne() / 3) * 3;
            finColonne = ((position.getColonne() / 3) + 1) * 3;
            for (int i = debutLigne; i < finLigne; i++) {
                for (int j = debutColonne; j < finColonne; j++) {
                    if (i != position.getLigne() && j != position.getColonne()) {
                        int n = board.get(i, j);
                        if (n > 0) {
                            res.remove(n);
                        }
                    }
                    if(res.isEmpty()) {
                        break;
                    }
                }
                if(res.isEmpty()) {
                    break;
                }
            }
        }
        return res;
    }
}
