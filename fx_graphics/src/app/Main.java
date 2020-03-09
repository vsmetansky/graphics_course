package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import sample.components.Axe;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        final int SCREEN_WIDTH = 1366;
        final int SCREEN_HEIGHT = 768;

        final Group ROOT = createGroup(SCREEN_WIDTH, SCREEN_HEIGHT);

        primaryStage.setTitle("Axe");
        primaryStage.setScene(createScene(SCREEN_WIDTH, SCREEN_HEIGHT, ROOT));
        primaryStage.show();
    }

    public static Group createGroup(int sWidth, int sHeight) {
        Group g = new Group();
        Axe a = new Axe(sWidth / 2, sHeight / 2, (int) (sWidth * 0.75), (int) (sHeight * 0.9));
        a.materialize(g);
        return g;
    }

    public static Scene createScene(int sWidth, int sHeight, Group root) {
        Scene s = new Scene(root, sWidth, sHeight);
        s.setFill(Color.DARKRED);
        return s;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
