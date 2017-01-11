package com.example.pr_idi.mydatabaseexample;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by roger on 11/01/17.
 */

public class AboutActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        getSupportActionBar().setTitle("About");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_about);

        mDrawerList = (ListView) findViewById(R.id.navListAbout);
        LayoutInflater myinflater = getLayoutInflater();
        ViewGroup myHeader = (ViewGroup)myinflater.inflate(R.layout.headerlayout, mDrawerList, false);
        mDrawerList.addHeaderView(myHeader, null, false);
        addDrawerItems();

        setupDrawer();
    }
    private void addDrawerItems() {
        String[] filmArray = {"My Films", "Add Film", "Help", "About"};
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filmArray);
        mDrawerList.setAdapter(mAdapter);


        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        finish();
                        break;
                    case 2: //cas Add Film
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        Intent i = new Intent(AboutActivity.this, NewFilmActivity.class);
                        startActivity(i);
                        break;
                    case 3: //cas Help
                        Toast.makeText(AboutActivity.this, "Help encara s'ha de implementar XD", Toast.LENGTH_SHORT).show();
                        break;
                    case 4: //cas About
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Es crida quan el drawer esta completament obert */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Menu");
                invalidateOptionsMenu();
            }

            /** Es crida quan el drawer esta completament tancat */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle("About");
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Controlar els clicks de la action bar aqui.
        int id = item.getItemId();

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
