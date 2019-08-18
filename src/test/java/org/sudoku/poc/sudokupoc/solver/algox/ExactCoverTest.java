package org.sudoku.poc.sudokupoc.solver.algox;

import com.google.common.collect.Streams;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudoku.poc.sudokupoc.Board;
import org.sudoku.poc.sudokupoc.Position;
import org.sudoku.poc.sudokupoc.solver.SudokuFastAlgorithmSolver;
import org.sudoku.poc.sudokupoc.util.ConvertBoard;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ExactCoverTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExactCoverTest.class);
    public static final int SIZE_SUDOKU = 9;

    @Test
    void solve() {

        int[][] board = {
                {8, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 6, 0, 0, 0, 0, 0},
                {0, 7, 0, 0, 9, 0, 2, 0, 0},
                {0, 5, 0, 0, 0, 7, 0, 0, 0},
                {0, 0, 0, 0, 4, 5, 7, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 3, 0},
                {0, 0, 1, 0, 0, 0, 0, 6, 8},
                {0, 0, 8, 5, 0, 0, 0, 1, 0},
                {0, 9, 0, 0, 0, 0, 4, 0, 0}
        };

        ExactCover exactCover=new ExactCover();
        List<int[][]> res = exactCover.solve(board);
        LOGGER.info("res={}",res);

        assertNotNull(res);
        assertEquals(1,res.size());

        int[][] boardRef = {
                {8,1,2,7,5,3,6,4,9},
                {9,4,3,6,8,2,1,7,5},
                {6,7,5,4,9,1,2,8,3},
                {1,5,4,2,3,7,8,9,6},
                {3,6,9,8,4,5,7,2,1},
                {2,8,7,1,6,9,5,3,4},
                {5,2,1,9,7,4,3,6,8},
                {4,3,8,5,2,6,9,1,7},
                {7,9,6,3,1,8,4,5,2},
        };

        int[][] resultat = res.get(0);
        assertEquals(SIZE_SUDOKU,resultat.length);
        egal(boardRef,resultat);
        solutionValide(board,resultat);
    }

    @Test
    void testSolve() {

        int[][] board = {
                {2, 0, 0, 0, 0, 9, 3, 0, 6},
                {0, 0, 0, 0, 6, 7, 0, 1, 0},
                {3, 0, 6, 1, 2, 8, 4, 0, 7},
                {1, 0, 0, 9, 0, 5, 6, 0, 0},
                {0, 4, 0, 0, 0, 0, 0, 7, 0},
                {0, 0, 7, 2, 0, 1, 0, 0, 3},
                {6, 0, 3, 7, 8, 4, 9, 0, 5},
                {0, 2, 0, 5, 1, 0, 0, 0, 0},
                {4, 0, 8, 6, 0, 0, 0, 0, 1}
        };

        Board board2= ConvertBoard.convertTab(board);

        ExactCover exactCover=new ExactCover();
        List<Board> res = exactCover.solve(board2);
        LOGGER.info("res={}",res);

        assertNotNull(res);
        assertEquals(1,res.size());

        int[][] boardRef = {
                {2,7,1,4,5,9,3,8,6},
                {5,8,4,3,6,7,2,1,9},
                {3,9,6,1,2,8,4,5,7},
                {1,3,2,9,7,5,6,4,8},
                {9,4,5,8,3,6,1,7,2},
                {8,6,7,2,4,1,5,9,3},
                {6,1,3,7,8,4,9,2,5},
                {7,2,9,5,1,3,8,6,4},
                {4,5,8,6,9,2,7,3,1},
        };

        Board resultat = res.get(0);

        int[][] resultat2=ConvertBoard.convertBoard(resultat);
        assertEquals(SIZE_SUDOKU,resultat2.length);
        egal(boardRef,resultat2);
        solutionValide(board,resultat2);
    }

    // methodes utilitaires

    private void egal(int[][] tab,int[][]tab2){
        if(tab==null){
            assertNull(tab2);
        } else {
            assertAll(
                    () -> assertNotNull(tab2),
                    () -> assertEquals(tab.length,tab2.length))
            ;

            for(int i=0;i<tab.length;i++){
                assertEquals(tab[i].length,tab2[i].length);
                for(int j=0;j<tab[i].length;j++){

                    assertEquals(tab[i][j],tab2[i][j]);
                }
            }
        }
    }

    private void solutionValide(int[][] boardAResoudre, int[][] resultat) {
        assertNotNull(boardAResoudre);
        assertNotNull(resultat);
        assertAll(
                ()-> assertEquals(SIZE_SUDOKU,boardAResoudre.length),
                ()-> assertEquals(SIZE_SUDOKU,resultat.length)
        );

        // vérification si les cases non nulles sont identiques
        for(int i=0;i<SIZE_SUDOKU;i++){
            for(int j=0;j<SIZE_SUDOKU;j++){
                int val=boardAResoudre[i][j];
                if(val==0) {
                    // on ne teste pas
                } else if(val>0&&val<=9){
                    assertEquals(val,resultat[i][j]);
                } else {
                    fail("valeur invalide:"+val);
                }
            }
        }

        // vérification des lignes et des coclonnes
        Map<Integer, Set<Integer>> lignes=new HashMap<>();
        Map<Integer, Set<Integer>> colonnes=new HashMap<>();

        for(int i=0;i<SIZE_SUDOKU;i++) {
            assertEquals(SIZE_SUDOKU,resultat[i].length);
            for (int j = 0; j < SIZE_SUDOKU; j++) {
                int val=resultat[i][j];
                assertTrue(val>=1&&val<=9);
                if(!lignes.containsKey(i)){
                    lignes.put(i,new HashSet<>());
                }
                if(!colonnes.containsKey(j)){
                    colonnes.put(j,new HashSet<>());
                }
                assertFalse(lignes.get(i).contains(val));
                lignes.get(i).add(val);
                assertFalse(colonnes.get(j).contains(val));
                colonnes.get(j).add(val);
            }
        }

        // vérification du carré
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){

                Set<Integer> set=new HashSet<>();
                for(int m=0;m<3;m++){
                    for(int n=0;n<3;n++){
                        int val=resultat[i*3+m][j*3+n];
                        assertFalse(set.contains(val));
                        set.add(val);
                    }
                }

            }
        }
    }

}