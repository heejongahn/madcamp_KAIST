package com.example.nobell.project3;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
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
import com.example.nobell.project3.ui.MainTabLayout;
import com.example.nobell.project3.ui.PagerFragment;
import com.example.nobell.project3.ui.TagTabFragment;
import com.example.nobell.project3.ui.WriteEventFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private PagerFragment pagerFragment;
    private TabLayout tabLayout;

    /* Singleton class */
    private static MainActivity mInstance;

    /* To managing fragments */
    private FragmentManager fragmentManager;
    // private int stackCount; /* This decides whether TabLayout is shown or not. */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Used when fragment transaction is needed
         * Moreover, database needs its context.
         * Assumption: there is only one MainActivity class : should be wrong. */
        if (mInstance != null) {
            throw new RuntimeException("Main Activity onCreate called twice!");
        }
        mInstance = this;
        fragmentManager = getSupportFragmentManager();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.maintablayout);
        pagerFragment = new PagerFragment();

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.maincontent, pagerFragment, "PAGER");
        ft.commit();

        ActiveAndroid.initialize(this);
        dummyDataSetup();
        Log.i("debug", Integer.toString(new Select().all().from(Event.class).execute().size()));
        List<Appearance> appearances = new Select().all().from(Appearance.class).execute();
        Log.i("debug", Integer.toString(appearances.size()));
        for (Appearance appearance : appearances) {
            Log.d("debug", String.format("%d", appearance.friend.getId()));
        }
    }

    /*
     * After Initialize the fragment, call this function with initialized fragment.
     * This could be called by MainActivity.getInstance().startFragment()
     * It removes the Tabs, hide previous fragment in R.id.maincontent in activitymain layout
     *   and add the given new fragment to the R.id.maincontent in activitymain.
     * Backbutton makes the transition reversed. (remove child fragment, and reveal the given fragment)
     * This revealing has callback on onHide(boolean b=false).
     */
    public void startFragment(Fragment fragment) {
        FragmentTransaction t = fragmentManager.beginTransaction();
        t.hide(getSupportFragmentManager().findFragmentById(R.id.maincontent));
        t.add(R.id.maincontent, fragment);
        t.addToBackStack(null);
        t.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        t.commit();
    }
    //public void launchNewFragment ()
    // Menu is for debugging purpose
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menumain, menu);
        return true;
    }

    // Menu is for debugging purpose
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();

        switch (item.getItemId()) {
            case R.id.fragmenttest:
                supportInvalidateOptionsMenu();
                WriteEventFragment.activate();
                break;
        }
        return true;
    }

    public static MainActivity getInstance() {
        return mInstance;
    }
    public TabLayout getTabLayout() { return tabLayout; }

    private void dummyDataSetup() {
        new Delete().from(Appearance.class).execute();
        new Delete().from(Description.class).execute();
        new Delete().from(Event.class).execute();
        new Delete().from(Tag.class).execute();
        new Delete().from(Friend.class).execute();

        int i;
        Event e;
        Friend f;
        Tag t;
        Appearance a;
        Description d;

        for (i=0; i<50; i++) {
            e = new Event(String.format("Event name %d", i), new Date());
            e.save();
            if (i%5 == 0) {
                f = new Friend(String.format("Friend %d", i/5));
                f.save();
                e.addFriend(f);
            }
            if (i%5 == 2) {
                t = new Tag(String.format("Tag %d", i/5));
                t.save();
                e.addTag(t);
            }
        }
    }
}
