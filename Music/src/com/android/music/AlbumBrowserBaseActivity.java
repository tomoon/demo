package com.android.music;


import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.ExpandableListView;

public abstract class  AlbumBrowserBaseActivity extends ListActivity {
    
    private static final String TAG = "ArtistAlbumBaseActivity";
    
    private GestureDetector mGestureDetector;
    private static final int MIN_X_FLING_DISTANCE = 20;
    
    
    public abstract boolean supportFlingLeft();
    public abstract boolean supportFlingRight();
    public abstract void  flingLeft();
    public abstract void  flingRight();
    
    
    
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    // TODO Auto-generated method stub
	    Log.d(TAG,"onTouchEvent called");
	    if (mGestureDetector.onTouchEvent(event))
		return true;
	    return super.onTouchEvent(event);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    // TODO Auto-generated method stub
	    super.onCreate(savedInstanceState);
	    mGestureDetector = new GestureDetector(this, new MyGestureListener());
	    Log.d(TAG,"onCreate called");
	}
    
	private class MyGestureListener extends SimpleOnGestureListener {    
	    @Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
					float distanceX, float distanceY) {
		float mOldX = e1.getX(), mOldY = e1.getY();
		int y = (int) e2.getRawY();
		float x = e2.getX();
		Display disp = getWindowManager().getDefaultDisplay();
		int windowWidth = disp.getWidth();
		int windowHeight = disp.getHeight();
		float distance = x -mOldX;
		float min_x_distance = windowWidth / 12;
		android.util.Log.d(TAG,"distance="+distance+",min_x_distance="+min_x_distance);
		
		if(distance> min_x_distance){ //fling  left
		    if(supportFlingLeft()){
			flingLeft();
		    }
		}else if(distance < - min_x_distance){
		    if(supportFlingRight()){
			flingRight();
		    }
		}
		
		return super.onScroll(e1, e2, distanceX, distanceY);
	    }
	}
	
	
	
}
