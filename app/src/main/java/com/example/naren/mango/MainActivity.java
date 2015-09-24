package com.example.naren.mango;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.naren.mango.fragments.AllFragment;
import com.example.naren.mango.fragments.ArtFragment;
import com.example.naren.mango.fragments.AskRedditFragment;
import com.example.naren.mango.fragments.DIYFragment;
import com.example.naren.mango.fragments.DocumentariesFragment;
import com.example.naren.mango.fragments.EarthPornFragment;
import com.example.naren.mango.fragments.FitnessFragment;
import com.example.naren.mango.fragments.FrontPageFragment;
import com.example.naren.mango.fragments.FuturologyFragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.navigationView);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Reddit");

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close);

        mDrawerLayout.setDrawerListener(mToggle);
        mToggle.syncState();

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.nav_profile:
                        Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_messages:
                        Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_user:
                        Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_Subreddit:
                        Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_settings:
                        Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.item_frontpage:
                        mViewPager.setCurrentItem(0);
                        break;

                    case R.id.item_all:

                        mViewPager.setCurrentItem(1);
                        break;

                    case R.id.item_art:

                        mViewPager.setCurrentItem(2);
                        break;

                    case R.id.item_askreddit:

                        mViewPager.setCurrentItem(3);
                        break;

                    case R.id.item_diy:

                        mViewPager.setCurrentItem(4);
                        break;

                    case R.id.item_documentaries:

                        mViewPager.setCurrentItem(5);
                        break;

                    default:

                        mViewPager.setCurrentItem(0);

                }

                mDrawerLayout.closeDrawers();

                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    public static class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private final String[] FEED_TITLES =
                {"frontpage", "all", "Art", "AskReddit", "DIY", "Documentaries"};

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment mFragment = null;

            switch (position) {

                case 0:
                    mFragment = FrontPageFragment.newInstance("", "");
                    break;

                case 1:
                    mFragment = AllFragment.newInstance("", "");
                    break;

                case 2:
                    mFragment = ArtFragment.newInstance("", "");
                    break;

                case 3:
                    mFragment = AskRedditFragment.newInstance("", "");
                    break;

                case 4:
                    mFragment = DIYFragment.newInstance("", "");
                    break;

                case 5:
                    mFragment = DocumentariesFragment.newInstance("", "");
                    break;


//                case 6:
//                    mFragment = EarthPornFragment.newInstance("", "");
//                    break;
//
//                case 7:
//                    mFragment = FitnessFragment.newInstance("", "");
//                    break;
//
//                case 8:
//                    mFragment = FuturologyFragment.newInstance("", "");
//                    break;

//                case 9:
//                    mFragment = AskRedditFragment.newInstance("", "");
//                    break;

//                case 10:
//                    mFragment = DIYFragment.newInstance("", "");
//                    break;
//
//                case 11:
//                    mFragment = DocumentariesFragment.newInstance("", "");
//                    break;
//
//
//                case 12:
//                    mFragment = AskRedditFragment.newInstance("", "");
//                    break;
//
//                case 13:
//                    mFragment = DIYFragment.newInstance("", "");
//                    break;
//
//                case 14:
//                    mFragment = DocumentariesFragment.newInstance("", "");
//                    break;
//
//
//                case 15:
//                    mFragment = AskRedditFragment.newInstance("", "");
//                    break;
//
//                case 16:
//                    mFragment = DIYFragment.newInstance("", "");
//                    break;
//
//                case 17:
//                    mFragment = DocumentariesFragment.newInstance("", "");
//                    break;
//
//                case 18:
//                    mFragment = AskRedditFragment.newInstance("", "");
//                    break;
//
//                case 19:
//                    mFragment = DIYFragment.newInstance("", "");
//                    break;
//
//                case 20:
//                    mFragment = DocumentariesFragment.newInstance("", "");
//                    break;
//
//                case 21:
//                    mFragment = AskRedditFragment.newInstance("", "");
//                    break;
//
//                case 22:
//                    mFragment = DIYFragment.newInstance("", "");
//                    break;
//
//                case 23:
//                    mFragment = DocumentariesFragment.newInstance("", "");
//                    break;

            }

            return mFragment;
        }

        @Override
        public int getCount() {
            return FEED_TITLES.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return FEED_TITLES[position];
        }
    }

    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {

            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
