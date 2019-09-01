package org.sudoku.poc.sudokupoc.solver;

import com.google.common.base.Preconditions;
import com.google.common.base.Verify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudoku.poc.sudokupoc.Board;
import org.sudoku.poc.sudokupoc.Position;

import java.util.*;

public class SudokuBasicSolver implements SudokuAlgorithm {

    private static final Logger LOGGER = LoggerFactory.getLogger(SudokuBasicSolver.class);

    @Override
    public List<Board> resolveTout(Board board, boolean toutesSolutions){
        Preconditions.checkNotNull(board);
        List<Board> listeBoard=new ArrayList<>();
        List<Position> positionList2=board.listePositionsNonAffecte();
        if(positionList2.isEmpty()){
            LOGGER.trace("resolveTout positionList2 empty : {}",board);
            listeBoard.add(new Board(board,true));
        } else {
            for (Position position : positionList2) {
                Set<Integer> set = valeursPossibles(board, position);
                if (set.isEmpty()) {
                    return new ArrayList<>();
                } else {
                    for (int val : set) {
                        //Board board2 = new Board(board);
                        Board board2 =board;
                        board2.set(position, val);
                        if(board2.isResolved()){
                            if(!listeBoard.contains(board2)) {
                                LOGGER.trace("resolveTout resolved : {} {}", board2, position);
                                listeBoard.add(new Board(board2, true));
                            }
                        } else {
                            List<Board> listeResultat = resolveTout(board2, toutesSolutions);
                            if (!listeResultat.isEmpty()) {
                                LOGGER.trace("resolveTout listeResultat : listeBoard={} listeResultat={}",listeBoard, listeResultat);
                                for(Board b:listeResultat) {
                                    if(!listeBoard.contains(b)) {
                                        listeBoard.add(b);
                                    }
                                }
                            }
                        }
                        board2.unset(position);
                        if(!toutesSolutions){
                            if(listeBoard.size()>1){
                                break;
                            }
                        }
                    }
                }
            }
        }
        return  listeBoard;
    }

    @Override
    public Set<Integer> valeursPossibles(Board board, Position position){
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
