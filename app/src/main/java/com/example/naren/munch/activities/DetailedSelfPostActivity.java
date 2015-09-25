package com.example.naren.munch.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.naren.munch.R;
import com.example.naren.munch.adapters.CommentAdapter;
import com.example.naren.munch.model.Comment;
import com.example.naren.munch.network.VolleySingleton;
import com.hannesdorfmann.swipeback.Position;
import com.hannesdorfmann.swipeback.SwipeBack;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailedSelfPostActivity extends AppCompatActivity {

    Bundle bundle;

    private TextView mPostTitle, mPostScore, mComments, mSubreddit, mAuthor,
            mDomain, mTime, mSelfttext_html;
    private LinearLayout mSelfTextPlaceholder;

    private Toolbar mToolbar;
    private ProgressBar mProgressbar;
    private CommentAdapter adapter;
    private ListView mListView;
    private RequestQueue mRequestQueue;
    private ArrayList<Comment> commentArrayList = new ArrayList<>();
    private LayoutInflater layoutInflater;

    private String selftext_html;
    private String title;
    private String author;
    private String subreddit;
    private String domain;
    private int postScore;
    private int comments;
    private String permalink;
    private int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SwipeBack.attach(this, Position.LEFT)
                .setContentView(R.layout.activity_detailed_self_post)
                .setSwipeBackView(R.layout.swipeback_custom);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Back");

        mProgressbar = (ProgressBar) findViewById(R.id.progressbar);

        bundle = getIntent().getExtras();

        String op_author = bundle.getString("author");

        initializeBundleData();

        mRequestQueue = VolleySingleton.getInstance().getRequestQueue();
        mListView = (ListView) findViewById(R.id.comment_listView);

        layoutInflater = getLayoutInflater();
        View header = layoutInflater.inflate(R.layout.detail_self_post_card, null);

        mPostTitle = (TextView) header.findViewById(R.id.post_title_textView);
        mSelfTextPlaceholder = (LinearLayout) header.findViewById(R.id.selftext_placeholder);
        mSelfttext_html = (TextView) header.findViewById(R.id.selftext_id);
        mPostScore = (TextView) header.findViewById(R.id.post_score);
        mComments = (TextView) header.findViewById(R.id.post_comments);
        mSubreddit = (TextView) header.findViewById(R.id.post_subreddit);
        mDomain = (TextView) header.findViewById(R.id.post_domain);
//        mTime = (TextView) header.findViewById(R.id.post_time);
        mAuthor = (TextView) header.findViewById(R.id.post_author);

        mListView.addHeaderView(header);
        adapter = new CommentAdapter(this, new CommentProcessor().fetchComments());
        mListView.setAdapter(adapter);

        attachBundleData();

    }

    public void initializeBundleData() {

        selftext_html = bundle.getString("selftext_html");
        title = bundle.getString("title");
        author = bundle.getString("author");
        subreddit = bundle.getString("subreddit");
        domain = bundle.getString("domain");
        postScore = bundle.getInt("post_score");
        comments = bundle.getInt("comments");
        permalink = bundle.getString("permalink");
        time = bundle.getInt("time");

    }

    public void attachBundleData() {

        mPostTitle.setText(title);
        mAuthor.setText(author);
        mSubreddit.setText(subreddit);
        mDomain.setText(domain);
        mPostScore.setText(postScore + " points");
        mComments.setText(comments + " comments");

        if (selftext_html.contains("null")) {
            mSelfTextPlaceholder.setVisibility(View.GONE);

        } else {

            mSelfTextPlaceholder.setVisibility(View.VISIBLE);
            mSelfttext_html.setText(Html.fromHtml(selftext_html));

        }

    }

    public class CommentProcessor {

        public CommentProcessor() {

        }

        ArrayList<Comment> fetchComments() {

            final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                    Comment.BASE_COMMENT_URL + permalink + ".json?" + "raw_json=1", new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {


                    try {
                        String post_OP = response.getJSONObject(0).getJSONObject("data").getJSONArray("children").getJSONObject(0).getJSONObject("data").getString("author");
                        JSONArray childrenArray = response.getJSONObject(1).getJSONObject("data").getJSONArray("children");
                        process(commentArrayList, childrenArray, 0, post_OP);
                        adapter.notifyDataSetChanged();
                        mListView.setVisibility(View.VISIBLE);
                        mProgressbar.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            mRequestQueue.add(jsonArrayRequest);

            return commentArrayList;

        }

        private void process(ArrayList<Comment> comments, JSONArray c, int level, String post_OP) throws Exception {

            for (int i = 0; i < c.length(); i++) {
                if (c.getJSONObject(i).optString("kind") == null)
                    continue;
                if (!c.getJSONObject(i).optString("kind").equals("t1"))
                    continue;
                JSONObject data = c.getJSONObject(i).getJSONObject("data");
                Comment comment = loadComment(data, level, post_OP);

                if (comment.getComment_author() != null) {
                    comments.add(comment);
                    addReplies(comments, data, level + 1, post_OP);
                }
            }
        }

        // Load various details about the comment
        private Comment loadComment(JSONObject data, int level, String post_OP) {
            Comment comment = new Comment();
            try {
                comment.setComment_body(data.getString("body_html"));
                comment.setComment_author(data.getString("author"));
                comment.setComment_score(data.getInt("score"));
                comment.setPost_OP(post_OP);
                comment.level = level;
            } catch (Exception e) {
                Log.d("ERROR", "Unable to parse comment : " + e);
            }
            return comment;
        }

        // Add replies to the comments
        private void addReplies(ArrayList<Comment> comments,
                                JSONObject parent, int level, String post_OP) {
            try {
                if (parent.get("replies").equals("")) {
                    // This means the comment has no replies
                    return;
                }
                JSONArray r = parent.getJSONObject("replies")
                        .getJSONObject("data")
                        .getJSONArray("children");
                process(comments, r, level, post_OP);
            } catch (Exception e) {
                Log.d("ERROR", "addReplies : " + e);
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detailed_self_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {

            finish();

            return true;

        }

        if (id == R.id.action_refresh) {

            mListView.setVisibility(View.GONE);
            mProgressbar.setVisibility(View.VISIBLE);
            new CommentProcessor().fetchComments();
            mListView.smoothScrollToPosition(0,0);

            return true;

        }


        return super.onOptionsItemSelected(item);
    }
}
