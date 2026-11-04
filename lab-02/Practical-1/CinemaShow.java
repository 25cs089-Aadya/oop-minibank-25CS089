public class CinemaShow {
    private String title;
    private int seatsAvailable;
    private final int capacity;
    private static int totalBooked=0;
    CinemaShow(String title, int capacity){
        this.title=title;
        this.capacity=capacity;
        this.seatsAvailable=capacity;
    }
    CinemaShow(String title){
        this(title,100);
    }   
    public boolean book(int n){
        if(n<=seatsAvailable){
            seatsAvailable-=n;
            totalBooked+=n;
            return true;
        }
        else{
            return false;
        }
    }
    public void cancel(int n){
        seatsAvailable+=n;
        if(seatsAvailable>capacity){
            seatsAvailable=capacity;
        }
    }
    public int getSeatsAvailable(){
        return seatsAvailable;
    }
    public static int getTotalBooked(){
        return totalBooked;
    }
    public static void main(String args[]){
        CinemaShow c1=new CinemaShow("I",200);
        CinemaShow c2=new CinemaShow("II");

        System.out.println("Book 10 in I: "+c1.book(10)+", seats left: "+c1.getSeatsAvailable());
        System.out.println("Book 250 in I: "+c1.book(250)+", seats left: "+c1.getSeatsAvailable());
        c1.cancel(4);
        System.out.println("Cancel 4 in I, seats left: "+c1.getSeatsAvailable());
        System.out.println("Book 30 in II: "+c2.book(30)+", seats left: "+c2.getSeatsAvailable());
        c2.cancel(7);
        System.out.println("Cancel 7 in II, seats left: "+c2.getSeatsAvailable());
        c2.cancel(50);
        System.out.println("Cancel 50 in II, seats left: "+c2.getSeatsAvailable());

        System.out.println("Total Booked "+CinemaShow.getTotalBooked());
    }

}