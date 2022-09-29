/*
Since other information such as Parking, Phone Number, National Number or ...
can be implemented as same as Name and do not affect program functions
they are not implemented by default to increase
code readablity and they can be added if needed.

In one of the customers set by default (Ali Asghari) this is shown
by adding his National Number after his name.

This also helps search system implemented in the menu bar since search works on name
it can find customers by National Number or even Phone Number if preferred.
*/

import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Main{
    //Prefrences
    public static String title = "ezHotel", unit = "Tomans", day;
    public static int defx = 1500, defy = 800;
    //Data Stacks
    public static ArrayList<Room> rooms = new ArrayList<Room>();
    public static ArrayList<Customer> customers = new ArrayList<Customer>();
    public static ArrayList<Long> earns = new ArrayList<Long>();
    public static ArrayList<Long> maxEarns = new ArrayList<Long>();
    //Main Window
    public static Window window1;
    public static void main(String[] args) {
        //Loading Database
        try{
            //Rooms
            BufferedReader rreader = new BufferedReader(new FileReader("rooms.db"));
            String room = rreader.readLine();
            while(room != null){
                String temp[] = room.split("\\*");
                rooms.add(new Room(temp[0], temp[1], temp[2], temp[3], temp[4], temp[5]));
                room = rreader.readLine();
            }
            rreader.close();
            //Customers 
            BufferedReader creader = new BufferedReader(new FileReader("customers.db"));
            String customer = creader.readLine();
            while(customer != null){
                String temp[] = customer.split("\\*");
                customers.add(new Customer(temp[0], temp[1], temp[2], temp[3], temp[4]));
                customer = creader.readLine();
            }
            creader.close();
            //Earnings
            BufferedReader ereader = new BufferedReader(new FileReader("earnings.db"));
            //First line is the date to check day change
            day = ereader.readLine();
            String earn = ereader.readLine();
            while(earn != null){
                String temp[] = earn.split("\\*");
                earns.add(Long.parseLong(temp[0]));
                maxEarns.add(Long.parseLong(temp[1]));
                earn = ereader.readLine();
            }
            ereader.close();
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(new JFrame(), "Some Databases Are Missing Or Inaccessable !", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        //Main Window
        dataUpdate();
        window1 = new Window(title, defx, defy);
    }
    //Window Update
    public static void reopenWin(){
        window1.dispose();
        window1 = new Window(title, defx, defy);
    }
    public static void dataUpdate(){
        //Check day change
        /*
        To check day change we use a temporary object from customer class
        with its expire date set to last known date saved in "day" and use
        its isExpired() method to check if day has changed and we need to
        save last day's data.
        */
        if(day == null){ //First day & if "earnings.db" is missing
            day = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        }
        if(new Customer("0", "0", "0", "", day).isExpired()){
            day = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            earns.add(Room.getTodayEarn());
            maxEarns.add(Room.getTotalEarn());
        }
        //Saving Database
        try{
            //Rooms
            File rfile = new File("rooms.db");
            if(!rfile.delete()){ 
                JOptionPane.showMessageDialog(new JFrame(), "Rooms Database Not Found ! Creating One ...", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            rfile = new File("rooms.db");
            rfile.createNewFile();
            BufferedWriter rwriter = new BufferedWriter(new FileWriter(rfile));
            for(Room r : rooms){
                rwriter.write(r.getId() + "*" + r.getBeds() + "*" + r.getPrice() + "*" + r.isReserved() + "*" + r.emptyDate() + "*" + r.getReserverId());
                rwriter.newLine();
            }
            rwriter.close();
            //Customers
            File cfile = new File("customers.db");
            if(!cfile.delete()){ 
                JOptionPane.showMessageDialog(new JFrame(), "Customers Database Not Found ! Creating One ...", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            cfile = new File("customers.db");
            cfile.createNewFile();
            BufferedWriter cwriter = new BufferedWriter(new FileWriter(cfile));
            for(Customer c : customers){
                cwriter.write(c.getId() + "*" + c.getDept() + "*" + c.getRoomId() + "*" + c.getName() + "*" + c.getExpireDate());
                cwriter.newLine();
            }
            cwriter.close();
            //Earnings
            File efile = new File("earnings.db");
            if(!efile.delete()){ 
                JOptionPane.showMessageDialog(new JFrame(), "Earnings Database Not Found ! Creating One ...", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            efile = new File("earnings.db");
            efile.createNewFile();
            BufferedWriter ewriter = new BufferedWriter(new FileWriter("earnings.db"));
            //First line is the date to check day change
            ewriter.write(day);
            ewriter.newLine();
            for(int i = 0; i < earns.size(); i++){
                ewriter.write(earns.get(i) + "*" + maxEarns.get(i));
                ewriter.newLine();
            }
            ewriter.close();
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(new JFrame(), "An Error Occurred While Writing To The Database", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Program Exit
    public static void end(){
        window1.dispose();
        dataUpdate();
        System.exit(0);
    }
}