package org.sudoku.poc.sudokupoc.solver;

import com.google.common.base.Preconditions;
import com.google.common.base.Verify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.sudoku.poc.sudokupoc.Board;
import org.sudoku.poc.sudokupoc.Position;
import org.sudoku.poc.sudokupoc.util.PositionUtils;

import java.util.*;

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
        List<Board> liste= getSudokuAlgorithm().resolveTout(board,toutesSolutions);
//        if(!CollectionUtils.isEmpty(liste)){
//            nettoie_solution(liste);
//        }
        return liste;
    }

    private void nettoie_solution(List<Board> liste) {
        Preconditions.checkNotNull(liste);
        LOGGER.info("nb solution avant nettoyage: {}",liste.size());
        Iterator<Board> iter=liste.iterator();
        while(iter.hasNext()){
            Board board = iter.next();
            int nbNombreCache=calculNbCache(board);
            if(nbNombreCache<5){
                LOGGER.info("nb cache : {}",nbNombreCache);
                iter.remove();
            }
        }
        LOGGER.info("nb solution apres nettoyage: {}",liste.size());
    }

    private int calculNbCache(Board board) {
        int compteur=0;
        for(Position p: PositionUtils.listePositions()){
            if(!board.isSet(p)){
                compteur++;
            }
        }
        return compteur;
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
