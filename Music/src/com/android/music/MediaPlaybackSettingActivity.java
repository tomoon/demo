package com.android.music;

import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import java.util.ArrayList;
import java.util.Iterator;

public class MediaPlaybackSettingActivity extends MusicBaseActivity implements View.OnClickListener {

    private static final String TAG ="MediaPlaybackSettingActivity";

    //    private static MediaPlaybackActivity callBackListener;

    
    private ImageView mCurrentPlayListImageView;
    private Button mCurrentPlayListButton;

    private ImageView mShuffleImageView;
    private Button mShuffleButton;

    private ImageView mRepeatImageView;
    private Button mRepeatButton;

    private SeekBar mVolumeSeekBar;
    
    AudioManager mAudioManager;


    //public static void setCallBackListener(MediaPlaybackActivity listener){
    //	callBackListener = listener;
    //}
	
    @Override
    public boolean supportFlingLeft() {
	// TODO Auto-generated method stub
	return false;
    }
    
    @Override
    public boolean supportFlingRight() {
	// TODO Auto-generated method stub
	return true;
    }

    @Override
    public void flingLeft() {
	// TODO Auto-generated method stub
	
    }
    
    @Override
    public void flingRight() {
	// TODO Auto-generated method stub
	finish();
	overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);       
    }
    
    public void party_shuffle_switch(View view){
	MusicUtils.togglePartyShuffle();
	updatePartyShuffleUI();
    }
    
    public void party_shuffle_all(View view){
	Cursor cursor;
	cursor = MusicUtils.query(this, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				  new String [] { MediaStore.Audio.Media._ID}, 
				  MediaStore.Audio.Media.IS_MUSIC + "=1", null,
				  MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
	if (cursor != null) {
	    MusicUtils.shuffleAll(this, cursor,false);
	    cursor.close();
	}	   
	updatePartyShuffleUI();
    }
    
    private void setShuffleButtonImage() {
	int shuffle = MusicUtils.getCurrentShuffleMode();
	switch (shuffle) {
	case MediaPlaybackService.SHUFFLE_NONE:
	    mShuffleImageView.setImageResource(R.drawable.ic_mp_shuffle_off_btn);
	    mShuffleButton.setText(R.string.shuffle_mode_state_none);
	    break;
	case MediaPlaybackService.SHUFFLE_AUTO:
	    mShuffleImageView.setImageResource(R.drawable.ic_mp_partyshuffle_on_btn);
	    mShuffleButton.setText(R.string.shuffle_mode_state_party_shuffle);
	    break;
	default:
	    mShuffleImageView.setImageResource(R.drawable.ic_mp_shuffle_on_btn);
	    mShuffleButton.setText(R.string.shuffle_mode_state_shuffle_all);
	    break;
	}
    }

    private void setRepeatButtonImage() {
	int repeatMode =  MusicUtils.getCurrentRepeatMode();
     
	switch (repeatMode) {
	case MediaPlaybackService.REPEAT_ALL:		
	    mRepeatImageView.setImageResource(R.drawable.ic_mp_repeat_all_btn);
	    mRepeatButton.setText(R.string.repeat_mode_state_repeat_all);
	    break;
	case MediaPlaybackService.REPEAT_CURRENT:
	    mRepeatImageView.setImageResource(R.drawable.ic_mp_repeat_once_btn);
	    mRepeatButton.setText(R.string.repeat_mode_state_repeat_current);
	    break;
	default:
	    mRepeatImageView.setImageResource(R.drawable.ic_mp_repeat_off_btn);
	    mRepeatButton.setText(R.string.repeat_mode_state_none);
	    break;
	}
    }
    
    private void updateCurrentVolume(){
	mVolumeSeekBar.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));	
    }

    private void updatePartyShuffleUI(){
	setShuffleButtonImage();
	setRepeatButtonImage();
	updateCurrentVolume();
    }
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.mediaplayback_setting);
	mAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
	initView();
    }
    
    
    private void initView(){	           	
	mCurrentPlayListImageView =(ImageView) findViewById(R.id.curplaylistImage);
	mCurrentPlayListButton =(Button) findViewById(R.id.curplaylist);
	mCurrentPlayListButton.setOnClickListener(this);

	mShuffleImageView = (ImageView) findViewById(R.id.shuffleImage);
	mShuffleButton =(Button) findViewById(R.id.shuffle);
	mShuffleButton.setOnClickListener(this);
	
	mRepeatImageView = (ImageView) findViewById(R.id.repeatImage);
	mRepeatButton =(Button) findViewById(R.id.repeat);
	mRepeatButton.setOnClickListener(this);

	mVolumeSeekBar = (SeekBar) findViewById(R.id.volumeSeekBar);
	mVolumeSeekBar.setMax(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
	mVolumeSeekBar.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
	mVolumeSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
					      boolean fromUser) {
		    // TODO Auto-generated method stub
		    if(fromUser){
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);			
		    }
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		    // TODO Auto-generated method stub
		    
		}
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		    // TODO Auto-generated method stub
		    
		}		
	    });		
    }

    @Override
    public void onClick(View v) {
	// TODO Auto-generated method stub	
	//if(callBackListener==null){
	//return ;
	//}
	switch(v.getId()){
	case R.id.curplaylist:
	    startActivity(
			  new Intent(Intent.ACTION_EDIT)
			  .setDataAndType(Uri.EMPTY, "vnd.android.cursor.dir/track")
			  .putExtra("playlist", "nowplaying"));
	    break;
	case R.id.shuffle:
	    toggleShuffle();
	    updatePartyShuffleUI();
	    //callBackListener.callBack(MediaPlaybackActivity.ACTION_SHUFFLE_MODE_CHANGE);
	    break;
	case R.id.repeat:
	    cycleRepeat();
	    updatePartyShuffleUI();
	    //callBackListener.callBack(MediaPlaybackActivity.ACTION_REPEAT_MODE_CHANGE);
	    break;
	default:
	    break;
	}
    }
    
    private void toggleShuffle() {
	int shuffle = MusicUtils.getCurrentShuffleMode();
	Log.d(TAG,"---toggleShuffle() shuffle="+shuffle);
	if (shuffle == MediaPlaybackService.SHUFFLE_NONE) {
	    MusicUtils.setShuffleMode(MediaPlaybackService.SHUFFLE_NORMAL);
	    if (MusicUtils.getCurrentRepeatMode() == MediaPlaybackService.REPEAT_CURRENT) {
		Log.d(TAG,"---toggleShuffle() setRepeatMode==REPEAT_ALL");
		MusicUtils.setRepeatMode(MediaPlaybackService.REPEAT_ALL);
	    }	    
	} else if (shuffle == MediaPlaybackService.SHUFFLE_NORMAL ||
		   shuffle == MediaPlaybackService.SHUFFLE_AUTO) {
	    Log.d(TAG,"---toggleShuffle() setShuffleMode==SHUFFLE_NONE");
	    MusicUtils.setShuffleMode(MediaPlaybackService.SHUFFLE_NONE);
	} else {
	    Log.e("MediaPlaybackActivity", "Invalid shuffle mode: " + shuffle);
	}	
    }
    
    
    
    private void cycleRepeat() {    
	int mode = MusicUtils.getCurrentRepeatMode();
	Log.d(TAG,"---cycleRepeat mode="+mode);
	if (mode == MediaPlaybackService.REPEAT_NONE) {
	    Log.d(TAG,"---toggleShuffle() setRepeatMode REPEAT_ALL");
	    MusicUtils.setRepeatMode(MediaPlaybackService.REPEAT_ALL);	    
	} else if (mode == MediaPlaybackService.REPEAT_ALL) {
	    MusicUtils.setRepeatMode(MediaPlaybackService.REPEAT_CURRENT);
	    if (MusicUtils.getCurrentShuffleMode() != MediaPlaybackService.SHUFFLE_NONE) {
		MusicUtils.setShuffleMode(MediaPlaybackService.SHUFFLE_NONE);
	    }	   
	} else {
	    MusicUtils.setRepeatMode(MediaPlaybackService.REPEAT_NONE);
	}	
    }
    
    @Override
    protected void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
    }
    
    @Override
    protected void onRestart() {
	// TODO Auto-generated method stub
	super.onRestart();
    }
    
    @Override
    protected void onResume() {
	// TODO Auto-generated method stub
	updatePartyShuffleUI();
	super.onResume();
    }
    
    @Override
    protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
    }
    
    @Override
    protected void onStop() {
	// TODO Auto-generated method stub
	super.onStop();
    }
    
    @Override
    protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
    }




}
