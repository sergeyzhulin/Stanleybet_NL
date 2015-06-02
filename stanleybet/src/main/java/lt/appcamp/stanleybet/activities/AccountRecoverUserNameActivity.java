package lt.appcamp.stanleybet.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.viewpagerindicator.CirclePageIndicator;

import lt.appcamp.stanleybet.R;
import lt.appcamp.stanleybet.adapters.NewAccountPagerAdapter;
import lt.appcamp.stanleybet.app.App;
import lt.appcamp.stanleybet.views.LockedViewPager;

/**
 * Created by Tadas on 6/14/13.
 */
public class AccountRecoverUserNameActivity extends Activity {

    private static final String TAG = AccountRecoverUserNameActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        // Set the drawer toggle as the DrawerListener
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.sb_action_bar));

        setTitle(R.string.sb_recover_username_title);
    }

    protected int getContentView() {
        return R.layout.activity_recover_username;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onResetClick(View view) {
        this.finish();
    }

}
