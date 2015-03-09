package edu.washington.chau93.trackd;

import android.net.Uri;
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

import edu.washington.chau93.trackd.fragments.About;
import edu.washington.chau93.trackd.fragments.EventList;
import edu.washington.chau93.trackd.fragments.Explore;
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

    private String[] mItemSelection;
    private ListView mItemSelectionListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = (TrackdApp) getApplication();

        mItemSelection = getResources().getStringArray(R.array.item_selection);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer. Need to move to "NavigationDrawerFragment".
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        mItemSelectionListView = (ListView) findViewById(R.id.itemSelection);
        mItemSelectionListView.setAdapter(
                new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_list_item_activated_1,
                        mItemSelection
                )
        );
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
}
