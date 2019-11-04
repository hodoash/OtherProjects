package ffgui_javafx;

import java.util.*;


@SuppressWarnings("ALL")
public class AdjacencyMapGraph implements Graph<user,Integer> {
    private static final int MAXCONNECTIONFORDUMMY = 10;
    //--------------------------------Implement InnerVertex and InnerEdge Class---------------------------

    //---------------- nested Vertex class ----------------
    /** A vertex of an adjacency map graph representation. */
    private class InnerVertex implements Vertex<user>, Comparable<Vertex<user>> {
        private user element;
        private Position<Vertex<user>> pos;
        private Map<Vertex<user>, Edge<Integer>> outgoing, incoming;

        /** Constructs a new InnerVertex instance storing the given element. */
        public InnerVertex(user elem, boolean graphIsDirected) {
            element = elem;
            outgoing = new ProbeHashMap<>();
            if (graphIsDirected)
                incoming = new ProbeHashMap<>();
            else
                incoming = outgoing;    // if undirected, alias outgoing map
        }

        /** Validates that this vertex instance belongs to the given graph. */
        public boolean validate(Graph<user,Integer> graph) {
            return (AdjacencyMapGraph.this == graph && pos != null);
        }

        /** Returns the element associated with the vertex. */
        public user getElement() { return element; }

        /** Stores the position of this vertex within the graph's vertex list. */
        public void setPosition(Position<Vertex<user>> p) { pos = p; }

        /** Returns the position of this vertex within the graph's vertex list. */
        public Position<Vertex<user>> getPosition() { return pos; }

        /** Returns reference to the underlying map of outgoing edges. */
        public Map<Vertex<user>, Edge<Integer>> getOutgoing() { return outgoing; }

