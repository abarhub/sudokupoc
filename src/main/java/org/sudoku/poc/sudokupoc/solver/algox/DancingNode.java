package org.sudoku.poc.sudokupoc.solver.algox;

public class DancingNode {

    private DancingNode left;
    private DancingNode right;
    private DancingNode up;
    private DancingNode down;
    private ColumnNode column;

    public DancingNode hookDown(DancingNode node) {
        assert (this.getColumn() == node.getColumn());
        node.setDown(this.getDown());
        node.getDown().setUp(node);
        node.setUp(this);
        this.setDown(node);
        return node;
    }

    public DancingNode hookRight(DancingNode node) {
        node.setRight(this.getRight());
        node.getRight().setLeft(node);
        node.setLeft(this);
        this.setRight(node);
        return node;
    }

    void unlinkLR() {
        this.getLeft().setRight(this.getRight());
        this.getRight().setLeft(this.getLeft());
    }

    void relinkLR() {
        //this.L.R = this.R.L = this;
        this.getLeft().setRight(this);
        this.getRight().setLeft(this);
    }

    void unlinkUD() {
        this.getUp().setDown(this.getDown());
        this.getDown().setUp(this.getUp());
    }

    void relinkUD() {
        //this.U.D = this.D.U = this;
        this.getUp().setDown(this);
        this.getDown().setUp(this);
    }

    DancingNode() {
        //L = R = U = D = this;
        setLeft(this);
        setRight(this);
        setUp(this);
        setDown(this);
    }

    DancingNode(ColumnNode column) {
        this();
        setColumn(column);
    }

    public DancingNode getLeft() {
        return left;
    }

    public void setLeft(DancingNode left) {
        this.left = left;
    }

    public DancingNode getRight() {
        return right;
    }

    public void setRight(DancingNode right) {
        this.right = right;
    }

    public DancingNode getUp() {
        return up;
    }

    public void setUp(DancingNode up) {
        this.up = up;
    }

    public DancingNode getDown() {
        return down;
    }

    public void setDown(DancingNode down) {
        this.down = down;
    }

    public ColumnNode getColumn() {
        return column;
    }

    public void setColumn(ColumnNode column) {
        this.column = column;
    }
}
