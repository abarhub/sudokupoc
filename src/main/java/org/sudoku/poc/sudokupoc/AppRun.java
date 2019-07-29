package org.sudoku.poc.sudokupoc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.TreeSet;

@Service
public class AppRun implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppRun.class);

    @Override
    public void run(String... args) throws Exception {
        test1();

        System.exit(0);
    }

    private void test1() {
        int tab[][];
        tab=createTab();
        complete(tab);
        affiche(tab);
    }

    private int[][] createTab() {
        int res[][]=new int[10][10];
        return res;
    }

    private void affiche(int[][] tab){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                System.out.print(tab[i][j]);
            }
            System.out.println();
        }
    }

    private void complete(int[][] tab){
        complete(tab,0,0);
    }

    private Set<Integer> valeursPossibles(int[][] tab,int ligne,int colonne){
        Set<Integer> res=new TreeSet<>();
        for(int i=1;i<=9;i++){
            res.add(i);
        }
        for(int i=0;i<9;i++){
            int n=tab[i][colonne];
            if(n>0){
                res.remove(n);
            }
        }
        for(int i=0;i<9;i++){
            int n=tab[ligne][i];
            if(n>0){
                res.remove(n);
            }
        }
        int debutLigne,finLigne;
        int debutColonne,finColonne;
        debutLigne=(ligne/3)*3;
        finLigne=((ligne/3)+1)*3;
        debutColonne=(colonne/3)*3;
        finColonne=((colonne/3)+1)*3;
        for(int i=debutLigne;i<finLigne;i++){
            for(int j=debutColonne;j<finColonne;j++){
                if(i!=ligne&&j!=colonne) {
                    int n = tab[i][j];
                    if(n>0){
                        res.remove(n);
                    }
                }
            }
        }
        return res;
    }

    private int complete(int[][] tab,int ligne,int colonne){
        Set<Integer> valeursPossibles=valeursPossibles(tab,ligne,colonne);
        if(valeursPossibles.isEmpty()){
            return 0;
        }
        for(int val:valeursPossibles){
            tab[ligne][colonne]=val;
            int ligne2,colonne2;
            if(colonne<8) {
                ligne2 = ligne;
                colonne2 = colonne + 1;
            }else if(ligne<8){
                ligne2=ligne+1;
                colonne2=0;
            } else {
                return 1;
            }
            int res=complete(tab,ligne2,colonne2);
            if(res==1){
                return 1;
            }
        }
        tab[ligne][colonne]=0;
        return 0;
    }
}
