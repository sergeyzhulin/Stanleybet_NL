package lt.appcamp.stanleybet.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import lt.appcamp.stanleybet.R;
import lt.appcamp.stanleybet.adapters.LoginPagerAdapter;
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
public class LoginActivity extends FragmentActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
	private SharedPreferences pref;
//	private static final String PREF_LAST_PAYMENT_TIMESTAMP = "pref_last_payment_timestamp";
//	private static App app;
//	private static Api api;
//    private List<String> sports;
//    protected List<String> countries;
//    protected List<String> leagues;
//    private List navTreeList;
//    protected int sportID = 0;
//    private int paymentType;
    private ViewPager mViewPager;
//    private View sportsView, countriesView, leaguesView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.sb_action_bar));

        //Get the preferences file
        pref = getSharedPreferences(App.PREF_FILE_NAME, Context.MODE_PRIVATE);
        
//        //Get the core application class
//        app = App.instance();//(App) getApplicationContext();
//        api = app.api;
//
        // Set the drawer toggle as the DrawerListener
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
//        Intent intent = getIntent();
//
//        //if (intent.hasExtra(ConWin.BUNDLE_MODEL)) {
//        sports = intent.getStringArrayListExtra(ConWin.BUNDLE_SPORTS_LIST);
//        countries = intent.getStringArrayListExtra(ConWin.BUNDLE_COUNTRIES_LIST);
//        leagues = intent.getStringArrayListExtra(ConWin.BUNDLE_LEAGUES_LIST);

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

//        if (sports == null) {
//           sports = new ArrayList<String>();
//           sports.add("Basketball");
//           sports.add("Football");
//
//            countries = new ArrayList<String>();
//            countries.add("LT");
//            countries.add("UK");
//            countries.add("USA");
//
//            leagues = new ArrayList<String>();
//            leagues.add("League A");
//            leagues.add("League B");
//            leagues.add("League C");
//        }


        mViewPager = (ViewPager) findViewById(R.id.login_viewpager);
        mViewPager.setAdapter(new LoginPagerAdapter(getSupportFragmentManager()));
        ((LockedViewPager) mViewPager).setLock(true);

//        mViewPager.setOffscreenPageLimit(2);
        setTitle(R.string.sb_login_login_title);

//        mViewPager.setOffscreenPageLimit(2);
//        mViewPager.setCurrentItem(1);
//        mViewPager.setCurrentItem(2);
//        mViewPager.setCurrentItem(0);


    }

    protected int getContentView() {
        return R.layout.activity_login;
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

    public void onClickLogin(View view) {
        mViewPager.setCurrentItem(1);
    }

    public void onClickCreateAccount(View view) {
        Intent intent = new Intent(LoginActivity.this, NewAccountActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void onClickRecoverUsername(View view) {
        Intent intent = new Intent(LoginActivity.this, AccountRecoverUserNameActivity.class);
        startActivity(intent);
    }

    public void onClickRecoverPassword(View view) {
        Intent intent = new Intent(LoginActivity.this, AccountRecoverPasswordActivity.class);
        startActivity(intent);
    }

    public void onClickContinue(View view) {
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
