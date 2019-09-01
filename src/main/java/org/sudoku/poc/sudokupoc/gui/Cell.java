package org.sudoku.poc.sudokupoc.gui;

public class Cell {

    private final boolean fixe;
    private final int valeur;
    private final boolean visible;

    public Cell(boolean fixe, int valeur, boolean valeurVisible) {
        this.fixe = fixe;
        this.valeur = valeur;
        this.visible = valeurVisible;
    }

    public boolean isFixe() {
        return fixe;
    }

    public int getValeur() {
        return valeur;
    }

    public boolean isVisible() {
        return visible;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "fixe=" + fixe +
                ", valeur=" + valeur +
                ", visible=" + visible +
                '}';
    }
}
