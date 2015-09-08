package com.example.naren.mango.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.naren.mango.R;
import com.example.naren.mango.adapters.ImgurGalleryAdapter;
import com.example.naren.mango.model.ImgurAlbum;
import com.example.naren.mango.model.RedditPost;
import com.example.naren.mango.network.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ImageGalleryActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private String imageUrl;
    private ImgurGalleryAdapter adapter;
    private LinearLayoutManager mLayoutManager;
    private RequestQueue mRequestQueue;
    private ArrayList<ImgurAlbum> imgurAlbumArrayList = new ArrayList<>();
    ImgurAlbum imgurAlbum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Back");

        Bundle bundle = getIntent().getExtras();
        imageUrl = bundle.getString("imgur_album");

        mRequestQueue = MySingleton.getInstance(this).getRequestQueue();

        mRecyclerView = (RecyclerView) findViewById(R.id.all_recyclerview);
        adapter = new ImgurGalleryAdapter(this, getImagurAlbum(imageUrl));
        mRecyclerView.setAdapter(adapter);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    public void onClick(View view) {

        Intent intent = null;

        switch (view.getId()) {

            case R.id.action_open_browser:

                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(imageUrl));
                startActivity(intent);
                break;

            case R.id.action_share:

                intent = new Intent(Intent.ACTION_SEND);
                Uri comicUri = Uri.parse(imageUrl);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, comicUri.toString());
                startActivity(Intent.createChooser(intent, "Share with"));
                break;

            case R.id.action_close:

                finish();
                break;

        }
    }

    private ArrayList<ImgurAlbum> getImagurAlbum(String imageUrl) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                imageUrl + ImgurAlbum.JSON_ENDPOINT, (String) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray imagesArray = response.getJSONObject("data").getJSONObject("image").getJSONObject("album_images").getJSONArray("images");

                    for (int i = 0; i < imagesArray.length(); i++) {

                        imgurAlbum = new ImgurAlbum();

                        String description = imagesArray.getJSONObject(i).getString("description");
                        String hash = imagesArray.getJSONObject(i).getString("hash");
                        String ext = imagesArray.getJSONObject(i).getString("ext");

                        imgurAlbum.setHash(hash);
                        imgurAlbum.setExt(ext);
                        imgurAlbum.setDescription(description);

                        imgurAlbumArrayList.add(imgurAlbum);

                    }

                    adapter.notifyItemRangeChanged(0, imgurAlbumArrayList.size());


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

        return imgurAlbumArrayList;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_gallery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_open_browser) {

            Intent intent = new Intent(Intent.ACTION_VIEW);

            intent.setData(Uri.parse(imageUrl));
            startActivity(intent);

            return true;
        }

        if (id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
