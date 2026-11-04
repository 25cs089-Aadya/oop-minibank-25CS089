public class Cinema {
    private String title;
    private int seatAvailable;
    private final int capacity;
    private static int totalbooked=0;
    Cinema(String title, int capacity){
        this.title=title;
        this.capacity=capacity;
        this.seatAvailable=capacity;
    }
    Cinema(String title){
        this(title,100);
    }   
    public boolean book(int n){
        if(n<=seatAvailable){
            seatAvailable-=n;
            totalbooked+=n;
            return true;
        }
        else{
            return false;
        }
    }
    public void cancel(int n){
        seatAvailable+=n;
        if(seatAvailable>capacity){
            seatAvailable=capacity;
        }
    }
    public int getSeatAvailable(){
        return seatAvailable;
    }
    public static int gettotalbooked(){
        return totalbooked;
    }
    public static void main(String args[]){
        Cinema c1=new Cinema("I",200);
        Cinema c2=new Cinema("II");
            c1.book(10);
            c2.cancel(7);
        System.out.println("Seats available "+c1.getSeatAvailable());
        System.out.println("Total Booked "+c1.gettotalbooked());
    }

}