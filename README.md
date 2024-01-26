# Calorie Tracker
## Developers: Auric Poku and Colton Gowans
###How to run the jar file
To run the jar file of this program, you will first need to navigate to
the path of the jar file in the terminal.  
For example: (using my path 
to my jar file)
``` 
C:\Users\Colto\OneDrive - University of Calgary\CPSC 233\CPSC233Demo3\out\artifacts\CalorieTracker
```
Once you are in the directory of your jar file, you can now run the file
by typing the following command:
```
java --module-path "C:\Users\Colto\OneDrive - University of Calgary\CPSC 233\JavaFX download\
javafx-sdk-17.0.2\lib" --add-modules javafx.controls,javafx.fxml -jar CalorieTracker.jar
```
Where the first path in quotations will be the path to your unique location
for your javaFX package.  
Following these steps will allow you to run the jar
file for this program.
###How to use the program
Our calorie tracker has many functionalities the user can access, with the central focus being on  
inputting daily food eaten and exercises, and viewing all of those associated calories to keep track  
of them. To start, the user should first input their various health measurements in the top left, as  
much of the program uses calculations that depend on user specific data to tailor the program to their  
individual self. The user can use the converter at the bottom of the screen to convert any measurements  
to metric standards if needed. From here, the user can access any part of the program. The main part  
is adding the food and calories for each day. At the top of the program, the user can choose what date  
they want the program to be keeping track of. Then, they can input all the eaten food and exercises for  
that day using the top right of the program. All of these inputs can either be viewed directly in the  
tale below, or the user cna visualize their progress with the graph in the middle. At any point, the  
user can also calculate general health metrics using the bottom left of the program. Finally, the user  
can save all of their session data into a csv file, from which the data can be reloaded back into the  
program using the load file feature.
