import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
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
    Stage calander;
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
        String[] weekdays = {"Weekday/Timeslot","Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        String[] detailsNames = {"Weekday:", "Timeslot:", "Course(expected students):", "Professor(s):", "Room(s),capacities:"};
        
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
                if (calander == null || !calander.isShowing()) { // does not work....
                    Stage calander = new Stage();
                    calander.initModality(Modality.NONE);
                    calander.initOwner(stage);
                    HBox calanderHbox = new HBox();

                    for (int i = 0; i < 6; i++) {
                        VBox dayCalenderVBox = new VBox();
                        dayCalenderVBox.getChildren().add(new Text(weekdays[i]));
                        if (i==0) {
                            dayCalenderVBox.getChildren().add(new Text("Morning: "));
                            dayCalenderVBox.getChildren().add(new Text("Afternoon: "));
                            dayCalenderVBox.setSpacing(35);
                        }
                        else{
                            Button morningButton = new Button();
                            morningButton.setText("idk, SMT fetched \n from SQLite");

                            morningButton.setOnAction(new EventHandler<ActionEvent>() { // could just be .setOnAction(event ->{})
                                @Override
                                public void handle(ActionEvent event) {
                                    Stage details = new Stage();
                                    details.initModality(Modality.NONE);
                                    details.initOwner(stage);
                                    HBox detailsHbox = new HBox();
                                    VBox detailsnameVbox = new VBox();
                                    VBox detailsValueVbox = new VBox();

                                    for (int j = 0; j < detailsNames.length; j++) {
                                        detailsnameVbox.getChildren().add(new Text(detailsNames[j]));
                                        detailsValueVbox.getChildren().add(new Text("idk, smt from SQLite"));                         
                                    }
                                    detailsHbox.getChildren().addAll(detailsnameVbox, detailsValueVbox);

                                    Scene detailsScene = new Scene(detailsHbox, 275, 100);
                                    details.setTitle("Details");
                                    details.setScene(detailsScene);
                                    details.show();

                                }
                            });
                            Button afternoonButton = new Button();
                            afternoonButton.setText("idk, SMT fetched \n from SQLite");

                            afternoonButton.setOnAction(new EventHandler<ActionEvent>() { // could just be .setOnAction(event ->{})
                                @Override
                                public void handle(ActionEvent event) {
                                    Stage details = new Stage();
                                    details.initModality(Modality.NONE);
                                    details.initOwner(stage);
                                    HBox detailsHbox = new HBox();
                                    VBox detailsnameVbox = new VBox();
                                    VBox detailsValueVbox = new VBox();

                                    for (int j = 0; j < detailsNames.length; j++) {
                                        detailsnameVbox.getChildren().add(new Text(detailsNames[j]));
                                        detailsValueVbox.getChildren().add(new Text("idk, smt from SQLite"));                         
                                    }
                                    detailsHbox.getChildren().addAll(detailsnameVbox, detailsValueVbox);

                                    Scene detailsScene = new Scene(detailsHbox, 275, 100);
                                    details.setTitle("Details");
                                    details.setScene(detailsScene);
                                    details.show();

                                }
                            });
                            dayCalenderVBox.getChildren().add(morningButton);
                            dayCalenderVBox.getChildren().add(afternoonButton);
                            dayCalenderVBox.setSpacing(20);
                        }

                        calanderHbox.getChildren().add(dayCalenderVBox);
                        calanderHbox.setSpacing(10);
                    }
                    
                    
                    Scene calanderScene = new Scene(calanderHbox, 700, 150);
                    calander.setTitle("calendar");
                    calander.setScene(calanderScene);
                    calander.show();
                    System.out.println("Search");
                } else {
                    calander.toFront();
                }
              
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
