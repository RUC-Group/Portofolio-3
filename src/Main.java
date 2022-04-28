import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

//Main class
public class Main extends Application {
    Button addButton;
    Button searchButton;
    Button resetButton;
    TextField lecturer;
    ComboBox<String> day;
    TextField room;
    TextField course;
    ComboBox<String> ExAmountStud;
    ComboBox<String> timeOfDay;
    ComboBox<String> maxStuds;
    String dayTextValue = "";
    String maxAmountValue = "";
    String timeOfDayValue = "";
    String courseTextValue = "";
    String lecturerTextValue = "";
    String ExAmountStudValue = "";
    String roomTextValue = "";
    Stage calander;
    Model model;
    ArrayList<String> schedule;

    // Main method of the program
    public static void main(String[] args){
        launch(args);
    }
    
    //start method
    public void start(Stage stage) throws Exception {
        Group root = new Group(); // the root is Group or Pane
        BorderPane bPane = new BorderPane(); // borderpane to contain everything

        model = new Model(); // connection to SQlite
        
        //Boxes to orginize visuals of program
        VBox buttons = new VBox();
        VBox textFields = new VBox();
        HBox courseHbox = new HBox();
        HBox dayHbox = new HBox();
        HBox lecturerHbox = new HBox();
        HBox roomHbox = new HBox();
        
        //labels
        String[] printLabels = {"Day: ", ", Time of day: ", "\n\tCourse: ", ", Expected attendants: ","\n\tLecturer: ", "\n\tRoom: ", ", Max capacity: "};
        String[] weekdays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "All"};
        
        // Map to converte from the value in the database to human readable Weekdays
        Map<String,String> numberToDay= new HashMap<String,String>();
        numberToDay.put("1", "Monday");
        numberToDay.put("2", "Tuesday");
        numberToDay.put("3", "Wednesday");
        numberToDay.put("4", "Thursday");
        numberToDay.put("5", "Friday");

        // Course text field
        course = new TextField();
        courseHbox.getChildren().addAll(new Text("Course: "), course);
        courseHbox.setSpacing(14);

        // Expected amount of studens dropDown menu
        ExAmountStud = new ComboBox<String>();
        ExAmountStud.setPromptText("Expected amount");
        for (int i = 0; i < 60; i++) {
            ExAmountStud.getItems().add(String.valueOf(i + 1));
        }
        courseHbox.getChildren().addAll(ExAmountStud);
        textFields.getChildren().addAll(courseHbox);

        // Day text field
        day = new ComboBox<String>();
        day.setPromptText("Day");
        for (int i = 0; i < weekdays.length; i++) {
            day.getItems().add(weekdays[i]);
        }
        Text dayText = new Text("Timeslot: ");
        dayText.prefWidth(50);
        dayHbox.getChildren().addAll(dayText, day);
        dayHbox.setSpacing(28);
        textFields.getChildren().addAll(dayHbox);
        
        // Time of day dropDown menu
        timeOfDay = new ComboBox<String>();
        timeOfDay.setPromptText("Time");
        timeOfDay.getItems().add("Morning");
        timeOfDay.getItems().add("Afternoon");
        timeOfDay.getItems().add("All");
        dayHbox.getChildren().addAll(timeOfDay);
        
        // Lecturer text field
        lecturer = new TextField();
        Text lecturerText = new Text("Lecturer: ");
        lecturerText.prefWidth(50);
        lecturerHbox.getChildren().addAll(lecturerText, lecturer);
        lecturerHbox.setSpacing(8);
        textFields.getChildren().addAll(lecturerHbox);

        //Room text field
        room = new TextField();
        roomHbox.getChildren().addAll(new Text("Room: "), room);
        roomHbox.setSpacing(20);

        // Max amount of students allowed in a room.
        maxStuds = new ComboBox<String>();
        maxStuds.setPromptText("Max capacity");
        for (int i = 0; i < 100; i++) {
            maxStuds.getItems().add(String.valueOf(i+1));
        }
        roomHbox.getChildren().addAll(maxStuds);
        textFields.getChildren().addAll(roomHbox);


