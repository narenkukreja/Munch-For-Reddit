package com.example.naren.munch.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.naren.munch.R;
import com.example.naren.munch.adapters.RecyclerViewAdapter;
import com.example.naren.munch.model.RedditPost;
import com.example.naren.munch.network.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RedditPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RedditPostFragment extends Fragment {
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
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayoutManager mLayoutManager;
    private ProgressBar mProgressbar;
    private String newAfter, subreddit;

    //variables for scrolling down to more items
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
     * @return A new instance of fragment RedditPostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RedditPostFragment newInstance(String param1, String param2) {
        RedditPostFragment fragment = new RedditPostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public RedditPostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            setHasOptionsMenu(true);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_redditpost, container, false);

        mProgressbar = (ProgressBar) rootView.findViewById(R.id.progressbar);

        //JSON request
        mRequestQueue = MySingleton.getInstance(getActivity()).getRequestQueue();

        //setting up the recyclerview with the adapter
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.all_recyclerview);

        if (mParam1.contains("frontpage")) {

            adapter = new RecyclerViewAdapter(getActivity(), getRedditPost());


        } else {

            adapter = new RecyclerViewAdapter(getActivity(), getCustomRedditPost(mParam1));

        }

        mRecyclerView.setAdapter(adapter);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //swipe to refresh layout
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (mParam1.contains("frontpage")) {

                    getRedditPost();
                    mSwipeRefreshLayout.setRefreshing(true);
                    mRecyclerView.setVisibility(View.GONE);


                } else {

                    getCustomRedditPost(mParam1);
                    mSwipeRefreshLayout.setRefreshing(true);
                    mRecyclerView.setVisibility(View.GONE);

                }


            }
        });

        //keep loading additions posts
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

                    if (mParam1.contains("frontpage")) {

                        getMoreRedditPost(newAfter);

                    } else {

                        getMoreCustomRedditPost(mParam1, newAfter);

                    }

                    loading = true;
                }
            }
        });

        return rootView;
    }

    //send JSON request to URL and then parse the responses into relevant data
    private ArrayList<RedditPost> getRedditPost() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                RedditPost.FRONT_PAGE_URL + RedditPost.JSON_ENDPOINT, (String) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray childrenArray = response.getJSONObject("data").getJSONArray("children");

                    for (int i = 0; i < childrenArray.length(); i++) {

                        post = new RedditPost();

                        String title = childrenArray.getJSONObject(i).getJSONObject("data").getString("title");
                        String url = childrenArray.getJSONObject(i).getJSONObject("data").getString("url");
                        String thumbnail = childrenArray.getJSONObject(i).getJSONObject("data").getString("thumbnail");
                        String after = response.getJSONObject("data").getString("after");
                        newAfter = after;

                        String author = childrenArray.getJSONObject(i).getJSONObject("data").getString("author");
                        subreddit = childrenArray.getJSONObject(i).getJSONObject("data").getString("subreddit");
                        String domain = childrenArray.getJSONObject(i).getJSONObject("data").getString("domain");
                        String permalink = childrenArray.getJSONObject(i).getJSONObject("data").getString("permalink");
                        String selfttext_html = childrenArray.getJSONObject(i).getJSONObject("data").getString("selftext_html");

                        int score = childrenArray.getJSONObject(i).getJSONObject("data").getInt("score");
                        int comments = childrenArray.getJSONObject(i).getJSONObject("data").getInt("num_comments");
                        long time = childrenArray.getJSONObject(i).getJSONObject("data").getInt("created_utc");

                        //thumbnail for youtube videos
                        if (domain.equals("youtube.com") || domain.equals("youtu.be")
                                || domain.equals("m.youtube.com")) {

                            String youtube_thumbnail = childrenArray.getJSONObject(i).getJSONObject("data").getJSONObject("media").getJSONObject("oembed").getString("thumbnail_url");
                            post.setYoutubeThumbnail(youtube_thumbnail);


                        }

                        //set the data for each post
                        post.setThumbnail(thumbnail);
                        post.setUrl(url);
                        post.setAfter(newAfter);
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

                    //handle refresh actions
                    mSwipeRefreshLayout.setRefreshing(false);
                    mProgressbar.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);


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

    //send JSON request to URL and then parse the responses into relevant data
    private ArrayList<RedditPost> getCustomRedditPost(String custom) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                RedditPost.Test_URL + custom + RedditPost.JSON_ENDPOINT, (String) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray childrenArray = response.getJSONObject("data").getJSONArray("children");

                    for (int i = 0; i < childrenArray.length(); i++) {

                        post = new RedditPost();

                        String title = childrenArray.getJSONObject(i).getJSONObject("data").getString("title");
                        String url = childrenArray.getJSONObject(i).getJSONObject("data").getString("url");
                        String thumbnail = childrenArray.getJSONObject(i).getJSONObject("data").getString("thumbnail");
                        String after = response.getJSONObject("data").getString("after");
                        newAfter = after;

                        String author = childrenArray.getJSONObject(i).getJSONObject("data").getString("author");
                        subreddit = childrenArray.getJSONObject(i).getJSONObject("data").getString("subreddit");
                        String domain = childrenArray.getJSONObject(i).getJSONObject("data").getString("domain");
                        String permalink = childrenArray.getJSONObject(i).getJSONObject("data").getString("permalink");
                        String selfttext_html = childrenArray.getJSONObject(i).getJSONObject("data").getString("selftext_html");

                        int score = childrenArray.getJSONObject(i).getJSONObject("data").getInt("score");
                        int comments = childrenArray.getJSONObject(i).getJSONObject("data").getInt("num_comments");
                        long time = childrenArray.getJSONObject(i).getJSONObject("data").getInt("created_utc");

                        //thumbnail for youtube videos
                        if (domain.equals("youtube.com") || domain.equals("youtu.be")
                                || domain.equals("m.youtube.com")) {

                            String youtube_thumbnail = childrenArray.getJSONObject(i).getJSONObject("data").getJSONObject("media").getJSONObject("oembed").getString("thumbnail_url");
                            post.setYoutubeThumbnail(youtube_thumbnail);


                        }

                        //set the data for each post
                        post.setThumbnail(thumbnail);
                        post.setUrl(url);
                        post.setAfter(newAfter);
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

                    //handle refresh actions
                    mSwipeRefreshLayout.setRefreshing(false);
                    mProgressbar.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);


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

    //method to keep recieveing loading more posts
    private ArrayList<RedditPost> getMoreRedditPost(String after) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                RedditPost.FRONT_PAGE_URL + RedditPost.JSON_ENDPOINT + RedditPost.AFTER_ENDPOINT + after, (String) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray childrenArray = response.getJSONObject("data").getJSONArray("children");

                    for (int i = 0; i < childrenArray.length(); i++) {

                        post = new RedditPost();

                        String title = childrenArray.getJSONObject(i).getJSONObject("data").getString("title");
                        String url = childrenArray.getJSONObject(i).getJSONObject("data").getString("url");
                        String thumbnail = childrenArray.getJSONObject(i).getJSONObject("data").getString("thumbnail");
                        String after = response.getJSONObject("data").getString("after");
                        newAfter = after;

                        String author = childrenArray.getJSONObject(i).getJSONObject("data").getString("author");
                        subreddit = childrenArray.getJSONObject(i).getJSONObject("data").getString("subreddit");
                        String domain = childrenArray.getJSONObject(i).getJSONObject("data").getString("domain");

                        String permalink = childrenArray.getJSONObject(i).getJSONObject("data").getString("permalink");
                        String selfttext_html = childrenArray.getJSONObject(i).getJSONObject("data").getString("selftext_html");

                        int score = childrenArray.getJSONObject(i).getJSONObject("data").getInt("score");
                        int comments = childrenArray.getJSONObject(i).getJSONObject("data").getInt("num_comments");
                        long time = childrenArray.getJSONObject(i).getJSONObject("data").getInt("created_utc");

                        if (domain.equals("youtube.com") || domain.equals("youtu.be")
                                || domain.equals("m.youtube.com")) {

                            String youtube_thumbnail = childrenArray.getJSONObject(i).getJSONObject("data").getJSONObject("media").getJSONObject("oembed").getString("thumbnail_url");
                            post.setYoutubeThumbnail(youtube_thumbnail);


                        }

                        post.setThumbnail(thumbnail);
                        post.setUrl(url);
                        post.setAfter(newAfter);
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

    //method to keep recieveing loading more posts
    private ArrayList<RedditPost> getMoreCustomRedditPost(String custom, String after) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                RedditPost.Test_URL + custom + RedditPost.JSON_ENDPOINT + RedditPost.AFTER_ENDPOINT + after, (String) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray childrenArray = response.getJSONObject("data").getJSONArray("children");

                    for (int i = 0; i < childrenArray.length(); i++) {

                        post = new RedditPost();

                        String title = childrenArray.getJSONObject(i).getJSONObject("data").getString("title");
                        String url = childrenArray.getJSONObject(i).getJSONObject("data").getString("url");
                        String thumbnail = childrenArray.getJSONObject(i).getJSONObject("data").getString("thumbnail");
                        String after = response.getJSONObject("data").getString("after");
                        newAfter = after;

                        String author = childrenArray.getJSONObject(i).getJSONObject("data").getString("author");
                        subreddit = childrenArray.getJSONObject(i).getJSONObject("data").getString("subreddit");
                        String domain = childrenArray.getJSONObject(i).getJSONObject("data").getString("domain");

                        String permalink = childrenArray.getJSONObject(i).getJSONObject("data").getString("permalink");
                        String selfttext_html = childrenArray.getJSONObject(i).getJSONObject("data").getString("selftext_html");

                        int score = childrenArray.getJSONObject(i).getJSONObject("data").getInt("score");
                        int comments = childrenArray.getJSONObject(i).getJSONObject("data").getInt("num_comments");
                        long time = childrenArray.getJSONObject(i).getJSONObject("data").getInt("created_utc");

                        if (domain.equals("youtube.com") || domain.equals("youtu.be")
                                || domain.equals("m.youtube.com")) {

                            String youtube_thumbnail = childrenArray.getJSONObject(i).getJSONObject("data").getJSONObject("media").getJSONObject("oembed").getString("thumbnail_url");
                            post.setYoutubeThumbnail(youtube_thumbnail);


                        }

                        post.setThumbnail(thumbnail);
                        post.setUrl(url);
                        post.setAfter(newAfter);
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

    //Handle actions in toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_refresh:

                if (mParam1.contains("frontpage")) {

                    mRecyclerView.setVisibility(View.GONE);
                    mProgressbar.setVisibility(View.VISIBLE);
                    getRedditPost();
                    mLayoutManager.scrollToPosition(0);

                } else {

                    mRecyclerView.setVisibility(View.GONE);
                    mProgressbar.setVisibility(View.VISIBLE);
                    getCustomRedditPost(mParam1);
                    mLayoutManager.scrollToPosition(0);
                }


                return true;

            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    //FIX SORTING OPTIONS

//            case R.id.action_sort:
//
//                final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(
//                        getContext(), android.R.layout.select_dialog_singlechoice
//                );
//
//                stringArrayAdapter.add("Hot");
//                stringArrayAdapter.add("New");
//                stringArrayAdapter.add("Rising");
//                stringArrayAdapter.add("Top • Hour");
//                stringArrayAdapter.add("Top • Day");
//                stringArrayAdapter.add("Top • Week");
//                stringArrayAdapter.add("Top • Month");
//                stringArrayAdapter.add("Top • Year");
//                stringArrayAdapter.add("Top • All Time");
//                stringArrayAdapter.add("Controversial • Hour");
//                stringArrayAdapter.add("Controversial • Day");
//                stringArrayAdapter.add("Controversial • Week");
//                stringArrayAdapter.add("Controversial • Month");
//                stringArrayAdapter.add("Controversial • Year");
//                stringArrayAdapter.add("Controversial • All Time");
//
//                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setTitle("Sort By:");
//
//                builder.setAdapter(stringArrayAdapter, new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                        String sortItem = stringArrayAdapter.getItem(i);
//
//                        switch (sortItem){
//
//                            case "New":
//
////                                mRecyclerView.setVisibility(View.GONE);
////                                mProgressbar.setVisibility(View.VISIBLE);
//
////                                Collections.sort(getSortedRedditPost(), new Comparator<RedditPost>() {
////                                    @Override
////                                    public int compare(RedditPost redditPost, RedditPost t1) {
////                                        return  ;
////                                    }
////                                });
//
//                                adapter = new RecyclerViewAdapter(getActivity(), getSortedRedditPost());
//                                mRecyclerView.setAdapter(adapter);
//                                mLayoutManager = new LinearLayoutManager(getActivity());
//                                mRecyclerView.setLayoutManager(mLayoutManager);                                dialogInterface.dismiss();
//
//                                dialogInterface.dismiss();
//
//                                Toast.makeText(getContext(), "New", Toast.LENGTH_SHORT).show();
//                                break;
//
//
//                        }
//
//
//                    }
//                });
//
//                builder.setSingleChoiceItems(stringArrayAdapter, 0, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                        String sortItem = stringArrayAdapter.getItem(i);
//
//                        switch (sortItem) {
//
//                            case "Hot":
//
//                                mRecyclerView.setVisibility(View.GONE);
//                                mProgressbar.setVisibility(View.VISIBLE);
//                                mLayoutManager.scrollToPosition(0);
//                                getRedditPost();
//                                dialogInterface.dismiss();
//
//                                break;
//
//                            case "New":
//
////                                mRecyclerView.setVisibility(View.GONE);
////                                mProgressbar.setVisibility(View.VISIBLE);
//
//                                mRecyclerView.setAdapter(new RecyclerViewAdapter(getActivity(), getSortedRedditPost()));
//                                dialogInterface.dismiss();
//
//                                Toast.makeText(getContext(), "New", Toast.LENGTH_SHORT).show();
//                                break;
//
//                            case "Rising":
//                                Toast.makeText(getContext(), "Hot", Toast.LENGTH_SHORT).show();
//                                break;
//
//                            case "Top • Hour":
//                                Toast.makeText(getContext(), "Hot", Toast.LENGTH_SHORT).show();
//                                break;
//
//                            case "Top • Day":
//                                Toast.makeText(getContext(), "Hot", Toast.LENGTH_SHORT).show();
//                                break;
//
//                            case "Top • Week":
//                                Toast.makeText(getContext(), "Hot", Toast.LENGTH_SHORT).show();
//                                break;
//
//                            case "Top • Month":
//                                Toast.makeText(getContext(), "Hot", Toast.LENGTH_SHORT).show();
//                                break;
//
//                            case "Top • Year":
//                                Toast.makeText(getContext(), "Hot", Toast.LENGTH_SHORT).show();
//                                break;
//
//                            case "Top • All Time":
//                                Toast.makeText(getContext(), "Hot", Toast.LENGTH_SHORT).show();
//                                break;
//
//                            case "Controversial • Hour":
//                                Toast.makeText(getContext(), "Hot", Toast.LENGTH_SHORT).show();
//                                break;
//
//                            case "Controversial • Day":
//                                Toast.makeText(getContext(), "Hot", Toast.LENGTH_SHORT).show();
//                                break;
//
//                            case "Controversial • Month":
//                                Toast.makeText(getContext(), "Hot", Toast.LENGTH_SHORT).show();
//                                break;
//
//                            case "Controversial • Week":
//                                Toast.makeText(getContext(), "Hot", Toast.LENGTH_SHORT).show();
//                                break;
//
//                            case "Controversial • Year":
//                                Toast.makeText(getContext(), "Hot", Toast.LENGTH_SHORT).show();
//                                break;
//
//                            case "Controversial • All Time":
//                                Toast.makeText(getContext(), "Hot", Toast.LENGTH_SHORT).show();
//                                break;
//
//
//                            default:
//                                break;
//
//                        }
//
//
//                    }
//                });
//
//                builder.create();
//                builder.show();
//
//
//
//                Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
//                return true;

//            case R.id.action_settings:
//                Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
//                return true;


    //FIX SORTING OPTIONS

//    private ArrayList<RedditPost> getSortedRedditPost() {
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
//                "https://www.reddit.com/r/soccer/.json?", (String) null, new Response.Listener<JSONObject>() {
//
//            @Override
//            public void onResponse(JSONObject response) {
//
//                try {
//
//                    JSONArray childrenArray = response.getJSONObject("data").getJSONArray("children");
//
//                    for (int i = 0; i < childrenArray.length(); i++) {
//
//                        post = new RedditPost();
//
//                        String title = childrenArray.getJSONObject(i).getJSONObject("data").getString("title");
//                        String url = childrenArray.getJSONObject(i).getJSONObject("data").getString("url");
//                        String thumbnail = childrenArray.getJSONObject(i).getJSONObject("data").getString("thumbnail");
//                        String after = response.getJSONObject("data").getString("after");
//                        newAfter = after;
//
//                        String author = childrenArray.getJSONObject(i).getJSONObject("data").getString("author");
//                        subreddit = childrenArray.getJSONObject(i).getJSONObject("data").getString("subreddit");
//                        String domain = childrenArray.getJSONObject(i).getJSONObject("data").getString("domain");
//
//                        if (domain.equals("youtube.com") || domain.equals("youtu.be")
//                                || domain.equals("m.youtube.com")) {
//
//                            String youtube_thumbnail = childrenArray.getJSONObject(i).getJSONObject("data").getJSONObject("media").getJSONObject("oembed").getString("thumbnail_url");
//                            post.setYoutubeThumbnail(youtube_thumbnail);
//
//
//                        }
//
//
//                        String permalink = childrenArray.getJSONObject(i).getJSONObject("data").getString("permalink");
//                        String selfttext_html = childrenArray.getJSONObject(i).getJSONObject("data").getString("selftext_html");
//
//                        int score = childrenArray.getJSONObject(i).getJSONObject("data").getInt("score");
//                        int comments = childrenArray.getJSONObject(i).getJSONObject("data").getInt("num_comments");
//                        long time = childrenArray.getJSONObject(i).getJSONObject("data").getInt("created_utc");
//
//                        post.setThumbnail(thumbnail);
//
//                        post.setUrl(url);
//
//                        post.setAfter(newAfter);
//                        post.setTitle(title);
//                        post.setPermalink(permalink);
//                        post.setAuthor(author);
//                        post.setSubreddit(subreddit);
//                        post.setDomain(domain);
//                        post.setTime(time);
//                        post.setScore(score);
//                        post.setComments(comments);
//                        post.setSelftext_html(selfttext_html);
//
//                        redditPostArrayList.add(post);
//
//
//                    }
//
//                    adapter.notifyItemRangeChanged(0, redditPostArrayList.size());
////                    mSwipeRefreshLayout.setRefreshing(false);
////                    mProgressbar.setVisibility(View.GONE);
////                    mRecyclerView.setVisibility(View.VISIBLE);
//
//
//                    Toast.makeText(getContext(), "After: " + newAfter, Toast.LENGTH_SHORT).show();
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
//
//        return redditPostArrayList;
//
//    }
//
//    private ArrayList<RedditPost> getSortedByTimeRedditPost(String sortType, String timeFrame) {
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
//                RedditPost.FRONT_PAGE_URL, (String) null, new Response.Listener<JSONObject>() {
//
//            @Override
//            public void onResponse(JSONObject response) {
//
//                try {
//
//                    JSONArray childrenArray = response.getJSONObject("data").getJSONArray("children");
//
//                    for (int i = 0; i < childrenArray.length(); i++) {
//
//                        post = new RedditPost();
//
//                        String title = childrenArray.getJSONObject(i).getJSONObject("data").getString("title");
//                        String url = childrenArray.getJSONObject(i).getJSONObject("data").getString("url");
//                        String thumbnail = childrenArray.getJSONObject(i).getJSONObject("data").getString("thumbnail");
//                        String after = response.getJSONObject("data").getString("after");
//                        newAfter = after;
//
//                        String author = childrenArray.getJSONObject(i).getJSONObject("data").getString("author");
//                        subreddit = childrenArray.getJSONObject(i).getJSONObject("data").getString("subreddit");
//                        String domain = childrenArray.getJSONObject(i).getJSONObject("data").getString("domain");
//
//                        if (domain.equals("youtube.com") || domain.equals("youtu.be")
//                                || domain.equals("m.youtube.com")) {
//
//                            String youtube_thumbnail = childrenArray.getJSONObject(i).getJSONObject("data").getJSONObject("media").getJSONObject("oembed").getString("thumbnail_url");
//                            post.setYoutubeThumbnail(youtube_thumbnail);
//
//
//                        }
//
//
//                        String permalink = childrenArray.getJSONObject(i).getJSONObject("data").getString("permalink");
//                        String selfttext_html = childrenArray.getJSONObject(i).getJSONObject("data").getString("selftext_html");
//
//                        int score = childrenArray.getJSONObject(i).getJSONObject("data").getInt("score");
//                        int comments = childrenArray.getJSONObject(i).getJSONObject("data").getInt("num_comments");
//                        long time = childrenArray.getJSONObject(i).getJSONObject("data").getInt("created_utc");
//
//                        post.setThumbnail(thumbnail);
//
//                        post.setUrl(url);
//
//                        post.setAfter(newAfter);
//                        post.setTitle(title);
//                        post.setPermalink(permalink);
//                        post.setAuthor(author);
//                        post.setSubreddit(subreddit);
//                        post.setDomain(domain);
//                        post.setTime(time);
//                        post.setScore(score);
//                        post.setComments(comments);
//                        post.setSelftext_html(selfttext_html);
//
//                        redditPostArrayList.add(post);
//
//                    }
//
//                    adapter.notifyItemRangeChanged(0, redditPostArrayList.size());
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    mProgressbar.setVisibility(View.GONE);
//                    mRecyclerView.setVisibility(View.VISIBLE);
//
//
//                    Toast.makeText(getContext(), "After: " + newAfter, Toast.LENGTH_SHORT).show();
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
//
//        return redditPostArrayList;
//
//    }
}
