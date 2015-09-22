/*
 * Copyright 2015 DevCon5 GmbH, info@devcon5.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.devcon5.fx.helloworld;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Gerald Mücke on 22.09.2015.
 */
public class FxmlHelloWorld extends Application{

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        try {
            URL source = FxmlHelloWorld.class.getResource("/Example.fxml");
            Parent root = FXMLLoader.load(source);
            primaryStage.setScene(new Scene(root, 600, 480));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
