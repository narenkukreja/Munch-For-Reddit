package com.example.naren.mango.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllFragment extends Fragment {
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

    private LinearLayoutManager mLayoutManager;

    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllFragment newInstance(String param1, String param2) {
        AllFragment fragment = new AllFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AllFragment() {
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
                RedditPost.ALL_PAGE_URL, (String) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray childrenArray = response.getJSONObject("data").getJSONArray("children");

                    for (int i = 0; i < childrenArray.length(); i++) {

                        post = new RedditPost();

                        String title = childrenArray.getJSONObject(i).getJSONObject("data").getString("title");
                        String url = childrenArray.getJSONObject(i).getJSONObject("data").getString("url");
                        after = response.getJSONObject("data").getString("after");

                        String jpegImageUrl = url + ".jpg";
                        String pngImageUrl = url + ".png";

                        String domain = childrenArray.getJSONObject(i).getJSONObject("data").getString("domain");
                        String author = childrenArray.getJSONObject(i).getJSONObject("data").getString("author");
                        String subreddit = childrenArray.getJSONObject(i).getJSONObject("data").getString("subreddit");

                        int score = childrenArray.getJSONObject(i).getJSONObject("data").getInt("score");
                        int comments = childrenArray.getJSONObject(i).getJSONObject("data").getInt("num_comments");

                        post.setTitle(title);
                        post.setUrl(url);
                        post.setAfter(after);

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
                        post.setAuthor(author);
                        post.setSubreddit(subreddit);
                        post.setDomain(domain);
                        post.setScore(score);
                        post.setComments(comments);

                        redditPostArrayList.add(post);

                    }

                    adapter.notifyItemRangeChanged(0, redditPostArrayList.size());


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

    private ArrayList<RedditPost> getMoreRedditPost(String after) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                RedditPost.ALL_PAGE_URL + "&after=" + after, (String) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray childrenArray = response.getJSONObject("data").getJSONArray("children");

                    for (int i = 0; i < childrenArray.length(); i++) {

                        post = new RedditPost();

                        String title = childrenArray.getJSONObject(i).getJSONObject("data").getString("title");
                        String url = childrenArray.getJSONObject(i).getJSONObject("data").getString("url");
                        String after = response.getJSONObject("data").getString("after");

                        String jpegImageUrl = url + ".jpg";
                        String pngImageUrl = url + ".png";

                        String domain = childrenArray.getJSONObject(i).getJSONObject("data").getString("domain");
                        String author = childrenArray.getJSONObject(i).getJSONObject("data").getString("author");
                        String subreddit = childrenArray.getJSONObject(i).getJSONObject("data").getString("subreddit");

                        int score = childrenArray.getJSONObject(i).getJSONObject("data").getInt("score");
                        int comments = childrenArray.getJSONObject(i).getJSONObject("data").getInt("num_comments");

                        post.setTitle(title);
                        post.setUrl(url);
                        post.setAfter(after);

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

                        post.setAuthor(author);
                        post.setSubreddit(subreddit);
                        post.setDomain(domain);
                        post.setScore(score);
                        post.setComments(comments);

                        redditPostArrayList.add(post);

                    }

                    adapter.notifyItemRangeChanged(0, redditPostArrayList.size());


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
