package org.sudoku.poc.sudokupoc.gui;

import com.google.common.base.Preconditions;
import org.sudoku.poc.sudokupoc.Board;
import org.sudoku.poc.sudokupoc.Constants;
import org.sudoku.poc.sudokupoc.Position;
import org.sudoku.poc.sudokupoc.util.PositionUtils;

import java.util.HashMap;
import java.util.Map;

public class GuiModel {

    private final Map<Position,Cell> board;

    public GuiModel(Board board) {
        Preconditions.checkNotNull(board);
        this.board=new HashMap<>();
        for(Position p: PositionUtils.listePositions()){
            boolean fix=board.isSet(p);
            int valeur;
            if(fix){
                valeur=board.get(p);
            } else {
                valeur=board.getSolution().get(p);
            }
            this.board.put(p,new Cell(fix,valeur,fix));
        }
    }

    public Cell get(Position position){
        Preconditions.checkNotNull(position);
        Preconditions.checkArgument(board.containsKey(position));
        return board.get(position);
    }
    @Override
    public String toString() {
        return "GuiModel{" +
                "board=" + board +
                '}';
    }
}