        /** Returns reference to the underlying map of incoming edges. */
        public Map<Vertex<user>, Edge<Integer>> getIncoming() { return incoming; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof InnerVertex)) return false;
            InnerVertex that = (InnerVertex) o;
            return Objects.equals(element, that.element) &&
                    Objects.equals(pos, that.pos) &&
                    Objects.equals(outgoing, that.outgoing) &&
                    Objects.equals(incoming, that.incoming);
        }

        @Override
        public int hashCode() {
            return Objects.hash(element);
        }

        //Add compareTo so it can be sorted
        public int compareTo(Vertex<user> otherVertex){
            int callingObjectValue = this.getElement().similarityIndex(currentUser.getElement());
            int comparingObjectValue = otherVertex.getElement().similarityIndex(currentUser.getElement());

            //ascending order
            //return callingObjectValue - comparingObjectValue;

            //descending order
            return comparingObjectValue - callingObjectValue;
        }
    } //------------ end of InnerVertex class ------------


    //---------------- nested InnerEdge class ----------------
    /** An edge between two vertices. */
    @SuppressWarnings("TypeParameterHidesVisibleType")
    private class InnerEdge implements Edge<Integer> {
        private Integer element;
        private Position<Edge<Integer>> pos;
        private Vertex<user>[] endpoints;

        @SuppressWarnings({"unchecked"})
        public InnerEdge(Vertex<user> u, Vertex<user> v, Integer elem) {
            element = elem;
            endpoints = (Vertex<user>[]) new Vertex[]{u,v};  // array of length 2
        }

        /** Returns the element associated with the edge. */
        public Integer getElement() { return element; }

        /** Returns reference to the endpoint array. */
        public Vertex<user>[] getEndpoints() { return endpoints; }

        /** Validates that this edge instance belongs to the given graph. */
        public boolean validate(Graph<user,Integer> graph) {
            return AdjacencyMapGraph.this == graph && pos != null;
        }

        /** Stores the position of this edge within the graph's vertex list. */
        public void setPosition(Position<Edge<Integer>> p) { pos = p; }

        /** Returns the position of this edge within the graph's vertex list. */
        public Position<Edge<Integer>> getPosition() { return pos; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof InnerEdge)) return false;
            InnerEdge innerEdge = (InnerEdge) o;
            return Objects.equals(element, innerEdge.element) &&
                    Objects.equals(pos, innerEdge.pos) &&
                    Arrays.equals(endpoints, innerEdge.endpoints);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(element);
            result = 31 * result + Arrays.hashCode(endpoints);
            return result;
        }
    } //------------ end of InnerEdge class ------------


    //-------------------------------End of implementation of Inner classes------------------------------------------




    //Instance Variables
    private boolean isDirected;
    private PositionalList<Vertex<user>> vertices = new LinkedPositionalList<>( );
    private PositionalList<Edge<Integer>> edges = new LinkedPositionalList<>( );

    protected Vertex<user> currentUser = null;
    //create dummy user
    private user dummyUser = new user("Dummy", "Null", "Null", "Null", "Null", "Null",
            "Null", "Null", "Null", "Null", "Null", "Null");
    protected Vertex<user> dummyVertex;
    protected Map<LoginDetails, Vertex<user>> loginData = new ProbeHashMap<>();


    /**Constructs an empty graph (either undirected or directed).*/
    public AdjacencyMapGraph(boolean directed) {
        isDirected = directed;
        //add dummy user to vertex list
        this.dummyVertex = insertVertex(this.dummyUser);
    }


    //----------------Utility Validate Methods-------------------------
    private InnerVertex validate(Vertex<user> v){
        if (!(v instanceof InnerVertex))
            throw new IllegalArgumentException("Sorry,the vertex provided is not an inner vertex of this graph");
        //By this point, v is a valid instance and can be safely cast
        InnerVertex vertex = (InnerVertex) v;
        //FIGURE OUT A WAY TO INVALIDATE VERTEX AND UPDATE Method(convention for removed edge)
        return vertex;
    }


    private InnerEdge validate(Edge<Integer> e){
        if (!(e instanceof InnerEdge))
            throw new IllegalArgumentException("Sorry,the vertex provided is not an inner vertex of this graph");
        //By this point, v is a valid instance and can be safely cast
        InnerEdge edge = (InnerEdge) e;
        if (edge.getPosition() == null)
            throw new IllegalArgumentException("Sorry, this edge has been removed");
        return edge;
    }


    //----------------End of utility methods--------------------------




    /**Returns the number of vertices of the graph*/
    public int numVertices( ) { return vertices.size( ); }

    /**Returns the vertices of the graph as an iterable collection*/
    public Iterable<Vertex<user>> vertices( ) { return vertices; }

    /**Returns the number of edges of the graph*/
    public int numEdges( ) { return edges.size(); }

    /**Returns the edges of the graph as an iterable collection*/
    public Iterable<Edge<Integer>> edges( ) { return edges; }

    /**Returns the number of edges for which vertex v is the origin.*/
    public int outDegree(Vertex<user> v) {
        InnerVertex vert = validate(v);
        return vert.getOutgoing( ).size( );
    }

    /**Returns and iterable collection of edges for which vertex v is the origin*/
    public Iterable<Edge<Integer>> outgoingEdges(Vertex<user> v) {
        InnerVertex vert = validate(v);
        return vert.getOutgoing().values();         // edges are the values in the adjacency map
    }

    /**Returns the number of edges for which vertex v is the destination*/
    public int inDegree(Vertex<user> v){
        InnerVertex vert = validate(v);
        return vert.getIncoming().size();
    }

    /**Returns an iterable collection of edges for which vertex v is the destination. */
    public Iterable<Edge<Integer>> incomingEdges(Vertex<user> v) {
        InnerVertex vert = validate(v);
        return vert.getIncoming().values();             //Note that edges are values in the adjecency map
    }

    /**Returns the edge from u to v, or null if they are not adjacent.*/
    public Edge<Integer> getEdge(Vertex<user> u, Vertex<user> v){
        InnerVertex origin = validate(u);
        return origin.getOutgoing().get(v);         //this will be null if no edge exist between u and v
    }

    /**Returns the vertices of edge e as an array of length two.*/
    public Vertex<user>[ ] endVertices(Edge<Integer> e) {
        InnerEdge edge = validate(e);
        return edge.getEndpoints();
    }

    /**Returns the vertex that is opposite vertex v on edge e. */
    public Vertex<user> opposite(Vertex<user> v, Edge<Integer> e) throws IllegalArgumentException{
        InnerEdge edge = validate(e);
        Vertex<user>[] endpoints = edge.getEndpoints();
        if (endpoints[0] == v)
            return endpoints[1];
        else if(endpoints[1] == v)
            return endpoints[0];
        else
            throw new IllegalArgumentException("The vertex v is ont incident to this edge");
    }


    /**Inserts and returns a new edge between u and v, storing given element.*/
    public Vertex<user> insertVertex(user element){
        //create inner vertex
        InnerVertex v = new InnerVertex(element, isDirected);
        //add to collection of vertices of the graph
        v.setPosition(vertices.addLast(v));
        //Create LoginDetails object and add to loginData Map for checking when user is logging in
        LoginDetails userLoginDetails = null;
        if (element != this.dummyUser)              //Don't add dummy to loginDatabase
            userLoginDetails = new LoginDetails(element.getUsername(), element.getPassword());
        if (userLoginDetails != null)               //put if the element was not the dummy
            this.loginData.put(userLoginDetails, v);
        //link all users to dummy at creation
        if (element != this.dummyUser)
            insertEdge(v, this.dummyVertex, 0);
        return v;
    }

    /**Inserts and returns a new edge between u and v, storing given element.*/
    public Edge<Integer> insertEdge(Vertex<user> v, Vertex<user> u, Integer element) throws IllegalArgumentException{
        if (getEdge(v, u) == null){         //if there is no edge between the vertices
            InnerEdge e = new InnerEdge(v, u, element);    //create new edge
            e.setPosition(edges.addLast(e));
            InnerVertex origin = validate(v);
            InnerVertex dest = validate(u);
            origin.getOutgoing().put(u, e);
            dest.getIncoming().put(v, e);
            //for both vertex, if there is still a connection with the dummy and vertex, and v has more than min, disconnect from dummy
            if (outDegree(v) > MAXCONNECTIONFORDUMMY & getEdge(v, this.dummyVertex) != null)
                removeEdge(getEdge(v, this.dummyVertex));
            if (outDegree(u) > MAXCONNECTIONFORDUMMY & getEdge(u, this.dummyVertex) != null)
                removeEdge(getEdge(u, this.dummyVertex));

            //add the string of each username to the other
            if (v != this.dummyVertex && u != this.dummyVertex) {
                origin.getElement().addConnection(dest.getElement().getUsername());
                dest.getElement().addConnection(origin.getElement().getUsername());
            }

            return e;           //return the edge
        }
        else
            throw new IllegalArgumentException("There is already an edge between u and v");
    }

    /**Removes a vertex and all its incident edges from the graph*/
    public void removeVertex(Vertex<user> v){
        InnerVertex vert = validate(v);
        //remove all incident edges from the graph
        for (Edge<Integer> e : vert.getOutgoing().values())
            removeEdge(e);
        for (Edge<Integer> e : vert.getIncoming().values())
            removeEdge(e);

        //Finally, remove this vertex from the list of vertices
        vertices.remove(vert.getPosition());
    }

    @SuppressWarnings({"unchecked"})
    public void removeEdge(Edge<Integer> e) throws IllegalArgumentException {
        InnerEdge edge = validate(e);
        // remove this edge from vertices' adjacencies
        InnerVertex[] verts = (InnerVertex[]) edge.getEndpoints();
        verts[0].getOutgoing().remove(verts[1]);
        verts[1].getIncoming().remove(verts[0]);
        // remove this edge from the list of edges
        edges.remove(edge.getPosition());
        edge.setPosition(null);             // invalidates the edge
    }

    /**
     * Returns a string representation of the graph.
     * This is used only for debugging; do not rely on the string representation.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Vertex<user> v : vertices) {
            sb.append("Vertex " + v.getElement() + "\n");
            if (isDirected)
                sb.append(" [outgoing]");
            sb.append(" " + outDegree(v) + " adjacencies:");
            for (Edge<Integer> e: outgoingEdges(v))
                sb.append(String.format(" (%s, %s)", opposite(v,e).getElement(), e.getElement()));
            sb.append("\n");
            if (isDirected) {
                sb.append(" [incoming]");
                sb.append(" " + inDegree(v) + " adjacencies:");
                for (Edge<Integer> e: incomingEdges(v))
                    sb.append(String.format(" (%s, %s)", opposite(v,e).getElement(), e.getElement()));
                sb.append("\n");
            }
        }
        return sb.toString();
    }


    //-----------------------------------Implementation of methods peculiar to project---------------------------------------

    /**
     * Performs depth-first search of graph G starting at vertex current.
     * This is utility that aids in the generation of search results
     *
     * @param current is the node that is currently being worked on. At the first call, it is the baseUser for the search
     * @param known this is Map that keeps track of elements that have already been added, so they are not duplicated
     * @param resultList this is a list that is built up and returned as the result of the entire search
     * @param searchDepth this is an integer that designates how deeply we search the tree
     */
    private void DFSUtility(Vertex<user> current,
                            Set<Vertex<user>> known, List<Vertex<user>> resultList){
        known.add(current);
        for (Edge<Integer> e : this.outgoingEdges(current)){
            Vertex<user> v = this.opposite(current, e);
            System.out.println(v.getElement());
            if (!known.contains(v)){           //edge has not been iterated and there isn't already a connected
                if (this.currentUser.getElement().similarityIndex(v.getElement()) >= 5)             //only add to results if similarityIndex >= 5
                    resultList.add(v);
               DFSUtility(v, known, resultList);
            }
        }
    }

    /**
     * This is a method that returns search results. Only search depth required as parameter
     * @param searchDepth is an interger that specifies how deep the search should go from the current vertice
     * @return
     */
    public List<Vertex<user>> DFS(){
        //create set to keep track of Vertices that have already been discovered in search
        Set<Vertex<user>> known = new HashSet<Vertex<user>>();
        //create list to build up results
        List<Vertex<user>> searchList = new ArrayList<>();
        //call utility search method
        DFSUtility(this.currentUser, known, searchList);

        //return sorted list in ascending order in relation to the similarity-index with current user
        //Collections.sort(searchList);
        return searchList;
    }

    /**
     * login takes a string and a name as arguments and return the vertice of the user (null if no details match found)
     * @param name name of the user as a string
     * @param password password of the user as a string
     * @return
     */
    protected Vertex<user> login(String name, String password){
        //create login object
        LoginDetails details = new LoginDetails(name, password);
        Vertex<user> loginVertexReturnedFromCheck = loginData.get(details);
        if ((loginVertexReturnedFromCheck == null))                //match not found
            return null;
        //Set current user to be the user who is logging in
        this.currentUser = loginVertexReturnedFromCheck;
        return this.currentUser;                       //return the vertex so that it can be used if needed
    }

    /**
     *logout method simply resets the current user variable, such that no user is currently using the app. logout
     * takes no parameters
     */
    protected void logout(){this.currentUser = null;}



    //-----------------------------------This section has to do with generating a graph from user objects---------------------
    public void populateGraph(List<UserConnectionsEntry> userList){
        //Create an ArrayList that will store the UserConnectionEntry objects for later iteration
        List<UserConnectionsEntry> userEntryObjects = new ArrayList<>();
        //Create map to store username(key) and correponding Vertex(value)
        HashMap<String, Vertex<user>> usernameMap = new HashMap<>();
        for (UserConnectionsEntry personEntry : userList){
            Vertex<user> createdUser = insertVertex(personEntry.getUser());
            //add person's username and Vertex to hashMap
            usernameMap.put(createdUser.getElement().getUsername(), createdUser);

            //add userEntryObject to list
            userEntryObjects.add(personEntry);
        }

        //Next, loop through userEntryObjects and create all connections
        for (UserConnectionsEntry e : userEntryObjects){
            //Assume that current person is also connected to another user (by default, everyone is at least connected to the dummy).
            //This means that, the user has been added to our usernameMap.
            Vertex<user> person = usernameMap.get(e.getUser().getUsername());
            if (person != null){                //make sure vertex was found
                //split connections String into distinct usernames
                Scanner scan = new Scanner(e.getConnections()).useDelimiter("[^A-Za-z0-9]+");
                //Now iterate through usernames, find their vertices and create connections if needed
                while (scan.hasNext()){
                    String username = scan.next();
                    //next get Vertex of username (will return null if not found)
                    Vertex<user> uservertex = usernameMap.get(username);
                    if (uservertex != null){            //Ensure vertex was found to avoid errors
                        if (getEdge(person, uservertex) == null){           //Make sure there isn't an edge connecting vertices already
                            //insert edge between two vertices
                            insertEdge(person, uservertex, person.getElement().similarityIndex(uservertex.getElement()));
                        }
                    }
                }
            }
            else
                System.out.println("The user vertice could not be found from the username provided");

        }

    }

    //-------------------------------------------End of generating graphs section----------------------------------


}
