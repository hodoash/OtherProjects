package ffgui_javafx;

/**
 * This is a class that stores a user object as well as all its connections as a single string.
 * This class helps with the implementation of generating a graph from a text file
 */
public class UserConnectionsEntry {
    //declare instance variables
    private user person;
    private String connections;

    //declare constructor
    public UserConnectionsEntry(user userObject, String userConnections){
        person = userObject;
        connections = userConnections;
    }

    //Users of this object can only get and not set

    public user getUser() {
        return person;
    }

    public String getConnections() {
        return connections;
    }
}
