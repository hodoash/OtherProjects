/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffgui_javafx;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.application.Application;
/**
 *
 * @author Albert Kyei
 */
public class ReloadEntryScene{
    
    
    private static AdjacencyMapGraph mainGraph = new AdjacencyMapGraph(false);
    
    public static void reloadFile() throws FileNotFoundException {
        
        //List of UserConnections
        List<UserConnectionsEntry> userList = new ArrayList<>();


        //Declaring variables in preparation to read file
        File graphFile;
        Scanner readFile;

        //Creating path for text file
        //Note: file mustt contain newline '\n' at the bottom at all times
        Path filePath = Paths.get("C:\\Users\\Kwadwo Kyei\\Documents\\NetBeansProjects\\FFGUI_JavaFX\\src\\ffgui_javafx\\User_Graph__Data.txt");

        //Opening file to read
        graphFile = new File(filePath.toString());

        readFile = new Scanner(graphFile);


        user new_addition;

        String newAdditionConnections;

        while(readFile.hasNextLine() && !readFile.hasNext("\n")){
            
            String[] currentLine = readFile.nextLine().split(",");
            
            //Creating user object
            new_addition = new user(currentLine[0].split("=")[1],currentLine[1].split("=")[1],currentLine[2].split("=")[1],
            currentLine[3].split("=")[1],currentLine[4].split("=")[1],currentLine[5].split("=")[1],currentLine[6].split("=")[1],
            currentLine[7].split("=")[1], currentLine[8].split("=")[1], currentLine[9].split("=")[1], currentLine[10].split("=")[1],
            currentLine[11].split("=")[1]);
            
            //Creating string of user's connections
            newAdditionConnections = currentLine[12];
            
            //Creating UserConnectionsEntry object for each user and their respective connections
            UserConnectionsEntry new_Entry = new UserConnectionsEntry(new_addition, newAdditionConnections);
            
            //Adding UserConnectionsEntry object to arraylist for graph
            userList.add(new_Entry);
            
            }
           
            mainGraph.populateGraph(userList);
            
            //System.out.println(mainGraph.toString());
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        
        
    }
    
}
