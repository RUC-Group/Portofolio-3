import org.sqlite.Function.Window;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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
        Group root = new Group(); // the root is Group or Pane
        BorderPane bPane = new BorderPane();
        
        //text fields for inputting
        VBox textFields = new VBox();
        HBox courseHbox = new HBox();
        HBox dayHbox = new HBox();
        HBox professorHbox = new HBox();
        HBox roomHbox = new HBox();
        
        // Buttons
        VBox buttons = new VBox();
        
        //professor text field
        professor = new TextField();
        Text professorText = new Text("Professor: ");
        professorText.prefWidth(50);
        professorHbox.getChildren().addAll(professorText, professor);
        textFields.getChildren().addAll(professorHbox);
        
        
        //day text field
        day = new TextField();
        Text dayText = new Text("Day: ");
        dayText.prefWidth(50);
        dayHbox.getChildren().addAll(dayText, day);
        dayHbox.setSpacing(28);
        textFields.getChildren().addAll(dayHbox);
        
        //room text field
        room = new TextField();
        roomHbox.getChildren().addAll(new Text("room: "), room);
        roomHbox.setSpacing(20);
        textFields.getChildren().add(roomHbox);
        
        // course text field
        course = new TextField();
        courseHbox.getChildren().addAll(new Text("course: "), course);
        courseHbox.setSpacing(14);
        textFields.getChildren().add(courseHbox);
        
        //search button
        searchButton = new Button();
        searchButton.setText("Search");
        searchButton.setPrefHeight(75);
        searchButton.setPrefWidth(100);
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final Stage calander = new Stage();
                calander.initModality(Modality.NONE);
                calander.initOwner(stage);
                
                VBox calanderVbox = new VBox();
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
        updateButton.setPrefHeight(75);
        updateButton.setPrefWidth(100);
        updateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("update");
            }
        });
        buttons.getChildren().add(updateButton);

        buttons.setSpacing(10);
        textFields.setSpacing(20);
        
        
        //add the two boxes to the root
        
        bPane.setCenter(textFields);
        bPane.setRight(buttons);
        root.getChildren().add(bPane);

        // add the root to the scene
        Scene scene = new Scene(root, 310, 175, Color.WHITE);
        stage.setTitle("Scedule searcher and modifyer");
        stage.setScene(scene);
        stage.show();
    }
}
