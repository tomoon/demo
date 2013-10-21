/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.music;

import com.android.music.MusicUtils.ServiceToken;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MusicBrowserActivity extends MusicBaseActivity
    implements MusicUtils.Defs, View.OnClickListener{

    private ServiceToken mToken;
    private static final String TAG = "MusicBrowserActivity";
    //private TextView artistbutton;
    //private TextView albumbutton;
    private TextView artistbutton;
    private TextView albumbutton;
    private TextView songbutton;
    private TextView playlistbutton;
    String shuf = "";

    public MusicBrowserActivity() {
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        /*int activeTab = MusicUtils.getIntPref(this, "activetab", R.id.artisttab);
        if (activeTab != R.id.artisttab
                && activeTab != R.id.albumtab
                && activeTab != R.id.songtab
                && activeTab != R.id.playlisttab) {
            activeTab = R.id.artisttab;
        }
        MusicUtils.activateTab(this, activeTab);*/ //lnliu need remove
        
        setContentView(R.layout.musicbrowser);
        artistbutton = (TextView) findViewById(R.id.artist);
        albumbutton = (TextView) findViewById(R.id.album);
        songbutton = (TextView) findViewById(R.id.song);
        playlistbutton = (TextView) findViewById(R.id.playlist);
        artistbutton.setOnClickListener(this);
        albumbutton.setOnClickListener(this);
        songbutton.setOnClickListener(this);
        playlistbutton.setOnClickListener(this);
        
        artistbutton.getBackground().setAlpha(150);
        albumbutton.getBackground().setAlpha(150);
        songbutton.getBackground().setAlpha(150);
        playlistbutton.getBackground().setAlpha(150);
        
        shuf = getIntent().getStringExtra("autoshuffle");
        Log.e(TAG,"onCreate shuf = "+shuf);
        //if ("true".equals(shuf)) {
            mToken = MusicUtils.bindToService(this, autoshuffle);
        //}
        }
    
    

    @Override
	protected void onStart() {
		Log.e(TAG,"onStart");
		super.onStart();
	}

	@Override
	protected void onRestart() {
		Log.e(TAG,"onRestart");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		Log.e(TAG,"onResume");
		//MusicUtils.updateNowPlaying(MusicBrowserActivity.this); 
		super.onResume();
	}

	@Override
	protected void onPause() {
		Log.e(TAG,"onPause");
		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.e(TAG,"onStop");
		super.onStop();
    }

    @Override
    public void onDestroy() {
        if (mToken != null) {
            MusicUtils.unbindFromService(mToken);
        }
        super.onDestroy();
    }

    private ServiceConnection autoshuffle = new ServiceConnection() {
        public void onServiceConnected(ComponentName classname, IBinder obj) {
            // we need to be able to bind again, so unbind
        	if ("true".equals(shuf)) {
            try {
                unbindService(this);
            } catch (IllegalArgumentException e) {
            }
            IMediaPlaybackService serv = IMediaPlaybackService.Stub.asInterface(obj);
            if (serv != null) {
                try {
                    serv.setShuffleMode(MediaPlaybackService.SHUFFLE_AUTO);
                } catch (RemoteException ex) {
                }
            }
        }
        	//MusicUtils.updateNowPlaying(MusicBrowserActivity.this); 
        }

        public void onServiceDisconnected(ComponentName classname) {
        }
    };

	@Override
	public void onClick(View v) {
	    Intent intent = new Intent(Intent.ACTION_PICK);
	    switch(v.getId()){
	    case R.id.artist:
		intent.setDataAndType(Uri.EMPTY, "vnd.android.cursor.dir/artistalbum");
		break;
	    case R.id.album:
		intent.setDataAndType(Uri.EMPTY, "vnd.android.cursor.dir/album");
		break;
	    case R.id.song:
		intent.setDataAndType(Uri.EMPTY, "vnd.android.cursor.dir/track");
		break;
	    case R.id.playlist:
		intent.setDataAndType(Uri.EMPTY, MediaStore.Audio.Playlists.CONTENT_TYPE);
		break;
	    default:
		return;
	    }
	    startActivity(intent);
	}

	@Override
	public boolean supportFlingLeft() {
		// TODO Auto-generated method stub
	    return true;
	}

	@Override
	public boolean supportFlingRight() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void flingLeft() {
		// TODO Auto-generated method stub
	    Intent i = new Intent(this,MusicBrowserSettingActivity.class);
	    startActivity(i);
	    overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
	}

	@Override
	public void flingRight() {
	    // TODO Auto-generated method stub
	    Log.d(TAG,"call flingRight");
	    MusicUtils.FlingRight(this);
	    overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
	}

}

