package org.sudoku.poc.sudokupoc.util;

import org.sudoku.poc.sudokupoc.Constants;
import org.sudoku.poc.sudokupoc.Position;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PositionUtils {

    //private static final

    public static Iterator<Position> iterator(){
        return listePositions().iterator();
    }

    public static List<Position> listePositions(){
        List<Position> liste=new ArrayList<>();
        for(int ligne=0;ligne<Constants.NB_LIGNES;ligne++){
            for(int colonne=0;colonne<Constants.NB_COLONNES;colonne++){
                liste.add(getPosition(ligne,colonne));
            }
        }
        return liste;
    }

    public static Position getPosition(int ligne, int colonne){
        return new Position(ligne,colonne);
    }

}
