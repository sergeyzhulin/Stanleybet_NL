package lt.appcamp.stanleybet.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import lt.appcamp.stanleybet.R;
import lt.appcamp.stanleybet.app.App;
import lt.appcamp.stanleybet.helper.ConWin;
import lt.appcamp.stanleybet.model.Countries;
import lt.appcamp.stanleybet.model.Leagues;
import lt.appcamp.stanleybet.model.Sport;
import lt.appcamp.stanleybet.services.Api;
import lt.appcamp.stanleybet.views.LockedViewPager;

/**
 * Created by Tadas on 6/14/13.
 */
public class SportsBettingActivity extends FragmentActivity {

    private static final String TAG = SportsBettingActivity.class.getSimpleName();
	private SharedPreferences pref;
	private static final String PREF_LAST_PAYMENT_TIMESTAMP = "pref_last_payment_timestamp";
	private static App app;
	private static Api api;
    private List<String> sports;
    protected List<String> countries;
    protected List<String> leagues;
    private List navTreeList;
    protected int sportID = 0;
    private int paymentType;
    private ViewPager mViewPager;
    private View sportsView, countriesView, leaguesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.sb_action_bar));

        //Get the preferences file
        pref = getSharedPreferences(App.PREF_FILE_NAME, Context.MODE_PRIVATE);
        
        //Get the core application class
        app = App.instance();//(App) getApplicationContext();        
        api = app.api;

        // Set the drawer toggle as the DrawerListener
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        Intent intent = getIntent();

        //if (intent.hasExtra(ConWin.BUNDLE_MODEL)) {
        sports = intent.getStringArrayListExtra(ConWin.BUNDLE_SPORTS_LIST);
        countries = intent.getStringArrayListExtra(ConWin.BUNDLE_COUNTRIES_LIST);
        leagues = intent.getStringArrayListExtra(ConWin.BUNDLE_LEAGUES_LIST);

//        byte[] navTreeBytes = intent.getByteArrayExtra(ConWin.BUNDLE_NAVIGATION_TREE);
//        navTreeList = new ArrayList();

//        ByteArrayInputStream bis = new ByteArrayInputStream(navTreeBytes);
//        ObjectInput in = null;
//        try {
//            in = new ObjectInputStream(bis);
//            navTreeList = (List) in.readObject();
//        } catch (IOException ex) {
//
//        } catch (ClassNotFoundException ex) {
//
//        } finally {
//            try {
//                bis.close();
//            } catch (IOException ex) {
//                // ignore close exception
//            }
//            try {
//                if (in != null) {
//                    in.close();
//                }
//            } catch (IOException ex) {
//                // ignore close exception
//            }
//        }

