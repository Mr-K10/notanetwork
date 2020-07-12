package com.notanetwork;

import java.util.ArrayList;
import java.time.*;
import java.util.String;

import org.omg.PortableServer.AdapterActivator;

/** 
 * An <code> Network </code> object implements 
 * common network for the corresponding members
 * @author kautilya
 * @version 1.1
 */
 
 class SNetwork{
  public static void main(String[] args){
   // create 4 members
   System.out.println("Testing the creating member feature.");
   member Lin = new member("Lin",LocalDate.of(1954,Month.MARCH,4),"I am a Mafia in Mumbai","Australia");
   member Khader = new member("Khader",LocalDate.of(1909,Month.JULY,27),"I was a ruthless Mafia leader of mumbai mafia","Afghanistan");
   member Abdullha = new member("Abdullha",LocalDate.of(1948,Month.APRIL,30),"I am a member of Khader Mafia gang","Iraq");
   // test friend request
   Abdullha.sendRequest(Khader);
   Lin.sendRequest(Khader);
   Khader.showRequests();
   // accept friend request
   Khader.acceptRequest(1);
   Khader.showRequests();
   System.out.println("\nKhader Friend list:\n");
   Khader.showFriends();
   Khader.acceptRequest(1);
   System.out.println("\nKhader Friend list:\n");
   Khader.showFriends();

   // remove friends

   System.out.println("\nAbdullha Friend list:\n");
   Abdullha.showFriends();

   // testing add post feature

   System.out.println("\nTesting Add Post Feature\n");

   member.post Apost = Abdullha.new post("I am dead, Remove me as a friend");  
   Abdullha.postIt(Apost);
   Lin.postIt(Apost);

   //check wall

   System.out.println("\nAbdullha's Wall\n");
   Abdullha.myWall();

   System.out.println("\nAbdullha's Wall for Khader\n");
   Khader.showWall(Abdullha);
   System.out.println("\nAbdullha Friend list:\n");
   Abdullha.showFriends();
   
   // create 2 group
   System.out.println("Creating the groups\n");
   group G1 = new group("Mafia gang","The Mumbai Mafia Gang deals to get you a new Identity like Passport, voter Id and Visas, Gold Smuggling and Currency Exchange. A gang with Ethics.");
   group G2 = new group("Afghan trip","People gather to help Khader in his trip to Afghan to Supply arms and medicines and any kind of support not available in Afghanistan");

   // add members to the group
   System.out.println("Adding Members\n");
   G1.addMember(Abdullha);
   G1.addMember(Lin);
   G1.addMember(Khader);
   System.out.println("Mafia Gang Members:");
   G1.showMembers();
   // post a post in group

   G1.postIt(Apost, Abdullha);
   G1.showWall();

  }

  public static String ordinal(int i) {
   String[] sufixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
   switch (i % 100) {
   case 11:
   case 12:
   case 13:
       return i + "th";
   default:
       return i + sufixes[i % 10];

   }
  }
 }
