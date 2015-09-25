package com.example.naren.munch;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.naren.munch.adapters.RecyclerViewAdapter;
import com.example.naren.munch.fragments.RedditPostFragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
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
        getSupportActionBar().setTitle("Munch");

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

                    case R.id.item_aww:
                        mViewPager.setCurrentItem(2);
                        break;

                    case R.id.item_art:

                        mViewPager.setCurrentItem(3);
                        break;

                    case R.id.item_askreddit:

                        mViewPager.setCurrentItem(4);
                        break;

                    case R.id.item_askscience:

                        mViewPager.setCurrentItem(5);
                        break;

                    case R.id.item_announcements:

                        mViewPager.setCurrentItem(6);
                        break;

                    case R.id.item_blog:

                        mViewPager.setCurrentItem(7);
                        break;

                    case R.id.item_books:

                        mViewPager.setCurrentItem(8);
                        break;

                    case R.id.item_creepy:

                        mViewPager.setCurrentItem(9);
                        break;

                    case R.id.item_dataisbeautiful:

                        mViewPager.setCurrentItem(10);
                        break;

                    case R.id.item_diy:

                        mViewPager.setCurrentItem(11);
                        break;

                    case R.id.item_documentaries:

                        mViewPager.setCurrentItem(12);
                        break;

                    case R.id.item_earthporn:

                        mViewPager.setCurrentItem(13);
                        break;


                    case R.id.item_eli5:

                        mViewPager.setCurrentItem(14);
                        break;

                    case R.id.item_fitness:

                        mViewPager.setCurrentItem(15);
                        break;

                    case R.id.item_food:

                        mViewPager.setCurrentItem(16);
                        break;

                    case R.id.item_funny:

                        mViewPager.setCurrentItem(17);
                        break;

                    case R.id.item_futurology:

                        mViewPager.setCurrentItem(18);
                        break;

                    case R.id.item_gadgets:

                        mViewPager.setCurrentItem(19);
                        break;

                    case R.id.item_gaming:

                        mViewPager.setCurrentItem(20);
                        break;

                    case R.id.item_getmotivated:

                        mViewPager.setCurrentItem(21);
                        break;

                    case R.id.item_gifs:

                        mViewPager.setCurrentItem(22);
                        break;

                    case R.id.item_history:

                        mViewPager.setCurrentItem(23);
                        break;

                    case R.id.item_iama:

                        mViewPager.setCurrentItem(24);
                        break;

                    case R.id.item_internetisbeautiful:

                        mViewPager.setCurrentItem(25);
                        break;

                    case R.id.item_jokes:

                        mViewPager.setCurrentItem(26);
                        break;

                    case R.id.item_lpt:

                        mViewPager.setCurrentItem(27);
                        break;

                    case R.id.item_listentothis:

                        mViewPager.setCurrentItem(28);
                        break;


                    case R.id.item_mildlyinteresting:

                        mViewPager.setCurrentItem(29);
                        break;

                    case R.id.item_movies:

                        mViewPager.setCurrentItem(30);
                        break;

                    case R.id.item_music:

                        mViewPager.setCurrentItem(31);
                        break;

                    case R.id.item_news:

                        mViewPager.setCurrentItem(32);
                        break;

                    case R.id.item_nosleep:

                        mViewPager.setCurrentItem(33);
                        break;


                    case R.id.item_nottheonion:

                        mViewPager.setCurrentItem(34);
                        break;

                    case R.id.item_oldschoolcool:

                        mViewPager.setCurrentItem(35);
                        break;


                    case R.id.item_personalfinance:

                        mViewPager.setCurrentItem(36);
                        break;

                    case R.id.item_philosophy:

                        mViewPager.setCurrentItem(37);
                        break;

                    case R.id.item_photoshopbattles:

                        mViewPager.setCurrentItem(38);
                        break;

                    case R.id.item_pics:

                        mViewPager.setCurrentItem(39);
                        break;

                    case R.id.item_science:

                        mViewPager.setCurrentItem(40);
                        break;

                    case R.id.item_showerthoughts:

                        mViewPager.setCurrentItem(41);
                        break;

                    case R.id.item_space:

                        mViewPager.setCurrentItem(42);
                        break;

                    case R.id.item_sports:

                        mViewPager.setCurrentItem(43);
                        break;

                    case R.id.item_television:

                        mViewPager.setCurrentItem(44);
                        break;

                    case R.id.item_tifu:

                        mViewPager.setCurrentItem(45);
                        break;

                    case R.id.item_til:

                        mViewPager.setCurrentItem(46);
                        break;

                    case R.id.item_twox:

                        mViewPager.setCurrentItem(47);
                        break;

                    case R.id.item_upnews:

                        mViewPager.setCurrentItem(48);
                        break;

                    case R.id.item_videos:

                        mViewPager.setCurrentItem(49);
                        break;

                    case R.id.item_worldnews:

                        mViewPager.setCurrentItem(50);
                        break;


                    case R.id.item_writingprompts:

                        mViewPager.setCurrentItem(51);
                        break;



                    default:

                        mViewPager.setCurrentItem(0);

                }

//                mDrawerLayout.closeDrawers();

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

                {"frontpage", "all", "aww", "art", "askreddit", "askscience",
                        "announcements", "blogs", "books", "creepy", "dataisbeautiful", "DIY", "Documentaries",
                        "earthporn", "explainlimeimfive", "fitness", "food", "funny", "futurology", "gadgets", "gaming", "getmotivated",
                        "gifs", "history", "iama", "internetisbeautiful", "jokes", "lifeprotips",
                        "listentothis", "mildlyinteresting", "movies", "Music", "news", "nosleep",
                        "nottheonion", "oldschoolcool", "personalfinance", "philosophy",
                        "photoshopbattles", "pics", "science", "showerthoughts", "space", "sports",
                        "television", "tifu", "todayilearned", "twoxchromosomes", "upliftingnews", "videos", "worldnews", "writingprompts"
                };

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment mFragment = null;


