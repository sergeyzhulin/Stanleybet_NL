package lt.appcamp.stanleybet.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.viewpagerindicator.CirclePageIndicator;

import lt.appcamp.stanleybet.R;
import lt.appcamp.stanleybet.adapters.LoginPagerAdapter;
import lt.appcamp.stanleybet.adapters.NewAccountPagerAdapter;
import lt.appcamp.stanleybet.app.App;
import lt.appcamp.stanleybet.views.LockedViewPager;

/**
 * Created by Tadas on 6/14/13.
 */
public class NewAccountActivity extends FragmentActivity {

    private static final String TAG = NewAccountActivity.class.getSimpleName();
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
    private NewAccountPagerAdapter mNewAccountPagerAdapter;
//    private View sportsView, countriesView, leaguesView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        //Get the preferences file
        pref = getSharedPreferences(App.PREF_FILE_NAME, Context.MODE_PRIVATE);

        // Set the drawer toggle as the DrawerListener
        getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.sb_action_bar));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mViewPager = (ViewPager) findViewById(R.id.new_account_viewpager);
        mNewAccountPagerAdapter = new NewAccountPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mNewAccountPagerAdapter);
        ((LockedViewPager) mViewPager).setLock(true);

//        mViewPager.setOffscreenPageLimit(2);
        setTitle(R.string.sb_new_account_title);

//        mViewPager.setOffscreenPageLimit(2);
//        mViewPager.setCurrentItem(1);
//        mViewPager.setCurrentItem(2);
//        mViewPager.setCurrentItem(0);
        //Bind the title indicator to the adapter
        CirclePageIndicator circleIndicator = (CirclePageIndicator) findViewById(R.id.new_account_circle_indicator);
        circleIndicator.setViewPager(mViewPager);

    }

    protected int getContentView() {
        return R.layout.activity_new_account;
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

    public void onNextClick(View view) {
        if (mViewPager.getCurrentItem() == 2)
            this.finish();
        else if (mViewPager.getCurrentItem() == 0)
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
        else if (mViewPager.getCurrentItem() == 1) {
            ((Button) view).setText(R.string.sb_new_account_button_finish);
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
        }
    }

    public void onClicAgreement (View view) {
        Toast.makeText(getBaseContext(),R.string.sb_new_account_agreement_text, Toast.LENGTH_LONG).show();
    }

}
