import java.util.ArrayList;

public class Model {
    MyDB db = new MyDB();
    Model(){

        db.cmd("CREATE TABLE if not exists Course" + "(name TEXT PRIMARY KEY, expectedAmount INTEGER);");
        db.cmd("CREATE TABLE if not exists Lecturer" + "(name TEXT PRIMARY KEY);");
        db.cmd("CREATE TABLE if not exists Room" + "(number TEXT PRIMARY KEY, maxAmount INTEGER);");
        db.cmd("CREATE TABLE if not exists Timeslot" + "(day INTEGER, slot INTEGER,PRIMARY KEY(day,slot));");
        db.cmd("CREATE TABLE if not exists Schedule" + "(courseName TEXT,lecturer TEXT,room TEXT,day TEXT,slot TEXT,FOREIGN KEY (courseName) REFERENCES Course(name),FOREIGN KEY (lecturer) REFERENCES Lecturer(name),FOREIGN KEY (room) REFERENCES Room(number),FOREIGN KEY (day) REFERENCES Timeslot(day),FOREIGN KEY (slot) REFERENCES Course(slot));");
        db.cmd("CREATE TRIGGER if not exists teacherTimeslot BEFORE INSERT ON Schedule BEGIN SELECT CASE WHEN NEW.lecturer IN(SELECT Schedule.lecturer FROM Schedule WHERE Schedule.day =NEW.day AND Schedule.slot = NEW.slot) THEN RAISE (ABORT, 'Teacher already in that time slot') END; END;");
        db.cmd("CREATE TRIGGER if not exists roomTimeslot BEFORE INSERT ON Schedule BEGIN SELECT CASE WHEN NEW.room IN(SELECT Schedule.room FROM Schedule WHERE Schedule.day =NEW.day AND Schedule.slot = NEW.slot) THEN RAISE (ABORT, 'Room already booked in that time slot') END; END;");

       /*

        db.cmd("drop table if exists lst1;");
        db.cmd("create table if not exists lst1 "+
                "(fld1 integer primary key autoincrement, fld2 text);");

        db.cmd("drop table if exists Courses;");
        db.cmd("create table if not exists Courses "+
                "(name text, stud integer);");
        addCourses("Software Development","50");
        addCourses("Essential Computing","90");

        db.cmd("drop table if exists Rooms;");
        db.cmd("create table if not exists Rooms "+
                "(name text, stud integer);");
        addRoom("10.2.49","60");
        addRoom("10.1.25","30");

        db.cmd("drop table if exists Timeslot;");
        db.cmd("create table if not exists Timeslot "+
                "(name text);");
//        for(int i=1;i<=10;i++)addTimeslot("Slot "+i);
        String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday"};
//        for(int i=1;i<=10;i++)addTimeslot("Slot "+i);
        for(String day:days){addTimeslot(day+" AM");addTimeslot(day+" PM");}
        db.cmd("drop table if exists Lecturer;");
        db.cmd("create table if not exists Lecturer "+
                "(name text);");
        addLecturer("Mads Rosendahl");
        addLecturer("Mickey Mouse");
        addLecturer("Spiderman");
         */
    }

    void addLecturer(String s){  db.cmd("insert into Lecturer (name) values ('"+s+"');");}
    ArrayList<String> getLecturer(){return db.query("select name from Lecturer;","name");}

    boolean hasLecturer(String s){
        ArrayList<String> lst= db.query("select name from Lecturer where name = '"+s+"';","name");
        System.out.println(lst);
        return lst.size()>0;
        //return getLecturer().contains(s);
    }

    void addRoom(String s,String maxAmount){db.cmd("insert into Room (number,maxAmount) values ('"+s+"',"+maxAmount+");");}
    ArrayList<String> getRoom(){return db.query("select name from Rooms;","name");}

    void addCourses(String s,String expectedAmount){ db.cmd("insert into Course (name,expectedAmount) values ('"+s+"',"+expectedAmount+");");}
    ArrayList<String> getCourses(){
        return db.query("select name from Courses;","name");
    }

    String findRoom(String c){
        ArrayList<String> lst= db.query(
            "select Rooms.name from Rooms inner join Courses"
            +" where Courses.name = '"+c+"' and Rooms.stud > Courses.stud;","name");
        System.out.println(lst);
        if(lst.size()==0)return "";
        else return lst.get(0);
    }
    
    
    void addTimeslot(String day, String slot){ // remember to sanitize your data!
        db.cmd("insert into Timeslot (day,slot) values ('" + day + "','" + slot + "');");
    }
    void addSchedule(String courseName,String lecturer, String room, String day, String slot){ // remember to sanitize your data!
        db.cmd("insert into Schedule (courseName, lecturer, room, day, slot) values ('" + courseName + "','" + lecturer + "','" + room + "','" + day + "','" + slot + "');");
    }
    ArrayList<String> getTimeslot(){
        return db.query("select name from Timeslot;","name");
    }

    void add(String s){ // remember to sanitize your data!
        db.cmd("insert into lst1 (fld2) values ('"+s+"');");
    }
    ArrayList<String> get(){
        return db.query("select fld2 from lst1 order by fld1;","fld2");
    }
}