/**
 * Features for the network:
 * 1) Members have a profile
 * 2) Members have a wall for posts
 * 3) Members can be friends of each other
 * 4) Mutual friendship
 * 5) Groups with members
 * 6) Groups have seperate wall for posts
 * 7) Group posts are send to every member's wall
 */
 
 /**
  * Member: class
  friends: array of members in each member
  friend request: array of requests when accepted tranferred to friends array
  post: class 
  wall: array of posts in member and group class
  */

 /**
  * An <code> member </code> objects implements a member on the network
  Features:
  name
  aboutme
  dob
  place, where member lives
  friends array: Friends receive the post on their walls
  friend request array: permission to be friends
  wall: array of posts
  */

 class member{
  private String name;
  private String aboutme;
  private LocalDate dob;
  private String place;
  ArrayList<member> friends;
  ArrayList<member> requests;
  ArrayList<post> wall;
  public member(String name,LocalDate dob,String aboutme,String place){
   this.name = name;
   this.aboutme = aboutme;
   this.dob = dob;
   this.place = place;
   this.friends = new ArrayList<member>();
   this.requests = new ArrayList<member>();
   this.wall = new ArrayList<post>();
  }

  public void sendRequest(member obj){
   obj.requests.add(this);
  }

  public String getName(){
   return name;
  }

  public void showRequests(){
   for(int i=0;i<requests.size();i++){
    System.out.println(SNetwork.ordinal(i+1)+" request:");
    System.out.println(requests.get(i).describe());
   }

   System.out.println("Call the acceptRequest( number ) to accept the request:");
  }

  public void acceptRequest(int i){
   member obj = requests.get(i-1);
   requests.remove(i-1);
   obj.addFriend(this);
   this.addFriend(obj);
  }

  private void addFriend(member obj){
   this.friends.add(obj);
  }

  public String removeFriend(member obj){
   if(!friends.contains(obj)){
    return obj.name+" is not a friend.";
   }
   friends.remove(obj);
   obj.friends.remove(this);
   return obj.name+" no more a friend.";
  }

  public void showFriends(){
   for(int i=0;i<friends.size();i++){
    System.out.println(SNetwork.ordinal(i+1)+" Friend:");
    System.out.println(friends.get(i).describe());
   }

  }

  public String describe(){
   return "Name : " + name + "\nAbout Me : " + aboutme +"\n";
  }


/**
 * An <code> post </code> object implements a message by a member to its friends or a in a group
 * features:
 * doc: LocalDate of creation
 * message: description of the post
 * owner: the member who send it
 * postIt: Adds the post to the owner and its friend's wall
 */

 public class post{

  private LocalDate doc;
  private String message;
  private member owner;

  public post(String message){
   this.message = message;
   owner = member.this;
   doc = LocalDate.now();
  } 

  public member getOwner(){
   return owner;
  }

  public LocalDate getDoc(){
   return doc;
  }

  public String getMessage(){
   return message;
  }

  public String describe(){
   return "Owner : "+owner.name+"\nDate of Creation : "+doc.toString()+"\nMessage : "+message;
  }

 }

 public void postIt(post p){
  if(p.getOwner()!=this){
   System.out.println("You don't have permission to post this!");
   return;
  }
  wall.add(p);
  for(member f:friends){
   f.wall.add(p);
  }
 }

 public void showWall(member obj){
  //return error if obj is not a friend
  //iterate the wall of the friend and show the formatted version of the posts
  if(obj==this){
   myWall();
   return;
  }
  if(!friends.contains(obj)){
   System.out.println("You don't have access to "+obj.name+" wall");
   return;
  }
  
  for(post p:obj.wall){
   System.out.println("\n"+p.describe()+"\n");
  }

 }

 public void myWall(){
  //iterate and show wall
  
  for(post p:wall){
   System.out.println("\n"+p.describe()+"\n");
  }

 }

 }

/**
 * An <code> group </code> object will create a common wall for group members
 * features:
 * wall: array of posts
 * description
 * name
 * dog LocalDate of gathering
 */

 class group{
  private String name;
  private String about;
  private LocalDate dog;
  private ArrayList<member.post> wall;
  private ArrayList<member> members;

  public group(String name,String about){
   this.name = name;
   this.about = about;
   this.dog = LocalDate.now();
   wall = new ArrayList<member.post>();
   members = new ArrayList<member>();
  }
  public void postIt(member.post p,member obj){
   if(p.getOwner()!=obj){
    System.out.println("You don't have enough permissions");
    return;
   }
   wall.add(p);   
  }

  public void addMember(member obj){
   members.add(obj);
  }

  public void showMembers(){
   for(member mem:members){
    System.out.println(mem.getName());
   }
  }


  public void showWall(){
   //iterate and show wall
   
   for(member.post p:wall){
    System.out.println("\n"+p.describe()+"\n");
   }

  }

  public void removeMember(member obj){
   members.remove(obj);
  }
 }