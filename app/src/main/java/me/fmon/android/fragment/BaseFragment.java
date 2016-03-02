package me.fmon.android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import me.fmon.android.MyApplication;
import me.fmon.android.R;
import me.fmon.android.activity.BaseActivity;
import me.fmon.android.dialog.MessageDialog;
import me.fmon.android.event.ErrorEvent;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;

import de.greenrobot.event.EventBus;

@EFragment
public class BaseFragment extends Fragment {

    @App
    MyApplication myApp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {

        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);

        super.onDestroy();
    }

    @UiThread
    public void onEvent(ErrorEvent event) {
    }

    @UiThread
    public void showLoading(boolean loading) {
        showLoading(loading, getString(R.string.txt_please_wait));
    }

    @UiThread
    public void showLoading(boolean loading, String message) {
        ((BaseActivity) getActivity()).showLoading(loading, message);
    }

    @UiThread
    public void showSnackError(String error) {
        ((BaseActivity) getActivity()).showSnackError(error);
    }

    @UiThread
    public void showSnackConfirm(String text) {
        ((BaseActivity) getActivity()).showSnackConfirm(text);
    }

    @UiThread
    public void showMessage(String title, String message, boolean showCancel, MessageDialog
            .OnOKListener listener) {
        ((BaseActivity) getActivity()).showMessage(title, message, showCancel, listener);
    }

}