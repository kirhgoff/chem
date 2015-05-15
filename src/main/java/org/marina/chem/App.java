package org.marina.chem;
import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.image.*;
import java.util.*;
import java.math.*;



import java.util.ArrayList;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage myStage) {
        myStage.setTitle("Equation");
        FlowPane rootNode = new FlowPane();
        rootNode.setAlignment(Pos.TOP_LEFT);
        Scene myScene = new Scene(rootNode,600,250);
        myStage.setScene(myScene);

        final Label a = new Label("Param a: ");
        final Label b = new Label("Param b: ");
        final Label c = new Label("Param c: ");

        final TextField field_a = new TextField("1.0");
        final TextField field_b = new TextField("1.0");
        final TextField field_c = new TextField("0.0");

        final Label d = new Label("");
        final Label e = new Label("");

        final RadioButton rbLinear = new RadioButton("Linear");
        final RadioButton rbQuadratic = new RadioButton("Quadratic");
        final ToggleGroup tg = new ToggleGroup();
        rbLinear.setToggleGroup(tg);
        rbQuadratic.setToggleGroup(tg);

        rbLinear.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent ae) {
                c.setVisible(false);
                field_c.setVisible(false);
            }
        });

        rbQuadratic.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent ae) {
                c.setVisible(true);
                field_c.setVisible(true);
            }
        });

        rbLinear.setSelected(true);

        final Linear lEquation = new Linear(Double.valueOf(field_a.getText()),Double.valueOf(field_b.getText()));
        final Quadratic qEquation = new Quadratic(Double.valueOf(field_a.getText()),Double.valueOf(field_b.getText()),Double.valueOf(field_c.getText()));

        Button btnSolve = new Button("Solve");
        btnSolve.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent ae) {
                if ((RadioButton)tg.getSelectedToggle() == rbLinear)  {
                    lEquation.setParam(Double.valueOf(field_a.getText()),Double.valueOf(field_b.getText()));
                    d.setText("Equation: " + lEquation.show());
                    ArrayList<Double> sol = lEquation.solve();
                    Double sol1 = sol.get(0);
                    e.setText("  Solution: " + sol1);
                }
                else if ((RadioButton)tg.getSelectedToggle()== rbQuadratic) {
                    qEquation.setParam(Double.valueOf(field_a.getText()),Double.valueOf(field_b.getText()),Double.valueOf(field_c.getText()));
                    d.setText("Equation: " + qEquation.show());
                    ArrayList<Double> sol2 = qEquation.solve();
                    String es = "";
                    if (sol2.size() == 0) es = "no solution"; else {
                        for (int s = 0; s < sol2.size(); s++) {
                            es = es + sol2.get(s) + "   ";
                        }
                    }
                    e.setText("  Solution: " + es);
                }
            }
        });



        rootNode.getChildren().addAll(a,field_a, b, field_b, c, field_c, btnSolve,rbLinear,rbQuadratic,d,e);
        myStage.show();

    }
}