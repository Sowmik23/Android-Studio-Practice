package com.reyansh.audio.audioplayer.free.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.reyansh.audio.audioplayer.free.Common;
import com.reyansh.audio.audioplayer.free.NowPlaying.NowPlayingActivity;
import com.reyansh.audio.audioplayer.free.R;
import com.reyansh.audio.audioplayer.free.Utils.Constants;
import com.reyansh.audio.audioplayer.free.Utils.MusicUtils;
import com.reyansh.audio.audioplayer.free.Utils.PreferencesHelper;
import com.reyansh.audio.audioplayer.free.Utils.TypefaceHelper;
import com.reyansh.audio.audioplayer.free.Views.RangeSeekBar;

/**
 * Created by Reyansh on 23/07/2016.
 */
public class ABRepeatDialog extends DialogFragment {


    private Common mApp;

    private int repeatPointA;
    private int repeatPointB;

    private int currentSongDurationMillis;

    private TextView repeatSongATime;
    private TextView repeatSongBTime;
    private SeekBar mSeekBar;

    private RangeSeekBar<Integer> mRangeSeekBar;
    private ViewGroup viewGroup;


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mApp = (Common) getActivity().getApplicationContext();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.a_b_repeat);
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_ab_repeat, null);
        mSeekBar = view.findViewById(R.id.repeat_song_range_placeholder_seekbar);

        repeatSongATime = view.findViewById(R.id.repeat_song_range_A_time);
        repeatSongBTime = view.findViewById(R.id.repeat_song_range_B_time);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mSeekBar.getLayoutParams();
        viewGroup = (ViewGroup) mSeekBar.getParent();
        viewGroup.removeView(mSeekBar);


        repeatSongATime.setTypeface(TypefaceHelper.getTypeface(getActivity().getApplicationContext(), "Futura-Condensed-Font"));
        repeatSongBTime.setTypeface(TypefaceHelper.getTypeface(getActivity().getApplicationContext(), "Futura-Condensed-Font"));

        TextView textView = view.findViewById(R.id.repeat_song_range_instructions);
        textView.setTypeface(TypefaceHelper.getTypeface(getActivity().getApplicationContext(), "Futura-Condensed-Font"));

        currentSongDurationMillis = mApp.getService().getMediaPlayer().getDuration();

        mRangeSeekBar = new RangeSeekBar<>(0, currentSongDurationMillis, getActivity().getApplicationContext());

        mRangeSeekBar.setLayoutParams(params);
        viewGroup.addView(mRangeSeekBar);


        if (PreferencesHelper.getInstance().getInt(PreferencesHelper.Key.REPEAT_MODE, Constants.REPEAT_OFF) == Constants.A_B_REPEAT) {

            repeatSongATime.setText(MusicUtils.convertMillisToMinsSecs(mApp.getService().getRepeatSongRangePointA()));
            repeatSongBTime.setText(MusicUtils.convertMillisToMinsSecs(mApp.getService().getRepeatSongRangePointB()));


            repeatPointA = mApp.getService().getRepeatSongRangePointA();
            repeatPointB = mApp.getService().getRepeatSongRangePointB();

            mRangeSeekBar.setSelectedMinValue(repeatPointA);
            mRangeSeekBar.setSelectedMaxValue(repeatPointB);

        } else {
            repeatSongATime.setText("0:00");
            repeatSongBTime.setText(MusicUtils.convertMillisToMinsSecs(currentSongDurationMillis));

            repeatPointA = 0;
            repeatPointB = currentSongDurationMillis;
        }

        mRangeSeekBar.setOnRangeSeekBarChangeListener((bar, minValue, maxValue) -> {
            repeatPointA = minValue;
            repeatPointB = maxValue;
            repeatSongATime.setText(MusicUtils.convertMillisToMinsSecs(minValue));
            repeatSongBTime.setText(MusicUtils.convertMillisToMinsSecs(maxValue));
        });

        repeatSongATime.setText(MusicUtils.convertMillisToMinsSecs(repeatPointA));
        repeatSongBTime.setText(MusicUtils.convertMillisToMinsSecs(repeatPointB));


        builder.setView(view);

        builder.setPositiveButton(R.string.repeat, (arg0, arg1) -> {
            PreferencesHelper.getInstance().put(PreferencesHelper.Key.REPEAT_MODE, Constants.A_B_REPEAT);
            mApp.getService().setRepeatSongRange(repeatPointA, repeatPointB);
            ((NowPlayingActivity) getActivity()).applyRepeatButton();
            dismiss();
        });

        builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());
        return builder.create();
    }


}
