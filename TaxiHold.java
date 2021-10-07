import java.lang.Exception;
public class TaxiHold extends Thread{
    Taxi t;
    char pp;
    TaxiHold(Taxi t,char pp) {
        this.t=t;
        this.pp=pp;
    }
    public void run() {
        t.isFree=false;
        try {
            Thread.sleep(Math.abs(pp-t.currentPoint)*10000);
            t.isFree=true;
        }
        catch(Exception e) {System.out.println(e);};
    }
}