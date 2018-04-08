package nevelev.aviv.amdb;

import java.net.URL;

/**
 * Created by Ss12Dark on 3/9/2018.
 */

public class Movie {

    private int _id;
    private String subject;
    private String body;
    private String url;

    public Movie(){}

    public  Movie(int id, String subject , String body , String url){
        this._id = id;
        this.subject = subject;
        this.body = body;
        this.url = url;
    }

    public  Movie(String subject , String body , String url){
        this.subject = subject;
        this.body = body;
        this.url = url;
    }

    public  Movie(String subject , String body){
        this.subject = subject;
        this.body = body;

    }

    public  Movie(String subject ){

        this.subject = subject;

    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
