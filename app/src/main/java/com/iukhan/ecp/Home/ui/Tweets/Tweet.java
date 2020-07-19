package com.iukhan.ecp.Home.ui.Tweets;

public class Tweet {
    private String user;
    private String text;
    private String link;

    public Tweet() {
    }

    public Tweet(String user, String text, String link) {
        this.user = user;
        this.text = text;
        this.link = link;
    }

    public String getuser() {
        return user;
    }

    public void setuser(String user) {
        this.user = user;
    }

    public String gettext() {
        return text;
    }

    public void settext(String text) {
        this.text = text;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}