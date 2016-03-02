package me.fmon.android.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.support.annotation.UiThread;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import de.greenrobot.event.EventBus;
import me.fmon.android.MyApplication;
import me.fmon.android.MyPrefs_;
import me.fmon.android.R;
import me.fmon.android.dialog.MessageDialog;
import me.fmon.android.dialog.MessageDialog_;
import me.fmon.android.event.ErrorEvent;

@EActivity
public class BaseActivity extends AppCompatActivity {

    @App
    protected MyApplication myApp;

    @Pref
    MyPrefs_ myPrefs;

    @ViewById
    protected Toolbar toolbar;

    ProgressDialog progress;

    @AfterViews
    protected void afterViews() {
        setSupportActionBar(toolbar);

        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

    }

    @UiThread
    public void onEvent(ErrorEvent event) {
        showLoading(false);
        showSnackError(event.getMessage());
    }

    @UiThread
    public void showLoading(boolean loading) {
        showLoading(loading, getString(R.string.txt_please_wait));
    }

    @UiThread
    public void showLoading(boolean loading, String message) {
        if (loading && progress == null && !isFinishing()) {
            progress = new ProgressDialog(this);
            progress.setMessage(message);
            progress.setCancelable(false);
            progress.show();
        } else {
            if (!loading && progress != null) {
                progress.dismiss();
                progress = null;
            }
        }
    }

    @UiThread
    public void showSnackError(String error) {
        Snackbar snackbar = Snackbar.make(toolbar, error,
                Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
        snackbar.show();
    }

    @UiThread
    public void showSnackConfirm(String text) {
        Snackbar snackbar = Snackbar.make(toolbar, text,
                Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
        snackbar.show();
    }

    public void showSnackConfirm(String text, String action, View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(toolbar, text,
                Snackbar.LENGTH_LONG);
        snackbar.setAction(action, listener);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
        snackbar.show();

    }

    @UiThread
    public void showMessage(String title, String message) {
        showMessage(title, message, null);
    }

    @UiThread
    public void showMessage(String title, String message, MessageDialog.OnOKListener listener) {
        showMessage(title, message, false, listener);
    }

    @UiThread
    public void showMessage(String title, String message, boolean showCancel, MessageDialog
            .OnOKListener listener) {
        MessageDialog dialog = MessageDialog_.builder().title(title).message(message).showCancel
                (showCancel).build();
        dialog.setListener(listener);
        dialog.show(getSupportFragmentManager(), "Dialog");
    }

    public MyApplication getApp() {
        return myApp;
    }

    /* BACK HANDLER */
    protected boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        exitDoubleCheck();
    }

    private void exitDoubleCheck() {
        if (doubleBackToExitPressedOnce) {
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.txt_confirm_exit, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    /**
     * Remember to @Override onOptionsItemSelected (android.R.id.home)
     */
    protected void enableBack() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onDestroy() {
        if (progress != null)
            progress.dismiss();
        progress = null;

        clearReferences();

        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);

        super.onDestroy();
    }

    protected void onResume() {
        super.onResume();
        myApp.setCurrentActivity(this);
    }

    protected void onPause() {
        clearReferences();
        super.onPause();
    }

    private void clearReferences() {
        Activity currActivity = myApp.getCurrentActivity();
        if (currActivity != null && currActivity.equals(this))
            myApp.setCurrentActivity(null);
    }

}
