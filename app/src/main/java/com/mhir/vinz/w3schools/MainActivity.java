package com.mhir.vinz.w3schools;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.GridView;

import com.mhir.vinz.w3schools.Adapter.GridListAdapter;
import com.mhir.vinz.w3schools.Adapter.ThreeLevelListAdapter;
import com.mhir.vinz.w3schools.Helper.FragmentNavigationManager;
import com.mhir.vinz.w3schools.Interface.NavigationManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static DrawerLayout mDrawerLayout;
    public static ActionBarDrawerToggle mDrawerToggle;
    public static ActionBar actionBar;
    FloatingActionButton fab;
    private String mActivityTitle;
    public static String[] parent, tutorialsSubParent, referencesSubParent, othersSubParent, htmlAndCss,
            javaScript, Programming, serverSide, webBuilding, xmlTutorials, feedback, support;
    private String[] refhtml, refCss, refJavaScript, refProgramming, refServerSide, refXML, refCharSet;

    private ExpandableListView expandableListView;
    private GridView gridView;

    // for data Structure assigning
    public static List<String[]> secondLevel;
    public static List<LinkedHashMap<String, String[]>> data;
    public static LinkedHashMap<String, String[]> thirdLevelTutorials, thirdLevelReferences, thirdLevelOthers;
    public static NavigationManager navigationManager;

    public static int[] icon = { R.drawable.ic_book, R.drawable.ic_study, R.drawable.ic_others };
    public static int[] socialImage = { R.drawable.ic_facebook, R.drawable.ic_gmail,
            R.drawable.ic_linkedin, R.drawable.ic_twitter };
    public static String[] socialWord = {"Facebook", "Email Us", "LinkedIn", "Twitter"};


    // setting in-app fonts
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init view
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        mActivityTitle = getTitle().toString();
        expandableListView = (ExpandableListView)findViewById(R.id.expandableListView);
        //gridView = (GridView)findViewById(R.id.grdView);
        navigationManager = FragmentNavigationManager.getmInstance(this);

        initItems();

        /*
         * This 2 lines of code will add the navigation header along with expandable list view,
         * instead of adding manual on activity_main.xml
         */
        View listHeaderView = getLayoutInflater().inflate(R.layout.nav_header_main, null, false);
        expandableListView.addHeaderView(listHeaderView);

        View listFooterView = getLayoutInflater().inflate(R.layout.nav_footer_main, null, false);
        expandableListView.addFooterView(listFooterView);


        genData();

        addDrawersItem();


        setupDrawer();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_main);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        //if (savedInstanceState == null)
         //   selectFirstItemAsDefault();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("W3Schools");
        getSupportActionBar().show();
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /*private void selectFirstItemAsDefault() {
        if (navigationManager != null) {
            String firstItem = parent[0];
            navigationManager.showFragment(firstItem);
            getSupportActionBar().setTitle(firstItem);
        }
    }*/

    private void setupDrawer() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Menu");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //actionBar.setHomeAsUpIndicator(R.drawable.list_selector);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };

        actionBar = getSupportActionBar();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logo2);
    }

    private void addDrawersItem() {
        /*
         * this is for second level and third level expandable list
         */
        ThreeLevelListAdapter adapter = new ThreeLevelListAdapter(this, parent, secondLevel, data);
        GridListAdapter gridListAdapter = new GridListAdapter(this, socialImage, socialWord);
        gridView = (GridView) findViewById(R.id.grdView);
        gridView.setAdapter(gridListAdapter);

        expandableListView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            /* show one list at a time */
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup) {
                    expandableListView.collapseGroup(previousGroup);
                    previousGroup = groupPosition;
                }
                getSupportActionBar().setTitle(parent[groupPosition]);
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                getSupportActionBar().setTitle("W3Schools");
            }
        });


    }

    public void initItems() {
        /**
         * for data structure:
         */
        secondLevel = new ArrayList<>();
        data = new ArrayList<>();
        /**
         * Datastructure for Third level tutorials.
         */
        thirdLevelTutorials = new LinkedHashMap<>();
        /**
         * Datastructure for Third level references..
         */
        thirdLevelReferences = new LinkedHashMap<>();
        /**
         * /**
         * Datastructure for Third level references..
         */
        thirdLevelOthers = new LinkedHashMap<>();
        /**
         * Initializing all arrays will be used for expandableList menu
         */
        // names for parent
        parent = getResources().getStringArray(R.array.listGroup);
        // for second level
        tutorialsSubParent = getResources().getStringArray(R.array.tutorialsSubParent);
        referencesSubParent = getResources().getStringArray(R.array.referencesSubParent);
        othersSubParent = getResources().getStringArray(R.array.othersSubParent);
        // for child parent
        htmlAndCss = getResources().getStringArray(R.array.htmlAndCss);
        javaScript = getResources().getStringArray(R.array.JavaScript);
        Programming = getResources().getStringArray(R.array.Programming);
        serverSide = getResources().getStringArray(R.array.ServerSide);
        webBuilding = getResources().getStringArray(R.array.WebBuilding);
        xmlTutorials = getResources().getStringArray(R.array.XmlTutorials);
        refhtml = getResources().getStringArray(R.array.refHtml);
        refCss = getResources().getStringArray(R.array.refCSS);
        refJavaScript = getResources().getStringArray(R.array.refJavaScript);
        refProgramming = getResources().getStringArray(R.array.refProgramming);
        refServerSide = getResources().getStringArray(R.array.refServerSide);
        refXML = getResources().getStringArray(R.array.refXML);
        refCharSet = getResources().getStringArray(R.array.refCharSet);

        feedback = getResources().getStringArray(R.array.feedback);
        support = getResources().getStringArray(R.array.support);
    }

    public void genData() {
        // second level category names (main menu)
        secondLevel.add(0, tutorialsSubParent);
        secondLevel.add(1, referencesSubParent);
        secondLevel.add(2, othersSubParent);
        // tutorials category all data
        thirdLevelTutorials.put(tutorialsSubParent[0], htmlAndCss);
        thirdLevelTutorials.put(tutorialsSubParent[1], javaScript);
        thirdLevelTutorials.put(tutorialsSubParent[2], Programming);
        thirdLevelTutorials.put(tutorialsSubParent[3], serverSide);
        thirdLevelTutorials.put(tutorialsSubParent[4], webBuilding);
        thirdLevelTutorials.put(tutorialsSubParent[5], xmlTutorials);

        // references category all data
        thirdLevelReferences.put(referencesSubParent[0], refhtml);
        thirdLevelReferences.put(referencesSubParent[1], refCss);
        thirdLevelReferences.put(referencesSubParent[2], refJavaScript);
        thirdLevelReferences.put(referencesSubParent[3], refProgramming);
        thirdLevelReferences.put(referencesSubParent[4], refServerSide);
        thirdLevelReferences.put(referencesSubParent[5], refXML);
        thirdLevelReferences.put(referencesSubParent[6], refCharSet);

        // others category all data
        thirdLevelOthers.put(othersSubParent[0], feedback);
        thirdLevelOthers.put(othersSubParent[1], support);
        // all data
        data.add(thirdLevelTutorials);
        data.add(thirdLevelReferences);
        data.add(thirdLevelOthers);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder alertBuild =new AlertDialog.Builder(this);
            alertBuild.setMessage("Are you sure you want to exit").setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog alert = alertBuild.create();
            alert.setTitle("Exit");
            alert.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (mDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_fb) {
            Intent fb = new Intent(getApplicationContext(), home.class);
            startActivity(fb);
            this.finish();

        } else if (id == R.id.nav_email) {

        } else if (id == R.id.nav_linkedInn) {

        } else if (id == R.id.nav_twitter) {

        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}










/*public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
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
            AlertDialog.Builder alertBuild =new AlertDialog.Builder(this);
            alertBuild.setMessage("Are you sure you want to exit").setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog alert = alertBuild.create();
            alert.setTitle("Exit");
            alert.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}*/
