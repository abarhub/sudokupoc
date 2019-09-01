package org.sudoku.poc.sudokupoc.gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudoku.poc.sudokupoc.Board;
import org.sudoku.poc.sudokupoc.BoardBuilder;
import org.sudoku.poc.sudokupoc.Constants;
import org.sudoku.poc.sudokupoc.util.PositionUtils;

import java.time.Duration;
import java.time.Instant;

public class Sudoku1 extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sudoku1.class);

    @FXML
    Button button_two;

    @FXML
    Canvas canvas;

    private Board board;
    private GuiModel guiModel;

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
        //affiche(tab);
        board=tab;
        guiModel=new GuiModel(board);
    }

    private Scene createScene(){
//        Button button1=new Button("1");
//        Button button2=new Button("2");

        VBox vbox = new VBox();

//        HBox hbox = new HBox();
//        vbox.getChildren().add(hbox);

        for(int i = 1; i<= Constants.NB_LIGNES; i++){
            HBox hbox = new HBox();
            vbox.getChildren().add(hbox);
            for(int j=1;j<=Constants.NB_COLONNES;j++) {
                Cell cell=guiModel.get(PositionUtils.getPosition(i-1,j-1));
                int val;
                val=i*j;
                val=cell.getValeur();
                Button button = new Button("" + val);
                hbox.getChildren().add(button);
//                if (i == 3 || i == 6) {
//                    hbox = new HBox();
//                    vbox.getChildren().add(hbox);
//                }
            }
        }

//        Button button3=new Button("3");

//        vbox.getChildren().add(button3);

        Scene scene = new Scene(vbox, 720, 480);

        return scene;
    }

}
