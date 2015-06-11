package org.marina.chem;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.*;
import java.util.ArrayList;
import javafx.scene.canvas.*;
import javafx.util.Duration;

//Проверяем русские комментарии
public class App extends Application {

    Double maxX, maxY;
    GraphicsContext gc;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage myStage) {

        myStage.setTitle("Brownian");
        Group rootNode = new Group();

        Box myBox = new Box(400.0,450.0);
        maxX = myBox.getWidth();
        maxY = myBox.getHeight();

        Canvas myCanvas = new Canvas(maxX, maxY);
        gc = myCanvas.getGraphicsContext2D();
        Scene myScene = new Scene(rootNode,600, 500);
        myStage.setScene(myScene);

        // óêëàäûâàåì ÷àñòèöû â êîðîáêó
        myBox.addBalls(15);

        // ðèñóåì òåêóùåå ïîëîæåíèå ÷àñòèö
        drawBalls(myBox, gc);
        gc.setFill(Color.BLACK);
        gc.strokeLine(0,maxY,maxX,maxY);
        gc.strokeLine(maxX,0,maxX,maxY);

        Button btnMove = new Button("Move");
        btnMove.setLayoutX(550);
        btnMove.setLayoutY(50);

        btnMove.setOnAction(new EventHandler<ActionEvent > () {
            public void handle (ActionEvent ae) {
                updateImage(myBox,gc);
            }
        });

        Button btnShow = new Button("Show");
        btnShow.setLayoutX(550);
        btnShow.setLayoutY(100);

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(250),
                ae1 -> updateImage(myBox,gc)));

        btnShow.setOnAction(new EventHandler<ActionEvent > () {
            public void handle (ActionEvent ae) {
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();
            }
        });

        Button btnStop = new Button("Stop");
        btnStop.setLayoutX(550);
        btnStop.setLayoutY(150);

        btnStop.setOnAction(new EventHandler<ActionEvent > () {
            public void handle (ActionEvent ae) {
                timeline.stop();
            }
        });

        rootNode.getChildren().addAll(myCanvas, btnMove, btnShow, btnStop);
        myStage.show();
    }

    public void updateImage(Box box, GraphicsContext gc) {
        box.move();
        gc.clearRect(0.0,0.0,maxX,maxY);
        gc.strokeLine(0,maxY,maxX,maxY);
        gc.strokeLine(maxX,0,maxX,maxY);
        gc.strokeLine(0,0,0,maxY);
        drawBalls(box, gc);
    }

    public void drawBalls(Box box, GraphicsContext gc) {
        ArrayList<Ball> b = box.getBalls();
        for (int i = 0; i < b.size(); i++) {
            gc.setFill(b.get(i).color);
            gc.fillOval(b.get(i).getX()-b.get(i).getRadius(), b.get(i).getY()-b.get(i).getRadius(), 2*b.get(i).getRadius(),2*b.get(i).getRadius());

        }
    }

}
