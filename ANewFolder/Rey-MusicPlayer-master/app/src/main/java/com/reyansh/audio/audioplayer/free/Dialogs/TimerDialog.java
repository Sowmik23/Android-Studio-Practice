package com.reyansh.audio.audioplayer.free.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TextView;

import com.reyansh.audio.audioplayer.free.R;
import com.reyansh.audio.audioplayer.free.Utils.TypefaceHelper;

import java.util.Timer;

/**
 * Created by REYANSH on 4/30/2017.
 */

public class TimerDialog extends DialogFragment {


    private TextView mTimerTextView;
    private Timer mTimer;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_timer, null);
        mTimerTextView = (TextView) view.findViewById(R.id.text_view_timer_dialog);

        mTimerTextView.setTypeface(TypefaceHelper.getTypeface(getActivity().getApplicationContext(), TypefaceHelper.FUTURA_BOOK));
        builder.setView(view);

        builder.setPositiveButton(R.string.stop, (dialog, which) -> {

        });

        builder.setNegativeButton(R.string.stop, (dialog, which) -> {

        });


        return builder.create();
    }
}
