package com.example.nobell.project4;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // 애플리케이션 전체에서 사용할 shared preferencce
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor sharedEditor;

    private String ID="";
    private String PWD="";
    private String SID ="";

    private Menu option_menu;
    private SearchFragment fragment_search;
    private int isSearch=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = new Intent(this , LoginActivity.class);
        startActivityForResult(i, 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        sharedPref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        sharedEditor = sharedPref.edit();
        ID = sharedPref.getString("ID", "");
        PWD = sharedPref.getString("PWD", "");
        Log.d("ID", ID);

        sharedPref = getSharedPreferences("sid", Activity.MODE_PRIVATE);
        sharedEditor = sharedPref.edit();
        SID = sharedPref.getString("connect.sid", "");
        Log.d("SID", SID);


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Shop & Shop");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                TextView email_text = (TextView) findViewById(R.id.textView);
                email_text.setText(ID);
                super.onDrawerOpened(drawerView);

                Button button = (Button) findViewById(R.id.logout_button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recreate();
                    }
                });




            }
        };

        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        option_menu = menu;
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.main, option_menu);
        // Associate searchable configuration with the SearchView
        MenuItem item = option_menu.findItem(R.id.menu_search);
        item.setVisible(false);


        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        MenuItem menu_item = option_menu.findItem(R.id.menu_search);
        if (isSearch ==0){
            menu_item.setVisible(false);
        }
        else {
            menu_item.setVisible(true);

            //Execute the search process
            SearchManager searchManager =
                    (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView =
                    (SearchView) option_menu.findItem(R.id.menu_search).getActionView();
            searchView.setSearchableInfo(
                    searchManager.getSearchableInfo(getComponentName()));

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    if (s.equals("")) {
                        fragment_search.initialize();
                    } else {
                        fragment_search.search_items(s);
                    }
                    return false;
                }
            });
        }
        return super.onPrepareOptionsMenu(menu);
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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_feeds) {
            isSearch=0;
            this.invalidateOptionsMenu();

            Fragment fragment;
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragment = new FeedFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                            .commit();
        } else if (id == R.id.nav_search) {
            isSearch =1;
            this.invalidateOptionsMenu();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragment_search = new SearchFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment_search)
                    .commit();
        } else if (id == R.id.nav_myshops) {
            isSearch=0;
            this.invalidateOptionsMenu();

            Fragment fragment;
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragment = new ShopListFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
