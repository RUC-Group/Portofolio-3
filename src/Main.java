import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.paint.Color;


public class Main extends Application {
    public static void main(String[] args){
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        Group root = new Group(); // the root is Group or Pane
        Scene scene = new Scene(root, 500, 500, Color.RED);
        stage.setTitle("JavaFX Demo");
        stage.setScene(scene);
        stage.show();
    }
}
