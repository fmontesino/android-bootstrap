package me.fmon.android.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import me.fmon.android.R;

@EFragment
public class MessageDialog extends DialogFragment {

    @FragmentArg
    String title, message;

    @FragmentArg
    boolean showCancel;

    OnOKListener listener;

    public void setListener(OnOKListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title).setMessage(message).setPositiveButton(getString(R.string.button_ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (listener != null)
                            listener.onOK();
                    }
                });

        if (showCancel)
            builder.setNegativeButton(getString(R.string.button_cancel), new DialogInterface
                    .OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

        setCancelable(false);

        return builder.create();
    }

    public interface OnOKListener {
        void onOK();
    }
}