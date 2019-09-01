package org.sudoku.poc.sudokupoc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.sudoku.poc.sudokupoc.gui.StartJavaFX;
import org.sudoku.poc.sudokupoc.solver.algox.ExactCover;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Service
public class AppRun implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppRun.class);

    @Override
    public void run(String... args) throws Exception {
        boolean gui;

        gui=false;
        gui=true;

        if(gui){
            test3();

        } else {
            test1();
            //test2();

            System.exit(0);
        }
    }

    private void test2() {
        //Solver solver=new Solver();

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
    }

    private void test1() {
        Board tab;
        BoardBuilder boardBuilder=new BoardBuilder();
        Instant debut=Instant.now();
        tab=boardBuilder.build();
        Instant fin=Instant.now();
        System.out.println("duree:"+ Duration.between(debut,fin));
        affiche(tab);
    }

    private void affiche(Board tab){
        System.out.println(tab);
    }


    private void test3() {
        StartJavaFX startJavaFX=new StartJavaFX();
        startJavaFX.start();
    }

}
