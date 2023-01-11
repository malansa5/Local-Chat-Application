import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.io.FileNotFoundException;

public class ChatApplication {
private static Scanner x;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, FileNotFoundException {
        // TODO code application logic here
        String username,password,room_name,message ="";
        //String roomName = "";
        String newUsername, newPassword = "";
        int count= 0;
        int usernameCount=0;
        int userCheck = 0;
        Scanner scanner=new Scanner(System.in);
        FileWriter room_writer=null;
        //FileWriter file_writer= new FileWriter("register.txt",true);
        //FileWriter chat_writer = new FileWriter("chatroom.txt",true);

        while(true){
            System.out.println("Welcome to HMR's Chat App!\n");
            System.out.println("Please select from the following options:");
            System.out.println("(R)egister, (L)ogin, (Q)uit");
            System.out.println("-----------------------------------------");
            String option=scanner.nextLine();
            switch(option){
                case "register":
                    userCheck = 0;
                    File file1 = new File("register.txt");
                    FileWriter file_writer= new FileWriter("register.txt",true);
                    System.out.println("Set username: ");
                    username=scanner.nextLine();
                    System.out.println("Set Password: ");
                    password=scanner.nextLine();
                    Scanner usernameReader = new Scanner(file1);
                    while(usernameReader.hasNextLine()){
                        //String data1 = usernameReader.nextLine();
                        String[] userTemp = usernameReader.nextLine().split(",");
                        if(userTemp[0].equals(username)){
                            userCheck++;
                        }
                    }
                    if(userCheck == 0){
                    file_writer.write(username+","+password+",none\n" );
                    file_writer.close();
                    System.out.println("\nUsername and Password stored!\n");
                    } else{
                        System.out.println("\nError: This username has already been made!");
                        System.out.println("You must choose an orignal username\n");
                    }
                    
                    usernameReader.close();
                    break;
                case "login":
                    File file = new File("register.txt");
                    System.out.print("username: ");
                    username=scanner.nextLine();
                    System.out.print("\npassword: ");
                    password=scanner.nextLine();
                    Scanner reader = new Scanner(file);
                    while (reader.hasNextLine()) {
                        String data = reader.nextLine();
                        String[] temp=data.split(",");
                        if(temp[0].equals(username)){
                            usernameCount++;
                            if(temp[1].equals(password)){
                                
                                System.out.println("\nLogin successful");
                                while(true) {
                                    System.out.println("\nPlease select from the following options:");
                                    System.out.println("(J)oin, (C)reate, (A)ccount, (L)ogout");
                                    System.out.println("-------------------------------------------");
                                    String choice = scanner.nextLine();

                                    
                                    if (choice.equals("join") || choice.equals("J")) {
                                        

                                        File chatroomFile = new File("chatroom.txt");
                                        Scanner reader1 = new Scanner(chatroomFile);

                                        System.out.println("Enter room name to join: ");
                                        room_name = scanner.nextLine();
                                        while (reader1.hasNextLine()) {
                                            String room = reader1.nextLine();

                                            if (room.equals(room_name)) { 
                                                count++;
                                                setStatus(username, room_name);
                                                System.out.println("\nRoom found!\n");
                                                
                                                
                                                while (!message.equals("/leave")) {
                                                    System.out.println("Welcome to the chatroom: '" + room_name + "', user: '" + username + "'");
                                                    System.out.println("Type '/help' for help.");
                                                    System.out.println("-------------------------------------------");
                                                    message = scanner.nextLine();
                                                    if (message.equals("/leave")) {
                                                        message = "";
                                                        //room_name = "none";
                                                        setStatus(username, "none");
                                                        System.out.println("\nYou have left the chat room");
                                                        break;
                                                    } else if (message.equals("/list")) {
                                                        ArrayList<String> onlineUsers = getOnlineUsers(room_name);
                                                        System.out.println("Online users: " + String.join(", ", onlineUsers));
                                                    } else if (message.equals("/history")) {
                                                        File file_1 = new File(room_name + ".txt");
                                                        Scanner reader_1 = new Scanner(file_1);
                                                        while (reader_1.hasNextLine()) {
                                                            System.out.println(reader_1.nextLine());
                                                        }
                                                        System.out.println();
                                                        reader_1.close();
                                                    } else if (message.equals("/help")) {
                                                        System.out.println("\n\nThe available commands are:");
                                                        System.out.println("/list ------- This command shows the list of users in the chatroom");
                                                        System.out.println("/leave ------ This command exits the user from the chatroom");
                                                        System.out.println("/history ---- This command shows the history of messages in the chatroom\n");

                                                    } else if(message.substring(0,1).equals("/")){
                                                        System.out.println("\nInvalid command!");
                                                        System.out.println("Type /help to see available commands :)\n");
                                                    }else {
                                                        System.out.println("\n" + username + ":" + message +"\n");
                                                        room_writer = new FileWriter(room_name + ".txt", true);
                                                        room_writer.write(username + ":" + message + "\n");
                                                        room_writer.close();

                                                    }
                                                }
                                            } 
                                        }
                                        if (count != 1){

                                            System.out.println("\nError: Room not found!");
                                            System.out.println("You must create a room or join one that has been created");
                                        }
                                        count = 0;
                                        reader1.close();
                                        
                                    }//added }
                                    else if(choice.equals("create") || choice.equals("C")){

                                        File file2 = new File("chatroom.txt");
                                        userCheck =0;
                                        FileWriter chat_writer = new FileWriter("chatroom.txt",true);
                                        System.out.println("Enter room name to create: ");
                                        room_name = scanner.nextLine();
                                        Scanner roomReader = new Scanner(file2);
                                        while(roomReader.hasNextLine()){
                                            //String data1 = usernameReader.nextLine();
                                            String roomTemp = roomReader.nextLine();
                                            if(roomTemp.equals(room_name)){
                                                userCheck++;
                                            } 
                                        }
                                        if(userCheck == 0){
                                            chat_writer.write(room_name+ "\n");
                                            chat_writer.close();
                                        System.out.println("\n" + room_name + " has been created!\n");
                                        } else{
                                            System.out.println("\nError: This chatroom has already been made!");
                                            System.out.println("You must choose an orignal chatroom\n");
                                        }
                                        
                                        roomReader.close();
                                        //chat_writer.write(room_name+ "\n");
                                        //chat_writer.close();
                                       

                                    }

                                    else if(choice.equals("account") || choice.equals("A")){
                                        
                                        String filepath = "register.txt";
                                        System.out.println("Set new username: ");
                                        newUsername = scanner.nextLine();
                                        System.out.println("Set new password: ");
                                        newPassword = scanner.nextLine();

                                            updateRecord(username, newUsername, newPassword, filepath); //method used to update
                                        }

                                    else if(choice.equals("logout")){
                                        System.out.println("\n" + username + " has logged out\n" );
                                        break;
                                    }
                                }


                            }
                            else{
                                System.out.println("\nInvalid password!\n");
                            }
                        }
                        

                    }
                    if(usernameCount == 0){
                        System.out.println("\nInvalid username!\n");
                    }
                    usernameCount = 0;
                    reader.close();

                    break;
                case "quit":
                    System.exit(0);
            }
        
        }
        
    }

  
    public static void setStatus(String userName, String roomName) throws FileNotFoundException, IOException {
        x = new Scanner(new File("register.txt"));
        FileWriter fw = new FileWriter("users-temp.txt", true);
        BufferedWriter f_users_temp = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(f_users_temp);
    
        while (x.hasNextLine()) {
          String userLine = x.nextLine();
          
          String[] userInfo = userLine.split(",");
          String oldName = userInfo[0];
          String oldPass = userInfo[1];
    
          if (oldName.equalsIgnoreCase(userName)) {
            pw.println(oldName + "," + oldPass + "," + roomName);
          } else {
            pw.println(oldName + "," + oldPass + "," + "none");
          }
        }
    
        f_users_temp.close();
        x.close();
    
        File usersTemp = new File("users-temp.txt");
        File usersDB = new File("register.txt");
        usersTemp.renameTo(usersDB);
      }


