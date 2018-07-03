package database;

public class Episodes {
    private int id;
    private int num;
    private String title;
    private String name;
    private String url;

    public Episodes(int num, String title, String name, String url){
        this.num = num;
        this.title = title;
        this.name = name;
        this.url = url;
    }

    public String getString(){
        return String.valueOf(num) + "!" + String.valueOf(name) + "!" + url + "|";
    }

    /** Getters and Setters block **/

    public void setTitle(String title) {
        this.title = title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getNum() {
        return num;
    }
}