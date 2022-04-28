import java.lang.reflect.Array;
import java.util.ArrayList;

//Class that handles the queries between JavaFX and SQlite
public class Model {
    MyDB db = new MyDB();
    //Constructor for Model, Creates SQLite tables if the tables are not found in the database
    Model(){
        
        //create tables
        db.cmd("CREATE TABLE if not exists Course" + "(name TEXT NOT NULL PRIMARY KEY, expectedAmount INTEGER NOT NULL);");
        db.cmd("CREATE TABLE if not exists Lecturer" + "(name TEXT NOT NULL PRIMARY KEY);");
        db.cmd("CREATE TABLE if not exists Room" + "(number TEXT NOT NULL PRIMARY KEY, maxCapacity INTEGER NOT NULL);");
        db.cmd("CREATE TABLE if not exists Schedule" + "(courseName TEXT NOT NULL ,lecturer TEXT NOT NULL ,room TEXT NOT NULL ,day INTEGER NOT NULL CHECK (day >=0 AND day <=5) ,timeOfDay TEXT NOT NULL CHECK (timeOfDay IN ('Morning','Afternoon')),FOREIGN KEY (courseName) REFERENCES Course(name),FOREIGN KEY (lecturer) REFERENCES Lecturer(name),FOREIGN KEY (room) REFERENCES Room(number), PRIMARY KEY(courseName,lecturer,room,day,timeOfDay));");
        
        //create Triggers
        db.cmd("CREATE TRIGGER if not exists teacherTimeslot BEFORE INSERT ON Schedule BEGIN SELECT CASE WHEN NEW.lecturer IN(SELECT Schedule.lecturer FROM Schedule WHERE Schedule.day =NEW.day AND Schedule.timeOfDay = NEW.timeOfDay AND Schedule.courseName < NEW.courseName) THEN RAISE (ABORT, 'Teacher already in that time timeOfDay') END; END;");
        db.cmd("CREATE TRIGGER if not exists roomTimeslot BEFORE INSERT ON Schedule BEGIN SELECT CASE WHEN NEW.room IN(SELECT Schedule.room FROM Schedule WHERE Schedule.day =NEW.day AND Schedule.timeOfDay = NEW.timeOfDay AND Schedule.courseName < NEW.courseName) THEN RAISE (ABORT, 'Room already booked in that time timeOfDay') END; END;");
   }
   //adds s to the 'Lecturer' table
    void addLecturer(String s){  
        db.cmd("insert or ignore into Lecturer (name) values ('"+s+"');");
    }
    
    // adds s and maxCapacity to the 'Room' table
    void addRoom(String s,String maxCapacity){
        db.cmd("insert or ignore into Room (number,maxCapacity) values ('"+s+"',"+maxCapacity+");");
        db.cmd("update Room set maxCapacity = "+maxCapacity+" where number = '"+s+"'");
    }


    // adds s and expectedAmount to the 'Course' table
    void addCourses(String s,String expectedAmount){ 
        db.cmd("insert or ignore into Course (name,expectedAmount) values ('" + s + "'," + expectedAmount + ");");
        db.cmd("update Course set expectedAmount = " + expectedAmount + " where name = '" + s + "'");
    }

    //fills the schedule by first adding to the 'Course', 'Lecturer' and 'Room' tables, and then filling out the 'schedule' table
    void addSchedule(String courseName,String lecturer, String room, String day, String timeOfDay, String expectedAmount, String maxCapacity){ // remember to sanitize your data!
        addCourses(courseName, expectedAmount);
        addLecturer(lecturer);
        addRoom(room, maxCapacity);
        db.cmd("insert or ignore into Schedule (courseName, lecturer, room, day, timeOfDay) values ('" + courseName + "','" + lecturer + "','" + room + "'," + day + ",'" + timeOfDay + "');");
    }

    //sends query to the database to show the 'Schedule' table
    ArrayList<String> getSchedule(String day,String time,String course, String lecturer ,String room){

        //if any of the input values for the database are not empty, format them to be recognizable to SQlite
        if (day != "") {
            day = " AND Schedule.day = '" + day + "'";
        }
        if(time !="") {
            time = " AND Schedule.timeOfDay = '" +time + "'";
        }
        if(course != "") {
            course = " AND Schedule.courseName = '" + course + "'";
        }
        if(lecturer != "") {
            lecturer = " AND Schedule.lecturer = '" + lecturer + "'" ;
        }
        if(room != "") {
            room = " AND Schedule.room = '" + room + "'";
        }

        String[] s = {"day", "timeOfDay", "coursename", "expectedAmount", "lecturer", "room", "maxCapacity"};
        return db.query("SELECT day, timeOfDay, coursename, expectedAmount, lecturer, room, maxCapacity FROM Schedule JOIN Course JOIN Room WHERE courseName=Course.name AND room=Room.number" + course + room + lecturer + day + time + " ORDER BY day ASC, timeOfDay DESC, coursename ASC, lecturer ASC, room ASC", s);
    }
}
