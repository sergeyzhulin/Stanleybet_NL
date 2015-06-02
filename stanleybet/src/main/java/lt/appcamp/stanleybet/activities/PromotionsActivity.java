package lt.appcamp.stanleybet.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lt.appcamp.stanleybet.R;
import lt.appcamp.stanleybet.adapters.LoginPagerAdapter;
import lt.appcamp.stanleybet.adapters.PromotionsPagerAdapter;
import lt.appcamp.stanleybet.app.App;
import lt.appcamp.stanleybet.views.LockedViewPager;
import lt.appcamp.utils.AppFont;

/**
 * Created by Tadas on 6/14/13.
 */
public class PromotionsActivity extends FragmentActivity {

    private static final String TAG = PromotionsActivity.class.getSimpleName();
	private SharedPreferences pref;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        //Get the preferences file
        pref = getSharedPreferences(App.PREF_FILE_NAME, Context.MODE_PRIVATE);
        
//        //Get the core application class
//        app = App.instance();//(App) getApplicationContext();
//        api = app.api;
//
        // Set the drawer toggle as the DrawerListener
        getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.sb_action_bar));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mViewPager = (ViewPager) findViewById(R.id.promotions_viewpager);
        mViewPager.setAdapter(new PromotionsPagerAdapter(getSupportFragmentManager()));
        ((LockedViewPager) mViewPager).setLock(true);

//        mViewPager.setOffscreenPageLimit(2);
        setTitle(R.string.sb_promotions_title);
    }

    protected int getContentView() {
        return R.layout.activity_promotions;
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

    public void onClickPromotion(View view) {
        mViewPager.setCurrentItem(1);
        ((LockedViewPager) mViewPager).setLock(false);
    }

//    public void onClickCreateAccount(View view) {
//        Intent intent = new Intent(PromotionsActivity.this, NewAccountActivity.class);
//        startActivity(intent);
//        this.finish();
//    }
//
//    public void onClickRecoverUsername(View view) {
//        Intent intent = new Intent(PromotionsActivity.this, AccountRecoverUserNameActivity.class);
//        startActivity(intent);
//    }
//
//    public void onClickRecoverPassword(View view) {
//        Intent intent = new Intent(PromotionsActivity.this, AccountRecoverPasswordActivity.class);
//        startActivity(intent);
//    }

//    public void onClickContinue(View view) {
//        this.finish();
//    }

    @Override
    protected void onResume() {
        ((LockedViewPager) mViewPager).setLock(true);
        super.onResume();
    }
}
