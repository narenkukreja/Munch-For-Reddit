package com.example.naren.mango.model;


public class RedditPost {

    public static final String FRONT_PAGE_URL = "https://www.reddit.com/r/gifs.json?raw_json=1";
    public static final String ALL_PAGE_URL = "https://www.reddit.com/r/all.json?raw_json=1";
    public static final String ART_PAGE_URL = "https://www.reddit.com/r/art.json?raw_json=1";
    public static final String ASKREDDIT_PAGE_URL = "https://www.reddit.com/r/askreddit.json?raw_json=1";
    public static final String DIY_PAGE_URL = "https://www.reddit.com/r/diy.json?raw_json=1";
    public static final String DOCUMENTARIES_PAGE_URL = "https://www.reddit.com/r/documentaries.json?raw_json=1";

    public static final String AFTER_ENDPOINT = "&after=";

    private String title, url, after, domain, author, subreddit, selftext_html, permalink, thumbnail;
    private int score, comments;
    private long time;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getAfter() {
        return after;
    }
    public void setAfter(String after) {
        this.after = after;
    }


    public String getPermalink() {
        return permalink;
    }
    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }


    public String getSelftext_html() {
        return selftext_html;
    }
    public void setSelftext_html(String selftext_html) {
        this.selftext_html = selftext_html;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSubreddit() {
        return subreddit;
    }
    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public int getComments() {
        return comments;
    }
    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public String getDomain() {
        return domain;
    }
    public void setDomain(String domain) {
        this.domain = domain;
    }


}
