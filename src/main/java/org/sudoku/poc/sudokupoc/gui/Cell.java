package org.sudoku.poc.sudokupoc.gui;

public class Cell {

    private final boolean fixe;
    private final int valeur;
    private boolean visible;
    private int valeurAffecte;

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

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getValeurAffecte() {
        return valeurAffecte;
    }

    public void setValeurAffecte(int valeurAffecte) {
        this.valeurAffecte = valeurAffecte;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "fixe=" + fixe +
                ", valeur=" + valeur +
                ", visible=" + visible +
                ", valeurAffecte=" + valeurAffecte +
                '}';
    }
}
