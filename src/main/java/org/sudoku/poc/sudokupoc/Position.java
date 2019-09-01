package org.sudoku.poc.sudokupoc;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Position {
    private final int ligne;
    private final int colonne;

    public Position(int ligne, int colonne) {
        Preconditions.checkElementIndex(ligne,Constants.NB_LIGNES,"ligne");
        Preconditions.checkElementIndex(colonne,Constants.NB_COLONNES,"colonne");
        this.ligne = ligne;
        this.colonne = colonne;
    }

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }

    @Override
    public String toString() {
        return "(" +ligne +',' + colonne +')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return ligne == position.ligne &&
                colonne == position.colonne;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ligne, colonne);
    }

}
