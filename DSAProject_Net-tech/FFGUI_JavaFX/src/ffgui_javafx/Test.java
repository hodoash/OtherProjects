package ffgui_javafx;

import java.util.List;

public class Test {
    public static void main(String[] args){
        //Create graph of students
        AdjacencyMapGraph database = new AdjacencyMapGraph(false);

        user stud1 = new user("Albert", "Null", "Null", "Null", "Null", "Null", "Null", "Null",
                "Null", "Null", "alby", "heya");
        user stud2 = new user("Banahene","Null", "Null", "Null", "Null", "Null", "Null", "Null",
                "Null", "Null", "Bana", "1234");
        user stud3 = new user("Godloove","Null", "Null", "Null", "Null", "Null", "Null", "Null",
                "Null", "Null", "Godie", "qwerty");
        user stud4 = new user("Kojo","Null", "Null", "Null", "Null", "Null", "Null", "Null",
                "Null", "Null", "kojo", "kojey");

        //Establish connections between objects
        Vertex a  = database.insertVertex(stud1);
        Vertex b  = database.insertVertex(stud2);
        Vertex c  = database.insertVertex(stud3);
        Vertex d  = database.insertVertex(stud4);

        //connect all other objects to stud1
        database.insertEdge(a, b, 3);
        database.insertEdge(a, c, 3);
        database.insertEdge(a, d, 3);

        System.out.println(database);

        System.out.println();
        System.out.println();

        System.out.println("Print LoginDetails currently in database");
        for (LoginDetails key : database.loginData.keySet())
            System.out.println("Username: " + key.getUsername() + "\t\tPassword: " + key.getPassword());

        System.out.println("\nNext, print all vertices in database");
        for (Vertex<user> person : database.loginData.values())
            System.out.println(person.getElement().toString());

        //login with Godlove and check to see if it works
        Vertex<user> loggedUser = database.login("Bana", "1234");
        System.out.println();


        //Finally, make sure search works
        System.out.println();

        List<Vertex<user>> results = database.DFS();

        System.out.println("Printing out the search results\n");
        for (Vertex<user> u : results)
            System.out.println(u.getElement().toString());

        System.out.println();
        System.out.println(database.currentUser.getElement());

        System.out.println("Logout user and check current user variable");
        database.logout();

        if (database.currentUser == null)
            System.out.println("The user was successfully removed");
        }
}
