package org.sudoku.poc.sudokupoc.solver.algox;

public class ColumnNode extends DancingNode {

    private int size;
    private String name;

    ColumnNode(String n) {
        super();
        setSize(0);
        setName(n);
        setColumn(this);
    }

    void cover() {
        unlinkLR();
        for (DancingNode i = this.getDown(); i != this; i = i.getDown()) {
            for (DancingNode j = i.getRight(); j != i; j = j.getRight()) {
                j.unlinkUD();
                j.getColumn().decrSize();
            }
        }
    }

    void uncover() {
        for (DancingNode i = this.getUp(); i != this; i = i.getUp()) {
            for (DancingNode j = i.getLeft(); j != i; j = j.getLeft()) {
                j.getColumn().incrSize();
                j.relinkUD();
            }
        }
        relinkLR();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void incrSize(){
        this.size++;
    }

    public void decrSize(){
        this.size--;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