//        Parcel getNavTree = Parcel.obtain();
//        try {
//            getNavTree.unmarshall(navTreeBytes, 0, navTreeBytes.length);
//            getNavTree.setDataPosition(0);
//            navTreeList = (List) getNavTree.readValue(Sport.class.getClassLoader());
//        } finally {
//            getNavTree.recycle();
//        }

        if (sports == null) {
           sports = new ArrayList<String>();
           sports.add("Basketball");
           sports.add("Football");

            countries = new ArrayList<String>();
            countries.add("LT");
            countries.add("UK");
            countries.add("USA");

            leagues = new ArrayList<String>();
            leagues.add("League A");
            leagues.add("League B");
            leagues.add("League C");
        }


        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager(), this));

        mViewPager.setOffscreenPageLimit(2);
        setTitle(R.string.sport_betting_title);

        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setCurrentItem(1);
        mViewPager.setCurrentItem(2);
        mViewPager.setCurrentItem(0);
        ((LockedViewPager) mViewPager).setLock(true);

    }

    protected int getContentView() {
        return R.layout.activity_sports;
    }

    public void switchTabs(boolean direction, TabHost tabHost) {
        if (direction) // true = move left
        {
            if (tabHost.getCurrentTab() == 0)
                tabHost.setCurrentTab(tabHost.getTabWidget().getTabCount() - 1);
            else
                tabHost.setCurrentTab(tabHost.getCurrentTab() - 1);
        } else
        // move right
        {
            if (tabHost.getCurrentTab() != (tabHost.getTabWidget()
                    .getTabCount() - 1))
                tabHost.setCurrentTab(tabHost.getCurrentTab() + 1);
            else
                tabHost.setCurrentTab(0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
//            case R.id.action_share:
//                share();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public class SportsListAdapter extends ArrayAdapter<String> {
        private LinearLayout sportView;

        public SportsListAdapter(Context context, List<String> albumNames) {
            super(context, R.layout.sports_list_item, R.id.sport_name, albumNames);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            sportView = (LinearLayout) inflater.inflate(R.layout.sports_list_item, parent, false);
            ((TextView) sportView.findViewById(R.id.sport_name)).setText(getItem(position));
//            if (MusicPlayerUI.currentAlbum == position) {
//                ((TextView) albumView.findViewById(R.id.trackName)).setTextColor(getContext().getResources().getColor(R.color.trackSelected));
////            trackView.findViewById(R.id.currentTrack).setBackgroundResource(R.drawable.ribbon_wrap);
//            } else {
//                ((TextView) albumView.findViewById(R.id.trackName)).setTextColor(getContext().getResources().getColor(R.color.white));
////            trackView.findViewById(R.id.currentTrack).setBackground(null);
//            }

            return sportView;
        }

    }

    public class CountriesListAdapter extends ArrayAdapter<String> {
        private LinearLayout sportView;

        public CountriesListAdapter(Context context, List<String> albumNames) {
            super(context, R.layout.sports_list_item, R.id.sport_name, albumNames);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            sportView = (LinearLayout) inflater.inflate(R.layout.sports_list_item, parent, false);
            ((TextView) sportView.findViewById(R.id.sport_name)).setText(getItem(position));
//            if (MusicPlayerUI.currentAlbum == position) {
//                ((TextView) albumView.findViewById(R.id.trackName)).setTextColor(getContext().getResources().getColor(R.color.trackSelected));
////            trackView.findViewById(R.id.currentTrack).setBackgroundResource(R.drawable.ribbon_wrap);
//            } else {
//                ((TextView) albumView.findViewById(R.id.trackName)).setTextColor(getContext().getResources().getColor(R.color.white));
////            trackView.findViewById(R.id.currentTrack).setBackground(null);
//            }

            return sportView;
        }

    }


    public class LeaguesListAdapter extends ArrayAdapter<String> {
        private LinearLayout sportView;

        public LeaguesListAdapter(Context context, List<String> leaguesNames) {
            super(context, R.layout.sports_list_item, R.id.sport_name, leaguesNames);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            sportView = (LinearLayout) inflater.inflate(R.layout.sports_list_item, parent, false);
            ((TextView) sportView.findViewById(R.id.sport_name)).setText(getItem(position));
            ((ImageView) sportView.findViewById(R.id.sport_image)).setVisibility(View.VISIBLE);

//            if (MusicPlayerUI.currentAlbum == position) {
//                ((TextView) albumView.findViewById(R.id.trackName)).setTextColor(getContext().getResources().getColor(R.color.trackSelected));
////            trackView.findViewById(R.id.currentTrack).setBackgroundResource(R.drawable.ribbon_wrap);
//            } else {
//                ((TextView) albumView.findViewById(R.id.trackName)).setTextColor(getContext().getResources().getColor(R.color.white));
////            trackView.findViewById(R.id.currentTrack).setBackground(null);
//            }

            return sportView;
        }

    }

    /**
     * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private FragmentActivity fragmentActivity;

        public SectionsPagerAdapter(FragmentManager fm, FragmentActivity fragmentActivity) {
            super(fm);
            this.fragmentActivity = fragmentActivity;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a DummySectionFragment (defined as a static inner class
            // below) with the page number as its lone argument.
            Fragment fragment = new SportsBettingFragment(fragmentActivity);
            Bundle args = new Bundle();
            args.putInt(SportsBettingFragment.ARG_SECTION_NUMBER, position + 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return "1".toUpperCase(l);
                case 1:
                    return "2".toUpperCase(l);
                case 2:
                    return "3".toUpperCase(l);
            }
            return null;
        }

        public List<String> getSports() {
            return sports;
        }
    }



    public class SportsBettingFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public static final String ARG_SECTION_NUMBER = "section_number";
        private View fragmentView;
        private float lastX = 0;

        private  FragmentActivity fragmentActivity;

        public SportsBettingFragment(FragmentActivity fragmentActivity) {
            this.fragmentActivity = fragmentActivity;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            int position = getArguments().getInt(ARG_SECTION_NUMBER);

            if (position == 1) {
                if (sportsView == null) {
                    sportsView = inflater.inflate(R.layout.sb_sports_fragment, container, false);
                    ListView sportsList = (ListView) sportsView.findViewById(R.id.sports_list);

                    SportsListAdapter sportsListAdapter = new SportsListAdapter(getBaseContext(), sports);
                    sportsList.setAdapter(sportsListAdapter);
                    sportsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            ArrayList<String> countries = new ArrayList<String>();

                            if (navTreeList != null && !navTreeList.isEmpty()) {

                                Sport sport = (Sport) ((ArrayList<Sport>) navTreeList.get(i * 2 - 2)).get(0);
                                List<List> countries_node = (List<List>) navTreeList.get(i * 2 - 1);

                                for (int ii = 0; countries_node.size() > ii; ii = ii + 2) {
                                    List<Countries> cont = (List<Countries>) countries_node.get(ii);
                                    countries.add(((Countries) cont.get(0)).name);
                                }

                                ((SportsBettingActivity) getActivity()).countries = countries;
                            }

                            sportID = i;

                            //Change countries depending on selected sport
//                            CountriesListAdapter countriesListAdapter = new CountriesListAdapter(getBaseContext(), countries);
//                            ((ListView) countriesView.findViewById(R.id.country_list)).setAdapter(countriesListAdapter);
                            ((SportsBettingActivity) getActivity()).setTitle(sports.get(i));
                            mViewPager.setCurrentItem(1);
                        }
                    });
                }

                fragmentView = sportsView;

            } else if (position == 2) {
                if (countriesView == null) {
                    countriesView = inflater.inflate(R.layout.sb_country_fragment, container, false);
                    ListView countryList = (ListView) countriesView.findViewById(R.id.country_list);

                    //Setup leagues tabs
                    TabHost libTabs = (TabHost) countriesView.findViewById(R.id.tabHost);
                    libTabs.setup();
                    TabHost.TabSpec spec = libTabs.newTabSpec("featured");
                    spec.setIndicator(getResources().getString(R.string.sb_betting_featured));
                    spec.setContent(R.id.lg_featured_scroll);
                    libTabs.addTab(spec);
                    spec = libTabs.newTabSpec("favorites");
                    spec.setIndicator(getResources().getString(R.string.sb_betting_favorites));
                    spec.setContent(R.id.lg_favorites_scroll);
                    libTabs.addTab(spec);
                    spec = libTabs.newTabSpec("today");
                    spec.setIndicator(getResources().getString(R.string.sb_betting_today));
                    spec.setContent(R.id.lg_today_scroll);
                    libTabs.addTab(spec);
                    spec = libTabs.newTabSpec("next_3h");
                    spec.setIndicator(getResources().getString(R.string.sb_betting_next3h));
                    spec.setContent(R.id.lg_next3h_scroll);
                    libTabs.addTab(spec);

                    CountriesListAdapter countriesListAdapter = new CountriesListAdapter(getBaseContext(), countries);
                    countryList.setAdapter(countriesListAdapter);
                    countryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            ArrayList<String> leagues = new ArrayList<String>();
                            if (navTreeList != null && !navTreeList.isEmpty()) {
                                Sport sport = (Sport) ((ArrayList<Sport>) navTreeList.get(sportID * 2 - 2)).get(0);
                                List<List> countries_node = (List<List>) navTreeList.get(sportID * 2 - 1);
                                List<Countries> cont = (List<Countries>) countries_node.get(i * 2 - 2);
                                List<Leagues> leagues_node = (List<Leagues>) countries_node.get(i * 2 - 1);

                                for (int ii = 0; leagues_node.size() > ii; ii++) {
                                    leagues.add(((Leagues) leagues_node.get(ii)).name);
                                }

                                ((SportsBettingActivity) getActivity()).leagues = leagues;
                            }

                            //Change countries depending on selected sport
//                            LeaguesListAdapter countriesListAdapter = new LeaguesListAdapter(getBaseContext(), leagues);
//                            ((ListView) leaguesView.findViewById(R.id.groups_list)).setAdapter(countriesListAdapter);
                            ((SportsBettingActivity) getActivity()).setTitle(sports.get(sportID));
                            mViewPager.setCurrentItem(2);
                        }
                    });
                    //Swipe tabs
                    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent touchevent) {
                            switch (touchevent.getAction()) {
                                // when user first touches the screen to swap
                                case MotionEvent.ACTION_DOWN: {
                                    lastX = touchevent.getX();
                                    break;
                                }
                                case MotionEvent.ACTION_UP: {
                                    float currentX = touchevent.getX();

                                    // if left to right swipe on screen
                                    if (lastX < currentX) {

                                        switchTabs(true, (TabHost) countriesView.findViewById(R.id.tabHost));
                                    }

                                    // if right to left swipe on screen
                                    if (lastX > currentX) {
                                        switchTabs(false, (TabHost) countriesView.findViewById(R.id.tabHost));
                                    }

                                    break;
                                }
                            }
                            return false;
                        }
                    };
                    countryList.setOnTouchListener(onTouchListener);


                    ListView countryFavoritsList = (ListView) countriesView.findViewById(R.id.lg_favorites_list);
                    ListView countryTodayList = (ListView) countriesView.findViewById(R.id.lg_today_list);
                    ListView countryNext3HList = (ListView) countriesView.findViewById(R.id.lg_next3h_list);
                    countryFavoritsList.setAdapter(countriesListAdapter);
                    countryTodayList.setAdapter(countriesListAdapter);
                    countryNext3HList.setAdapter(countriesListAdapter);
                    //Tab swipe
                    countryFavoritsList.setOnTouchListener(onTouchListener);
                    countryTodayList.setOnTouchListener(onTouchListener);
                    countryNext3HList.setOnTouchListener(onTouchListener);
                }

                fragmentView = countriesView;
            } else if (position == 3) {
                if (leaguesView == null) {
                    leaguesView = inflater.inflate(R.layout.sb_groups_fragment, container, false);
                    ListView groupsList = (ListView) leaguesView.findViewById(R.id.lg_featured_list);

                    //Setup leagues tabs
                    TabHost libTabs = (TabHost) leaguesView.findViewById(R.id.tabHost);
                    libTabs.setup();
                    TabHost.TabSpec spec = libTabs.newTabSpec("featured");
                    spec.setIndicator(getResources().getString(R.string.sb_betting_featured));
                    spec.setContent(R.id.lg_featured_scroll);
                    libTabs.addTab(spec);
                    spec = libTabs.newTabSpec("favorites");
                    spec.setIndicator(getResources().getString(R.string.sb_betting_favorites));
                    spec.setContent(R.id.lg_favorites_scroll);
                    libTabs.addTab(spec);
                    spec = libTabs.newTabSpec("today");
                    spec.setIndicator(getResources().getString(R.string.sb_betting_today));
                    spec.setContent(R.id.lg_today_scroll);
                    libTabs.addTab(spec);
                    spec = libTabs.newTabSpec("next_3h");
                    spec.setIndicator(getResources().getString(R.string.sb_betting_next3h));
                    spec.setContent(R.id.lg_next3h_scroll);
                    libTabs.addTab(spec);

                    LeaguesListAdapter leaguesListAdapter = new LeaguesListAdapter(getBaseContext(), leagues);
                    groupsList.setAdapter(leaguesListAdapter);
                    groupsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            ((SportsBettingActivity) getActivity()).setTitle(R.string.sport_betting_title);
                            mViewPager.setCurrentItem(0);
                        }
                    });
                    //Swipe tabs
                    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent touchevent) {
                            switch (touchevent.getAction()) {
                                // when user first touches the screen to swap
                                case MotionEvent.ACTION_DOWN: {
                                    lastX = touchevent.getX();
                                    break;
                                }
                                case MotionEvent.ACTION_UP: {
                                    float currentX = touchevent.getX();

                                    // if left to right swipe on screen
                                    if (lastX < currentX) {

                                        switchTabs(true, (TabHost) leaguesView.findViewById(R.id.tabHost));
                                    }

                                    // if right to left swipe on screen
                                    if (lastX > currentX) {
                                        switchTabs(false, (TabHost) leaguesView.findViewById(R.id.tabHost));
                                    }

                                    break;
                                }
                            }
                            return false;
                        }
                    };
                    groupsList.setOnTouchListener(onTouchListener);

                    ListView leaguesFavoritsList = (ListView) leaguesView.findViewById(R.id.lg_favorites_list);
                    ListView leaguesTodayList = (ListView) leaguesView.findViewById(R.id.lg_today_list);
                    ListView leaguesNext3HList = (ListView) leaguesView.findViewById(R.id.lg_next3h_list);
                    leaguesFavoritsList.setAdapter(leaguesListAdapter);
                    leaguesTodayList.setAdapter(leaguesListAdapter);
                    leaguesNext3HList.setAdapter(leaguesListAdapter);
                    //Tabs swipe
                    leaguesFavoritsList.setOnTouchListener(onTouchListener);
                    leaguesTodayList.setOnTouchListener(onTouchListener);
                    leaguesNext3HList.setOnTouchListener(onTouchListener);

                    ListView countriesFavoritsList = (ListView) countriesView.findViewById(R.id.lg_favorites_list);
                    countriesFavoritsList.setAdapter(leaguesListAdapter);
                }

                fragmentView = leaguesView;
            }

            return fragmentView;
        }
    }
}
