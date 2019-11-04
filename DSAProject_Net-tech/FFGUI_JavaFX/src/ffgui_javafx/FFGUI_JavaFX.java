/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//Path of some files used are specific for my laptop, you may have to change them at:
//FFGUI_JavaFX line 224 and line 263
//EventHandling_FFGUI line 109 and line 733

package ffgui_javafx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
/**
 *
 * @author Albert Kyei
 */
public class FFGUI_JavaFX extends Application {
    
    private static AdjacencyMapGraph mainGraph = new AdjacencyMapGraph(false);
    
    private static ReloadEntryScene reload = new ReloadEntryScene();
    
    private Button signIn_Btn, signUp_Btn;
    private Text entryTitle, unameTitle, pwordTitle;
    private TextField usernameField;
    private PasswordField passwordField;
    
    ffgui_javafx.EventHandling_FFGUI sceneControl = new ffgui_javafx.EventHandling_FFGUI();
    
    @Override
    public void start(Stage primaryStage) {
        
        //Title for window
        primaryStage.setTitle("Net-X");
        //Setting up grid for placing items in scene
        GridPane mainGrid = new GridPane();
        mainGrid.setAlignment(Pos.CENTER);
        mainGrid.setHgap(10);
        mainGrid.setVgap(15);
        mainGrid.setPadding(new Insets(25,25,100,25));
        
        StackPane layout1 = new StackPane();
        StackPane layout2 = new StackPane();
        
        layout2.getChildren().add(mainGrid);
        layout2.setId("layer");
        layout1.getChildren().add(layout2);
        
        Scene entryScene;
        entryScene = new Scene(layout1, 1366, 768);
        
        //Creating various items for entryScene, and placing in grid
        //Format for adding to grid: grid.add(item, column, row, columnspan, rowspan)
        entryTitle = new Text("WELCOME TO NET-X");
       
        entryTitle.setId("Htext");
        
        HBox titleBox = new HBox(10);
        
        titleBox.setPadding(new Insets(25,25,30,25));
        
        titleBox.setAlignment(Pos.CENTER);
        
        titleBox.getChildren().add(entryTitle);
        
        mainGrid.add(titleBox, 0, 0, 2, 2);
        
        //Creating username Field and Text
        //unameTitle = new Text("Username: ");
        //mainGrid.add(unameTitle, 0, 2);
        
        usernameField = new TextField();
        
        usernameField.setId("uTextField");
        
        usernameField.setPromptText("Username");
        
        usernameField.setOnKeyTyped(e -> {
           usernameField.setStyle("-fx-background-image: none");
        });
        
        usernameField.setOnMouseExited(e-> {
            if (usernameField.getText().isEmpty()){
                
                usernameField.setStyle("-fx-background-image: url('user.png')");
    
                usernameField.setStyle("-fx-background-repeat: no-repeat");

                usernameField.setStyle("-fx-background-position: right center");
            }
        });
        
        mainGrid.add(usernameField, 1, 2, 2, 1);
        
        //Creating password Field and Text
        //pwordTitle = new Text("Password: ");
        //mainGrid.add(pwordTitle, 0, 3);
        
        passwordField = new PasswordField();
        
        passwordField.setId("pTextField");
        
        passwordField.setPromptText("Password");
        
        passwordField.setOnMouseExited(e-> {
            if (passwordField.getText().isEmpty()){
                
                passwordField.setStyle("-fx-background-image: url('locked.png')");
    
                passwordField.setStyle("-fx-background-repeat: no-repeat");

                passwordField.setStyle("-fx-background-position: right center");
            }
        });
        
        mainGrid.add(passwordField, 1, 3, 2, 1);
        
        //Creating signIn button
        signIn_Btn = new Button();
        
        signIn_Btn.setText("Sign In");
        
        signIn_Btn.setId("signBtn");
        
        HBox lI_Btn = new HBox(10);
        
        lI_Btn.setAlignment(Pos.CENTER);
        
        lI_Btn.getChildren().add(signIn_Btn);
        
        signIn_Btn.setOnAction(e -> {
            
            //If the username and password exist:
            if (mainGraph.login(usernameField.getText(), passwordField.getText()) != null){
            
                sceneControl.signIn_Scene(primaryStage, entryScene, usernameField.getText(), mainGraph);
            
            }
            else{
            
            Alert invalidUser = new Alert(Alert.AlertType.ERROR);
            invalidUser.setHeaderText("SignIn Error");
            invalidUser.setContentText("Invalid username or password");
            invalidUser.show();
            
            }
            
            
        });
        
        mainGrid.add(lI_Btn, 0, 4, 2,1);
        
        //creating signup button
        signUp_Btn = new Button();
        
        signUp_Btn.setText("Sign Up");
        
        signUp_Btn.setId("signBtn");
        
        HBox sU_Btn = new HBox(10);
        
        sU_Btn.setAlignment(Pos.CENTER);
        
        sU_Btn.getChildren().add(signUp_Btn);
        
        signUp_Btn.setOnAction(e -> {
            sceneControl.signUp_Scene(primaryStage, entryScene, mainGraph);
        });
        
        mainGrid.add(sU_Btn, 0, 5, 2,1);
       
        
        String css = this.getClass().getResource("EntryGUI_CSS.css").toExternalForm();
        entryScene.getStylesheets().add(css);
        
        primaryStage.setScene(entryScene);
        primaryStage.show();
        
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                
                try {
                    System.out.println("Stage is closing");
                    
                    primaryStage.close();
                    
                    //List of UserConnections
                    List<UserConnectionsEntry> userList = new ArrayList<>();
                    
                    //Declaring variables in preparation to read file
                    FileWriter  graphFile;
                    
                    //Creating path for text file
                    //Note: file mustt contain newline '\n' at the bottom at all times
                    Path filePath = Paths.get("C:\\Users\\Kwadwo Kyei\\Documents\\NetBeansProjects\\FFGUI_JavaFX\\src\\ffgui_javafx\\User_Graph__Data.txt");
                    
                    //Opening file to read
                    graphFile = new FileWriter(filePath.toString());
                    
                    //List of current nodes in graph
                    Iterable <Vertex<user>> allUsers = mainGraph.vertices();
                    
                    for( Vertex<user> node : allUsers){
                        
                        if (node != mainGraph.dummyVertex) graphFile.write(node.getElement().toString()+"\n");
                        
                    }
                    graphFile.close();
                } catch (IOException ex) {
                    Logger.getLogger(FFGUI_JavaFX.class.getName()).log(Level.SEVERE, null, ex);
                }
                
          }
        });        
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        
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
            System.out.println("sout: "+currentLine[12]);
            newAdditionConnections = currentLine[12];
            
            //Creating UserConnectionsEntry object for each user and their respective connections
            UserConnectionsEntry new_Entry = new UserConnectionsEntry(new_addition, newAdditionConnections);
            
            //Adding UserConnectionsEntry object to arraylist for graph
            userList.add(new_Entry);
            
            }
           
            mainGraph.populateGraph(userList);
            
            //System.out.println(mainGraph.toString());
            
        launch(args);
        
        }
    }
