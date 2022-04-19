import org.sqlite.Function.Window;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class Main extends Application {
    Button updateButton;
    Button searchButton;
    TextField professor;
    TextField day;
    TextField room;
    TextField course;
    
    
    int windowSize;
    public static void main(String[] args){
        launch(args);
        
    }

    public void start(Stage stage) throws Exception {
        windowSize = 250;
        Group root = new Group(); // the root is Group or Pane
        VBox textFields = new VBox();
        HBox buttons = new HBox();


        //professor text field
        professor = new TextField("professor");
        textFields.getChildren().add(professor);

        //day text field
        day = new TextField();
        textFields.getChildren().add(day);

        //room text field
        room = new TextField();
        textFields.getChildren().add(room);

        // course text field
        course = new TextField();
        textFields.getChildren().add(course);

        //search button
        searchButton = new Button();
        searchButton.setText("Search");
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final Stage calander = new Stage();
                calander.initModality(Modality.NONE);
                calander.initOwner(stage);

                VBox calanderVbox = new VBox(40);
                calanderVbox.getChildren().add(new Text("This will contain a calander"));

                Scene dialogScene = new Scene(calanderVbox, 300, 200);
                calander.setScene(dialogScene);
                calander.show();
                System.out.println("Search");
            }
        });
        buttons.getChildren().add(searchButton);
        
        //update button
        updateButton = new Button();
        updateButton.setText("Update");
        updateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("update");
            }
        });
        buttons.getChildren().add(updateButton);

        root.getChildren().add(textFields);
        root.getChildren().add(buttons);

        Scene scene = new Scene(root, windowSize, windowSize, Color.rgb(71, 71, 71, 1));
        stage.setTitle("Scedule searcher and modifyer");
        stage.setScene(scene);
        stage.show();
    }
}
