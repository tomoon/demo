package com.android.music;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Iterator;

public class TrackBrowserSettingActivity extends MusicBaseActivity implements View.OnClickListener {

    private static final String TAG ="TrackBrowserSettingActivity";

    private static TrackBrowserActivity callBackListener;
	
    private Button playAllButton;
    private Button partyShuffleButton;
    private Button shuffleAllButton;
    private Button saveAsButton;
    private Button clearPlayListButton;

    private ImageView partyShuffleImageView ;
    private ImageView shuffleAllImageView;
    private LinearLayout playAllLineLayout;
    private LinearLayout saveAsLineLayout;
    private LinearLayout clearPlayListLineLayout;
    

    public static void setCallBackListener(TrackBrowserActivity listener){
	callBackListener = listener;
    }
	
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
    
    private void updatePartyShuffleUI(){
	int shuffle = MusicUtils.getCurrentShuffleMode();      
	switch (shuffle) {
	case MediaPlaybackService.SHUFFLE_NONE:
	    partyShuffleButton.setText(R.string.party_shuffle);
	    partyShuffleImageView.setBackgroundResource(R.drawable.ic_menu_party_shuffle);
	    shuffleAllImageView.setBackgroundResource(R.drawable.ic_mp_shuffle_off_btn);
	    //mShuffleButton.setImageResource(R.drawable.ic_mp_shuffle_off_btn);
	    break;
	case MediaPlaybackService.SHUFFLE_AUTO:
	    partyShuffleButton.setText(R.string.party_shuffle_off);
	    partyShuffleImageView.setBackgroundResource(R.drawable.ic_mp_partyshuffle_on_btn);
	    shuffleAllImageView.setBackgroundResource(R.drawable.ic_mp_shuffle_off_btn);
	    //mShuffleButton.setImageResource(R.drawable.ic_mp_partyshuffle_on_btn);
	    break;
	default:
	    partyShuffleButton.setText(R.string.party_shuffle);
	    partyShuffleImageView.setBackgroundResource(R.drawable.ic_menu_party_shuffle);
	    shuffleAllImageView.setBackgroundResource(R.drawable.ic_mp_shuffle_on_btn);
	    //mShuffleButton.setImageResource(R.drawable.ic_mp_shuffle_on_btn);
	    break;
	}
	
    }
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.trackbrowser_setting);
	initView();
    }
    
    
    private void initView(){	    
	ArrayList<Integer> showActionList=getIntent().getIntegerArrayListExtra("setting_show_action");
	Iterator<Integer> itor=showActionList.iterator();
	while(itor.hasNext()){
	    Integer nex= itor.next();
	    int action = nex;
	    if(action == TrackBrowserActivity.PLAY_ALL){
		playAllLineLayout = (LinearLayout) findViewById(R.id.play_all_content);
		playAllLineLayout.setVisibility(View.VISIBLE);		    
		playAllButton = (Button) findViewById(R.id.play_all_button);
		playAllButton.setOnClickListener(this);
	    }
	    if(action == TrackBrowserActivity.SAVE_AS_PLAYLIST){
		saveAsLineLayout = (LinearLayout) findViewById(R.id.save_as_content);
		saveAsLineLayout.setVisibility(View.VISIBLE);
		saveAsButton = (Button) findViewById(R.id.save_as_button);
		saveAsButton.setOnClickListener(this);
	    }
	    if(action == TrackBrowserActivity.CLEAR_PLAYLIST){
		clearPlayListLineLayout = (LinearLayout) findViewById(R.id.clear_playlist_content);
		clearPlayListLineLayout.setVisibility(View.VISIBLE);
		clearPlayListButton= (Button) findViewById(R.id.clear_playlist_button);
		clearPlayListButton.setOnClickListener(this);
	    }
	}

	
	
	partyShuffleButton  = (Button) findViewById(R.id.party_shuffle_switch_button);
	partyShuffleButton.setOnClickListener(this);
	shuffleAllButton = (Button) findViewById(R.id.shuffle_all_button);		
	shuffleAllButton.setOnClickListener(this);
	partyShuffleImageView = (ImageView) findViewById(R.id.party_shuffle_image);
	shuffleAllImageView = (ImageView) findViewById(R.id.shuffle_all_image);		
    }

    @Override
    public void onClick(View v) {
	// TODO Auto-generated method stub		
	if(callBackListener==null){
	    return ;
	}
	switch(v.getId()){
	case R.id.play_all_button:
	    callBackListener.callback(v,TrackBrowserActivity.PLAY_ALL,null);
	    break;
	case R.id.party_shuffle_switch_button :
	    callBackListener.callback(v,MusicUtils.Defs.PARTY_SHUFFLE,null);
	    updatePartyShuffleUI();
	    break;
	case R.id.shuffle_all_button:
	    callBackListener.callback(v,MusicUtils.Defs.SHUFFLE_ALL,null);
	    updatePartyShuffleUI();
	    break;
	case R.id.save_as_button:
	    Intent intent = new Intent();
	    intent.setClass(this, CreatePlaylist.class);
	    startActivityForResult(intent, TrackBrowserActivity.SAVE_AS_PLAYLIST);
	    //callBackListener.callback(v,TrackBrowserActivity.SAVE_AS_PLAYLIST,null);
	    break;
	case R.id.clear_playlist_button:
	    callBackListener.callback(v,TrackBrowserActivity.CLEAR_PLAYLIST,null);
	    break;
	}
    }
    


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
	case TrackBrowserActivity.SAVE_AS_PLAYLIST:
	    if (resultCode == RESULT_OK) {
		Uri uri = intent.getData();
		if(callBackListener == null){
		    return ;
		}
		callBackListener.callback(null,TrackBrowserActivity.SAVE_AS_PLAYLIST_RESULT,uri);
	    }
	    break;
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
