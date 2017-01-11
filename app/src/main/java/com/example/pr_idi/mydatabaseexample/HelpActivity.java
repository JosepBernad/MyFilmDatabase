package com.example.pr_idi.mydatabaseexample;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by roger on 11/01/17.
 */

public class HelpActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private Button prevButton, nextButton;
    private ImageView imageView;
    private int actualImage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        getSupportActionBar().setTitle("Help");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        actualImage=0;

        prevButton = (Button) findViewById(R.id.prevButton);
        nextButton = (Button) findViewById(R.id.nextButton);
        imageView = (ImageView) findViewById(R.id.imageView);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_help);

        mDrawerList = (ListView) findViewById(R.id.navListHelp);
        LayoutInflater myinflater = getLayoutInflater();
        ViewGroup myHeader = (ViewGroup)myinflater.inflate(R.layout.headerlayout, mDrawerList, false);
        mDrawerList.addHeaderView(myHeader, null, false);
        addDrawerItems();
        setupDrawer();

        prevButton.setVisibility(View.GONE);
        nextButton.setVisibility(View.VISIBLE);

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(actualImage==0){
                    Toast.makeText(HelpActivity.this,"This is the first image",Toast.LENGTH_SHORT).show();
                }
                else {
                    --actualImage;
                    changeImage();
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(actualImage==6){
                    Toast.makeText(HelpActivity.this,"This is the last image",Toast.LENGTH_SHORT).show();
                }
                else {
                    ++actualImage;
                    changeImage();
                }
            }
        });
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
                        finish();
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        Intent i = new Intent(HelpActivity.this, NewFilmActivity.class);
                        startActivity(i);
                        break;
                    case 3: //cas Help
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4: //cas About
                        finish();
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        i = new Intent(HelpActivity.this, AboutActivity.class);
                        startActivity(i);
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
                getSupportActionBar().setTitle("Help");
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
    private void changeImage(){
        switch(actualImage){
            case 0:
                prevButton.setVisibility(View.GONE);
                nextButton.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.im_help1);
                break;
            case 1:
                prevButton.setVisibility(View.VISIBLE);
                nextButton.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.im_help2);
                break;
            case 2:
                prevButton.setVisibility(View.VISIBLE);
                nextButton.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.im_help3);
                break;
            case 3:
                prevButton.setVisibility(View.VISIBLE);
                nextButton.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.im_help4);
                break;
            case 4:
                prevButton.setVisibility(View.VISIBLE);
                nextButton.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.im_help5);
                break;
            case 5:
                prevButton.setVisibility(View.VISIBLE);
                nextButton.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.im_help6);
                break;
            case 6:
                prevButton.setVisibility(View.VISIBLE);
                nextButton.setVisibility(View.GONE);
                imageView.setImageResource(R.drawable.im_help7);
                break;
            default:
                prevButton.setVisibility(View.GONE);
                nextButton.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.im_help1);
        }
    }
}
