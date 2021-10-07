import java.util.*;
public class TaxiBook extends Thread {
    static Scanner s=new Scanner(System.in);
    static int bookId=1;
    static ArrayList<Taxi> taxies=new ArrayList<>();
    TaxiBook() {
        for(int i=1;i<=4;i++) {
            Taxi t=new Taxi(i);
            taxies.add(t);
        }
    }
    static void getDetails() {
        System.out.println("Taxi Booking Application\n");
        System.out.println("1.Book Taxi");
        System.out.println("2.Taxi Status");
        System.out.println("3.All Taxies Details");
        System.out.println("4.Exit");
        System.out.println("Enter your choice");
        int choice=s.nextInt();
        switch (choice) {
            case 1:
                bookTaxi();
                getDetails();
                break;
            case 2:
                taxiStatus();
                getDetails();
                break;
            case 3:
                taxiDetails();
                getDetails();
            case 4:
                System.out.println("Thank you for using our application");
                return;
        }

    }
    static void bookTaxi() {
        System.out.println("Enter customer id");
        char cid=s.next().charAt(0);
        System.out.println("Enter pickup point");
        char pp=s.next().charAt(0);
        System.out.println("Enter drop point");
        char dp=s.next().charAt(0);
        System.out.println("Enter pickup time");
        int pt=s.nextInt();
        int rej=1;
        for(Taxi t:taxies) {
            if(t.isFree && (t.currentPoint==pp)) {
                t.assign(bookId,cid,pp,dp,pt);
                bookId++;
                TaxiHold th=new TaxiHold(t,pp);
                th.start();
                try {
                    Thread.sleep(1000);
                }
                catch(Exception e) {System.out.println(e);};
                return;
            }
            if(t.isFree) rej=0;
        }
        if(rej==1) {
            System.out.println("Sorry no taxi available now :(");
            System.out.println("Your booking is rejected");
            return;
        }
        ArrayList<Taxi> nearestTaxies=new ArrayList<>();
        for(Taxi t1:taxies) {
            if(Math.abs(t1.currentPoint-pp)==1)
                nearestTaxies.add(0,t1);
            else
                nearestTaxies.add(t1);
        }
        if(nearestTaxies.size()==1) {
            nearestTaxies.get(0).assign(bookId,cid,pp,dp,pt);
            bookId++;
            TaxiHold th=new TaxiHold(nearestTaxies.get(0),pp);
            th.start();
            try {
                Thread.sleep(1000);
            }
            catch(Exception e) {System.out.println(e);};
            return;
        }
        else if((pp=='A'||pp=='F') && (Math.abs(nearestTaxies.get(0).currentPoint-pp)==1)) {
            nearestTaxies.get(0).assign(bookId,cid,pp,dp,pt);
            bookId++;
            TaxiHold th=new TaxiHold(nearestTaxies.get(0),pp);
            th.start();
            try {
                Thread.sleep(1000);
            }
            catch(Exception e) {System.out.println(e);};
            return;
        }
        else {
            int min=Math.abs(nearestTaxies.get(0).currentPoint-pp);
            int iden=0,loc=0;
            for(int i=1;i<nearestTaxies.size();i++) {
                if(min>Math.abs(nearestTaxies.get(i).currentPoint-pp)) {
                    min=Math.abs(nearestTaxies.get(i).currentPoint-pp);
                    loc=i;
                }
                else 
                    nearestTaxies.remove(i);
                if(min==Math.abs(nearestTaxies.get(i).currentPoint-pp))
                    iden++;
            }
            if(iden==0) {
                nearestTaxies.get(loc).assign(bookId,cid,pp,dp,pt);
                bookId++;
                TaxiHold th=new TaxiHold(nearestTaxies.get(loc),pp);
                th.start();
                try {
                    Thread.sleep(1000);
                }
                catch(Exception e) {System.out.println(e);};
                return;
            }
            else {
                double minEarn=nearestTaxies.get(0).totalEarn;
                int minEarnLoc=0;
                for(int i=1;i<nearestTaxies.size();i++) {
                    if(minEarn>nearestTaxies.get(i).totalEarn) {
                        minEarn=nearestTaxies.get(i).totalEarn;
                        minEarnLoc=i;
                    }
                }
                nearestTaxies.get(minEarnLoc).assign(bookId,cid,pp,dp,pt);
                bookId++;
                TaxiHold th=new TaxiHold(nearestTaxies.get(minEarnLoc),pp);
                th.start();
                try {
                    Thread.sleep(1000);
                }
                catch(Exception e) {System.out.println(e);};
                return;
            }
        }
    }
    static void taxiDetails() {
        for(Taxi t:taxies) {
            System.out.println("Total Earnings of taxi "+t.id+" is Rs."+t.totalEarn+"\n");
            for(int i=0;i<t.bookingId.size();i++) {
                System.out.println("Booking id "+t.bookingId.get(i));
                System.out.println("Customer id "+t.custId.get(i));
                System.out.println("From "+t.from.get(i));
                System.out.println("To "+t.to.get(i));
                System.out.println("Pickup time "+t.pickupTime.get(i)+"hrs");
                System.out.println("Drop time "+t.dropTime.get(i)+"hrs");
                System.out.println("Amount Rs."+t.amount.get(i));
            }
            System.out.println();
        }
    }
    static void taxiStatus() {
        for(Taxi t:taxies) {
            if(t.isFree) {
                System.out.println("Taxi id "+t.id+" is Free");
            }
            else {
                System.out.println("Taxi id "+t.id+" is busy");
            }
        }
    }
    public static void main(String[] args) {
        TaxiBook tb=new TaxiBook();
        tb.getDetails();
        return;
    }
}