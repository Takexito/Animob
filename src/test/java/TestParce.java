
import database.DataBase;
import parce.Parce;

public class TestParce {

    public static void main(String[] args){
       // System.out.print(parce.Parce.getVideoLink(parce.Parce.getTitlesLinks().get(3)));
        //parce.Parce.getSeriaLinks((parce.Parce.getTitlesLinks().get(3)));
        //parce.Parce.ss(parce.Parce.getTitlesLinks().get(2)).forEach();
       //test.Proxy.start();
        DataBase dataBase = null;
        Parce.startFullParce(dataBase);
    }

}
