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

import com.activeandroid.ActiveAndroid;
import com.example.nobell.project3.dataset.Event;
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
    private int stackCount; /* This decides whether TabLayout is shown or not. */

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
    }

    //public void launchNewFragment ()
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menumain, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();

        switch (item.getItemId()) {
            case R.id.fragmenttest:
                supportInvalidateOptionsMenu();
                FragmentTransaction t = getSupportFragmentManager().beginTransaction();
                WriteEventFragment mFrag = new WriteEventFragment();
                // t.replace(R.id.maincontent, mFrag);
                // t.detach(getSupportFragmentManager().findFragmentByTag("PAGER"));
                t.hide(getSupportFragmentManager().findFragmentByTag("PAGER"));
                t.add(R.id.maincontent, mFrag);
                t.addToBackStack(null);
                t.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                t.commit();
                break;
        }
        return true;
    }

    public static MainActivity getInstance() {
        return mInstance;
    }
    public TabLayout getTabLayout() { return tabLayout; }
}
