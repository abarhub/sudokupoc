package org.sudoku.poc.sudokupoc.solver.algox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DancingLinks {

    private static final Logger LOGGER = LoggerFactory.getLogger(DancingLinks.class);

    private final int size = 9;

    private ColumnNode header;
    private List<DancingNode> answer;
    private List<int [][]> result=new ArrayList<>();

    private void search(int k) {
        if (header.getRight() == header) {
            handleSolution(answer);
        } else {
            ColumnNode c = selectColumnNodeHeuristic();
            c.cover();

            for (DancingNode r = c.getDown(); r != c; r = r.getDown()) {
                answer.add(r);

                for (DancingNode j = r.getRight(); j != r; j = j.getRight()) {
                    j.getColumn().cover();
                }

                search(k + 1);

                r = answer.remove(answer.size() - 1);
                c = r.getColumn();

                for (DancingNode j = r.getLeft(); j != r; j = j.getLeft()) {
                    j.getColumn().uncover();
                }
            }
            c.uncover();
        }
    }

    private ColumnNode selectColumnNodeHeuristic() {
        int min = Integer.MAX_VALUE;
        ColumnNode ret = null;
        for (ColumnNode c = (ColumnNode) header.getRight(); c != header; c = (ColumnNode) c.getRight()) {
            if (c.getSize() < min) {
                min = c.getSize();
                ret = c;
            }
        }
        return ret;
    }

    private ColumnNode makeDLXBoard(boolean[][] grid) {
        final int COLS = grid[0].length;

        ColumnNode headerNode = new ColumnNode("header");
        List<ColumnNode> columnNodes = new ArrayList<>();

        for (int i = 0; i < COLS; i++) {
            ColumnNode n = new ColumnNode(Integer.toString(i));
            columnNodes.add(n);
            headerNode = (ColumnNode) headerNode.hookRight(n);
        }
        headerNode = headerNode.getRight().getColumn();

        for (boolean[] aGrid : grid) {
            DancingNode prev = null;
            for (int j = 0; j < COLS; j++) {
                if (aGrid[j]) {
                    ColumnNode col = columnNodes.get(j);
                    DancingNode newNode = new DancingNode(col);
                    if (prev == null)
                        prev = newNode;
                    col.getUp().hookDown(newNode);
                    prev = prev.hookRight(newNode);
                    col.setSize(col.getSize() + 1);
                }
            }
        }

        headerNode.setSize(COLS);

        return headerNode;
    }

    DancingLinks(boolean[][] cover) {
        header = makeDLXBoard(cover);
    }

    public void runSolver() {
        answer = new LinkedList<>();
        search(0);
    }

    private void handleSolution(List<DancingNode> answer) {
        int[][] result = parseBoard(answer);
        this.result.add(result);
        printSolution(result);
    }


    private int[][] parseBoard(List<DancingNode> answer) {
        int[][] result = new int[size][size];
        for (DancingNode n : answer) {
            DancingNode rcNode = n;
            int min = Integer.parseInt(rcNode.getColumn().getName());
            for (DancingNode tmp = n.getRight(); tmp != n; tmp = tmp.getRight()) {
                int val = Integer.parseInt(tmp.getColumn().getName());
                if (val < min) {
                    min = val;
                    rcNode = tmp;
                }
            }
            int ans1 = Integer.parseInt(rcNode.getColumn().getName());
            int ans2 = Integer.parseInt(rcNode.getRight().getColumn().getName());
            int r = ans1 / size;
            int c = ans1 % size;
            int num = (ans2 % size) + 1;
            result[r][c] = num;
        }
        return result;
    }

    private void printSolution(int[][] result) {
        if(LOGGER.isDebugEnabled()) {
            int size = result.length;
            StringBuilder stringBuilder = new StringBuilder();
            for (int[] aResult : result) {
                StringBuilder ret = new StringBuilder();
                for (int j = 0; j < size; j++) {
                    ret.append(aResult[j]).append(" ");
                }
                //System.out.println(ret);
                stringBuilder.append(ret);
                stringBuilder.append('\n');
            }
            //System.out.println();
            stringBuilder.append('\n');
            LOGGER.debug(stringBuilder.toString());
        }
    }

    public List<int[][]> getResult() {
        return result;
    }
}
