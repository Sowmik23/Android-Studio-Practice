package com.reyansh.audio.audioplayer.free.Services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.PresetReverb;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.reyansh.audio.audioplayer.free.AppWidget.QueueWidgetProvider;
import com.reyansh.audio.audioplayer.free.AppWidget.SmallWidgetProvider;
import com.reyansh.audio.audioplayer.free.BroadcastReceivers.HeadsetNotificationBroadcast;
import com.reyansh.audio.audioplayer.free.BroadcastReceivers.HeadsetPlugBroadcastReceiver;
import com.reyansh.audio.audioplayer.free.Common;
import com.reyansh.audio.audioplayer.free.Equalizer.EqualizerHelper;
import com.reyansh.audio.audioplayer.free.Models.Song;
import com.reyansh.audio.audioplayer.free.NowPlaying.NowPlayingActivity;
import com.reyansh.audio.audioplayer.free.R;
import com.reyansh.audio.audioplayer.free.Utils.AudioManagerHelper;
import com.reyansh.audio.audioplayer.free.Utils.Constants;
import com.reyansh.audio.audioplayer.free.Utils.Logger;
import com.reyansh.audio.audioplayer.free.Utils.PreferencesHelper;
import com.reyansh.audio.audioplayer.free.Utils.SongDataHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MusicService extends Service {


    public static final int NOTIFICATION_ID = 1056;
    public MediaPlayer.OnErrorListener onErrorListener = ((mp, what, extra) -> {
         /* This error listener might seem like it's not doing anything.
     * However, removing this will cause the mMediaPlayer1 object to go crazy
     * and skip around. The key here is to make this method return true. This
     * notifies the mMediaPlayer1 object that we've handled all errors and that
     * it shouldn't do anything else to try and remedy the situation.
     *
     * TL;DR: Don't touch this interface. Ever.
     */
        return true;
    });
    Runnable mStopServiceRunnable = () -> stopSelf();
    private boolean mMediaPlayerPrepared = false;
    /**
     * On ServicePrepared listener.
     */
    private PrepareServiceListener mPrepareServiceListener;


    /*Let the system know we are playing music BIAAATCH*/
    private int mSongPos = 0;
    private Bundle mBundle;
    private Intent mMediaIntent;
    private Context mContext;
    private MediaPlayer mMediaPlayer1;

    private PowerManager.WakeLock mWakeLock;

    private EqualizerHelper mEqualizerHelper;

    /**
     * AudioHelpers
     */

    private AudioManager mAudioManager;
    private AudioManagerHelper mAudioManagerHelper;
    private Handler mHandler;
    private Common mApp;
    /**
     * First time playing flag
     */

    private boolean mPlayingForFirstTime = true;
    private HeadsetNotificationBroadcast mHeadsetNotificationBroadcast;
    //Headset plug receiver.
    private HeadsetPlugBroadcastReceiver mHeadsetPlugReceiver;
    private Service mService;
    //A-B Repeat variables.
    private int mRepeatSongRangePointA = 0;
    private int mRepeatSongRangePointB = 0;
    private Song mSong;
    /**
     * List of song which will be played in here.
     */

    private ArrayList<Song> mSongs;
    /**
     * List of shuffled songs
     */
    private ArrayList<Song> mShuffledSongs;
    /**
     * List of original songs used in shuffling the songs and vise versa.
     */
    private ArrayList<Song> mOriginalSongs;
    private MediaSessionCompat mMediaSession;
    private SongDataHelper mSongDataHelper;
    private Runnable duckUpVolumeRunnable = new Runnable() {

        @Override
        public void run() {
            if (mAudioManagerHelper.getCurrentVolume() < mAudioManagerHelper.getTargetVolume()) {
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        (mAudioManagerHelper.getCurrentVolume() + mAudioManagerHelper.getStepUpIncrement()), 0);

                mAudioManagerHelper.setCurrentVolume(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
                mHandler.postDelayed(this, 50);
            }
        }

    };
    private Runnable duckDownVolumeRunnable = new Runnable() {

        @Override
        public void run() {
            if (mAudioManagerHelper.getCurrentVolume() > mAudioManagerHelper.getTargetVolume()) {
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (mAudioManagerHelper.getCurrentVolume() - mAudioManagerHelper.getStepDownIncrement()), 0);
                mAudioManagerHelper.setCurrentVolume(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
                mHandler.postDelayed(this, 50);
            }
        }
    };
    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            sendMediaIntentData();
            mHandler.postDelayed(this, 500);
        }
    };
    /**
     * When MediaPlayer is done playing music.
     */

    MediaPlayer.OnCompletionListener mOnCompletionListener = mp -> {


        if (PreferencesHelper.getInstance().getInt(PreferencesHelper.Key.REPEAT_MODE, Constants.REPEAT_OFF) == Constants.REPEAT_OFF) {
            if (mSongPos < mSongs.size() - 1) {
                mSongPos++;
                startSong();
            } else {
                mSongPos = 0;
                startSong();
                stopSelf();
            }
        } else if (PreferencesHelper.getInstance().getInt(PreferencesHelper.Key.REPEAT_MODE, Constants.REPEAT_OFF) == Constants.REPEAT_PLAYLIST) {
            if (mSongPos < mSongs.size() - 1) {
                mSongPos++;
                startSong();
            } else {
                mSongPos = 0;
                startSong();
            }
        } else if (PreferencesHelper.getInstance().getInt(PreferencesHelper.Key.REPEAT_MODE, Constants.REPEAT_OFF) == Constants.REPEAT_SONG) {
            startSong();
        }
    };
    private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                try {
                    stopPlaying();
                    mAudioManagerHelper.setHasAudioFocus(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mAudioManagerHelper.setAudioDucked(true);
                mAudioManagerHelper.setTargetVolume(5);
                mAudioManagerHelper.setStepDownIncrement(1);
                mAudioManagerHelper.setCurrentVolume(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
                mAudioManagerHelper.setOriginalVolume(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
                mHandler.post(duckDownVolumeRunnable);

            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                if (mAudioManagerHelper.isAudioDucked()) {
                    mAudioManagerHelper.setTargetVolume(mAudioManagerHelper.getOriginalVolume());
                    mAudioManagerHelper.setStepUpIncrement(1);
                    mAudioManagerHelper.setCurrentVolume(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
                    mHandler.post(duckUpVolumeRunnable);
                    mAudioManagerHelper.setAudioDucked(false);
                } else {
                    mAudioManagerHelper.setHasAudioFocus(true);
                }
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                if (mMediaPlayer1 != null) {
                    stopPlaying();
                }
                mAudioManagerHelper.setHasAudioFocus(false);
            }

        }

    };
    private Runnable startMediaPlayerIfPrepared = new Runnable() {
        @Override
        public void run() {
            if (mMediaPlayerPrepared) {
                startPlaying();
            } else {
                mHandler.postDelayed(this, 100);
            }
        }
    };
    /**
     * Called when the MediaPlayer is prepared to play the music.
     */

    MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            mMediaPlayerPrepared = true;
            mMediaPlayer1.setOnCompletionListener(mOnCompletionListener);
            mMediaPlayer1.seekTo(PreferencesHelper.getInstance().getInt(PreferencesHelper.Key.SONG_CURRENT_SEEK_DURATION));
            if (mPlayingForFirstTime) {
                mPlayingForFirstTime = false;
            }
                applyMediaPlayerEQ(getSongDataHelper().getId());
            startPlaying();

            Intent intent = new Intent(Constants.ACTION_UPDATE_NOW_PLAYING_UI);
            intent.putExtra(Constants.JUST_UPDATE_UI, true);
            sendBroadcast(intent);
        }
    };
    /**
     * Called repetitively to check for A-B repeat markers.
     */
    private Runnable checkABRepeatRange = new Runnable() {

        @Override
        public void run() {
            try {
                if (getMediaPlayer().isPlaying()) {
                    if (getMediaPlayer().getCurrentPosition() >= (mRepeatSongRangePointB)) {
                        getMediaPlayer().seekTo(mRepeatSongRangePointA);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (PreferencesHelper.getInstance().getInt(PreferencesHelper.Key.REPEAT_MODE, Constants.A_B_REPEAT) == Constants.A_B_REPEAT) {
                mHandler.postDelayed(checkABRepeatRange, 100);
            }

        }

    };

    /**
     * Retrieves the EQ values for mMediaPlayer1's current song and
     * applies them to the EQ engine.
     *
     * @param songId The id of the song that mMediaPlayer1 is current handling.
     */
    private void applyMediaPlayerEQ(long songId)  {

        if (mEqualizerHelper == null)
            return;
    try {
        short fiftyHertzBand = mEqualizerHelper.getEqualizer().getBand(50000);
        short oneThirtyHertzBand = mEqualizerHelper.getEqualizer().getBand(130000);
        short threeTwentyHertzBand = mEqualizerHelper.getEqualizer().getBand(320000);
        short eightHundredHertzBand = mEqualizerHelper.getEqualizer().getBand(800000);
        short twoKilohertzBand = mEqualizerHelper.getEqualizer().getBand(2000000);
        short fiveKilohertzBand = mEqualizerHelper.getEqualizer().getBand(5000000);
        short twelvePointFiveKilohertzBand = mEqualizerHelper.getEqualizer().getBand(9000000);

        //Get the equalizer/audioFX settings for this specific song.
        int[] eqValues = mApp.getDBAccessHelper().getEQValues();

        //50Hz Band.
        if (eqValues[0] == 16) {
            mEqualizerHelper.getEqualizer().setBandLevel(fiftyHertzBand, (short) 0);
        } else if (eqValues[0] < 16) {

            if (eqValues[0] == 0) {
                mEqualizerHelper.getEqualizer().setBandLevel(fiftyHertzBand, (short) -1500);
            } else {
                mEqualizerHelper.getEqualizer().setBandLevel(fiftyHertzBand, (short) (-(16 - eqValues[0]) * 100));
            }

        } else if (eqValues[0] > 16) {
            mEqualizerHelper.getEqualizer().setBandLevel(fiftyHertzBand, (short) ((eqValues[0] - 16) * 100));
        }

        //130Hz Band.
        if (eqValues[1] == 16) {
            mEqualizerHelper.getEqualizer().setBandLevel(oneThirtyHertzBand, (short) 0);
        } else if (eqValues[1] < 16) {

            if (eqValues[1] == 0) {
                mEqualizerHelper.getEqualizer().setBandLevel(oneThirtyHertzBand, (short) -1500);
            } else {
                mEqualizerHelper.getEqualizer().setBandLevel(oneThirtyHertzBand, (short) (-(16 - eqValues[1]) * 100));
            }

        } else if (eqValues[1] > 16) {
            mEqualizerHelper.getEqualizer().setBandLevel(oneThirtyHertzBand, (short) ((eqValues[1] - 16) * 100));
        }

        //320Hz Band.
        if (eqValues[2] == 16) {
            mEqualizerHelper.getEqualizer().setBandLevel(threeTwentyHertzBand, (short) 0);
        } else if (eqValues[2] < 16) {

            if (eqValues[2] == 0) {
                mEqualizerHelper.getEqualizer().setBandLevel(threeTwentyHertzBand, (short) -1500);
            } else {
                mEqualizerHelper.getEqualizer().setBandLevel(threeTwentyHertzBand, (short) (-(16 - eqValues[2]) * 100));
            }

        } else if (eqValues[2] > 16) {
            mEqualizerHelper.getEqualizer().setBandLevel(threeTwentyHertzBand, (short) ((eqValues[2] - 16) * 100));
        }

        //800Hz Band.
        if (eqValues[3] == 16) {
            mEqualizerHelper.getEqualizer().setBandLevel(eightHundredHertzBand, (short) 0);
        } else if (eqValues[3] < 16) {

            if (eqValues[3] == 0) {
                mEqualizerHelper.getEqualizer().setBandLevel(eightHundredHertzBand, (short) -1500);
            } else {
                mEqualizerHelper.getEqualizer().setBandLevel(eightHundredHertzBand, (short) (-(16 - eqValues[3]) * 100));
            }

        } else if (eqValues[3] > 16) {
            mEqualizerHelper.getEqualizer().setBandLevel(eightHundredHertzBand, (short) ((eqValues[3] - 16) * 100));
        }

        //2kHz Band.
        if (eqValues[4] == 16) {
            mEqualizerHelper.getEqualizer().setBandLevel(twoKilohertzBand, (short) 0);
        } else if (eqValues[4] < 16) {

            if (eqValues[4] == 0) {
                mEqualizerHelper.getEqualizer().setBandLevel(twoKilohertzBand, (short) -1500);
            } else {
                mEqualizerHelper.getEqualizer().setBandLevel(twoKilohertzBand, (short) (-(16 - eqValues[4]) * 100));
            }

        } else if (eqValues[4] > 16) {
            mEqualizerHelper.getEqualizer().setBandLevel(twoKilohertzBand, (short) ((eqValues[4] - 16) * 100));
        }

        //5kHz Band.
        if (eqValues[5] == 16) {
            mEqualizerHelper.getEqualizer().setBandLevel(fiveKilohertzBand, (short) 0);
        } else if (eqValues[5] < 16) {

            if (eqValues[5] == 0) {
                mEqualizerHelper.getEqualizer().setBandLevel(fiveKilohertzBand, (short) -1500);
            } else {
                mEqualizerHelper.getEqualizer().setBandLevel(fiveKilohertzBand, (short) (-(16 - eqValues[5]) * 100));
            }

        } else if (eqValues[5] > 16) {
            mEqualizerHelper.getEqualizer().setBandLevel(fiveKilohertzBand, (short) ((eqValues[5] - 16) * 100));
        }

        //12.5kHz Band.
        if (eqValues[6] == 16) {
            mEqualizerHelper.getEqualizer().setBandLevel(twelvePointFiveKilohertzBand, (short) 0);
        } else if (eqValues[6] < 16) {

            if (eqValues[6] == 0) {
                mEqualizerHelper.getEqualizer().setBandLevel(twelvePointFiveKilohertzBand, (short) -1500);
            } else {
                mEqualizerHelper.getEqualizer().setBandLevel(twelvePointFiveKilohertzBand, (short) (-(16 - eqValues[6]) * 100));
            }

        } else if (eqValues[6] > 16) {
            mEqualizerHelper.getEqualizer().setBandLevel(twelvePointFiveKilohertzBand, (short) ((eqValues[6] - 16) * 100));
        }

        //Set the audioFX values.
        mEqualizerHelper.getVirtualizer().setStrength((short) eqValues[7]);
        mEqualizerHelper.getBassBoost().setStrength((short) eqValues[8]);
        float volume = ((float) eqValues[10]) / 100f;

        mMediaPlayer1.setVolume(volume, volume);

        if (eqValues[9] == 0) {
            mEqualizerHelper.getReverb().setPreset(PresetReverb.PRESET_NONE);
        } else if (eqValues[9] == 1) {
            mEqualizerHelper.getReverb().setPreset(PresetReverb.PRESET_LARGEHALL);
        } else if (eqValues[9] == 2) {
            mEqualizerHelper.getReverb().setPreset(PresetReverb.PRESET_LARGEROOM);
        } else if (eqValues[9] == 3) {
            mEqualizerHelper.getReverb().setPreset(PresetReverb.PRESET_MEDIUMHALL);
        } else if (eqValues[9] == 4) {
            mEqualizerHelper.getReverb().setPreset(PresetReverb.PRESET_MEDIUMROOM);
        } else if (eqValues[9] == 5) {
            mEqualizerHelper.getReverb().setPreset(PresetReverb.PRESET_SMALLROOM);
        } else if (eqValues[9] == 6) {
            mEqualizerHelper.getReverb().setPreset(PresetReverb.PRESET_PLATE);
        }
    }catch (Exception e){
        e.printStackTrace();
    }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mService = this;

        mApp = (Common) getApplicationContext();
        mApp.setIsServiceRunning(true);

        mSongs = new ArrayList<>();
        mSongs.addAll(mApp.getDBAccessHelper().getQueue());

        mShuffledSongs = new ArrayList<>(mSongs.size());
        mOriginalSongs = new ArrayList<>(mSongs.size());


        mSongPos = PreferencesHelper.getInstance().getInt(PreferencesHelper.Key.CURRENT_SONG_POSITION, 0);
        //Collections.copy(mShuffledSongs, mSongs);
        for (Song song : mSongs) {
            try {
                mOriginalSongs.add((Song) song.clone());
                mShuffledSongs.add((Song) song.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                Logger.log(e.getMessage());
            }
        }

        if (PreferencesHelper.getInstance().getInt(PreferencesHelper.Key.SHUFFLE_MODE, Constants.SHUFFLE_OFF) == Constants.SHUFFLE_ON) {
            setShuffledOne();
        }


        mHandler = new Handler();


        /**
         *Take the wakeup lock to stop CPU from sleeping cause we are dancing on the beats.
         */

        mWakeLock = ((PowerManager) getSystemService(Context.POWER_SERVICE)).newWakeLock(1, getClass().getName());
        mWakeLock.setReferenceCounted(false);

        /**
         *Headset Connect and disconnect receiver
         */
        registerHeadsetPlugReceiver();

        /**
         *Init the king
         */

        initMediaPlayers();

        /**
         *This is equalizer its pain in ass to manage.
         */

        initAudioFX();

        mBundle = new Bundle();

        mMediaIntent = new Intent();
        mMediaIntent.setAction(Constants.MEDIA_INTENT);

        mApp.setService(this);

        /**
         *Headset receivers
         */

        mHeadsetNotificationBroadcast = new HeadsetNotificationBroadcast();
        registerReceiver(mHeadsetNotificationBroadcast, new IntentFilter(Intent.ACTION_MEDIA_BUTTON));


        mAudioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        mAudioManagerHelper = new AudioManagerHelper();

        mMediaSession = new MediaSessionCompat(getApplicationContext(), "AudioPlayer", new ComponentName(this, HeadsetNotificationBroadcast.class), null);
        mMediaSession.setActive(true);
        mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        PendingIntent playPauseTrackPendingIntent = PendingIntent.getBroadcast(mContext, 56, intent, 0);
        mMediaSession.setMediaButtonReceiver(playPauseTrackPendingIntent);
        mMediaSession.setCallback(new MediaSessionCompat.Callback() {
            @Override
            public void onPlay() {
                super.onPlay();
                playPauseSong();
            }

            @Override
            public void onPause() {
                super.onPause();
                playPauseSong();
            }

            @Override
            public void onSkipToNext() {
                super.onSkipToNext();
            }

            @Override
            public void onSkipToPrevious() {
                super.onSkipToPrevious();
            }
        });

        mMediaSession.setPlaybackState(new PlaybackStateCompat.Builder()
                .setState(PlaybackStateCompat.STATE_PLAYING, 2, 1)
                .setActions(PlaybackStateCompat.ACTION_FAST_FORWARD |
                        PlaybackStateCompat.ACTION_PAUSE |
                        PlaybackStateCompat.ACTION_PLAY |
                        PlaybackStateCompat.ACTION_PLAY_PAUSE |
                        PlaybackStateCompat.ACTION_SKIP_TO_NEXT |
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                        PlaybackStateCompat.ACTION_STOP)
                .build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            handleIntent(intent);
        } else {
            setPrepareServiceListener(mApp.getPlayBackStarter());
            getPrepareServiceListener().onServiceRunning(this);
        }

        return START_NOT_STICKY;
    }

    private void handleIntent(Intent intent) {
        if (intent.getAction().equalsIgnoreCase(Constants.ACTION_PAUSE)) {
            playPauseSong();
        } else if (intent.getAction().equalsIgnoreCase(Constants.ACTION_NEXT)) {
            nextSong();
        } else if (intent.getAction().equalsIgnoreCase(Constants.ACTION_PREVIOUS)) {
            previousSong();
        }
    }

    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer1;
    }

    private boolean requestAudioFocus() {
        int result = mAudioManager.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (result != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            Toast.makeText(getApplicationContext(), R.string.unable_to_get_audio_focus, Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    public void addOneSongToQueue(Song song) {
        mSongs.add(song);
    }

    public void addOneSongToPlayNext(Song song) {
        try {
            mSongs.add(mSongPos + 1, song);
        } catch (Exception e) {
            mSongs.add(mSongPos, song);
        }
    }

    public void addSongsToQueue(ArrayList<Song> song) {
        mSongs.addAll(song);
    }

    public void addSongsToPlayNext(ArrayList<Song> song) {
        try {
            mSongs.addAll(mSongPos + 1, song);
        } catch (Exception e) {
            mSongs.addAll(mSongPos, song);
        }
    }

    public void headsetDisconnected() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean pauseOnUnplug = sharedPreferences.getBoolean("preference_key_pause_on_unplug", true);
        if (pauseOnUnplug && mMediaPlayer1.isPlaying()) {
            stopPlaying();
        }
    }

    public void headsetIsConnected() {
        if (!mMediaPlayer1.isPlaying() && !mPlayingForFirstTime) {
//            startPlaying();
        }
    }

    private void initAudioFX() {
        try {
            mEqualizerHelper = new EqualizerHelper(mMediaPlayer1.getAudioSessionId(), PreferencesHelper.getInstance().getBoolean(PreferencesHelper.Key.IS_EQUALIZER_ACTIVE, false));
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initMediaPlayers() {
        mMediaPlayer1 = new MediaPlayer();
        mMediaPlayer1.setWakeMode(this, PowerManager.PARTIAL_WAKE_LOCK);
        mMediaPlayer1.setAudioStreamType(AudioManager.STREAM_MUSIC);


        if (mSongs.size() > 0) {
            startSong();
        }
    }

    private void startSong() {
        mHandler.removeCallbacks(sendUpdatesToUI);
        mMediaPlayerPrepared = false;

        mSong = mSongs.get(mSongPos);

        mMediaPlayer1.reset();

        if (PreferencesHelper.getInstance().getInt(PreferencesHelper.Key.REPEAT_MODE, Constants.REPEAT_OFF) == Constants.REPEAT_SONG)
            mMediaPlayer1.setLooping(true);

        try {
            SongDataHelper songDataHelper = new SongDataHelper();
            setSongDataHelper(songDataHelper);
            songDataHelper.populateSongData(mContext, null, mSongPos);

            mApp.getDBAccessHelper().insertSongCount(mSongs.get(mSongPos));
            mApp.getDBAccessHelper().addToRecentlyPlayed(mSongs.get(mSongPos));

            mMediaPlayer1.setDataSource(mContext, getUri(mSongs.get(mSongPos)._id));
            mMediaPlayer1.setOnPreparedListener(onPreparedListener);
            mMediaPlayer1.setOnErrorListener(onErrorListener);
            mMediaPlayer1.prepareAsync();


            if (mSongs.size() > 1) {

            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public void updateNotification() {
        startForeground(NOTIFICATION_ID, mediaStyleNotification());

        updateWidgets();
        mMediaSession.setMetadata(new MediaMetadataCompat.Builder()
                .putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, getSongDataHelper().getAlbumArt())
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, getSongDataHelper().getArtist())
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, getSongDataHelper().getAlbum())
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, getSongDataHelper().getTitle())
                .build());
    }

    public void updateWidgets() {
        Intent smallWidgetIntent = new Intent(mContext, SmallWidgetProvider.class);
        Intent queueWidgetIntent = new Intent(mContext, QueueWidgetProvider.class);


        smallWidgetIntent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        queueWidgetIntent.setAction("android.appwidget.action.APPWIDGET_UPDATE");


        int smallWidgetIds[] = AppWidgetManager.getInstance(mContext).getAppWidgetIds(new ComponentName(mContext, SmallWidgetProvider.class));
        int queueWidgetIds[] = AppWidgetManager.getInstance(mContext).getAppWidgetIds(new ComponentName(mContext, QueueWidgetProvider.class));


        smallWidgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, smallWidgetIds);
        smallWidgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, queueWidgetIds);


        sendBroadcast(smallWidgetIntent);
        sendBroadcast(queueWidgetIntent);

    }

    /**
     * Every time you start playing ask for the AudioFocus or else it would start playing with and you would be furious.
     */

    public void startPlaying() {

        if (mMediaPlayerPrepared) {
            if (!mMediaPlayer1.isPlaying() && requestAudioFocus()) {
                mMediaPlayer1.start();
                mHandler.removeCallbacks(startMediaPlayerIfPrepared);
                mHandler.postDelayed(sendUpdatesToUI, 600);
            }
        } else {
            mHandler.post(startMediaPlayerIfPrepared);
        }
        /**
         *Play pause intent to display the correct UI throughout the entire app.
         */
        sendPlayPauseBroadCast();
        updateNotification();
    }

    public void stopPlaying() {
        if (mMediaPlayer1.isPlaying()) {
            mMediaPlayer1.pause();
            mAudioManager.abandonAudioFocus(audioFocusChangeListener);
            mHandler.removeCallbacks(sendUpdatesToUI);
        }
        updateNotification();
        stopForeground(false);
    }

    private void sendPlayPauseBroadCast() {
        Intent intent = new Intent(Constants.ACTION_UPDATE_NOW_PLAYING_UI);
        intent.putExtra(Constants.ACTION_PLAY_PAUSE, true);
        sendBroadcast(intent);
    }

    public void playPauseSong() {
        if (!mMediaPlayer1.isPlaying()) {
            startPlaying();
            mHandler.removeCallbacks(mStopServiceRunnable);
        } else {
            stopPlaying();
            stopForeground(false);
            mHandler.postDelayed(mStopServiceRunnable, 300000);

        }
        sendPlayPauseBroadCast();
        saveQueue();
    }

    private void saveQueue() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                saveIt();
                return null;
            }
        }.execute();


    }

    private void saveIt() {
        mApp.getDBAccessHelper().saveQueue(mSongs);
        PreferencesHelper.getInstance().put(PreferencesHelper.Key.CURRENT_SONG_POSITION, mSongPos);
        PreferencesHelper.getInstance().put(PreferencesHelper.Key.SONG_CURRENT_SEEK_DURATION, mMediaPlayer1.getCurrentPosition());
        PreferencesHelper.getInstance().put(PreferencesHelper.Key.SONG_TOTAL_SEEK_DURATION, mMediaPlayer1.getDuration());
    }

    /**
     * Let system know we are playing music and this intent is used my musixmatch also so kinda connection.
     */

    public void sendMediaIntentData() throws NumberFormatException {
        mBundle.putString("track", getSongDataHelper().getTitle());
        mBundle.putString("artist", getSongDataHelper().getArtist());
        mBundle.putString("album", getSongDataHelper().getAlbum());
        try {
            mBundle.putLong("duration", getSongDataHelper().getDuration());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            mBundle.putLong("position", mMediaPlayer1.getCurrentPosition());
        } catch (Exception e) {
            e.printStackTrace();
            mBundle.putLong("position", 0);
        }

        mBundle.putBoolean("playing", true);
        mBundle.putString("scrobbling_source", "com.reyansh.audio.audioplayer.free");
        mMediaIntent.putExtras(mBundle);
        sendBroadcast(mMediaIntent);
    }

    public void nextSong() {
        PreferencesHelper.getInstance().put(PreferencesHelper.Key.SONG_CURRENT_SEEK_DURATION, 0);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                if (mSongs.size() != 1) {
                    if (mSongPos < mSongs.size() - 1) {
                        mSongPos = mSongPos + 1;
                        startSong();
                    } else {
                        mSongPos = 0;
                        startSong();
                    }
                }
                return null;
            }
        }.execute();

    }

    public void previousSong() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                if (mMediaPlayer1.getCurrentPosition() >= 5000) {
                    mMediaPlayer1.seekTo(0);
                } else {
                    if (mSongs.size() > 1) {
                        if (mSongPos > 0) {
                            mSongPos--;
                            startSong();
                        } else {
                            mSongPos = mSongs.size() - 1;
                            startSong();
                        }
                    }
                }
                return null;
            }
        }.execute();

    }

    public void registerHeadsetPlugReceiver() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        mHeadsetPlugReceiver = new HeadsetPlugBroadcastReceiver();
        mService.registerReceiver(mHeadsetPlugReceiver, filter);
    }

    public ArrayList<Song> getSongList() {
        return mSongs;
    }

    public void setSongList(ArrayList<Song> listSong) {
        mSongs.clear();
        mShuffledSongs.clear();
        mOriginalSongs.clear();

        mSongs.addAll(listSong);
        for (Song oneSong : listSong) {
            try {
                mShuffledSongs.add((Song) oneSong.clone());
                mOriginalSongs.add((Song) oneSong.clone());
            } catch (CloneNotSupportedException e) {
                Logger.log(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void setSelectedSong(int pos) {
        mSongPos = pos;
        if (mSongs.size() != 0) {
            startSong();
        }
    }

    public int getCurrentSongIndex() {
        return mSongPos;
    }

    public void setCurrentSongIndex(int currentSongIndex) {
        mSongPos = currentSongIndex;
    }

    /*
    * Get the list from the shuffle list shuffle it again
    * and add it to the playing song list.
    *
    * */

    @Override
    public void onDestroy() {
        saveIt();
        mApp.setIsServiceRunning(false);
        clearABRepeatRange();
        updateWidgets();
        sendPlayPauseBroadCast();

        mHandler.removeCallbacks(sendUpdatesToUI);

        mMediaSession.release();
        unregisterReceiver(mHeadsetPlugReceiver);
        unregisterReceiver(mHeadsetNotificationBroadcast);

        mWakeLock.release();

        try {
            mEqualizerHelper.releaseEQObjects();
            mEqualizerHelper = null;
        } catch (Exception e1) {
            e1.printStackTrace();
            mEqualizerHelper = null;
        }

        mMediaPlayer1.pause();
        if (mMediaPlayer1 != null) {
            mMediaPlayer1.stop();
            mMediaPlayer1.release();
            mMediaPlayer1 = null;
        }
        mAudioManagerHelper.setHasAudioFocus(false);
        mAudioManager.abandonAudioFocus(audioFocusChangeListener);

        //stopNotify();
        mApp.setService(null);
    }

    public void setShuffledOne() {
        mSongs.clear();
        Collections.shuffle(mShuffledSongs, new Random(System.nanoTime()));
        Collections.shuffle(mShuffledSongs, new Random(System.nanoTime()));
        mSongs.addAll(mShuffledSongs);
    }

    /*Clear the playing song list and add the orignal song list*/
    public void setOriginalOne() {
        mSongs.clear();
        mSongs.addAll(mOriginalSongs);
    }

    private Notification mediaStyleNotification() {

        Intent intent = new Intent(getApplicationContext(), NowPlayingActivity.class);
        intent.putExtra("LAUNCHED_FROM_NOTIFICATION", true);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        int color = getSongDataHelper().getColor();
        Logger.exp("AAAAAA   " + color);
        NotificationCompat.Action action;
        if (!isPlayingMusic()) {
            action = generateAction(R.drawable.btn_playback_play_light, "Play", Constants.ACTION_PAUSE);
        } else {
            action = generateAction(R.drawable.btn_playback_pause_light, "Pase", Constants.ACTION_PAUSE);
        }

        return new NotificationCompat.Builder(this)

                .addAction(generateAction(R.drawable.btn_playback_previous_light, "Previous", Constants.ACTION_PREVIOUS))
                .addAction(action)
                .addAction(generateAction(R.drawable.btn_playback_next_light, "Previous", Constants.ACTION_NEXT))
                .setSmallIcon(R.mipmap.ic_music_file)
                .setContentTitle(getSongDataHelper().getTitle())
                .setContentIntent(pendingIntent)
                .setContentText(getSongDataHelper().getAlbum())
                .setLargeIcon(getSongDataHelper().getAlbumArt())
                .setColor(color)
                .setStyle(new NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1, 2)
                        .setMediaSession(mMediaSession.getSessionToken()))
                .build();
    }

    private NotificationCompat.Action generateAction(int icon, String title, String intentAction) {
        Intent intent = new Intent(getApplicationContext(), MusicService.class);
        intent.setAction(intentAction);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 1, intent, 0);
        return new NotificationCompat.Action.Builder(icon, title, pendingIntent).build();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int getRepeatSongRangePointA() {
        return mRepeatSongRangePointA;
    }

    /**
     * Returns point B in milliseconds for A-B repeat.
     */
    public int getRepeatSongRangePointB() {
        return mRepeatSongRangePointB;
    }

    /**
     * Sets the A-B Repeat song markers.
     *
     * @param pointA The duration to repeat from (in millis).
     * @param pointB The duration to repeat until (in millis).
     */

    public void setRepeatSongRange(int pointA, int pointB) {
        mRepeatSongRangePointA = pointA;
        mRepeatSongRangePointB = pointB;
        mMediaPlayer1.seekTo(pointA);
        startPlaying();
        mHandler.postDelayed(checkABRepeatRange, 100);
    }

    /**
     * Clears the A-B Repeat song markers.
     */

    public void clearABRepeatRange() {
        mHandler.removeCallbacks(checkABRepeatRange);
        mRepeatSongRangePointA = 0;
        mRepeatSongRangePointB = 0;
    }

    public void setSongPos(int songPos) {
        mSongPos = songPos;
    }

    /**
     * Indicates if music is currently playing.
     */
    public boolean isPlayingMusic() {
        try {
            return getMediaPlayer().isPlaying();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Uri getUri(long audioId) {
        return ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, audioId);
    }

    public EqualizerHelper getEqualizerHelper() {
        return mEqualizerHelper;
    }

    public Song getSong() {
        return mSong;
    }

    public PrepareServiceListener getPrepareServiceListener() {
        return mPrepareServiceListener;
    }

    public void setPrepareServiceListener(PrepareServiceListener prepareServiceListener) {
        mPrepareServiceListener = prepareServiceListener;
    }

    public SongDataHelper getSongDataHelper() {
        return mSongDataHelper;
    }

    public void setSongDataHelper(SongDataHelper songDataHelper) {
        mSongDataHelper = songDataHelper;
    }

    public boolean isMediaPlayerPrepared() {
        return mMediaPlayerPrepared;
    }

    public interface PrepareServiceListener {
        void onServiceRunning(MusicService musicService);
    }
}
