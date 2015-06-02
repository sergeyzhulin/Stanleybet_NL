package lt.appcamp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;

/**
 * User: Vykintas Valkaitis <v.valkaitis@appcamp.lt>
 * Date: 2013-05-13
 */
abstract public class AppActivity extends Activity {
    /**
     * Activity is running flag
     */
    protected boolean isRunning = false;
    /**
     * Progress dialog
     */
    protected ProgressDialog mProgressDialog;

    /**
     * Hide progress dialog
     */
    public void hideProgressDialog() {
        if (isRunning) {
            try {
                if (mProgressDialog != null) mProgressDialog.cancel();
            } catch (Exception e) {
                AppLog.e(e);
            }
        }
    }

    /**
     * Check if application is installed on OS
     *
     * @param uri
     * @return
     */
    protected boolean isApplicationInstalled(String uri) {
        try {
            getPackageManager().getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        // set running flag
        isRunning = true;
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // set running flag
        isRunning = true;
    }

    @Override
    public void onPause() {
        super.onPause();

        // set running flag
        isRunning = false;
    }

    /**
     * Reload current activity
     */
    protected void reload() {
        // build launch intent for current application
        Intent intent = getIntent();

        // set no animation for finish
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        // finish current activity
        finish();

        // set no animation for start
        overridePendingTransition(0, 0);

        // start activity
        startActivity(intent);
    }

    /**
     * Show alert dialog
     */
    public void showAlertDialog(int resource) {
        showAlertDialog(getText(resource).toString());
    }

    /**
     * Show alert dialog
     */
    protected void showAlertDialog(String message) {
        if (isRunning) {
            try {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage(message);
                alertDialogBuilder.setPositiveButton(getResources().getString(android.R.string.ok), null);
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();
            } catch (Exception e) {
                AppLog.e(e);
            }
        }
    }

    /**
     * Show Progress dialog
     *
     * @param resource
     */
    public void showProgressDialog(int resource) {
        showProgressDialog(getText(resource).toString());
    }

    /**
     * Show Progress dialog
     *
     * @param text
     */
    protected void showProgressDialog(String text) {
        if (isRunning) {
            try {
                if (mProgressDialog == null) {
                    mProgressDialog = new ProgressDialog(this);
                    mProgressDialog.setCancelable(true);
                }
                mProgressDialog.setMessage(text);
                mProgressDialog.show();
            } catch (Exception e) {
                AppLog.e(e);
            }
        }
    }

    /**
     * Return Activity context (this)
     * @return
     */
    protected AppActivity getContext() {
        return this;
    }

}