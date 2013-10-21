package com.android.music;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ArtistAlbumSettingActivity extends MusicBaseActivity {

	private static final String TAG ="ArtistAlbumSettingActivity";
	
        private Button partyShuffleButton;
	private Button shuffleAllButton;
        private ImageView partyShuffleImageView ;
        private ImageView shuffleAllImageView;
    
	
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
		setContentView(R.layout.artistalbum_setting);
		boolean isAlbum = getIntent().getBooleanExtra("isAlbum",false);
		if(isAlbum){
		    setTitle(R.string.album_settings);
		}
		partyShuffleButton  = (Button) findViewById(R.id.party_shuffle_switch_button);
		shuffleAllButton = (Button) findViewById(R.id.shuffle_all_button);		
		partyShuffleImageView = (ImageView) findViewById(R.id.party_shuffle_image);
		shuffleAllImageView = (ImageView) findViewById(R.id.shuffle_all_image);
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