//            for (int i = 0; i < FEED_TITLES.length; i++) {
//
//                mFragment = RedditPostFragment.newInstance(FEED_TITLES[i], "");
//                return mFragment;
//            }

            switch (position) {

                case 0:
                    mFragment = RedditPostFragment.newInstance("frontpage", "");
                    break;

                case 1:
                    mFragment = RedditPostFragment.newInstance("all", "");
                    break;

                case 2:
                    mFragment = RedditPostFragment.newInstance("aww", "");
                    break;

                case 3:
                    mFragment = RedditPostFragment.newInstance("art", "");
                    break;

                case 4:
                    mFragment = RedditPostFragment.newInstance("askreddit", "");
                    break;

                case 5:
                    mFragment = RedditPostFragment.newInstance("askscience", "");
                    break;

                case 6:
                    mFragment = RedditPostFragment.newInstance("announcements", "");
                    break;

                case 7:
                    mFragment = RedditPostFragment.newInstance("blogs", "");
                    break;

                case 8:
                    mFragment = RedditPostFragment.newInstance("books", "");
                    break;

                case 9:
                    mFragment = RedditPostFragment.newInstance("creepy", "");
                    break;

                case 10:
                    mFragment = RedditPostFragment.newInstance("dataisbeautiful", "");
                    break;

                case 11:
                    mFragment = RedditPostFragment.newInstance("diy", "");
                    break;

                case 12:
                    mFragment = RedditPostFragment.newInstance("documentaries", "");
                    break;

                case 13:
                    mFragment = RedditPostFragment.newInstance("earthporn", "");
                    break;

                case 14:
                    mFragment = RedditPostFragment.newInstance("explainlikeimfive", "");
                    break;

                case 15:
                    mFragment = RedditPostFragment.newInstance("fitness", "");
                    break;

                case 16:
                    mFragment = RedditPostFragment.newInstance("food", "");
                    break;

                case 17:
                    mFragment = RedditPostFragment.newInstance("funny", "");
                    break;

                case 18:
                    mFragment = RedditPostFragment.newInstance("futurology", "");
                    break;

                case 19:
                    mFragment = RedditPostFragment.newInstance("gadgets", "");
                    break;

                case 20:
                    mFragment = RedditPostFragment.newInstance("gaming", "");
                    break;

                case 21:
                    mFragment = RedditPostFragment.newInstance("getmotivated", "");
                    break;

                case 22:
                    mFragment = RedditPostFragment.newInstance("gifs", "");
                    break;

                case 23:
                    mFragment = RedditPostFragment.newInstance("history", "");
                    break;

                case 24:
                    mFragment = RedditPostFragment.newInstance("iama", "");
                    break;

                case 25:
                    mFragment = RedditPostFragment.newInstance("internetisbeautiful", "");
                    break;

                case 26:
                    mFragment = RedditPostFragment.newInstance("jokes", "");
                    break;

                case 27:
                    mFragment = RedditPostFragment.newInstance("lifeprotips", "");
                    break;

                case 28:
                    mFragment = RedditPostFragment.newInstance("listentothis", "");
                    break;

                case 29:
                    mFragment = RedditPostFragment.newInstance("mildlyinteresting", "");
                    break;

                case 30:
                    mFragment = RedditPostFragment.newInstance("movies", "");
                    break;

                case 31:
                    mFragment = RedditPostFragment.newInstance("music", "");
                    break;

                case 32:
                    mFragment = RedditPostFragment.newInstance("news", "");
                    break;

                case 33:
                    mFragment = RedditPostFragment.newInstance("nosleep", "");
                    break;

                case 34:
                    mFragment = RedditPostFragment.newInstance("nottheonion", "");
                    break;

                case 35:
                    mFragment = RedditPostFragment.newInstance("oldschoolcool", "");
                    break;

                case 36:
                    mFragment = RedditPostFragment.newInstance("personalfinances", "");
                    break;

                case 37:
                    mFragment = RedditPostFragment.newInstance("philosophy", "");
                    break;

                case 38:
                    mFragment = RedditPostFragment.newInstance("photoshopbattles", "");
                    break;

                case 39:
                    mFragment = RedditPostFragment.newInstance("pics", "");
                    break;

                case 40:
                    mFragment = RedditPostFragment.newInstance("science", "");
                    break;

                case 41:
                    mFragment = RedditPostFragment.newInstance("showerthoughts", "");
                    break;

                case 42:
                    mFragment = RedditPostFragment.newInstance("space", "");
                    break;

                case 43:
                    mFragment = RedditPostFragment.newInstance("sports", "");
                    break;

                case 44:
                    mFragment = RedditPostFragment.newInstance("television", "");
                    break;

                case 45:
                    mFragment = RedditPostFragment.newInstance("tifu", "");
                    break;

                case 46:
                    mFragment = RedditPostFragment.newInstance("todayilearned", "");
                    break;

                case 47:
                    mFragment = RedditPostFragment.newInstance("twoxchromosomes", "");
                    break;

                case 48:
                    mFragment = RedditPostFragment.newInstance("upliftingnews", "");
                    break;

                case 49:
                    mFragment = RedditPostFragment.newInstance("videos", "");
                    break;

                case 50:
                    mFragment = RedditPostFragment.newInstance("worldnews", "");
                    break;

                case 51:
                    mFragment = RedditPostFragment.newInstance("writingprompts", "");
                    break;


                default:
                    mFragment = RedditPostFragment.newInstance("frontpage", "");
                    break;
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
