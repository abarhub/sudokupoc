package org.sudoku.poc.sudokupoc;

import com.google.common.base.Preconditions;

public class Board {

    private int tab[][];

    public Board() {
        tab=createTab();
    }
    private int[][] createTab() {
        return new int[10][10];
    }

    public int get(int ligne,int colonne){
        Preconditions.checkArgument(ligne>=0);
        Preconditions.checkArgument(ligne<9);
        Preconditions.checkArgument(colonne>=0);
        Preconditions.checkArgument(colonne<9);
        return tab[ligne][colonne];
    }

    public void set(int ligne,int colonne, int val){
        Preconditions.checkArgument(ligne>=0);
        Preconditions.checkArgument(ligne<9);
        Preconditions.checkArgument(colonne>=0);
        Preconditions.checkArgument(colonne<9);
        Preconditions.checkArgument(val>=1);
        Preconditions.checkArgument(val<=9);
        tab[ligne][colonne]=val;
    }

    public void unset(int ligne,int colonne){
        Preconditions.checkArgument(ligne>=0);
        Preconditions.checkArgument(ligne<9);
        Preconditions.checkArgument(colonne>=0);
        Preconditions.checkArgument(colonne<9);
        tab[ligne][colonne]=0;
    }

    public boolean isSet(int ligne,int colonne){
        return tab[ligne][colonne]==0;
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
}
