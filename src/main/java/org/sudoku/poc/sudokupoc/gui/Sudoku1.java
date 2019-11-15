package org.sudoku.poc.sudokupoc.gui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudoku.poc.sudokupoc.Board;
import org.sudoku.poc.sudokupoc.BoardBuilder;
import org.sudoku.poc.sudokupoc.Constants;
import org.sudoku.poc.sudokupoc.Position;
import org.sudoku.poc.sudokupoc.util.PositionUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Sudoku1 extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sudoku1.class);
    public static final int DELETE_VALUE = -1;

    @FXML
    Button button_two;

    @FXML
    Canvas canvas;

    private Board board;
    private GuiModel guiModel;

    private Map<Position,Button> buttonGrille;

    private Map<Integer,ToggleButton> buttonValeurs;
    private ToggleButton delete;
    private ToggleGroup groupValeurs;

    private Label affichePosition;
    private Label afficheMessage;

    private Optional<Integer> valeurSelectionnee;

    @Override
    /* modify the method declaration to throw generic Exception (in case any of the steps fail) */
    public void start(Stage primaryStage) throws Exception {

        LOGGER.info("canvas={}", canvas);
        LOGGER.info("button_two={}", button_two);

        initModel();

        FXMLLoader loader = new FXMLLoader();

        /* load layout.fxml from file and assign it to a scene root object */
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sudoku1.fxml"));

        /* assign the root to a new scene and define its dimensions */
        //Scene scene = new Scene(root, 720, 480);

//        Button button1=new Button("1");
//        Button button2=new Button("2");
//
//        VBox vbox = new VBox(button1, button2);
//
//        Button button3=new Button("3");
//
//        vbox.getChildren().add(button3);
//
//        Scene scene = new Scene(vbox, 720, 480);

        Scene scene=createScene();

        /* set the title of the stage (window) */
        primaryStage.setTitle("Sudoku");
        /* set the scene of the stage to our newly created from the layout scene */
        primaryStage.setScene(scene);
        /* show the stage */
        primaryStage.show();
    }

    private void initModel(){
        Board tab;
        BoardBuilder boardBuilder=new BoardBuilder();
        Instant debut=Instant.now();
        tab=boardBuilder.build();
        Instant fin=Instant.now();
        LOGGER.info("duree:"+ Duration.between(debut,fin));
        LOGGER.info("sudoku={}",tab);
        LOGGER.info("sudoku2={}",tab.getSolution());
        //affiche(tab);
        board=tab;
        guiModel=new GuiModel(board);
        valeurSelectionnee=Optional.empty();
    }

    private Scene createScene(){
//        Button button1=new Button("1");
//        Button button2=new Button("2");

        BorderPane border = new BorderPane();

        final int espaceSeparation=10;

        // la grille du milleux

        VBox vbox = new VBox();
        vbox.setSpacing(espaceSeparation);

        VBox vbox2 = new VBox();
        vbox.getChildren().add(vbox2);

        border.setCenter(vbox);

        buttonGrille=new HashMap<>();

        for(int i = 1; i<= Constants.NB_LIGNES; i++){
            HBox hbox = new HBox();
            hbox.setSpacing(espaceSeparation);
            vbox2.getChildren().add(hbox);
            HBox hbox2 = new HBox();
            hbox2.setPrefWidth(50);
            hbox.getChildren().add(hbox2);
            for(int j=1;j<=Constants.NB_COLONNES;j++) {
                final Position position = PositionUtils.getPosition(i - 1, j - 1);
                Cell cell=guiModel.get(position);
                int val;
                String valStr=" ";
                //val=i*j;
                if(cell.isFixe()){
                    valStr="" + cell.getValeur();
                }
                //val=cell.getValeur();
                Button button = new Button(valStr);
                button.setMinWidth(hbox2.getPrefWidth());
                button.setMinWidth(hbox2.getPrefWidth());
                button.setOnAction(event -> cliqueChoixCaseGrille(position));
                buttonGrille.put(position,button);
                hbox2.getChildren().add(button);

                if(j%3==0){
                    hbox2 = new HBox();
                    hbox2.setPrefWidth(50);
                    hbox.getChildren().add(hbox2);
                }
            }
            if(i%3==0){
                vbox2 = new VBox();
                vbox.getChildren().add(vbox2);
            }
        }

        // les boutons du bas

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");
        //vbox.getChildren().add(hbox);
        border.setBottom(hbox);

        buttonValeurs=new HashMap<>();
        groupValeurs = new ToggleGroup();

        for(int i=1;i<=9;i++){
            final int val=i;
            String label;
            label="_"+val+"_";
            label=""+val;
            ToggleButton button = new ToggleButton(label);
            button.setOnAction(event -> cliqueChoixValeur(val));
            button.setSelected(false);
            button.setUserData(i);
            button.setToggleGroup(groupValeurs);
            buttonValeurs.put(i,button);
            hbox.getChildren().add(button);
        }

        delete=new ToggleButton("del");
        delete.setSelected(false);
        delete.setUserData(DELETE_VALUE);
        delete.setToggleGroup(groupValeurs);
        hbox.getChildren().add(delete);

        groupValeurs.selectedToggleProperty().addListener((ov, toggle, new_toggle) -> {
            if (new_toggle == null) {
                //LOGGER.info("rien n'est selectionne");
                selectionValeur(Optional.empty());
            } else {
                //LOGGER.info("selectionne: {}", groupValeurs.getSelectedToggle().getUserData());
                //rect.setFill(                           (Color) group.getSelectedToggle().getUserData()
                //);
                Object val=groupValeurs.getSelectedToggle().getUserData();
                Integer valeur= (Integer) val;
                selectionValeur(Optional.of(valeur));
            }
        });

        // la partie droite

        VBox vBox=new VBox();
        vBox.setPadding(new Insets(15, 12, 15, 12));
        vBox.setSpacing(10);
        vBox.setStyle("-fx-background-color: #478a99;");
        border.setRight(vBox);

        Label label = new Label("Case :");
        vBox.getChildren().add(label);

        affichePosition=new Label("");
        vBox.getChildren().add(affichePosition);

        afficheMessage=new Label("");
        vBox.getChildren().add(afficheMessage);

        // création de la scene
        Scene scene = new Scene(border, 720, 480);

        return scene;
    }

    private void cliqueChoixCaseGrille(Position position) {
        LOGGER.info("click grille={}",position);
        affichePosition.setText("x="+position.getLigne()+",y="+position.getColonne());
        if(!guiModel.get(position).isFixe()){
            if(this.valeurSelectionnee.isPresent()){
                int valeur=this.valeurSelectionnee.get().intValue();
                final Cell cell = guiModel.get(position);
                Button button = buttonGrille.get(position);
                if(valeur==DELETE_VALUE){
                    cell.setValeurAffecte(0);
                    cell.setVisible(false);
                    button.setText("");
                    button.setStyle("");
                } else {
                    cell.setValeurAffecte(valeur);
                    cell.setVisible(true);
                    button.setText("" + valeur);
                    if (valeur != cell.getValeur()) {
                        button.setStyle("-fx-background-color: #ff0000; ");
                    }
                }
            }
        }
    }

    private void selectionValeur(Optional<Integer> valeurSelectionnee){
        if(valeurSelectionnee.isPresent()){
            int valeur=valeurSelectionnee.get();
            LOGGER.info("selectionne: {}", valeur);
            this.valeurSelectionnee=valeurSelectionnee;
        } else {
            LOGGER.info("rien n'est selectionne");
            this.valeurSelectionnee=Optional.empty();
        }
    }

    private void cliqueChoixValeur(int val){
        LOGGER.info("click val={}",val);
        afficheMessage.setText("val="+val);
    }

}
