package org.sudoku.poc.sudokupoc;

import com.google.common.base.Preconditions;
import org.sudoku.poc.sudokupoc.util.PositionUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    private final int tab[][];
    private Instant lasteUpdate;
    private Instant lastIsResolvedInstant;
    private boolean lastIsResolvedValue;

    public Board() {
        tab=createTab();
    }

    public Board(Board board){
        Preconditions.checkNotNull(board);
        tab=createTab();
        for(int i=0;i<tab.length;i++){
            for(int j=0;j<tab[i].length;j++){
                tab[i][j]=board.tab[i][j];
            }
        }
    }

    private int[][] createTab() {
        return new int[9][9];
    }

    public int get(int ligne,int colonne){
        Preconditions.checkElementIndex(ligne,Constants.NB_LIGNES);
        Preconditions.checkElementIndex(colonne,Constants.NB_COLONNES);
        return get(PositionUtils.getPosition(ligne,colonne));
    }

    public int get(Position position){
        Preconditions.checkNotNull(position);
        return tab[position.getLigne()][position.getColonne()];
    }

    public void set(int ligne,int colonne, int val){
        Preconditions.checkElementIndex(ligne,Constants.NB_LIGNES);
        Preconditions.checkElementIndex(colonne,Constants.NB_COLONNES);
        Preconditions.checkArgument(val>=1);
        Preconditions.checkArgument(val<=9);
        set(PositionUtils.getPosition(ligne,colonne),val);
    }

    public void set(Position position, int val){
        Preconditions.checkNotNull(position);
        Preconditions.checkArgument(val>=1);
        Preconditions.checkArgument(val<=9);
        tab[position.getLigne()][position.getColonne()]=val;
        lasteUpdate=Instant.now();
    }

    public void unset(int ligne,int colonne){
        Preconditions.checkElementIndex(ligne,Constants.NB_LIGNES);
        Preconditions.checkElementIndex(colonne,Constants.NB_COLONNES);
        unset(PositionUtils.getPosition(ligne,colonne));
    }

    public void unset(Position position){
        Preconditions.checkNotNull(position);
        tab[position.getLigne()][position.getColonne()]=0;
        lasteUpdate=Instant.now();
    }

    public boolean isSet(int ligne,int colonne){
        Preconditions.checkElementIndex(ligne,Constants.NB_LIGNES);
        Preconditions.checkElementIndex(colonne,Constants.NB_COLONNES);
        return tab[ligne][colonne]!=0;
    }

    public boolean isSet(Position position){
        Preconditions.checkNotNull(position);
        return tab[position.getLigne()][position.getColonne()]!=0;
    }

    public String toString(){
        StringBuilder res=new StringBuilder();
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                res.append(tab[i][j]);
            }
            res.append('\n');
        }
        return res.toString();
    }

    public boolean isResolved(){
        if(lasteUpdate!=null&&lastIsResolvedInstant!=null){
            if(lastIsResolvedInstant.isAfter(lasteUpdate)){
                return lastIsResolvedValue;
            }
        }
        boolean res=true;
        for(int i=0;i<Constants.NB_LIGNES;i++) {
            for (int j = 0; j < Constants.NB_COLONNES; j++) {
                if(!isSet(PositionUtils.getPosition(i,j))){
                    res=false;
                }
            }
        }

        lastIsResolvedInstant=Instant.now();
        lastIsResolvedValue=res;
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        for(int i=0;i<tab.length;i++){
            if(!Arrays.equals(tab[i],board.tab[i])){
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int res=0;
        for(int i=0;i<tab.length;i++){
            res+=Arrays.hashCode(tab[i]);
        }
        return res;
    }

    public List<Position> listePositionsNonAffecte() {
        List<Position> liste = new ArrayList<>();
        for (int ligne = 0; ligne < Constants.NB_LIGNES; ligne++) {
            for (int colonne = 0; colonne < Constants.NB_COLONNES; colonne++) {
                Position p=PositionUtils.getPosition(ligne, colonne);
                if(!isSet(p)) {
                    liste.add(p);
                }
            }
        }
        return liste;
    }


    public List<Position> listePositionsAffecte() {
        List<Position> liste = new ArrayList<>();
        for (int ligne = 0; ligne < Constants.NB_LIGNES; ligne++) {
            for (int colonne = 0; colonne < Constants.NB_COLONNES; colonne++) {
                Position p=PositionUtils.getPosition(ligne, colonne);
                if(isSet(p)) {
                    liste.add(p);
                }
            }
        }
        return liste;
    }
}
