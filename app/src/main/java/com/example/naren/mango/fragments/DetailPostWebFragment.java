package com.example.naren.mango.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.naren.mango.R;
import com.example.naren.mango.activities.WebActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailPostWebFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailPostWebFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private WebView mWebView;
    private Bundle bundle;
    private String url;
    private Toolbar mToolbar;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailPostWebFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailPostWebFragment newInstance(String param1, String param2) {
        DetailPostWebFragment fragment = new DetailPostWebFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailPostWebFragment() {
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

        View rootView = inflater.inflate(R.layout.fragment_detail_post_web, container, false);


        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        final AppCompatActivity activity = (AppCompatActivity) getActivity();

        mToolbar.inflateMenu(R.menu.menu_web);

        mToolbar.setTitle("Back");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activity.finish();


            }
        });

        bundle = getActivity().getIntent().getExtras();

        url = bundle.getString("url");

        mWebView = (WebView) rootView.findViewById(R.id.webView);

        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        // Configure the client to use when opening URLs
        mWebView.setWebViewClient(new MyBrowser());
        // Load the initial URL
        mWebView.loadUrl(url);

        // Inflate the layout for this fragment
        return rootView;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_web, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    // Manages the behavior when URLs are loaded
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


}