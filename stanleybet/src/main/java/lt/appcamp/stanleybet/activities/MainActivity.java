package lt.appcamp.stanleybet.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Parcel;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lt.appcamp.stanleybet.R;
import lt.appcamp.stanleybet.adapters.LoginPagerAdapter;
import lt.appcamp.stanleybet.adapters.MainPagerAdapter;
import lt.appcamp.stanleybet.app.App;
import lt.appcamp.stanleybet.helper.ConWin;
import lt.appcamp.stanleybet.helper.TimeHelper;
import lt.appcamp.stanleybet.model.Countries;
import lt.appcamp.stanleybet.model.Leagues;
import lt.appcamp.stanleybet.model.Sport;
import lt.appcamp.stanleybet.services.Api;
import lt.appcamp.stanleybet.views.LockedViewPager;
import lt.appcamp.utils.AppFont;

public class MainActivity extends FragmentActivity {
    private String[] mMenuItems;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mTitle, mDrawerTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    private App app;
    private Api api;
    private int mSelectedMenuPos = -1;
    private final boolean loggedIn = false;
    private ArrayList<String> mStringList;
    private MenuNavigationAdapter mAdapter;
    private ArrayList<Sport> sportsList;
    private ArrayList<Countries> countriesList;
    private ArrayList<Leagues> leaguesList;
    private boolean mBS = true, mConnectedUser = false;
    private ArrayList<List> navigationTree;


