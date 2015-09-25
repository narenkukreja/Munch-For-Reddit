package com.example.naren.munch.model;


public class RedditPost {

    public static final String Test_URL = "https://www.reddit.com/r/";


    public static final String FRONT_PAGE_URL = "https://www.reddit.com";
    public static final String ALL_PAGE_URL = "https://www.reddit.com/r/all";
    public static final String ART_PAGE_URL = "https://www.reddit.com/r/art";
    public static final String ASKREDDIT_PAGE_URL = "https://www.reddit.com/r/askreddit";
    public static final String DIY_PAGE_URL = "https://www.reddit.com/r/diy";
    public static final String DOCUMENTARIES_PAGE_URL = "https://www.reddit.com/r/documentaries";
    public static final String EARTHPORN_URL = "https://www.reddit.com/r/earthporn";
    public static final String FITNESS_URL = "https://www.reddit.com/r/fitness";
    public static final String FUTUROLOGY_URL = "https://www.reddit.com/r/futurology";
    public static final String GETMOTIVATED_URL = "https://www.reddit.com/r/getmotivated";
    public static final String IAMA_URL = "https://www.reddit.com/r/iama";
    public static final String INTERNETISBEAUTIFUL_URL = "https://www.reddit.com/r/internetisbeautiful";
    public static final String JOKES_URL = "https://www.reddit.com/r/jokes";
    public static final String LPT_URL = "https://www.reddit.com/r/lifeprotips";
    public static final String MUSIC_URL = "https://www.reddit.com/r/music";
    public static final String OLDSCHOOLCOOL_URL = "https://www.reddit.com/r/oldschoolcool";
    public static final String SHOWERTHOUGHTS_URL = "https://www.reddit.com/r/showerthoughts";
    public static final String TWOX_URL = "https://www.reddit.com/r/twoxchromosomes";
    public static final String UPLIFT_NEWS_URL = "https://www.reddit.com/r/all/upliftingnews";
    public static final String WRITING_PROMPT_URL = "https://www.reddit.com/r/writingprompts";
    public static final String ANNOUNCEMENTS_URL = "https://www.reddit.com/r/announcements";
    public static final String ASKSCIENCE_URL = "https://www.reddit.com/r/askscience";
    public static final String AWW_URL = "https://www.reddit.com/r/aww";
    public static final String BLOG_URL = "https://www.reddit.com/r/blog";
    public static final String BOOKS_URL = "https://www.reddit.com/r/books";
    public static final String CREEPY_URL = "https://www.reddit.com/r/creepy";
    public static final String DIB_URL = "https://www.reddit.com/r/dataisbeautiful";
    public static final String ELI5_URL = "https://www.reddit.com/r/explainlikeimfive";
    public static final String FOOD_URL = "https://www.reddit.com/r/food";
    public static final String FUNNY_URL = "https://www.reddit.com/r/funny";
    public static final String GADGETS_URL = "https://www.reddit.com/r/gadgets";
    public static final String GAMING_URL = "https://www.reddit.com/r/gaming";
    public static final String GIFS_URL = "https://www.reddit.com/r/gifs";
    public static final String HISTORY_URL = "https://www.reddit.com/r/history";
    public static final String LISTENTOTHIS_URL = "https://www.reddit.com/r/listentothis";
    public static final String MILDLYINTERESTING_URL = "https://www.reddit.com/r/mildlyinteresting";
    public static final String MOVIES_URL = "https://www.reddit.com/r/movies";
    public static final String NEWS_URL = "https://www.reddit.com/r/news";
    public static final String NOSLEEP_URL = "https://www.reddit.com/r/nosleep";
    public static final String NOTTHEONION_URL = "https://www.reddit.com/r/nottheonion";
    public static final String PERSONALFINANCE_URL = "https://www.reddit.com/r/personalfinance";
    public static final String PHILOSOPHY_URL = "https://www.reddit.com/r/philosophy";
    public static final String PHBATTLES_URL = "https://www.reddit.com/r/photoshopbattles";
    public static final String PICS_URL = "https://www.reddit.com/r/pics";
    public static final String SCIENCE_URL = "https://www.reddit.com/r/sicence";
    public static final String SPACE_URL = "https://www.reddit.com/r/space";
    public static final String SPORTS_URL = "https://www.reddit.com/r/sports";
    public static final String TELEVISION_URL = "https://www.reddit.com/r/television";
    public static final String TIFU_URL = "https://www.reddit.com/r/tifu";
    public static final String TODAYILEARNED_URL = "https://www.reddit.com/r/todayilearned";
    public static final String VIDEOS_URL = "https://www.reddit.com/r/videos";
    public static final String WORLDNEWS_URL = "https://www.reddit.com/r/worldnews";


    public static final String AFTER_ENDPOINT = "&after=";
    public static final String JSON_ENDPOINT = "/.json?raw_json=1";


    private String title, url, after, domain, author, subreddit, selftext_html, permalink, thumbnail,
            youtubeThumbnail;
    private int score, comments;
    private long time;


    public String getThumbnail() {
        return thumbnail;
    }

    public String getYoutubeThumbnail() {
        return youtubeThumbnail;
    }

    public void setYoutubeThumbnail(String youtubeThumbnail) {
        this.youtubeThumbnail = youtubeThumbnail;
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
