To run this project do:

-Fetch JavaFX SDK from https://gluonhq.com/products/javafx/__
    Extract in a directory (here C:\mads\progs\javafx)
 
-Add jar files in lib directory to project using “Open Module Settings”

-Edit Configurations > modify options > add VM options__
    -p "C:\mads\progs\javafx\lib" --add-modules javafx.controls
    
-Fetch JDBC from to https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc__
    Extract in a directory (here C:\mads\progs\sql)
    
-Add jar file (sqlite-jdbc…jar) to project using “Open Module Settings”
