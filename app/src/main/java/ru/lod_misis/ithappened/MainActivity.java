package ru.lod_misis.ithappened;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import io.realm.Realm;
import ru.lod_misis.ithappened.fragments.FragmentHistoryEvents;
import ru.lod_misis.ithappened.fragments.FragmentListEvents;
import ru.lod_misis.ithappened.fragments.FragmentMain;
import ru.lod_misis.ithappened.fragments.FragmentStatisticsEvents;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static FloatingActionButton fab;
    private FragmentHistoryEvents fragmentHistoryEvents;
    private FragmentMain fragmentMain;
    private FragmentListEvents fragmentListEvents;
    private FragmentStatisticsEvents fragmentStatisticsEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initRealmDB();
        initToolbar();
        initFloatingActionButton();
        initFragments();
        initNavigationView();

        getFragmentTransaction().add(R.id.fl_fragmentContainer, fragmentMain).commit();
    }

    private void initNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void initRealmDB() {
        Realm.init(this);
    }

    private void initFragments() {
        fragmentHistoryEvents = new FragmentHistoryEvents();
        fragmentMain = new FragmentMain();
        fragmentListEvents = new FragmentListEvents();
        fragmentStatisticsEvents = new FragmentStatisticsEvents();
    }

    private void initFloatingActionButton() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditingEvents.class);
                startActivity(intent);
            }
        });
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        fab.setVisibility(View.VISIBLE);
        int id = item.getItemId();
        FragmentTransaction fragmentTransaction = getFragmentTransaction();

        switch (id) {
            case R.id.nav_main:
                fragmentTransaction.replace(R.id.fl_fragmentContainer, fragmentMain).commit();
                break;
            case R.id.nav_menu_listAllEvent:
                fragmentTransaction.replace(R.id.fl_fragmentContainer, fragmentListEvents).commit();
                break;
            case R.id.nav_history:
                fragmentTransaction.replace(R.id.fl_fragmentContainer, fragmentHistoryEvents).commit();
                break;
            case R.id.nav_statistics:
                fragmentTransaction.replace(R.id.fl_fragmentContainer, fragmentStatisticsEvents).commit();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private FragmentTransaction getFragmentTransaction() {
        return getSupportFragmentManager().beginTransaction();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
