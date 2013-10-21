package com.android.music;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class PlaylistBrowserSettingActivity extends MusicBaseActivity {

	private static final String TAG ="PlaylistBrowserSettingActivity";
	
        private Button partyShuffleButton;
        private ImageView partyShuffleImageView ;
    
	
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

       private void updatePartyShuffleUI(){
	   int shuffle = MusicUtils.getCurrentShuffleMode();      
	   if(shuffle==MediaPlaybackService.SHUFFLE_AUTO){	       
	       partyShuffleButton.setText(R.string.party_shuffle_off);
	       partyShuffleImageView.setBackgroundResource(R.drawable.ic_mp_partyshuffle_on_btn);
	   }else{
	       partyShuffleButton.setText(R.string.party_shuffle);
	       partyShuffleImageView.setBackgroundResource(R.drawable.ic_menu_party_shuffle);
	   }	   
       }
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    // TODO Auto-generated method stub
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.playlist_setting);
	    partyShuffleButton  = (Button) findViewById(R.id.party_shuffle_switch_button);       
	    partyShuffleImageView = (ImageView) findViewById(R.id.party_shuffle_image);
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
