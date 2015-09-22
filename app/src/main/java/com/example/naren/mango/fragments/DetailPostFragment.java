package com.example.naren.mango.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;
import com.example.naren.mango.R;
import com.example.naren.mango.activities.ExpandedImageView;
import com.example.naren.mango.activities.GifActivity;
import com.example.naren.mango.activities.YoutubeActivity;
import com.example.naren.mango.adapters.CommentAdapter;
import com.example.naren.mango.model.Comment;
import com.example.naren.mango.network.MySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailPostFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Bundle bundle;

    private CommentAdapter adapter;


    private ListView mListView;

    private RequestQueue mRequestQueue;
    private ArrayList<Comment> commentArrayList = new ArrayList<>();
    private CardView mCardView;
    private TextView mPostTitle, mPostScore, mComments, mSubreddit, mAuthor,
            mDomain, mLinkDomain, mTime;
    private ImageView mPostImage;
    private ViewPager mViewPager;
    private LinearLayout mLinearLayout;
    private Toolbar mToolbar;


    private String id;
    private String url;
    private String title;
    private String author;
    private String subreddit;
    private String domain;
    private int postScore;
    private int comments;
    private String permalink;
    private int time;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailPostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailPostFragment newInstance(String param1, String param2) {
        DetailPostFragment fragment = new DetailPostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailPostFragment() {
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

        View rootView = inflater.inflate(R.layout.fragment_detail_post, container, false);

        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        activity.getSupportActionBar().setTitle("Back");
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);

        bundle = getActivity().getIntent().getExtras();
        mViewPager = (ViewPager) getActivity().findViewById(R.id.viewPager);

        mLinearLayout = (LinearLayout) rootView.findViewById(R.id.container);

        initializeBundleData();

        mRequestQueue = MySingleton.getInstance(getActivity()).getRequestQueue();
        mListView = (ListView) rootView.findViewById(R.id.comment_listView);

        View header = LayoutInflater.from(getContext()).inflate(R.layout.detail_post_card, null);
        mPostTitle = (TextView) header.findViewById(R.id.post_title_textView);
        mPostImage = (ImageView) header.findViewById(R.id.post_image_imageView);
        mPostScore = (TextView) header.findViewById(R.id.post_score);
        mComments = (TextView) header.findViewById(R.id.post_comments);
        mSubreddit = (TextView) header.findViewById(R.id.post_subreddit);
        mDomain = (TextView) header.findViewById(R.id.post_domain);
        mTime = (TextView) header.findViewById(R.id.post_time);
        mAuthor = (TextView) header.findViewById(R.id.post_author);
        mLinkDomain = (TextView) header.findViewById(R.id.link_domain);

        mCardView = (CardView) header.findViewById(R.id.card_view);
        mCardView.setPreventCornerOverlap(false);

        mListView.addHeaderView(header);
        adapter =  new CommentAdapter(getContext(), new CommentProcessor().fetchComments());
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                mListView.getItemAtPosition(i);

                Toast.makeText(getContext(), "Post: " + i, Toast.LENGTH_SHORT).show();

            }
        });

        attachBundleData();

        // Inflate the layout for this fragment
        return rootView;
    }

    public void initializeBundleData() {

        id = bundle.getString("id");
        url = bundle.getString("url");
        title = bundle.getString("title");
        author = bundle.getString("author");
        subreddit = bundle.getString("subreddit");
        domain = bundle.getString("domain");
        postScore = bundle.getInt("post_score");
        comments = bundle.getInt("comments");
        permalink = bundle.getString("permalink");
        time = bundle.getInt("time");

    }

    private void attachBundleData() {

        Glide.with(this).load(url).into(mPostImage);

        mTime.setText(time + " hrs ago");

        mPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), ExpandedImageView.class);

                Bundle bundle = new Bundle();

                bundle.putString("image", url);

                intent.putExtras(bundle);

                startActivity(intent);

            }
        });

        mPostTitle.setText(title);
        mAuthor.setText(author);
        mSubreddit.setText(subreddit);
        mDomain.setText(domain);
        mPostScore.setText(postScore + " points");
        mComments.setText(comments + " comments");

        if (url.contains("youtube") && url.contains("youtu.be")) {

            mLinkDomain.setText("[Youtube] " + domain);

            mLinkDomain.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getActivity(), YoutubeActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("youtube_link", url);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            });

        } else if (url.contains("gallery") && url.contains("imgur") && domain.contains("imgur")) {

            mLinkDomain.setText("[Album] " + domain);

            mLinkDomain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    mViewPager.setCurrentItem(1);


                }
            });

        } else if (url.contains("imgur.com/a")) {

            mLinkDomain.setText("[Album] " + domain);

            mLinkDomain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mViewPager.setCurrentItem(1);


                }
            });


        } else if (url.contains(".gif") || url.contains(".gifv") || url.contains("gfy") ||
                url.contains("gfycat")) {

            mLinkDomain.setText("[Gif] " + domain);

            mLinkDomain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getActivity(), GifActivity.class);

                    Bundle bundle = new Bundle();

                    bundle.putString("gif", url);

                    intent.putExtras(bundle);

                    startActivity(intent);

                }
            });

        } else if (!url.contains("imgur") && !url.contains("gallery") &&
                !url.contains("gif") &&
                !url.contains("gifv") &&
                !url.contains("gfy") &&
                !url.contains("gfycat") &&
                !url.contains("gallery") &&
                !url.contains("youtube") &&
                !url.contains("youtu.be") &&
                !domain.contains("imgur") &&
                !domain.contains("youtube") &&
                !domain.contains("youtu.be") &&
                !domain.contains("gfycat")) {

            mLinkDomain.setText("[Link] " + domain);

            mLinkDomain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mViewPager.setCurrentItem(1);


                }
            });

        } else {

            mLinkDomain.setText("[Link] " + domain);

            mLinkDomain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mViewPager.setCurrentItem(1);

                }
            });

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

                        JSONArray childrenArray = response.getJSONObject(1).getJSONObject("data").getJSONArray("children");
                        process(commentArrayList, childrenArray, 0);
                        adapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            MySingleton.getInstance(getActivity()).addToRequestQueue(jsonArrayRequest);

            return commentArrayList;

        }

        private void process(ArrayList<Comment> comments, JSONArray c, int level) throws Exception {

            for (int i = 0; i < c.length(); i++) {
                if (c.getJSONObject(i).optString("kind") == null)
                    continue;
                if (c.getJSONObject(i).optString("kind").equals("t1") == false)
                    continue;

                JSONObject data = c.getJSONObject(i).getJSONObject("data");
                Comment comment = loadComment(data, level);

                if (comment.getComment_author() != null) {
                    comments.add(comment);
                    addReplies(comments, data, level + 1);
                }
            }
        }

        // Load various details about the comment
        private Comment loadComment(JSONObject data, int level) {
            Comment comment = new Comment();
            try {
                comment.setComment_body(data.getString("body_html"));
                comment.setComment_author(data.getString("author"));
                comment.setComment_score(data.getInt("score"));
                comment.setComment_time(data.getLong("created_utc"));
                comment.level = level;
            } catch (Exception e) {
                Log.d("ERROR", "Unable to parse comment : " + e);
            }
            return comment;
        }

        // Add replies to the comments
        private void addReplies(ArrayList<Comment> comments,
                                JSONObject parent, int level) {
            try {
                if (parent.get("replies").equals("")) {
                    // This means the comment has no replies
                    return;
                }
                JSONArray r = parent.getJSONObject("replies")
                        .getJSONObject("data")
                        .getJSONArray("children");
                process(comments, r, level);
            } catch (Exception e) {
                Log.d("ERROR", "addReplies : " + e);
            }
        }
    }

}



