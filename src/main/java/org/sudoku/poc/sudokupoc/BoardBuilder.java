package org.sudoku.poc.sudokupoc;

import java.util.*;

public class BoardBuilder {

    private static final Random RANDOM=new Random(System.currentTimeMillis());

    public Board build(){
        Board tab=new Board();
        complete(tab);
        return tab;
    }

    private void complete(Board tab){
        complete(tab,0,0);
    }

    private Set<Integer> valeursPossibles(Board tab, int ligne, int colonne){
        Set<Integer> res=new TreeSet<>();
        for(int i=1;i<=9;i++){
            res.add(i);
        }
        for(int i=0;i<9;i++){
            int n=tab.get(i,colonne);
            if(n>0){
                res.remove(n);
            }
        }
        for(int i=0;i<9;i++){
            int n=tab.get(ligne,i);
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
                    int n = tab.get(i,j);
                    if(n>0){
                        res.remove(n);
                    }
                }
            }
        }
        return res;
    }

    private int complete(Board tab,int ligne,int colonne){
        Set<Integer> valeursPossibles=valeursPossibles(tab,ligne,colonne);
        if(valeursPossibles.isEmpty()){
            return 0;
        }
        List<Integer> liste=new ArrayList<>(valeursPossibles);
        rotateList(liste);
        for(int val:liste){
            tab.set(ligne,colonne,val);
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
        tab.unset(ligne,colonne);
        return 0;
    }

    private void rotateList(List<Integer> liste) {
        int distance=RANDOM.nextInt(liste.size());
        if(distance!=0) {
            Collections.rotate(liste,distance);
        }
    }
}
