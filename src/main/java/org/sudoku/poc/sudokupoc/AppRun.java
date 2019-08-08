package org.sudoku.poc.sudokupoc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Service
public class AppRun implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppRun.class);

    @Override
    public void run(String... args) throws Exception {
        test1();

        System.exit(0);
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

}