    MainPagerAdapter mMainPagerAdapter;
    LockedViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);

        app = App.instance();
        api = app.api;

        setContentView(R.layout.activity_main);

        getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.sb_action_bar));


        if (loggedIn)
            mMenuItems = getResources().getStringArray(R.array.menu_array_logged_in);
        else
            mMenuItems = getResources().getStringArray(R.array.menu_array_anonymous);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        //Menu List
        mStringList = new ArrayList<String>(Arrays.asList(mMenuItems));
        mAdapter = new MenuNavigationAdapter(getBaseContext(), mStringList);
        mDrawerList.setAdapter(mAdapter);

        //mDrawerList.setAdapter(new ArrayAdapter<String> (this, R.layout.drawer_list_item, mMenuItems) );
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // Set the drawer toggle as the DrawerListener
        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.openDrawer(mDrawerList);

        if (savedInstanceState == null) {
            selectItem(0);
        }

        //Application main tabs swipe implementation
        mMainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mViewPager = (LockedViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mMainPagerAdapter);

        refreshNavigationTree();
    }

    private void refreshNavigationTree() {
        api.getNavigationTreeFromStringUri(
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        app.refreshDataTimestamp = TimeHelper.getNowTimestampGMT();

                        try {
                            sportsList = Sport.getSports(new JSONArray(response));
                            Log.e("JSON", sportsList.toString());

                            navigationTree = Sport.getNavigationTree(new JSONArray(response));
                            //countriesList = (ArrayList<Countries>) navigationTree.get(1);
                            //leaguesList = (ArrayList<Leagues>) navigationTree.get(2);


                        }
                        catch (JSONException jsException) {
                            jsException.printStackTrace();
                        }

                        Log.e("JSON", response.toString());

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getBaseContext(), "Nepavyko gauti informacijos iš Stanleybet", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    public List getNavigationTree() {
        return navigationTree;
    }

    private void selectItem(int position) {
        mSelectedMenuPos = position;

        if (position == 0) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);

            if (mConnectedUser) {
                mMenuItems = getResources().getStringArray(R.array.menu_array_logged_in);
                mStringList = new ArrayList<String>(Arrays.asList(mMenuItems));
                mAdapter = new MenuNavigationAdapter(getBaseContext(), mStringList);
                mDrawerList.setAdapter(mAdapter);
                mConnectedUser = false;
            } else {
                mMenuItems = getResources().getStringArray(R.array.menu_array_anonymous);
                mStringList = new ArrayList<String>(Arrays.asList(mMenuItems));
                mAdapter = new MenuNavigationAdapter(getBaseContext(), mStringList);
                mDrawerList.setAdapter(mAdapter);
                mConnectedUser =  true;
                mDrawerLayout.closeDrawers();
            }
        }
        else if (position == 1) {
            mDrawerLayout.closeDrawers();
            mViewPager.setCurrentItem(0);
            setTitle("Home");

        }
        else if (position == 3) {
            Intent intent = new Intent(MainActivity.this, SportsBettingActivity.class);


            if (this.sportsList == null) {
                this.sportsList = new ArrayList<Sport>();
                this.countriesList = new ArrayList<Countries>();
                this.leaguesList = new ArrayList<Leagues>();

            }
            //Lets put the filters and query into the intent
            ArrayList<String> sports = new ArrayList<String>();
            ArrayList<String> countries = new ArrayList<String>();
            ArrayList<String> leagues = new ArrayList<String>();

            List<List<List>> navTree;
            if (this.navigationTree != null) {
                navTree = (List<List<List>>) this.navigationTree.get(0);
            }
            if (this.navigationTree != null) {
                navTree = (List) this.navigationTree.get(0);
                for (int i = 0; navTree.size() > i; i = i + 2) {
                    Sport sport = (Sport) ((List<List<List>>) navTree).get(i).get(0);
                    List<List> countries_node = (List<List>) navTree.get(i + 1);

                    sports.add(sport.name);

                    for (int ii = 0; countries_node.size() > ii; ii = ii + 2) {
                        List<Countries> cont = (List<Countries>) countries_node.get(ii);
                        countries.add(((Countries) cont.get(0)).name);
                        List<Leagues> leagues_node = (List<Leagues>) countries_node.get(ii + 1);

                        for (Leagues lg : leagues_node) {
                            leagues.add(lg.name);
                        }
                    }

                }
            } else {
                navTree = new ArrayList();
            }



            if (countries.isEmpty()) {
                sports.add("Basketball");
                sports.add("Football");

                countries.add("NL");
                countries.add("LT");
                countries.add("UK");

                leagues.add("League A");
                leagues.add("League B");
                leagues.add("League C");

            }

            intent.putStringArrayListExtra(ConWin.BUNDLE_SPORTS_LIST, sports);
            intent.putStringArrayListExtra(ConWin.BUNDLE_LEAGUES_LIST, leagues);
            intent.putStringArrayListExtra(ConWin.BUNDLE_COUNTRIES_LIST, countries);

//            byte[] yourBytes = new byte[1];
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            ObjectOutput out = null;
//            try {
//                out = new ObjectOutputStream(bos);
//                out.writeObject(navTree);
//                yourBytes = bos.toByteArray();
//            } catch (IOException excp) {
//
//            } finally {
//                try {
//                    if (out != null) {
//                        out.close();
//                    }
//                } catch (IOException ex) {
//                    // ignore close exception
//                }
//                try {
//                    bos.close();
//                } catch (IOException ex) {
//                    // ignore close exception
//                }
//            }

//            Parcel storeNavTree = Parcel.obtain();
//            storeNavTree.writeList((List) navTree);
//            yourBytes = storeNavTree.marshall();
//            storeNavTree.recycle();

//            intent.putExtra(ConWin.BUNDLE_NAVIGATION_TREE, yourBytes);
            //intent.putExtra(ConWin.BUNDLE_SEARCH_STRING, mSearchQuery);
            //intent.putExtra(ConWin.BUNDLE_CATEGORY_TYPE, CategoryHelper.CONST_CATEGORY_CITY);

            startActivity(intent);
            //overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        }
        else if (position == 5) {
            Intent intent = new Intent(MainActivity.this, PromotionsActivity.class);
            startActivity(intent);
        }
        else if (position == 7) {
            mMenuItems = getResources().getStringArray(R.array.menu_array_anonymous);
            mStringList = new ArrayList<String>(Arrays.asList(mMenuItems));
            mAdapter = new MenuNavigationAdapter(getBaseContext(), mStringList);
            mDrawerList.setAdapter(mAdapter);
            mConnectedUser =  true;

        }
//        Fragment fragment = new SportsFragment();
//        Bundle args = new Bundle();
//        args.putInt(SportsFragment.ARG_SPORT_NUMBER, position);
//        fragment.setArguments(args);
//
//        // Insert the fragment by replacing any existing fragment
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        //fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
//
//        // Highlight the selected item, update the title, and close the drawer
//        mDrawerList.setItemChecked(position, true);
//        setTitle(mMenuItems[position]);
//        mDrawerLayout.closeDrawer(mDrawerList);
//        if (mViewPager != null)
//            mViewPager.setCurrentItem(position, true);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        //boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
            case R.id.action_betslip:
                if (mBS) {
                    mDrawerLayout.closeDrawers();
                    //mDrawerLayout.setDrawingCacheBackgroundColor(getResources().getColor(R.color.sb_red_buttom_item_bottom));
                    mViewPager.setCurrentItem(1);
                    mBS = false;
                    setTitle("BetSlip");
                }
                else {
                    mDrawerLayout.closeDrawers();
                    mViewPager.setCurrentItem(0);
                    setTitle("Home");
                    mBS = true;
                }

//                // create intent to perform web search for this planet
//                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
//                intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
//                // catch event that there's no activity to handle intent
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
//                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public static class SportsFragment extends Fragment {
        public static final String ARG_SPORT_NUMBER = "sport_number";

        public SportsFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int i = getArguments().getInt(ARG_SPORT_NUMBER);
            View rootView;
            if (i == 2) {
                rootView = inflater.inflate(R.layout.fragment_betslip, container, false);
            }
            else {
                rootView = inflater.inflate(R.layout.fragment_sport, container, false);
                //((ImageView) rootView.findViewById(R.id.image)).setImageResource(R.drawable.main_act);
            }

//            String sport;

//            if (loggedIn)
//                sport = getResources().getStringArray(R.array.menu_array_logged_in)[i];
//            else
//                sport = getResources().getStringArray(R.array.menu_array_anonymous)[i];


            //int imageId = getResources().getIdentifier(sport.toLowerCase(Locale.getDefault()), "drawable", getActivity().getPackageName());
            //getActivity().setTitle(sport);
            return rootView;
        }
    }

    public void stopSwipe (boolean stopSwipe) {
        mViewPager.setLock(stopSwipe);
    }

    private class MenuNavigationAdapter extends ArrayAdapter<String> {

        public MenuNavigationAdapter(Context context, List<String> menuStringList) {
            super(context, R.layout.drawer_list_item, R.id.txt_menu_item, menuStringList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View v = (LinearLayout) inflater.inflate(R.layout.drawer_list_item, parent, false);
            ImageView menuItemImage = (ImageView) v.findViewById(R.id.img_menu_item);
            if (position == 0) { // || position == 8) {
                menuItemImage.setVisibility(View.VISIBLE);
                menuItemImage.setBackgroundResource(R.drawable.user_anonymous);
                ((TextView) v.findViewById(R.id.txt_menu_item)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            }
//            else {
//                menuItemImage.setBackgroundResource(mMenuItemImg[position]);
//            }
            ((TextView) v.findViewById(R.id.txt_menu_item)).setText(mMenuItems[position]);
            AppFont.setCustomFont(v, getAssets(), "light");

            //if (mSelectedMenuPos == position) {
                //v.setBackgroundResource(R.color.semi_transparent_black_25);
                //v.findViewById(R.id.img_menu_item_selected).setBackgroundResource(R.drawable.menu_item_elipse_selected);
            //}

            if (position == 8) {
                v.setBackgroundResource(R.drawable.sb_list_bottom);
                //v.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }

            return v;
        }

        @Override
        public boolean isEnabled(int position) {
            if (position == 8) {
                return false;
            } else {
                return true;
            }
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

    }
}
