import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Customer{
    private int id, roomId;
    private long dept;
    private String name;
    private String expireDate;
    Customer(String i, String d, String r, String n, String e){
        id = Integer.parseInt(i);
        dept = Integer.parseInt(d);
        roomId = Integer.parseInt(r);
        name = n;
        expireDate = e;
    }
    public boolean isExpired(){
        String temp[] = expireDate.split("/");
        String today[] = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")).split("/");
        //Year Passed
        if(Integer.parseInt(today[0]) > Integer.parseInt(temp[0])){
            return true;
        }
        //Month Passed
        else if((Integer.parseInt(today[0]) == Integer.parseInt(temp[0])) && (Integer.parseInt(today[1]) > Integer.parseInt(temp[1]))){
            return true;
        }
        //Day Passed
        else if((Integer.parseInt(today[0]) == Integer.parseInt(temp[0])) && (Integer.parseInt(today[1]) == Integer.parseInt(temp[1])) && (Integer.parseInt(today[2]) > Integer.parseInt(temp[2]))){
            return true;
        }
        return false;
    }
    public int getId(){
        return this.id;
    }
    public long getDept(){
        return this.dept;
    }
    public int getRoomId(){
        return this.roomId;
    }
    public String getName(){
        return this.name;
    }
    public String getExpireDate(){
        return this.expireDate;
    }
    //Inspired by JavaScript's GetElementById
    public static Customer FindCustomerById(int i){
        for(Customer c : Main.customers){
            if(c.getId() == i){
                return c;
            }
        }
        return null; //customer not found !
    }
	public void extendTime(int days) {
        dept = dept + (days * Room.FindRoomById(this.getRoomId()).getPrice());
        String temp[] = expireDate.split("/");
        //It is considered that all monthes have 30 days and all years have 365 days to avoid complex calculations and increase code readability
        int d = Integer.parseInt(temp[2]) + days;
        int m = Integer.parseInt(temp[1]) + d/30;
        int y = Integer.parseInt(temp[0]) + m/12;
        d = d % 30;
        m = m % 12;
        expireDate = y + "/" + m + "/" + d;
	}
	public void setName(String n){
        this.name = n;
	}
}