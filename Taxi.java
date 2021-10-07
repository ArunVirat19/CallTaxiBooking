import java.util.*;
public class Taxi extends Thread {
    int id;
    char currentPoint='A';
    double totalEarn=0;
    ArrayList<Integer> bookingId=new ArrayList<>();
    ArrayList<Character> custId=new ArrayList<>();
    ArrayList<Character> from=new ArrayList<>();
    ArrayList<Character> to=new ArrayList<>();
    ArrayList<Integer> pickupTime=new ArrayList<>();
    ArrayList<Integer> dropTime=new ArrayList<>();
    ArrayList<Double> amount=new ArrayList<>();
    boolean isFree=true;
    Taxi(int id) {
        this.id=id;
    }
    void assign(int bookId,char cid,char pp,char dp, int pt) {
        bookingId.add(bookId);
        custId.add(cid);
        from.add(pp);
        to.add(dp);
        pickupTime.add(pt);
        dropTime.add(pt+Math.abs(pp-dp));
        amount.add((double)100+((15*Math.abs(pp-dp))-5)*10);
        totalEarn+=(double)100+((15*Math.abs(pp-dp))-5)*10;
        System.out.println("Taxi "+id+" is assigned");
        System.out.println("Amount to pay "+(100+((15*Math.abs(pp-dp))-5)*10));
        this.currentPoint=dp;
    }
}