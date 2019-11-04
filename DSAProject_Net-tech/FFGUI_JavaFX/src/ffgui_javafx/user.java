/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ffgui_javafx;


import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Godlove D. Otoo
 */
public class user {

    /**
     * @param args the command line arguments
     */
    private String name;
    
    private String DOB;
    
    private String bio;
    
    private String picture;
    
    private String favSpot;
    
    
    private ArrayList<String> connections;
    
    private String username;
    
    private String password;
    
    enum YearGroup{
       Freshman, Sophomore, Junior, Senior, Null;
    }
    YearGroup yearGroup;
    
    enum hostel{
        OnCampus, Hosanna, Dufie, Charlotte, Masere, Tanko, Null;
    }
    
    hostel Hostel;
    
    
    enum major{
        BA, CS, MIS, CE, EE, ME, Null;
    }
    major Major;
   
    
    enum sex{
        Male, Female, Other, Null;
    }
    sex Sex;
    
    
    enum interest{
        Sports, Music, Literature, Fashion, Art, Movies, Null;
        
    }
    interest Interest;

    public user(String name, String DOB, String Hostel,String favSpot, String Major, String year_Group,
            String bio, String Sex, String picture, String Interest, String username, String password) {
        
        this.connections = new ArrayList<>();
        
        this.connections.add(0, "dummy");
        
        this.name = name;
        
        this.DOB = DOB;
        
        this.Hostel = hostel.valueOf(Hostel);
        
        this.favSpot = favSpot;
        
        this.Major = major.valueOf(Major);
        
        this.yearGroup = YearGroup.valueOf(year_Group);
        
        this.bio = bio;
        
        this.Sex = sex.valueOf(Sex);
        
        this.picture = picture;
        
        this.Interest = interest.valueOf(Interest);
        
        this.username = username;
        
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getFavSpot() {
        return favSpot;
    }

    public void setFavSpot(String favSpot) {
        this.favSpot = favSpot;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public YearGroup getYearGroup() {
        return yearGroup;
    }

    public void setYearGroup(YearGroup yearGroup) {
        this.yearGroup = yearGroup;
    }

    public hostel getHostel() {
        return Hostel;
    }

    public void setHostel(hostel Hostel) {
        this.Hostel = Hostel;
    }

    public major getMajor() {
        return Major;
    }

    public void setMajor(major Major) {
        this.Major = Major;
    }

    public sex getSex() {
        return Sex;
    }

    public void setSex(sex Sex) {
        this.Sex = Sex;
    }

    public interest getInterest() {
        return Interest;
    }

    public void setInterest(interest Interest) {
        this.Interest = Interest;
    }
    

    
    @Override
    public String toString() {
        return "Name="+ name +
                ", DOB=" + DOB +", Hostel=" + Hostel+
                ", FavSpot=" + favSpot +", Major=" + Major +
                ", YearGroup=" + yearGroup + ", Bio=" + bio+
                ", Sex=" + Sex + ", Picture=" + picture +
                ", Interest=" + Interest + ", Username=" +
                username + ", Password=" + password + 
                ", " + connections.toString();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.username);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final user other = (user) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.DOB, other.DOB)) {
            return false;
        }
        if (!Objects.equals(this.bio, other.bio)) {
            return false;
        }
        if (!Objects.equals(this.picture, other.picture)) {
            return false;
        }
        if (!Objects.equals(this.favSpot, other.favSpot)) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.connections, other.connections)) {
            return false;
        }
        if (this.yearGroup != other.yearGroup) {
            return false;
        }
        if (this.Hostel != other.Hostel) {
            return false;
        }
        if (this.Major != other.Major) {
            return false;
        }
        if (this.Sex != other.Sex) {
            return false;
        }
        if (this.Interest != other.Interest) {
            return false;
        }
        return true;
    }
    
    
    
   public int similarityIndex(user user){
       
    int count=0;
    
    if (user.getInterest()== this.Interest){
        count+=3;
    }
    if (user.getFavSpot() == null ? this.favSpot == null : user.getFavSpot().equals(this.favSpot)){
       count+=1;
   }
    if (user.getYearGroup()==this.yearGroup){
        count+=2;
    }
    if (user.getMajor()==this.Major){
        count+=2;
    }
    if (user.getHostel()== this.Hostel){
        count+=3;
    }
    if (user.Sex!=this.Sex){
        count+=1;
    }
    
    return count;
    }
   
    /**
     *
     * @param user
     */
    public void addConnection(String user){
       
       connections.add(user);
       
   }
   
   
    public static void main(String[] args) {
        
         user student = new user("Kojo","01/05/2015", "Hosanna", 
                 "Library", "MIS", "Freshman", null, "Male", null, "Sports", "kkyei", "sssssssss");
         
         user student1 =new user("William","01/05/2015", "Hosanna", 
                 "Library", "MIS", "Freshman", null, "Male", null, "Music", "kkyei", "sssssssss");
         System.out.println(student1.similarityIndex(student));
    }
    
}
