package database;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

public class DataBase {

    public static Object getAnimeList(){
        return "LOL";
    }


    private Sql2o sql2o;

    public DataBase(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public void createAnime(String title) {
        try (Connection conn = sql2o.beginTransaction()) {
            conn.createQuery("insert into anime(name) VALUES (:title)")
                    .addParameter("title", title)
                    .executeUpdate();
            conn.commit();
        }
    }

    public void addEpisode(Anime anime){
        Connection conn = sql2o.beginTransaction();
        conn.createQuery("INSERT INTO anime (title) VALUES (\""+anime.getTitle()+"\")").executeUpdate();
        int id = conn.createQuery("select anime.id from anime where anime.title =\""+anime.getTitle()+"\"").executeScalar(Integer.class);

        //conn.createQuery("INSERT INTO anime_voicer (name) VALUES ("+anime.title+")").executeUpdate();
        anime.getEpisodes().forEach(episodes -> {

            if ( conn.createQuery("select id from anime_voicer where name = \""+episodes.getName()+"\"").executeScalar(Integer.class) == null)
                conn.createQuery("INSERT INTO anime_voicer (name) VALUES (\""+episodes.getName()+"\")")
                        .executeUpdate();
            int voicer_id = conn.createQuery("select id from anime_voicer where name = \""+episodes.getName()+"\"").executeScalar(Integer.class);
            conn.createQuery("INSERT INTO anime_series (anime_id, voicer_id, num, url) " +
                    "VALUES (\"" + id + "\", \"" + voicer_id + "\", \"" + episodes.getName() + "\", \"" + episodes.getUrl() + "\")")
                    .executeUpdate();
        });

        conn.commit();
    }


    public List<Anime> getAllAnimes() {
        try (Connection conn = sql2o.open()) {
            return conn.createQuery("select * from anime")
                    .executeAndFetch(Anime.class);
        }
    }

    public List<Episodes> getAllEpisodes(String id) {
        try (Connection conn = sql2o.open()) {
            List<Episodes> episodes = conn.createQuery(
                    "SELECT anime.title, anime_series.num, anime_voicer.name, anime_series.url\n" +
                    "FROM anime_series\n" +
                    "Inner JOIN anime ON anime_series.anime_id = anime.id\n" +
                    "Inner JOIN anime_voicer ON anime_series.voicer_id = anime_voicer.id\n" +
                    "WHERE anime_series.anime_id = "+ id)
                    .executeAndFetch(Episodes.class);
            return episodes;
        }
    }

}

