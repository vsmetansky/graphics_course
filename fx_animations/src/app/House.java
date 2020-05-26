package app;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class House extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public static void drawPath(Group root, Color color1, Color color2, double startX, double startY, double... points) {
        Path p = new Path();
        p.setStrokeWidth(0);
        p.setFill(color1);

        ArrayList<QuadCurveTo> curves = new ArrayList<>();

        for (int i = 0; i < (points.length / 4); i++) {
            curves.add(new QuadCurveTo(points[i * 4], points[i * 4 + 1], points[i * 4 + 2], points[i * 4 + 3]));
        }

        p.getElements().add(new MoveTo(startX, startY));
        p.getElements().addAll(curves);
        root.getChildren().add(p);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600);

        drawGround(root);

        drawWalls(root);

        drawRoof(root);

        drawDoor(root);

        drawWindow(root);

        drawPathAndChimney(root);

        drawTree(root);

        animate(root);

        primaryStage.setResizable(false);
        primaryStage.setTitle("House");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void animate(Group root) {
        int cycleCount = 1;
        int time = 1000;

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(time), root);
        scaleTransition.setToX(2);
        scaleTransition.setToY(3);
        scaleTransition.setAutoReverse(true);

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(time), root);
        rotateTransition.setByAngle(-180f);
        rotateTransition.setCycleCount(cycleCount);
        rotateTransition.setAutoReverse(true);

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(time), root);
        translateTransition.setFromX(150);
        translateTransition.setToX(300);
        translateTransition.setFromY(150);
        translateTransition.setToY(300);
        translateTransition.setCycleCount(cycleCount + 1);
        translateTransition.setAutoReverse(true);

        ScaleTransition scaleTransition2 = new ScaleTransition(Duration.millis(time), root);
        scaleTransition2.setToX(0.5);
        scaleTransition2.setToY(0.5);
        scaleTransition2.setCycleCount(cycleCount);
        scaleTransition2.setAutoReverse(true);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(
                rotateTransition,
                scaleTransition,
                scaleTransition2,
                translateTransition
        );
        parallelTransition.setCycleCount(Timeline.INDEFINITE);
        parallelTransition.play();
    }

    private static void drawTree(Group root) {
        drawPath(root, Color.web("#c37f11", 1), Color.web("#c37f11", 1), 70, 200,
                70, 200, 75, 200,
                75, 170, 60, 165,
                80, 180, 80, 200);
        {
            Circle c1 = new Circle(55, 145, 20);
            c1.setFill(Color.web("#629b47", 1));
            root.getChildren().addAll(c1);
        }
        {
            Circle c1 = new Circle(65, 145, 15);
            c1.setFill(Color.web("#629b47", 1));
            root.getChildren().addAll(c1);
        }
        {
            Circle c1 = new Circle(40, 140, 15);
            c1.setFill(Color.web("#629b47", 1));
            root.getChildren().addAll(c1);
        }
    }

    private static void drawPathAndChimney(Group root) {
        drawPath(root, Color.web("#f6e811", 1), Color.web("#738e19", 1), 234, 201,
                233, 223, 201, 230,
                142, 233, 116, 260,
                156, 268, 181, 267,
                184, 254, 200, 247,
                257, 225, 247, 192);
        drawPath(root, Color.web("#f39f16", 1), Color.web("#c37f11", 1), 214, 62,
                214, 62, 238, 75,
                238, 75, 250, 63,
                250, 63, 248, 20,
                248, 20, 230, 16,
                230, 16, 211, 26);
    }

    private static void drawWindow(Group root) {
        drawPath(root, Color.web("#bde0f6", 1), Color.web("#8c5d30", 1), 130, 144,
                130, 144, 135, 178,
                135, 178, 177, 184,
                177, 184, 180, 155,
                180, 155, 130, 144);
        Line w1 = new Line(155, 150, 154, 180);
        w1.setStrokeWidth(3);
        w1.setStroke(Color.web("#8c5d30", 1));
        root.getChildren().addAll(w1);
        Line w2 = new Line(134, 162, 178, 168);
        w2.setStrokeWidth(3);
        w2.setStroke(Color.web("#8c5d30", 1));
        root.getChildren().addAll(w2);
    }

    private static void drawDoor(Group root) {
        drawPath(root, Color.web("#cc954c", 1), Color.web("#865227", 1), 255, 155,
                240, 118, 227, 168,
                227, 168, 230, 205,
                230, 205, 250, 190,
                250, 190, 255, 155);
        Ellipse el2 = new Ellipse(243, 171, 3, 3);
        el2.setFill(Color.web("#f7cf17", 1));
        root.getChildren().add(el2);
    }

    private static void drawRoof(Group root) {
        drawPath(root, Color.web("#bd87bd", 1), Color.web("#bd87bd", 1), 127, 24,
                127, 24, 184, 41,
                184, 41, 203, 130,
                140, 150, 76, 100,
                76, 100, 127, 24);
        drawPath(root, Color.web("#884d9d", 1), Color.web("#6a3c7b", 1), 184, 41,
                184, 41, 203, 130,
                275, 130, 300, 85,
                300, 85, 232, 16,
                232, 16, 184, 41);
        drawPath(root, Color.web("#9353aa", 1), Color.web("#884d9d", 1), 184, 41,
                184, 41, 232, 16,
                232, 16, 183, 0,
                183, 0, 127, 24,
                127, 24, 184, 41);
    }

    private static void drawWalls(Group root) {
        drawPath(root, Color.web("#fef0e0", 1), Color.web("#fef0e0", 1), 200, 219,
                200, 219, 201, 126,
                201, 126, 278, 98,
                278, 98, 265, 180);

        drawPath(root, Color.web("#e1cc8b", 1), Color.web("#e1cc8b", 1), 113, 195,
                113, 205, 200, 218,
                200, 218, 203, 130,
                203, 130, 99, 106,
                88, 106, 113, 195);
    }

    private static void drawGround(Group root) {
        Ellipse el1 = new Ellipse(194, 207, 277 / 2, 125 / 2);
        el1.setFill(Color.web("#7bb23d"));
        root.getChildren().add(el1);
    }
}
