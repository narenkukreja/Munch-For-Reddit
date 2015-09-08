package com.example.naren.mango.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.naren.mango.OnSwipeTouchListener;
import com.example.naren.mango.R;
import com.example.naren.mango.fragments.AllFragment;
import com.example.naren.mango.fragments.ArtFragment;
import com.example.naren.mango.fragments.AskRedditFragment;
import com.example.naren.mango.fragments.DIYFragment;
import com.example.naren.mango.fragments.DetailPostFragment;
import com.example.naren.mango.fragments.DetailPostWebFragment;
import com.example.naren.mango.fragments.DocumentariesFragment;
import com.example.naren.mango.fragments.FrontPageFragment;

import uk.co.senab.photoview.PhotoViewAttacher;

public class DetailedPostActivity extends AppCompatActivity{

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_post);


        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

    }


    public static class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment mFragment = null;

            switch (position) {

                case 0:
                    mFragment = DetailPostFragment.newInstance("", "");

                    break;

                case 1:
                    mFragment = DetailPostWebFragment.newInstance("", "");
                    break;


            }
            return mFragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detailed_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home){

            finish();

            return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
