import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class Main extends Application {
    Button addButton;
    Button searchButton;
    TextField professor;
    ComboBox day;
    TextField room;
    TextField course;
    ComboBox ExAmountStud;
    ComboBox timeOfDay;
    ComboBox maxStuds;
    String dayTextValue = "";
    String maxAmountValue = "";
    String timeOfDayValue = "";
    String courseTextValue = "";
    String lecturerTextValue = "";
    String ExAmountStudValue = "";
    String roomTextValue = "";
    Stage calander;
    Model model;
    public static void main(String[] args){
        launch(args);
        
    }
    
    public void start(Stage stage) throws Exception {
        Group root = new Group(); // the root is Group or Pane
        BorderPane bPane = new BorderPane();

        model = new Model();
        
        //text fields for inputting
        VBox textFields = new VBox();
        HBox courseHbox = new HBox();
        HBox dayHbox = new HBox();
        HBox professorHbox = new HBox();
        HBox roomHbox = new HBox();
        
        String[] weekdays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "All"};
        String[] detailsNames = {" Weekday:", " Timeslot:", " Course(expected students):", " Professor(s):", " Room(s),capacities:"};
        
        // Buttons
        VBox buttons = new VBox();
        
        // course text field
        course = new TextField();
        courseHbox.getChildren().addAll(new Text("Course: "), course);
        courseHbox.setSpacing(14);
        // expected amount of studens combobox
        ExAmountStud = new ComboBox();
        ExAmountStud.setPromptText("Expected amount");
        for (int i = 0; i < 60; i++) {
            ExAmountStud.getItems().add(String.valueOf(i + 1));
        }
        courseHbox.getChildren().addAll(ExAmountStud);
        textFields.getChildren().addAll(courseHbox);

        //day text field
        day = new ComboBox();
        day.setPromptText("Day");
        for (int i = 0; i < weekdays.length; i++) {
            day.getItems().add(weekdays[i]);
        }
        Text dayText = new Text("Timeslot: ");
        dayText.prefWidth(50);
        dayHbox.getChildren().addAll(dayText, day);
        dayHbox.setSpacing(28);
        textFields.getChildren().addAll(dayHbox);
        
        // radio checkbox for Morning/Afternoon Courses


        HBox toggleTime = new HBox();
        timeOfDay = new ComboBox();
        timeOfDay.setPromptText("Time");
        timeOfDay.getItems().add("Morning");
        timeOfDay.getItems().add("Afternoon");
        timeOfDay.getItems().add("All");
        dayHbox.getChildren().addAll(timeOfDay);
        
        //professor text field
        professor = new TextField();
        Text professorText = new Text("Professor: ");
        professorText.prefWidth(50);
        professorHbox.getChildren().addAll(professorText, professor);
        textFields.getChildren().addAll(professorHbox);

        //room text field
        room = new TextField();
        roomHbox.getChildren().addAll(new Text("Room: "), room);
        roomHbox.setSpacing(20);
        //max amount of students allowed in a room.
        maxStuds = new ComboBox();
        maxStuds.setPromptText("Max capacity");
        for (int i = 0; i < 100; i++) {
            maxStuds.getItems().add(String.valueOf(i+1));
        }
        roomHbox.getChildren().addAll(maxStuds);
        textFields.getChildren().addAll(roomHbox);
        
        
        
        //search button
        searchButton = new Button();
        searchButton.setText("Search");
        searchButton.setPrefHeight(72);
        searchButton.setTranslateX(5);
        searchButton.setPrefWidth(100);
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage calender = new Stage();
                TextArea textArea = new TextArea();
                calender.initModality(Modality.NONE);
                calender.initOwner(stage);

                saveValues();

                ArrayList<String> schedule =  model.getSchedule(dayTextValue,timeOfDayValue,courseTextValue,lecturerTextValue,roomTextValue);

                
                String s = "";
                for (int i = 0; i < schedule.size(); i++) {
                    if (i%7 == 0 && i!=0) {
                        s+="\n";
                    }
                    s += schedule.get(i) + " ";
                }
                textArea.setText(s);  


                Scene calenderScene = new Scene(textArea, 500, 200);
                calender.setTitle("Calender");
                calender.setScene(calenderScene);
                calender.show();
            }
        });
            
                /*if (calander == null || !calander.isShowing()) { // does not work....
                    dayTextValue = day.getText();
                    courseTextValue = course.getText();
                    lecturerTextValue = professor.getText();
                    roomTextValue = room.getText();

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
                    calander.setTitle("Calendar");
                    calander.setScene(calanderScene);
                    calander.show();
                    System.out.println("Search");
                    System.out.println(morning.isSelected());
                    System.out.println(afternoon.isSelected());
                } else {
                    calander.toFront();
                }
              
            }
        });*/ 
        buttons.getChildren().add(searchButton);
        
        //update button
        addButton = new Button();
        addButton.setText("Add");
        addButton.setPrefHeight(72);
        addButton.setPrefWidth(100);
        addButton.setTranslateX(5);
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveValues();
                System.out.println(dayTextValue == null || courseTextValue == null || lecturerTextValue == null || roomTextValue == null || (timeOfDay.getValue() ==null));
                
                if (courseTextValue == null || lecturerTextValue == null || roomTextValue == null || timeOfDayValue=="" || dayTextValue == "" || maxAmountValue == "" || ExAmountStudValue == "") {
                    Stage error = new Stage();
                    error.initModality(Modality.NONE);
                    error.initOwner(stage);
                    HBox errorbox = new HBox(new Text(" Error! \n You need to fill in all the fields AND day and Time can't be 'All'. \n Fields: \n \tDay(Time) \n \tCourse(Expected amount of students) \n \tLecturer \n \tRoom(Max amount of students)"));
                    Scene errorScene = new Scene(errorbox, 375, 125);
                    error.setTitle("Error!");
                    error.setScene(errorScene);
                    error.show();

                } else {
                    model.addSchedule(courseTextValue, lecturerTextValue, roomTextValue, dayTextValue, timeOfDayValue, ExAmountStudValue, maxAmountValue);  
                }
            }
        });
        buttons.getChildren().add(addButton);

        buttons.setSpacing(25);
        textFields.setSpacing(20);
        
        
        //add the two boxes to the root
        
        bPane.setCenter(textFields);
        bPane.setRight(buttons);
        bPane.setTranslateX(5);
        root.getChildren().add(bPane);
        

        // add the root to the scene
        Scene scene = new Scene(root, 480, 180, Color.WHITE);
        stage.setTitle("Schedule searcher and modifier");
        stage.setScene(scene);
        stage.show();
    }

    String dayTranslator(String s){
        String res = "";
        
        switch(s){
            case "Monday": 
                res = "1";
                break;
            case "Tuesday": 
                res = "2";
                break;
            case "Wednesday": 
                res = "3";
                break;
            case "Thursday": 
                res = "4";
                break;
            case "Friday": 
                res = "5";
                break; 
            case "All":
                res = "";
                break;
            default:
                res = "";
                break; 
        }
        
        return res;
    }

    void saveValues(){
        String s = (String) day.getValue();
        String t = (String) timeOfDay.getValue();
        String e = (String) ExAmountStud.getValue();
        String m = (String) maxStuds.getValue();
        if(s==null){
            s="";
        }
        if(t == null || t=="All"){
            t="";
        }
        if(e == null){
            e="";
        }
        if(m == null){
            m="";
        }
        dayTextValue = dayTranslator(s);
        timeOfDayValue = t;
        courseTextValue = course.getText();
        lecturerTextValue = professor.getText();
        roomTextValue = room.getText();
        ExAmountStudValue = e;
        maxAmountValue = m;
        System.out.println("IN SAVES: c: "+courseTextValue + " r: "+ roomTextValue +" l: "+ lecturerTextValue + " d: "+dayTextValue + " t: "+timeOfDayValue + " m: " + maxAmountValue + " e: " + ExAmountStudValue );

    }
}


