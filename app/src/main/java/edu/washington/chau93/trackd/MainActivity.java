package edu.washington.chau93.trackd;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;

import edu.washington.chau93.trackd.download_manager.Checker;
import edu.washington.chau93.trackd.fragments.About;
import edu.washington.chau93.trackd.fragments.Event;
import edu.washington.chau93.trackd.fragments.EventList;
import edu.washington.chau93.trackd.fragments.Explore;
import edu.washington.chau93.trackd.fragments.Organization;
import edu.washington.chau93.trackd.fragments.OrganizationList;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, OnFragmentInteractionListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private CharSequence mTitle;

    private final String TAG = "MainActivity";
    private TrackdApp app;

    private PendingIntent pendingIntent;
    private AlarmManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = (TrackdApp) getApplication();
        am = (AlarmManager) getSystemService(ALARM_SERVICE);
        if(!Trackd.isScheduleStarted()){
            startScheduledUpdates();
            Trackd.setScheduleStarted();
        }

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

    }

    private void startScheduledUpdates() {
        Log.d(TAG, "Scheduling Updates");
        // Every 30 minutes
        long interval = 30 * 60 * 1000;

        Intent checker = new Intent(this, Checker.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, checker, 0);

        am.setRepeating(AlarmManager.ELAPSED_REALTIME, 0, interval, pendingIntent);

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment selection;
        switch (position){
            case 0:
                selection = Explore.newInstance();
                break;
            case 1:
                selection = EventList.newInstance();
                break;
            case 2:
                selection = OrganizationList.newInstance();
                break;
            case 3:
                selection = About.newInstance();
                break;
            case 4:
                selection = Organization.newInstance();
               break;
            case 5:
                selection = Event.newInstance();
                break;
            default:
                selection = Explore.newInstance();
                break;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.container,selection)
                .commit();
    }

    public void onSectionAttached(int number) {
        // mTitle = mItemSelection[number]; // Change the title to the item user chooses
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        // actionBar.setTitle(mTitle); // Changes the title of the action bar
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Good bye!");
        if(am != null){
            am.cancel(pendingIntent);
        }
        if(pendingIntent != null) pendingIntent.cancel();
    }
}
