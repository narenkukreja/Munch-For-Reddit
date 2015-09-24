package com.example.naren.mango.fragments;


import android.content.Intent;
import android.net.Uri;
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

        mToolbar.inflateMenu(R.menu.menu_web);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.action_goBack:

                        if (mWebView.canGoBack()) {

                            mWebView.goBack();
                        }

                        return true;

                    case R.id.action_goForward:

                        if (mWebView.canGoForward()) {

                            mWebView.goForward();
                        }

                        return true;

                    case R.id.action_refresh:

                        mWebView.loadUrl(url);

                        return true;

                    case R.id.action_share:

                        Intent intent = new Intent(Intent.ACTION_SEND);
                        Uri comicUri = Uri.parse(url);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, comicUri.toString());
                        startActivity(Intent.createChooser(intent, "Share with"));

                        return true;

                    case R.id.action_readability:

                        mWebView.loadUrl("http://www.readability.com/m?url=" + url);
                        item.setChecked(true);

                        return true;

                    case R.id.action_desktop:

                        String ua = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
                        mWebView.getSettings().setUserAgentString(ua);
                        mWebView.loadUrl(url);
                        item.setChecked(true);

                        return true;

                    case R.id.action_browser:

                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                        browserIntent.setData(Uri.parse(url));
                        startActivity(browserIntent);

                        return true;

                    default:
                        break;
                }

                return true;
            }
        });


        // Inflate the layout for this fragment
        return rootView;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_web, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()){
//
//            case R.id.action_ref:
//                Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
//                return true;
//
//        }
//
//
//
//        return false;
//
//
//    }

    // Manages the behavior when URLs are loaded
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
    }


}
