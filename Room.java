public class Room{
    private int id, beds, price, reserverId;
    private boolean reserved;
    private String emptydate;
    Room(String i, String b, String p, String r, String e, String ri){
        id = Integer.parseInt(i);
        beds = Integer.parseInt(b);
        price = Integer.parseInt(p);
        reserved = Boolean.parseBoolean(r);
        emptydate = e;
        reserverId = Integer.parseInt(ri);
    }
    public int getId(){
        return this.id;
    }
    public int getPrice(){
        return this.price;
    }
    public int getBeds(){
        return this.beds;
    }
    public int getReserverId(){
        return this.reserverId;
    }
    public boolean isReserved(){
        return this.reserved;
    }
    public String emptyDate(){
        return this.emptydate;
    }
    //Inspired by JavaScript's GetElementById
    public static Room FindRoomById(int i){
        for(Room r : Main.rooms){
            if(r.getId() == i){
                return r;
            }
        }
        return null; //room not found !
    }
	public void setEmptyDate(String date){
        emptydate = date;
    }
	public void clear(){
        this.reserved = false;
	}
	public void setBeds(int b){
        this.beds = b;
	}
	public void setPrice(int p){
        this.price = p;
	}
	public void reserve(int i) {
        this.reserverId = i;
        this.reserved = true;
        this.emptydate = Customer.FindCustomerById(i).getExpireDate();
    }
    //Today's Maximum Earnings
	public static long getTotalEarn() {
        long t = 0;
		for(Room r : Main.rooms){
            t = t + r.getPrice();
        }
        return t;
    }
    //Today's Earnings
	public static long getTodayEarn() {
		long t = 0;
		for(Room r : Main.rooms){
            if(r.isReserved()){
                t = t + r.getPrice();
            }
        }
        return t;
	}
	public static long getMonthTotalEarn() {
        long t = 0;
		for(int i = Main.maxEarns.size() - 1; i >= Main.maxEarns.size() - 30; i--){
            t = t + Main.maxEarns.get(i);
        }
        return t;
	}
	public static long getMonthEarn() {
		long t = 0;
		for(int i = Main.earns.size() - 1; i >= Main.maxEarns.size() - 30; i--){
            t = t + Main.earns.get(i);
        }
        return t;
    }
    public static long getYearTotalEarn() {
        long t = 0;
		for(int i = Main.maxEarns.size() - 1; i >= Main.maxEarns.size() - 365; i--){
            t = t + Main.maxEarns.get(i);
        }
        return t;
	}
	public static long getYearEarn() {
		long t = 0;
		for(int i = Main.earns.size() - 1; i >= Main.maxEarns.size() - 365; i--){
            t = t + Main.earns.get(i);
        }
        return t;
    }
    public static long getWeekTotalEarn() {
        long t = 0;
		for(int i = Main.maxEarns.size() - 1; i >= Main.maxEarns.size() - 7; i--){
            t = t + Main.maxEarns.get(i);
        }
        return t;
	}
	public static long getWeekEarn() {
		long t = 0;
		for(int i = Main.earns.size() - 1; i >= Main.maxEarns.size() - 7; i--){
            t = t + Main.earns.get(i);
        }
        return t;
	}
}