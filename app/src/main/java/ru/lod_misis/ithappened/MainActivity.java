package ru.lod_misis.ithappened;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
//import android.app.FragmentTransaction;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import ru.lod_misis.ithappened.asyncTasks.AsyncTaskGetPhotoFromGoogleServer;
import ru.lod_misis.ithappened.fragments.FragmentHistoryEvents;
import ru.lod_misis.ithappened.fragments.FragmentListEvents;
import ru.lod_misis.ithappened.fragments.FragmentMain;
import ru.lod_misis.ithappened.fragments.FragmentStatisticsEvents;
import ru.lod_misis.ithappened.model.Event;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView photo = (ImageView) findViewById(R.id.iv_imageAcc);
        TextView nameAcc = (TextView) findViewById(R.id.tv_nameAcc);
        TextView email = (TextView) findViewById(R.id.tv_email);

//        final Intent intent = getIntent();
//        nameAcc.setText(intent.getExtras().getString("name"));
//        email.setText(intent.getExtras().getString("email"));
//        Uri uri = (Uri) intent.getExtras().get("uri");
//        new AsyncTaskGetPhotoFromGoogleServer(photo).execute(uri.toString());

//        Controller.synchronizationData();

        initFloatingActionButton();
        initFragments();

        getFragmentTransaction().add(R.id.fl_fragmentContainer, fragmentMain).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        findViewById(R.id.tv_sync).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent1 = new Intent(MainActivity.this, AutGoogleAccount.class);
//                setResult(11, intent1);
//                finish();
//            }
//        });
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
//                DialogEvent dialogEvent = new DialogEvent(MainActivity.this);
//                dialogEvent.initDialogAddEvent();
//                dialogEvent.show();
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
