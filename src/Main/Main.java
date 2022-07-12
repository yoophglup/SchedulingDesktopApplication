package Main;
/**
 * class main.Main.java
 * Java 11 + JAVAfx
 * --module-path ${PATH_TO_FX} --add-modules javafx.fxml,javafx.controls,javafx.graphics
 *
 */
/**
 * class Main.java
 */
/**
 *
 * @author Chad Self
 */

import Controllers.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaDoc Location - SchedulingDesktopApplication.zip(unzipped location)\SDA-javadoc\index.html
 * SchedulingDesktopApplication javaDoc files are stored in a folder named
 * 'ims-javadoc' stored in the root of the zip file
 *
 *
 */
public class Main extends Application {
    /**
     * This method changes the scene to the main menu.
     * @param primaryStage creates the main menu scene
     * @throws IOException primaryStage
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../Scenes/loginscreen.fxml"));
        primaryStage.setTitle("SchedulingDesktopApplication");
        primaryStage.setScene(new Scene(root, 340, 200));
        primaryStage.show();
    }
    /**
     * This method calls the method to load the database, then pass args
     * to the start method through launch
     * @param args main Launch arguments
     */

    public static void main(String[] args) {
        JDBC.makeConnection();

        launch(args);
    }
}
