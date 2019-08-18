package org.sudoku.poc.sudokupoc.util;

import org.sudoku.poc.sudokupoc.Position;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PositionUtils {

    //private static final

    public static Iterator<Position> iterator(){
        List<Position> liste=new ArrayList<>();
        for(int ligne=0;ligne<9;ligne++){
            for(int colonne=0;colonne<9;colonne++){
                liste.add(getPosition(ligne,colonne));
            }
        }
        return liste.iterator();
    }

    public static List<Position> listePositions(){
        List<Position> liste=new ArrayList<>();
        for(int ligne=0;ligne<9;ligne++){
            for(int colonne=0;colonne<9;colonne++){
                liste.add(getPosition(ligne,colonne));
            }
        }
        return liste;
    }

    public static Position getPosition(int ligne, int colonne){
        return new Position(ligne,colonne);
    }

}