        // Reset button which wipes any values inputtet into the textfields or dropdown menues
        resetButton = new Button();
        resetButton.setText("Reset Values");
        resetButton.setPrefHeight(72);
        resetButton.setTranslateX(5);
        resetButton.setPrefWidth(460);
        resetButton.setOnAction(e -> {  //action for reset button
            day.setValue("All");
            timeOfDay.setValue("All");
            course.setText("");;
            lecturer.setText("");
            room.setText("");
            ExAmountStud.setValue(null);
            maxStuds.setValue(null);
        });
        bPane.setBottom(resetButton);

        
        //search button
        searchButton = new Button();
        searchButton.setText("Search");
        searchButton.setPrefHeight(72);
        searchButton.setTranslateX(5);
        searchButton.setPrefWidth(100);
        searchButton.setOnAction(e -> { // event for when the button is pressed
            Stage calender = new Stage();
            TextArea textArea = new TextArea();
                calender.initModality(Modality.NONE);
                calender.initOwner(stage);
                saveValues(); // save values from the textFields and dropdown menues
                
                // Send saved values to the model to make a query to send to the database
                schedule =  model.getSchedule(dayTextValue,timeOfDayValue,courseTextValue,lecturerTextValue,roomTextValue);
                String s = "";
                // Format the string returned by SQlite to show to the user
                for (int i = 0; i < schedule.size(); i++) {
                    if (i%7 == 0 && i!=0) {
                        s+="\n\n";
                    }
                    s += printLabels[i%7];
                    if (i%7==0) {
                        s +=numberToDay.get(schedule.get(i)) + " ";
                    }else{
                        s += schedule.get(i) + " ";
                    }
                }
                System.out.println(schedule.size());
                if (schedule.size() == 0) {
                    s = ":-( \n No results found with those attributes! \n :-("; 
                }
                textArea.setText(s);  
                // Show values of the database
                Scene calenderScene = new Scene(textArea, 500, 200);
                calender.setTitle("Calender");
                calender.setScene(calenderScene);
                calender.show();
            });
         buttons.getChildren().add(searchButton);
        
        // Update button
        addButton = new Button();
        addButton.setText("Add");
        addButton.setPrefHeight(72);
        addButton.setPrefWidth(100);
        addButton.setTranslateX(5);
        addButton.setOnAction(e -> {
                saveValues(); // Saves values from textFields and dropdown menu

                // If any value is null, The form is filled incorrectly, then send an error msg to the user  
                if (courseTextValue == null || lecturerTextValue == null || roomTextValue == null || timeOfDayValue=="" || dayTextValue == "" || maxAmountValue == "" || ExAmountStudValue == "") {
                    Stage error = new Stage();
                    error.initModality(Modality.NONE);
                    error.initOwner(stage);
                    HBox errorbox = new HBox(new Text(" Error! \n You need to fill in all the fields AND day and Time can't be 'All'. \n Fields: \n \tDay(Time) \n \tCourse(Expected amount of students) \n \tLecturer \n \tRoom(Max amount of students)"));
                    Scene errorScene = new Scene(errorbox, 375, 125);
                    error.setTitle("Error!");
                    error.setScene(errorScene);
                    error.show();

                } else { // Else add everything is filled out, add what has been filled out to the database
                    model.addSchedule(courseTextValue, lecturerTextValue, roomTextValue, dayTextValue, timeOfDayValue, ExAmountStudValue, maxAmountValue);  
                }
        });
        buttons.getChildren().add(addButton);
        buttons.setSpacing(25);
        textFields.setSpacing(20);
        
        // Add the buttons and textfields to the borderpane and the borderpane to the root
        bPane.setCenter(textFields);
        bPane.setRight(buttons);
        bPane.setTranslateX(5);
        root.getChildren().add(bPane);
        
        // Add the root to the scene
        Scene scene = new Scene(root, 480, 250, Color.WHITE);
        stage.setTitle("Schedule searcher and modifier");
        stage.setScene(scene);
        stage.show();
    }

    // Method that translates from a weekday to a value the database can understand
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

    //method that saves all the values a user has input into the textfields and dropdown menu
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
        lecturerTextValue = lecturer.getText();
        roomTextValue = room.getText();
        ExAmountStudValue = e;
        maxAmountValue = m;
    }
}


