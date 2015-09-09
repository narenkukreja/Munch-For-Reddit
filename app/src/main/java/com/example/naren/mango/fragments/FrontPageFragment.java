package com.example.naren.mango.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.naren.mango.R;
import com.example.naren.mango.adapters.RecyclerViewAdapter;
import com.example.naren.mango.model.RedditPost;
import com.example.naren.mango.network.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FrontPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FrontPageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RequestQueue mRequestQueue;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter adapter;
    private ArrayList<RedditPost> redditPostArrayList = new ArrayList<>();
    private RedditPost post;
    private String after;
    private SwipeRefreshLayout mSwipeRefreshLayout;



    private LinearLayoutManager mLayoutManager;

    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    private int firstVisibleItem, visibleItemCount, totalItemCount;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FrontPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FrontPageFragment newInstance(String param1, String param2) {
        FrontPageFragment fragment = new FrontPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FrontPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_frontpage, container, false);

        mRequestQueue = MySingleton.getInstance(getActivity()).getRequestQueue();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.all_recyclerview);
        adapter = new RecyclerViewAdapter(getActivity(), getRedditPost());
        mRecyclerView.setAdapter(adapter);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getRedditPost();
                mSwipeRefreshLayout.setRefreshing(false);

            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = mRecyclerView.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached
                    // Do something
                    getMoreRedditPost(post.getAfter());
                    loading = true;
                }
            }
        });

        return rootView;
    }

    private ArrayList<RedditPost> getRedditPost() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                RedditPost.FRONT_PAGE_URL, (String) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray childrenArray = response.getJSONObject("data").getJSONArray("children");

                    for (int i = 0; i < childrenArray.length(); i++) {

                        post = new RedditPost();

                        String title = childrenArray.getJSONObject(i).getJSONObject("data").getString("title");
                        String url = childrenArray.getJSONObject(i).getJSONObject("data").getString("url");
                        String thumbnail = childrenArray.getJSONObject(i).getJSONObject("data").getString("thumbnail");
                        after = response.getJSONObject("data").getString("after");

                        String jpegImageUrl = url + ".jpg";
                        String pngImageUrl = url + ".png";

                        String author = childrenArray.getJSONObject(i).getJSONObject("data").getString("author");
                        String subreddit = childrenArray.getJSONObject(i).getJSONObject("data").getString("subreddit");
                        String domain = childrenArray.getJSONObject(i).getJSONObject("data").getString("domain");
                        String permalink = childrenArray.getJSONObject(i).getJSONObject("data").getString("permalink");
                        String selfttext_html = childrenArray.getJSONObject(i).getJSONObject("data").getString("selftext_html");

                        int score = childrenArray.getJSONObject(i).getJSONObject("data").getInt("score");
                        int comments = childrenArray.getJSONObject(i).getJSONObject("data").getInt("num_comments");
                        long time = childrenArray.getJSONObject(i).getJSONObject("data").getInt("created_utc");

                        post.setUrl(url);
                        post.setThumbnail(thumbnail);

                        if (domain.contains("imgur") && url.contains("gallery") && url.contains("imgur")) {
                            post.setUrl(url);
                        }else if(url.contains("imgur.com/a")){
                            post.setUrl(url);
                        }else{
                            if (domain.equals("imgur.com") && !url.contains("gallery")) {

                                if (!url.equals(jpegImageUrl)) {

                                    post.setUrl(jpegImageUrl);

                                } else {

                                    post.setUrl(pngImageUrl);
                                }
                            }
                        }

                        if (domain.equals("m.imgur.com")) {
                            String mImageUrl = childrenArray.getJSONObject(i).getJSONObject("data").getJSONObject("media").getJSONObject("oembed").getString("thumbnail_url");
                            post.setUrl(mImageUrl);

                        }

                        post.setAfter(after);
                        post.setTitle(title);
                        post.setPermalink(permalink);
                        post.setAuthor(author);
                        post.setSubreddit(subreddit);
                        post.setDomain(domain);
                        post.setTime(time);
                        post.setScore(score);
                        post.setComments(comments);
                        post.setSelftext_html(selfttext_html);

                        redditPostArrayList.add(post);

                    }

                    adapter.notifyItemRangeChanged(0, redditPostArrayList.size());


                    Toast.makeText(getContext(), "Permalink: " + post.getPermalink(), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();

            }
        });

        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);

        return redditPostArrayList;

    }

    private ArrayList<RedditPost> getMoreRedditPost(String after) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                RedditPost.FRONT_PAGE_URL + RedditPost.AFTER_ENDPOINT + after, (String) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray childrenArray = response.getJSONObject("data").getJSONArray("children");

                    for (int i = 0; i < childrenArray.length(); i++) {

                        post = new RedditPost();

                        post = new RedditPost();

                        String title = childrenArray.getJSONObject(i).getJSONObject("data").getString("title");
                        String url = childrenArray.getJSONObject(i).getJSONObject("data").getString("url");
                        String thumbnail = childrenArray.getJSONObject(i).getJSONObject("data").getString("thumbnail");
                        String after = response.getJSONObject("data").getString("after");

                        String jpegImageUrl = url + ".jpg";
                        String pngImageUrl = url + ".png";

                        String author = childrenArray.getJSONObject(i).getJSONObject("data").getString("author");
                        String subreddit = childrenArray.getJSONObject(i).getJSONObject("data").getString("subreddit");
                        String domain = childrenArray.getJSONObject(i).getJSONObject("data").getString("domain");
                        String permalink = childrenArray.getJSONObject(i).getJSONObject("data").getString("permalink");
                        String selfttext_html = childrenArray.getJSONObject(i).getJSONObject("data").getString("selftext_html");

                        int score = childrenArray.getJSONObject(i).getJSONObject("data").getInt("score");
                        int comments = childrenArray.getJSONObject(i).getJSONObject("data").getInt("num_comments");
                        long time = childrenArray.getJSONObject(i).getJSONObject("data").getInt("created_utc");

                        post.setUrl(url);
                        post.setThumbnail(thumbnail);

                        if (domain.contains("imgur") && url.contains("gallery") && url.contains("imgur")) {
                            post.setUrl(url);
                        }else if(url.contains("imgur.com/a")){
                            post.setUrl(url);
                        }else{
                            if (domain.equals("imgur.com") && !url.contains("gallery")) {

                                if (!url.equals(jpegImageUrl)) {

                                    post.setUrl(jpegImageUrl);

                                } else {

                                    post.setUrl(pngImageUrl);
                                }
                            }
                        }

                        if (domain.equals("m.imgur.com")) {
                            String mImageUrl = childrenArray.getJSONObject(i).getJSONObject("data").getJSONObject("media").getJSONObject("oembed").getString("thumbnail_url");
                            post.setUrl(mImageUrl);

                        }

                        post.setAfter(after);
                        post.setTitle(title);
                        post.setPermalink(permalink);
                        post.setAuthor(author);
                        post.setSubreddit(subreddit);
                        post.setDomain(domain);
                        post.setTime(time);
                        post.setScore(score);
                        post.setComments(comments);
                        post.setSelftext_html(selfttext_html);

                        redditPostArrayList.add(post);

                    }

                    adapter.notifyItemRangeChanged(0, redditPostArrayList.size());
                    Toast.makeText(getContext(), "Permalink: " + post.getPermalink(), Toast.LENGTH_SHORT).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);

        return redditPostArrayList;

    }
}
