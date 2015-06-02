package lt.appcamp.stanleybet.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import lt.appcamp.stanleybet.R;

/**
 * Created by Tadas on 6/14/13.
 */
public class AccountRecoverPasswordActivity extends Activity {

    private static final String TAG = AccountRecoverPasswordActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        // Set the drawer toggle as the DrawerListener
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.sb_action_bar));

        setTitle(R.string.sb_recover_password_title);
    }

    protected int getContentView() {
        return R.layout.activity_recover_password;
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
