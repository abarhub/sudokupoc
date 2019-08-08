package org.sudoku.poc.sudokupoc;

import com.google.common.base.Preconditions;
import com.google.common.base.Verify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SudokuSolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(SudokuSolver.class);

    public Board resolve(Board board){
        Preconditions.checkNotNull(board);
        List<Board> liste = resolveTout(board);
        if(liste.isEmpty()) {
            LOGGER.info("il y a aucun sudoku");
            return null;
        } else {
            LOGGER.info("il y a {} sudoku",liste.size());
            int nb=1;
            for(Board b:liste){
                Verify.verify(b.isResolved(),"sudoku="+b);
                LOGGER.info("sudoku {}: {}",nb,b);
                nb++;
            }
            Set<Board> set=new HashSet<>(liste);
            LOGGER.info("sudoku nb : {}",set.size());
            if(liste.size()>=2){
                Board b,b2;
                b=liste.get(0);
                b2=liste.get(1);
                LOGGER.info("equals : {}",b.equals(b2));
            }

            return liste.get(0);
        }
    }

    public int nbSolution(Board board){
        Preconditions.checkNotNull(board);
        List<Board> liste = resolveTout(board);
        return liste.size();
    }

    public List<Board> resolveTout(Board board){
        Preconditions.checkNotNull(board);
        List<Board> listeBoard=new ArrayList<>();
        List<Position> positionList2=board.listePositionsNonAffecte();
        if(positionList2.isEmpty()){
            LOGGER.trace("resolveTout positionList2 empty : {}",board);
            listeBoard.add(board);
        } else {
            for (Position position : positionList2) {
                Set<Integer> set = valeursPossibles(board, position);
                if (set.isEmpty()) {
                    return new ArrayList<>();
                } else {
                    for (int val : set) {
                        Board board2 = new Board(board);
                        board2.set(position, val);
                        if(board2.isResolved()){
                            if(!listeBoard.contains(board2)) {
                                LOGGER.trace("resolveTout resolved : {} {}", board2, position);
                                listeBoard.add(board2);
                            }
                        } else {
                            List<Board> listeResultat = resolveTout(board2);
                            if (!listeResultat.isEmpty()) {
                                LOGGER.trace("resolveTout listeResultat : listeBoard={} listeResultat={}",listeBoard, listeResultat);
                                for(Board b:listeResultat) {
                                    if(!listeBoard.contains(b)) {
                                        listeBoard.add(b);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return  listeBoard;
    }

    public Set<Integer> valeursPossibles(Board board, Position position){
        Preconditions.checkNotNull(board);
        Preconditions.checkNotNull(position);
        Set<Integer> res=new TreeSet<>();
        for(int i=1;i<=9;i++){
            res.add(i);
        }
        for(int i=0;i<9;i++){
            int n=board.get(i,position.getColonne());
            if(n>0){
                res.remove(n);
            }
        }
        for(int i=0;i<9;i++){
            int n=board.get(position.getLigne(),i);
            if(n>0){
                res.remove(n);
            }
        }
        int debutLigne,finLigne;
        int debutColonne,finColonne;
        debutLigne=(position.getLigne()/3)*3;
        finLigne=((position.getLigne()/3)+1)*3;
        debutColonne=(position.getColonne()/3)*3;
        finColonne=((position.getColonne()/3)+1)*3;
        for(int i=debutLigne;i<finLigne;i++){
            for(int j=debutColonne;j<finColonne;j++){
                if(i!=position.getLigne()&&j!=position.getColonne()) {
                    int n = board.get(i,j);
                    if(n>0){
                        res.remove(n);
                    }
                }
            }
        }
        return res;
    }
}
