package parce;

import database.Anime;
import database.DataBase;
import database.Episodes;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import test.Save;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Parce {
    private static final String HOST = "https://play.shikimori.org/animes/status/ongoing,released,latest/season/!2010_2014,2000_2009,2015_2016,2017,2018/page/";

    private static ArrayList<Anime> animes = new ArrayList<>();

    private static int episodeId = 1;
    private static int titleId = 1;

    private static String getTitle(String string) {
        int i = string.indexOf('/');
        String title = string.substring(i + 2);
        i = title.indexOf('/');
        title = title.substring(0, i - 1);
        return title;
    }

    public static List<Episodes> allEpisodes(String link) { // Возвращает лист всех эпизодов данного аниме(параметр)
        Element allEpisodes;
        Document doc = null; //Здесь хранится будет разобранный html документ
        List<Episodes> episodeList = new ArrayList<>();
        List<Episodes> errorList = new ArrayList<>();

        try {
            Connection.Response response = Jsoup.connect(link)
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                    .timeout(10000)
                    .ignoreHttpErrors(true)
                    .execute();
            int statusCode = response.statusCode();
            if (statusCode == 200) {
                Connection connection = Jsoup.connect(link);
                doc = connection.get();
            } else {
                System.out.println("received error code : " + statusCode);
                Save.setLastEpisode(episodeId);
                Save.save();
                errorList.add(new Episodes(episodeId, "xxx", "none", "none"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Если всё считалось, что вытаскиваем из считанного html документа заголовок
        if (doc != null) {
            allEpisodes = doc.select("div[data-kind=\"all\"]").last();
            String title = doc.title();

            // Сюда пихаем то что не нужэно парсить
            errorList.add(new Episodes(episodeId, getTitle(title), "none", "none"));
            if (allEpisodes == null)
                return errorList;
            if (Objects.equals(title, "Эпизод 1 / Doraemon (2005) / Аниме"))
                return errorList;

            allEpisodes.select("div.b-video_variant").forEach(e -> {
                String url = e.select("a").attr("href");

                episodeList.add(new Episodes(episodeId, getTitle(title), e.select("span.video-author").text(), url));
            });

            String next = doc.select("a.next").first().attr("href");
            episodeId++;
            int j = next.length();

            if (!((next.charAt(j - 1) == '1' || next.charAt(j - 1) == '0') && next.charAt(j - 2) == '/')) {
                System.out.println("return " + " episode");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                episodeList.addAll(allEpisodes(next));
            }
        }
        episodeId = 1;
        return episodeList;
    }

    public static String startFullParce(DataBase dataBase) {
        for (int i = Save.getLastPage(); i <= 201; i++) {
            System.out.println("Page " + i + " start");
            List<String> list = Parce.getTitlesLinks(HOST + String.valueOf(i));
            list.forEach(l -> {
                System.out.println("Title " + " start");
                Parce.titleId++;
                List<Episodes> episodes = allEpisodes(l);
                Anime anime = new Anime(episodes);
                animes.add(anime);
                dataBase.addEpisode(anime);
                System.out.println("Title " + " end");
                Save.incLastTitle();
                Save.incLastPage();
            });
            Parce.titleId = 1;
        }
        return "good";
    }
//    private static void create(List<database.Episodes> strings){
//        strings.forEach(l -> { l = l + "|";
//            try {
//                Files.write(Paths.get("C:\\Users\\Tik\\Documents\\GitHub\\Animob\\src\\main\\resources\\out.txt"), l.getBytes(), StandardOpenOption.APPEND);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//    }

    public static List<String> getTitlesLinks(String host) {
        Elements anime_titles_links = null;
        List<String> links = null;
        Document doc = null; //Здесь хранится будет разобранный html документ
        try {
            Connection connection = Jsoup.connect(host);
            doc = connection.get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Если всё считалось, что вытаскиваем из считанного html документа заголовок
        if (doc != null) {
            //Находим заголовки тайтлов
            anime_titles_links = doc.select(".cover");
            //anime_titles_links = anime_titles_links.select("a");
            links = anime_titles_links.eachAttr("data-href");
        }
        return links;
    }
}