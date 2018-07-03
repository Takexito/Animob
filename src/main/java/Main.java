import database.DataBase;
import restapi.Rest;
import org.sql2o.Sql2o;

import static spark.Spark.get;

public class Main {

    public static void main(String[] args) {
        Sql2o sql2o = new Sql2o("jdbc:mysql://localhost:3306/animob", "root", "Abibos98");

        DataBase dataBase = new DataBase(sql2o);

        get("/anime", (req, res) -> Rest.getAnime(dataBase.getAllAnimes()));

        get("/episodes/:id", (req, res) -> Rest.getEpisodes(dataBase.getAllEpisodes(req.params(":id"))));

        get("/parce", (req, res) -> Rest.startParce(dataBase));

    }

}
