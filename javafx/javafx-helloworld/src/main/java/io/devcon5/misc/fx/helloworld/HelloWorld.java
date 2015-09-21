package io.devcon5.misc.fx.helloworld;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Its recommended to use the scene builder for designing the ui. Follow the instructions on
 * <a href="http://stackoverflow.com/questions/28880785/where-is-the-javafx-scene-builder-gone">this post</a>
 * Created by Gerald Mücke on 09.09.2015.
 */
public class HelloWorld extends Application {

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(event -> System.out.println("Hello World!"));
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}
