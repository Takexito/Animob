package test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Save {
    private static int lastEpisode = 1;
    private static int lastTitle = 1;
    private static int lastPage = 1;

    public static int getLastEpisode() {
        return lastEpisode;
    }

    public static void setLastEpisode(int lastEpisode) {
        Save.lastEpisode = lastEpisode;
    }

    public static int getLastTitle() {
        return lastTitle;
    }

    public static void setLastTitle(int lastTitle) {
        Save.lastTitle = lastTitle;
    }

    public static int getLastPage() {
        return lastPage;
    }

    public static void setLastPage(int lastPage) {
        Save.lastPage = lastPage;
    }

    public static void setAll(int lastEpisode, int lastTitle, int lastPage){
        Save.lastEpisode = lastEpisode;
        Save.lastTitle = lastTitle;
        Save.lastPage = lastPage;
    }

    public static void incLastTitle(){
        lastTitle++;
    }

    public static void incLastPage(){
        lastPage++;
    }

    public static void save(){
            try {
                Files.write(Paths.get("C:\\Users\\Tik\\Documents\\GitHub\\Animob\\src\\main\\resources\\save.txt"),
                        String.valueOf(lastEpisode + "|" + lastTitle + "|" + lastPage).getBytes());
            }
            catch (IOException e) {
                e.printStackTrace();
            }

    }
}
