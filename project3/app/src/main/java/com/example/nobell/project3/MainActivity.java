package com.example.nobell.project3;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.nobell.project3.dataset.Appearance;
import com.example.nobell.project3.dataset.Description;
import com.example.nobell.project3.dataset.Event;
import com.example.nobell.project3.dataset.Friend;
import com.example.nobell.project3.dataset.Tag;
import com.example.nobell.project3.ui.EventTabFragment;
import com.example.nobell.project3.ui.FriendTabFragment;
import com.example.nobell.project3.ui.TagTabFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Used when fragment transaction is needed
         * Moreover, database needs its context.
         * Assumption: there is only one MainActivity class : should be wrong. */
        mContext = this;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.mainViewPager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.mainTabLayout);
        tabLayout.setupWithViewPager(viewPager);

        ActiveAndroid.initialize(this);
        dummyDataSetup();
        Log.i("debug", Integer.toString(new Select().all().from(Event.class).execute().size()));
        List<Appearance> appearances = new Select().all().from(Appearance.class).execute();
        Log.i("debug", Integer.toString(appearances.size()));
        for (Appearance appearance : appearances) {
            Log.d("debug", String.format("%d", appearance.friend.getId()));
        }

    }

    private void setupViewPager (ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new EventTabFragment(), "Events");
        adapter.addFragment(new FriendTabFragment(mContext), "Friends" );
        adapter.addFragment(new TagTabFragment(), "Tags");
        viewPager.setAdapter(adapter);
        /*  the number of pages that should be retained to either side of the current page
         *  default value is 1 */
        viewPager.setOffscreenPageLimit(2);
    }

    public static Context getContext() {
        return mContext;
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<Fragment>();
        private final List<String> mFragmentTitleList = new ArrayList<String>();
        private int mSize = 0;

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mSize;
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
            mSize = mSize + 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void dummyDataSetup() {
        new Delete().from(Appearance.class).execute();
        new Delete().from(Description.class).execute();
        new Delete().from(Event.class).execute();
        new Delete().from(Tag.class).execute();
        new Delete().from(Friend.class).execute();

        int i, j;
        Event e;
        Friend f;
        Tag t;
        Appearance a;
        Description d;

        for (i=0; i<50; i++) {
            e = new Event("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", new Date());
            e.save();
            if (i%5 == 0) {
                for (j=0; j < i / 5; j++) {
                    f = new Friend(String.format("Friend %d", i/5));
                    f.save();
                    e.addFriend(f);
                }
                if (i%3 == 2) {
                    for (j=0; j < (i / 3) + 1; j++) {
                        t = new Tag(String.format("Tag %d", i/5));
                        t.save();
                        e.addTag(t);
                    }
                }
            }
            if (i%9 == 0) {
                for (j = 0; j < (i / 9) + 1; j++) {
                    t = new Tag(String.format("Tag %d", i / 5));
                    t.save();
                    e.addTag(t);
                }
            }
        }
    }

}
