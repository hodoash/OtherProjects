/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffgui_javafx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Albert Kyei
 */
public class EventHandling_FFGUI{
    
    private Scene signIn, signUp, details;
    
    private Text hpageTitle, suggestionsTitle, sug;
    
    private Image userImage;
    
    private Button connect, next, prev, signOut;
    
    private static ReloadEntryScene reload = new ReloadEntryScene();
    
    public int index = 0;
    
    
    public void signIn_Scene (Stage primaryStage, Scene entryScene, String username, AdjacencyMapGraph mainGraph){
        
        GridPane si_Grid = new GridPane();
        
        si_Grid.setAlignment(Pos.CENTER);
        
        si_Grid.setHgap(10);
        
        si_Grid.setVgap(20);
        
        si_Grid.setPadding(new Insets(5,25,25,60));
        
        GridPane so_Grid = new GridPane();
        
        so_Grid.setAlignment(Pos.CENTER);
        
        so_Grid.setHgap(10);
        
        so_Grid.setVgap(10);
        
        so_Grid.setPadding(new Insets(25,25,25,25));

        //Creating title in homepage
        hpageTitle = new Text("WELCOME, ");
        
        hpageTitle.setId("Htext");
        
        si_Grid.add(hpageTitle, 0, 1, 1, 1);
        
        Text uTitle = new Text(username);
        
        uTitle.setId("stextID");
        
        si_Grid.add(uTitle, 1, 1, 1, 1);
        
        //Creating title: Suggestions
        suggestionsTitle = new Text("Friend Suggestions");
        
        suggestionsTitle.setId("Htext");
        
        so_Grid.add(suggestionsTitle, 0, 0, 2, 1);
        
        
        //Adding user_image to grid in innerlayer2
        try {
            String workingDir = System.getProperty("user.dir");
            userImage = new Image(new FileInputStream(workingDir+"C:\\Users\\Kwadwo Kyei\\Documents\\NetBeansProjects\\FFGUI_JavaFX\\src\\ffgui_javafx\\userImage.png"));
        } 
        
        catch (FileNotFoundException ex) {
            Logger.getLogger(EventHandling_FFGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        si_Grid.add(new ImageView(userImage), 0, 2, 2, 1);
        
        signOut = new Button ("Sign Out");
        
        signOut.setId("signBtn");
        
        signOut.setOnAction( e-> {
        
            primaryStage.setScene(entryScene);
            
            mainGraph.logout();
        
        });
        
        si_Grid.add(signOut, 0, 4, 2, 2);
        
        List<Vertex<user>> result =  mainGraph.DFS();
        
        String suggestionDetails;
        
        if (result.size()>=1){
            user suggestion_1 = result.get(0).getElement();

            suggestionDetails = "Name: "+suggestion_1.getName()+
                "\n\nBio: "+suggestion_1.getBio()+
                "\n\nInterest: "+suggestion_1.getInterest()+
                    "\n\nSimilarity Index: "+mainGraph.currentUser.getElement().similarityIndex(result.get(index).getElement());
        }
        else{
            suggestionDetails = "Unfortunately, there isn't anyone cool enough for you.\nInvite more users and you may just find that special one. ;)";
        }
        sug = new Text(suggestionDetails);

        sug.setId("textID");
        
        so_Grid.add(sug, 0, 1, 2, 1);
        
        
        connect = new Button("Connect");
        
        connect.setId("signBtn");
        
        connect.setOnAction( e -> {
            
            if (mainGraph.getEdge(mainGraph.currentUser, result.get(index)) == null && result.size() > 1 ){
                
                mainGraph.insertEdge(mainGraph.currentUser, result.get(index), mainGraph.currentUser.getElement().similarityIndex(result.get(index).getElement()));
            
                Alert connected = new Alert(AlertType.INFORMATION);
                connected.setHeaderText("A New Connection");
                connected.setContentText("You have connected with "+result.get(index).getElement().getName()+". Your network is expanding!");
                connected.showAndWait();
                
            }
            else if(mainGraph.getEdge(mainGraph.currentUser, result.get(index)) != null && result.size() > 1 ){
                
                Alert oldConnection = new Alert(AlertType.INFORMATION);
                oldConnection.setHeaderText("Connection Already Established");
                oldConnection.setContentText("You have already connected with "+result.get(index).getElement().getName()+". Connect with someone else.");
                oldConnection.showAndWait();
            }
        });
        
        so_Grid.add(connect, 0, 5, 3, 1);
        
        
        prev = new Button("Previous Suggestion");
        
        prev.setId("hlfBtn");
        
        prev.setOnAction( e-> {
            
            if (result.size() >= 1) sug.setText(iterateSuggestions(-1, mainGraph));
            
        });
        
        so_Grid.add(prev, 0, 6, 1, 1);
        
        
        next = new Button("Next Suggestion");
        
        next.setId("hlfBtn");
        
        next.setOnAction( e -> {
            
            if (result.size() >= 1) sug.setText(iterateSuggestions(1, mainGraph));
            
        });
        
        so_Grid.add(next, 1, 6, 1, 1);
        
        
        //Outer layout
        BorderPane outer_SI = new BorderPane();
        
        outer_SI.setId("outerLayer");
        
        
        //1st Inner layout
        StackPane inner1_SI = new StackPane();
        
        inner1_SI.setId("layer1");
        
        inner1_SI.getChildren().add(so_Grid);
        
        
        //2nd Inner layout
        StackPane inner2_SI = new StackPane();
        
        inner2_SI.setId("layer2");
        
        inner2_SI.getChildren().add(si_Grid);
        
        
        outer_SI.setCenter(inner1_SI);
        
        outer_SI.setLeft(inner2_SI);
        
        
        signIn = new Scene(outer_SI, 1366, 768);
        
        String css = this.getClass().getResource("SignInGUI_CSS.css").toExternalForm();
        signIn.getStylesheets().add(css);
        
        primaryStage.setScene(signIn);
    
    }
    
    public String iterateSuggestions(int i, AdjacencyMapGraph mainGraph){
        
        index = index + i;
        
        List<Vertex<user>> result =  mainGraph.DFS();
        
        
        if (index > -1 && index < result.size()-1){
            
            user suggestion_1 = result.get(index).getElement();
            
            suggestion_1 = result.get(index).getElement();
            
            return "Name: "+suggestion_1.getName()+
                "\n\nBio: "+suggestion_1.getBio()+
                "\n\nInterest: "+suggestion_1.getInterest()+
                "\n\nSimilarity Index: "+mainGraph.currentUser.getElement().similarityIndex(result.get(index).getElement());
            
        }
        else if (index >= result.size()-1){
            
            index = result.size()-1;
            
            user suggestion_1 = result.get(index).getElement();
        
            return "Name: "+suggestion_1.getName()+
                "\n\nBio: "+suggestion_1.getBio()+
                "\n\nInterest: "+suggestion_1.getInterest()+
                "\n\nSimilarity Index: "+mainGraph.currentUser.getElement().similarityIndex(result.get(index).getElement());
            
        }
        index = 0;
        return "Name: "+result.get(0).getElement().getName()+
                "\n\nBio: "+result.get(0).getElement().getBio()+
                "\n\nInterest: "+result.get(0).getElement().getInterest()+
                "\n\nSimilarity Index: "+mainGraph.currentUser.getElement().similarityIndex(result.get(index).getElement());
            
    }
    
    
    
    private Text title, nameLabel, dobLabel, sexLabel, usernameLabel, emailLabel, passwordLabel, cpasswordLabel;
    private TextField name, email, username;
    private PasswordField pwField, pwField_Confirm;
    private DatePicker dob;
    private ToggleGroup sex;
    private Button signUP, back;
    
    public void signUp_Scene (Stage primaryStage, Scene entryScene, AdjacencyMapGraph mainGraph){
        
        //Grid to be placed in layout
        GridPane su_Grid = new GridPane();
        
        su_Grid.setAlignment(Pos.CENTER);
        
        su_Grid.setHgap(10);
        
        su_Grid.setVgap(12);
        
        su_Grid.setPadding(new Insets(25,25,25,25));
        
        //Creating Labels and fields for sign up
        
        title = new Text("CREATE AN ACCOUNT");
        
        title.setId("Htext");
        
        HBox title_BOX = new HBox(10);
        
        title_BOX.getChildren().add(title);
        
        title_BOX.setAlignment(Pos.CENTER);
        
        title_BOX.setPadding(new Insets(25,25,35,25));
        
        su_Grid.add(title_BOX, 0, 0, 2, 1);
        
        //Labels for all fields
        nameLabel = new Text("Name: ");
        nameLabel.setId("textID");
        
        su_Grid.add(nameLabel, 0, 1);
        
        dobLabel = new Text("Date of Birth: ");
        dobLabel.setId("textID");
        
        su_Grid.add(dobLabel, 0, 2);
        
        sexLabel = new Text("Sex: ");
        sexLabel.setId("textID");
        
        su_Grid.add(sexLabel, 0, 3);
        
        usernameLabel = new Text("Username: ");
        usernameLabel.setId("textID");
        
        su_Grid.add(usernameLabel, 0, 6);
        
        emailLabel = new Text("Email: ");
        emailLabel.setId("textID");
        
        su_Grid.add(emailLabel, 0, 7);
        
        passwordLabel = new Text("Password: ");
        passwordLabel.setId("textID");
        
        su_Grid.add(passwordLabel, 0, 8);
        
        cpasswordLabel = new Text("Connfirm password: ");
        
        cpasswordLabel.setId("textID");
        su_Grid.add(cpasswordLabel, 0, 9);
        
        
        //Creating all fields for their respective labels
        name = new TextField();
        name.setPromptText("e.g. John Doe");
        su_Grid.add(name, 1, 1);
        
        dob = new DatePicker();
        dob.setPromptText("mm/dd/yyyy");
        su_Grid.add(dob, 1, 2);
        
        sex = new ToggleGroup();
        
        //Creating multiple radio buttons for "male", "female", and "other" sex
        RadioButton mSex = new RadioButton("Male");
        mSex.setId("radioBtn");
        
        RadioButton fSex = new RadioButton("Female");
        fSex.setId("radioBtn");
        
        RadioButton oSex = new RadioButton("Other");
        oSex.setId("radioBtn");
        
        mSex.setToggleGroup(sex);
        fSex.setToggleGroup(sex);
        oSex.setToggleGroup(sex);
        
        su_Grid.add(mSex, 1, 3);
        su_Grid.add(fSex, 1, 4);
        su_Grid.add(oSex, 1, 5);
        
        username = new TextField();
        username.setPromptText("username");
        su_Grid.add(username, 1, 6);
        
        email = new TextField();
        email.setPromptText("johndoe@ligma.com");
        su_Grid.add(email, 1, 7);
        
        pwField = new PasswordField();
        su_Grid.add(pwField, 1, 8);
        
        pwField_Confirm = new PasswordField();
        su_Grid.add(pwField_Confirm, 1, 9);
        
        
        HBox hbox_Btn = new HBox(10);
        
        signUP = new Button("Create Account");
        
        signUP.setId("signBtn_Unique");
        
        HBox signUP_BOX = new HBox(10);
        
        signUP_BOX.getChildren().add(signUP);
        
        signUP_BOX.setAlignment(Pos.CENTER);
        
        signUP_BOX.setPadding(new Insets(5,5,5,5));
        
        //Action to occur when "signUp button is clicked"
        signUP.setOnAction(e ->{
            details_Scene(primaryStage, mainGraph, entryScene);
        });
        
        
        back = new Button("Back to LogIn Page");
        
        back.setId("signBtn_Unique");
        
        HBox back_BOX = new HBox(10);
        
        back_BOX.getChildren().add(back);
        
        back_BOX.setAlignment(Pos.CENTER);
        
        back_BOX.setPadding(new Insets(5,5,5,5));
        
        //Action to occur when "signUp button is clicked"
        back.setOnAction(e ->{
            primaryStage.setScene(entryScene);
        });
        
        hbox_Btn.getChildren().add(back_BOX);
        
        hbox_Btn.getChildren().add(signUP_BOX);
        
        hbox_Btn.setPadding(new Insets(20,5,5,5));
        
        su_Grid.add(hbox_Btn, 0, 10, 3, 2);        
        
        //Outer layout
        StackPane outer_SU = new StackPane();
        //Inner layout
        StackPane inner_SU = new StackPane();
        
        inner_SU.setId("layer");
        
        inner_SU.getChildren().add(su_Grid);
        outer_SU.getChildren().add(inner_SU);
        
        signUp = new Scene(outer_SU, 1366, 768);
        String css = this.getClass().getResource("SignUpGUI_CSS.css").toExternalForm();
        signUp.getStylesheets().add(css);
        
        primaryStage.setScene(signUp);
        
    }
    
    
    
    private Text detailsTitle, major_Title, yearGroup_Title, hostel_Title, favSpot_Title, bio_Title, interests_Title;
    private TextField favSpot, bio;
    private Button fin;
    private ChoiceBox major, yearGroup, hostel, interests;
    
    user user;

    
    public void details_Scene(Stage primaryStage, AdjacencyMapGraph mainGraph, Scene entryScene){
        
        //boolean determining if some fields are empty
        boolean emptyfields = (name.getText().isEmpty() || name.getText().startsWith(" "))
                || (username.getText().isEmpty()|| username.getText().startsWith(" "))
                || (dob.getValue() == null)
                || (sex.getSelectedToggle() == null)
                || (pwField.getText().isEmpty());
        
        //Regex used to check for a valid email address
        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        
        //boolean for right email address
        boolean validEmail = VALID_EMAIL_ADDRESS_REGEX.matcher(email.getText()).matches();
        
        //boolean for valid username
        boolean validUsername = username.getText().matches("^[a-zA-Z0-9]+$");
        
        //booleans for determining weak password or invalid confirm
        boolean weakPassword = (pwField.getText().length() < 8 && pwField_Confirm.getText().length() < 8);
        
        //
        boolean wrongPasswordConfirm = (pwField.getText() == null ? pwField_Confirm.getText() != null : !pwField.getText().equals(pwField_Confirm.getText()))
                || (pwField.getText().contains("]") || pwField.getText().contains("["))
                || (pwField_Confirm.getText().contains("]") || pwField_Confirm.getText().contains("["));
        
        //boolean to check if email and username have been used
        boolean originalDetails;
        
        boolean allValid = !(emptyfields) && (validEmail) && !(weakPassword) && !(wrongPasswordConfirm);
        
        //Alerts to pop up on any invalidity
        if(emptyfields) {
            
            Alert warning_empty = new Alert(AlertType.INFORMATION);
            warning_empty.setHeaderText("Empty Fields!");
            warning_empty.setContentText("Some fields have been left empty or contain whitespace, fill all fields appropriately.");
            warning_empty.showAndWait();
        
        }
        
        else if(!validUsername){
            
            Alert invalidUsername = new Alert(AlertType.INFORMATION);
            invalidUsername.setHeaderText("Invalid Username!");
            invalidUsername.setContentText("Enter a username with only alphanumeric characters.");
            invalidUsername.showAndWait();
        
        }
        
        else if(!validEmail){
            
            Alert wrongEmail = new Alert(AlertType.INFORMATION);
            wrongEmail.setHeaderText("Invalid Email Address!");
            wrongEmail.setContentText("Provide a valid email address.");
            wrongEmail.showAndWait();
        
        }
        
        else if(weakPassword){
            
            Alert weakPasscode = new Alert(AlertType.INFORMATION);
            weakPasscode.setHeaderText("Weak Passcode");
            weakPasscode.setContentText("Ensure your password is eight characters or more.");
            weakPasscode.show();
        
        }
        
        else if(wrongPasswordConfirm){
            
            Alert wrongConfirm = new Alert(AlertType.INFORMATION);
            wrongConfirm.setHeaderText("Invalid Confirm Password");
            wrongConfirm.setContentText("Ensure both passwords are the same and they do not contain '[' or ']'");
            wrongConfirm.showAndWait();
        
        }
        
        //If user inputs everything correctly, scene switches as code below is run
        else if (allValid){
            
            
            
            GridPane d_Grid = new GridPane();

            d_Grid.setAlignment(Pos.CENTER);
            
            d_Grid.setHgap(10);
            
            d_Grid.setVgap(10);
            
            d_Grid.setPadding(new Insets(25,25,25,25));

            
            //Creating labels for all fields
            detailsTitle = new Text("Fill in Details to Complete Registration");
            
            HBox titleBox = new HBox(10);
            
            titleBox.setPadding(new Insets(25,25,30,25));
            
            titleBox.setAlignment(Pos.CENTER);
            
            titleBox.getChildren().add(detailsTitle);
            
            detailsTitle.setId("Htext");
            
            d_Grid.add(titleBox, 0, 0, 2, 1);
            
            
            major_Title = new Text("Major: ");
            
            major_Title.setId("textID");
            
            d_Grid.add(major_Title, 0, 2, 1, 1);
            
            
            yearGroup_Title = new Text("Class: ");
            
            yearGroup_Title.setId("textID");
            
            d_Grid.add(yearGroup_Title, 0, 3, 1, 1);
            
            
            hostel_Title = new Text("Hostel: ");
            
            hostel_Title.setId("textID");
            
            d_Grid.add(hostel_Title, 0, 4);
            
            
            favSpot_Title = new Text("Frequent hangout (in school): ");
            
            favSpot_Title.setId("textID");
            
            d_Grid.add(favSpot_Title, 0, 5, 1, 1);
            
            
            bio_Title = new Text("Bio: ");
            
            bio_Title.setId("textID");
            
            d_Grid.add(bio_Title, 0, 6, 1, 1);
            
            
            interests_Title = new Text("Interests: ");
            
            interests_Title.setId("textID");
            
            d_Grid.add(interests_Title, 0, 7, 1, 1);
            
            
            
            //Creating all required fields
            major = new ChoiceBox(FXCollections.observableArrayList("CS", "MIS", "BA", "CE", "EE", "ME"));
            
            d_Grid.add(major, 1, 2, 1, 1);

            
            yearGroup = new ChoiceBox(FXCollections.observableArrayList("Freshman", "Sophomore", "Junior", "Senior"));
            
            d_Grid.add(yearGroup, 1, 3, 1, 1);
            
            
            hostel = new ChoiceBox(FXCollections.observableArrayList("OnCampus", "Hosanna", "Dufie", "Charlotte", "Masere", "Tanko"));
            
            d_Grid.add(hostel, 1, 4, 1, 1);
            
            
            favSpot = new TextField();
            
            favSpot.setPromptText("e.g. The Moon");
            
            d_Grid.add(favSpot, 1, 5, 1, 1);
            
            
            bio = new TextField();
            
            bio.setStyle("-fx-pref-height: 60px;");
            
            d_Grid.add(bio, 1, 6, 1, 1);
            
            
            interests = new ChoiceBox(FXCollections.observableArrayList("Sports", "Music", "Art", "Literature"));
            
            d_Grid.add(interests, 1, 7, 1, 1);
            
            
            //creating "finish" button
            fin = new Button("Fin.");
            
            fin.setId("signBtn");
            
            HBox finBtn_Box = new HBox(10);
            
            finBtn_Box.setPadding(new Insets(25,25,30,25));
            
            finBtn_Box.setAlignment(Pos.CENTER);
            
            finBtn_Box.getChildren().add(fin);
            
            fin.setOnAction( e -> {
                
                boolean noEmptyField = major.getValue() != null
                        && yearGroup.getValue() != null
                        && hostel.getValue() != null
                        && !(favSpot.getText().isEmpty())
                        && !(bio.getText().isEmpty())
                        && interests.getValue() != null;
                
                if(noEmptyField){
                    
                    storeUserData(mainGraph);
                    
                    primaryStage.setScene(entryScene);
                    primaryStage.show();
                    
                }
                else{
                    
                    Alert emptyDetailFields = new Alert(AlertType.INFORMATION);
                    emptyDetailFields.setHeaderText("Empty Fields!");
                    emptyDetailFields.setContentText("Ensure all details are provided.");
                    emptyDetailFields.showAndWait();
                    
                }
                
            });
            
            d_Grid.add(finBtn_Box, 0, 8, 2, 1);
            
            
            StackPane inner_D = new StackPane();
            
            inner_D.getChildren().add(d_Grid);
            
            inner_D.setId("layer");
            
            StackPane outer_D = new StackPane();
            
            outer_D.getChildren().add(inner_D);
            
            details = new Scene(outer_D, 1366, 768);
            
            
            
            String css = this.getClass().getResource("SignUpGUI_CSS.css").toExternalForm();
            details.getStylesheets().add(css);

            primaryStage.setScene(details);
            
        }
    }
    
    public void storeUserData(AdjacencyMapGraph mainGraph){
        //Declaring variables in preparation to read file
        File graphFile;
        Scanner readFile;

        Path filePath = Paths.get("C:\\Users\\Kwadwo Kyei\\Documents\\NetBeansProjects\\FFGUI_JavaFX\\src\\ffgui_javafx\\User_Graph__Data.txt");
        
        //Writing to file
        try{
            System.out.println("sex: "+sex.getSelectedToggle().toString().split("'")[1]);
            
            user = new user(name.getText(),dob.getValue().toString(),
                    hostel.getValue().toString(), favSpot.getText(),
                    major.getValue().toString(), yearGroup.getValue().toString(),
                    bio.getText(), sex.getSelectedToggle().toString().split("'")[1],
                    null, interests.getValue().toString(), username.getText(),
                    pwField.getText());
            
            mainGraph.insertVertex(user);

            String userDetails = user.toString();

            userDetails = userDetails+"\n";

            Files.write(filePath, userDetails.getBytes(), Files.exists(filePath) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);


        }
        catch(IOException e){

            System.out.println("Error writing to file: "+e);

        }
    }
}