package com.example.nobell.project4;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Menu option_menu;
    private SearchFragment fragment_search;
    private int isSearch=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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
            super.onBackPressed();
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
            Toast.makeText(this, "nav_feeds is clicked", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_search) {
            isSearch =1;
            this.invalidateOptionsMenu();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragment_search = new SearchFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment_search)
                    .commit();
            Toast.makeText(this, "nav_search is clicked", Toast.LENGTH_SHORT).show();


        } else if (id == R.id.nav_myshops) {
            isSearch=0;
            this.invalidateOptionsMenu();

            Fragment fragment;
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragment = new ShopListFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            Toast.makeText(this, "nav_my shops is clicked", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
