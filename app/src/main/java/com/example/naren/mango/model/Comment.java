package com.example.naren.mango.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Comment {

    public static final String BASE_COMMENT_URL = "https://www.reddit.com";
    private String comment_author, comment_body, post_OP;
    private int comment_score;
    private long comment_time;
    public int level;

    public String getPost_OP() {
        return post_OP;
    }

    public void setPost_OP(String post_OP) {
        this.post_OP = post_OP;
    }

    public String getComment_author() {
        return comment_author;
    }

    public void setComment_author(String comment_author) {
        this.comment_author = comment_author;
    }

    public String getComment_body() {
        return comment_body;
    }

    public void setComment_body(String comment_body) {
        this.comment_body = comment_body;
    }

    public int getComment_score() {
        return comment_score;
    }

    public void setComment_score(int comment_score) {
        this.comment_score = comment_score;
    }

    public long getComment_time() {
        return comment_time;
    }

    public void setComment_time(long comment_time) {
        this.comment_time = comment_time;
    }

}


