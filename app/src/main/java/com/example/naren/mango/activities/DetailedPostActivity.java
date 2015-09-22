package com.example.naren.mango.activities;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.naren.mango.R;
import com.example.naren.mango.fragments.DetailPostFragment;
import com.example.naren.mango.fragments.DetailPostWebFragment;
import com.hannesdorfmann.swipeback.Position;
import com.hannesdorfmann.swipeback.SwipeBack;

public class DetailedPostActivity extends AppCompatActivity{

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;
    private LinearLayout mLinearLayout;
    private int mPagerPosition;
    private int mPagerOffsetPixels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SwipeBack.attach(this, Position.LEFT)
                .setContentView(R.layout.activity_detailed_post)
                .setSwipeBackView(R.layout.swipeback_custom)
                .setDividerAsSolidColor(Color.WHITE)
                .setDividerSize(2)
                .setOnInterceptMoveEventListener(new SwipeBack.OnInterceptMoveEventListener() {
                    @Override
                    public boolean isViewDraggable(View view, int dx, int x, int y) {

                        if (view == mViewPager) {
                            return !(mPagerPosition == 0 && mPagerOffsetPixels == 0)
                                    || dx < 0;
                        }

                        return false;
                    }
                });


        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mPagerPosition = position;
                mPagerOffsetPixels = positionOffsetPixels;
            }

        });
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

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        switch (id){
//
//            case android.R.id.home:
//                finish();
//                return true;
//
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
