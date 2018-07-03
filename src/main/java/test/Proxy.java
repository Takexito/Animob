package test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Proxy {

    private String ip, port;


    public Proxy(String ip, String port){
        this.ip = ip;
        this.port = port;
    }

    public static ArrayList<Proxy> proxys = new ArrayList();

    public static void start(){
        try {
            Files.lines(Paths.get("C:\\Users\\Tik\\Documents\\GitHub\\Animob\\src\\main\\resources\\proxy.txt"), StandardCharsets.UTF_8).forEach(Proxy::addProxy);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addProxy(String string){
        int index = string.indexOf(":");
        proxys.add(new Proxy(string.substring(0,index), string.substring(index+1)));
    }

    public String getIp(){
        return ip;
    }

    public String getPort() {
        return port;
    }

    public static Proxy getRandomProxy(){
        int randomNum = ThreadLocalRandom.current().nextInt(0, proxys.size());
        return proxys.get(randomNum);
    }
}
