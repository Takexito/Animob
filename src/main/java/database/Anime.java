package database;

import java.util.List;

public class Anime {
    private int id;
    private String title;
    private List<Episodes> episodes;

    public Anime(List<Episodes> episodes){
        title = episodes.get(0).getTitle();
        this.episodes = episodes;
    }

    public String getString(){
        return String.valueOf(id) +"\n"+ title +"|";
    }

    /** Getters and Setters block **/

    public List<Episodes> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episodes> episodes) {
        this.episodes = episodes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}


