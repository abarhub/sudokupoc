package org.sudoku.poc.sudokupoc.util;

import com.google.common.base.Preconditions;
import org.sudoku.poc.sudokupoc.Board;
import org.sudoku.poc.sudokupoc.Position;

public class ConvertBoard {

    private static final int SIZE=9;

    public static final int[][] convertBoard(Board board){
        Preconditions.checkNotNull(board);
        int[][] tab=new int[SIZE][SIZE];

        for(int i=0;i<SIZE;i++){
            for(int j=0;j<SIZE;j++){
                Position p=new Position(i,j);
                if(board.isSet(p)) {
                    tab[i][j] = board.get(p);
                } else {
                    tab[i][j]=0;
                }
            }
        }

        return tab;
    }

    public static final Board convertTab(int[][] tab){
        Preconditions.checkNotNull(tab);
        Preconditions.checkArgument(tab.length==SIZE);
        Board board=new Board();

        for(int i=0;i<SIZE;i++) {
            Preconditions.checkArgument(tab[i].length==SIZE);
            for (int j = 0; j < SIZE; j++) {
                Position p=new Position(i,j);
                if(tab[i][j]==0){
                    board.unset(p);
                } else {
                    board.set(p,tab[i][j]);
                }
            }
        }

        return board;
    }

}
