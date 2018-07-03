package restapi;

import database.DataBase;

import java.util.ArrayList;
import java.util.List;

import database.Anime;
import database.Episodes;
import parce.Parce;


public class Rest {


    public static String getAnime(List<Anime> animeList){

        ArrayList<String> animesList = new ArrayList<>();
        animeList.forEach(anime -> animesList.add(anime.getString()));
        StringBuilder b = new StringBuilder();
        animesList.forEach(b::append);
        System.out.print(b);
        return b.toString();
    }

    public static String getEpisodes(List<Episodes> episodeList){
        ArrayList<String> episodesList = new ArrayList<>();
        episodeList.forEach(episode -> episodesList.add(episode.getString()));
        StringBuilder b = new StringBuilder();
        episodesList.forEach(b::append);
        return b.toString();
    }

    public static String startParce(DataBase dataBase){
        Parce.startFullParce(dataBase);
        return "Good"; //change to normal response
    }


}
