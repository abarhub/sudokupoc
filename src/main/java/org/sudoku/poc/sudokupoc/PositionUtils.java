package org.sudoku.poc.sudokupoc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PositionUtils {

    public static Iterator<Position> iterator(){
        List<Position> liste=new ArrayList<>();
        for(int ligne=0;ligne<9;ligne++){
            for(int colonne=0;colonne<9;colonne++){
                liste.add(new Position(ligne,colonne));
            }
        }
        return liste.iterator();
    }

    public static List<Position> listePositions(){
        List<Position> liste=new ArrayList<>();
        for(int ligne=0;ligne<9;ligne++){
            for(int colonne=0;colonne<9;colonne++){
                liste.add(new Position(ligne,colonne));
            }
        }
        return liste;
    }


}