    public static ArrayList<String> getOnlineUsers(String roomName)throws FileNotFoundException{
        x = new Scanner(new File("register.txt"));

    ArrayList<String> onlineUsers = new ArrayList<String>();
    while (x.hasNextLine()) {
      String[] userInfo = x.nextLine().split(",");
      String userName = userInfo[0];
      String status = userInfo[2];
      
      if (status.equalsIgnoreCase(roomName)) {
        onlineUsers.add(userName);
      }
    }

    x.close();
    return onlineUsers;
    }

    public static void updateRecord(String oldUsername, String newUsername, String newPassword, String filePath)throws IOException, NoSuchElementException{ 
        String tempFile = "temp.txt";
        File oldFile = new File(filePath);
        File newFile = new File(tempFile);
        String username, password = "";
        //try{
            FileWriter fw = new FileWriter(tempFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            x = new Scanner(new File(filePath));
            

            while(x.hasNextLine()){
                String[] userInfo = x.nextLine().split(",");
                username = userInfo[0];
                password = userInfo[1];
                if(username.equals(oldUsername)){
                    pw.println(newUsername + "," + newPassword);
                } 
                else{
                    pw.println(username + "," + password);
                }
                
            }
            x.close();
                pw.flush();
                pw.close();
                File dump = new File(filePath);
                if(newFile.renameTo(dump)){
                
                newFile.delete();
            }
        //}
           /*  catch(Exception e){
                System.out.println("Error");
            }*/

        }
}