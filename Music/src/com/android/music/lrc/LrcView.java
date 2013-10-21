package com.android.music.lrc;

import com.android.music.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.os.Environment;
import android.util.AttributeSet;
import android.widget.TextView;

public class LrcView extends TextView {

	private int middleX;
	private int middleY;
	private Paint mCurrPaint;
	private Paint mOtherPaint;
	private Lyric mCurrLyric;
	private long currTime;
	private int tmpCurrIndex;
	private int currIndex;
	private int DY; 
	private String lyricPath;
	private int scrollCount;
	private int scollX;
	private int scollStartX;
	private boolean playEnable = true;
	
//	private boolean isLrcEx ;
	
	//private String LrcDir = Environment.getExternalStorageDirectory().getAbsolutePath()+"/music/lrc/";
		
	public boolean isPlayEnable() {
		return playEnable;
	}


	public void setPlayEnable(boolean playEnable) {
		this.playEnable = playEnable;
	}


	public synchronized long getCurrTime() {
		return currTime;
	}


	public synchronized void setCurrTime(long currTime) {
		this.currTime = currTime;
	}
	
	
	public LrcView(Context context, AttributeSet attrs) {
		super(context, attrs);
		//init();
		// TODO Auto-generated constructor stub
	}
	

	public void init(String title){
		mCurrPaint = new Paint();
		mOtherPaint = new Paint();
		mCurrLyric = new Lyric(lyricPath,title);
//		if(!mCurrLyric.isLrcFileExists()){
//			isLrcEx = false;
//		}else{
//			isLrcEx = true;
//		}
		mCurrPaint.setColor(Color.YELLOW);
		mOtherPaint.setColor(Color.WHITE);
		mCurrPaint.setAntiAlias(true);
		mOtherPaint.setAntiAlias(true);
		mCurrPaint.setTextAlign(Paint.Align.CENTER);
		mCurrPaint.setTextSize(20);
		mOtherPaint.setTextAlign(Paint.Align.CENTER);
		mOtherPaint.setTextSize(15);
		currTime = 0;
		currIndex = 0;
		scrollCount = 0;
		scollX = 0;
		scollStartX = 0;
		tmpCurrIndex = -1;
		DY = fontHeight();
		
	}
	
	
	public String getLyricPath() {
		return lyricPath;
	}



	public void setLyricPath(String lyricPath) {
		this.lyricPath = lyricPath;
	}


	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		//currIndex = mCurrLyric.getNowSentenceIndex(currTime);
		if(mCurrLyric==null) return ;
		
		if(currIndex<0||currIndex>mCurrLyric.getSentences().size()){
			return;
		}
		//tmpCurrIndex = currIndex;
		middleX = (getRight()-getLeft())/2;
		middleY = (getBottom()-getTop())/2;
		
		if(currIndex>=0||currIndex<mCurrLyric.getSentences().size()){
			Sentence now = mCurrLyric.getSentences().get(currIndex);
			//float f = (currTime - now.getFromTime())*1.0f/now.getDuring();
			
			//Shader shader = new LinearGradient(0, 0, 0, 15,
			//		new int[]{Color.RED,Color.BLUE},new float[]{f,f+0.01f},TileMode.CLAMP);
			//mCurrPaint.setShader(shader);
			int contentLen = strWidth(now.getContent());
			int viewWidth = getWidth();
			
			int tmpMiddleX = middleX;
			//System.out.println(contentLen);
			//System.out.println(viewWidth);
			
			
			if(contentLen>viewWidth){					
				
				/* reslove music title is too long 
				 * add by jfang
				 * */  
				if(now.getDuring()==Integer.MAX_VALUE)
				{
					scollX = 10;
					scollStartX = middleX+(contentLen-viewWidth + 100)/2;
					tmpMiddleX = scollStartX - (int)(scollX * scrollCount/4) ;
					canvas.drawText(now.getContent(), tmpMiddleX, middleY, mCurrPaint);
					if(Math.abs(tmpMiddleX-scollStartX)<=contentLen){
						ScrollThread scroll = new ScrollThread();
						scroll.start();	
					}
					else
					{
						scrollCount = 0;
						scollStartX = 0;
						ScrollThread scroll = new ScrollThread();
						scroll.start();	
					}
				}
				else
				{
				//if(scrollCount == 0){
					scollX = (int)((contentLen - viewWidth + 100)/((float)(now.getDuring()/1000)));
					scollStartX = middleX+(contentLen-viewWidth + 100)/2;
					//tmpMiddleX = scollStartX;
					
//					System.out.println("scollx:"+ scollX );
//					System.out.println("scollStartx:"+ scollStartX);
//					System.out.println("scollcount:"+ scrollCount);
//					System.out.println("middleX:" + middleX);
//					System.out.println("during+"+now.getDuring());
					
					
				//}else{
					tmpMiddleX = scollStartX - (int)(scollX * scrollCount/4) ;
//					System.out.println("tmpMiddleX:" + tmpMiddleX);
				//}
				canvas.drawText(now.getContent(), tmpMiddleX, middleY, mCurrPaint);		
				if(getCurrTime()<now.getToTime()){
					ScrollThread scroll = new ScrollThread();
					scroll.start();	
				}	
				}
			}else{
				scrollCount = 0;
				scollStartX = 0;
				canvas.drawText(now.getContent(), middleX, middleY, mCurrPaint);
			}
		}
		int tmpCurrIndex = currIndex-1;
		int currY = middleY - DY;
		while(currY>0&&tmpCurrIndex>=0){
			canvas.drawText(mCurrLyric.getSentences().get(tmpCurrIndex).getContent(), middleX, currY, mOtherPaint);
			tmpCurrIndex = tmpCurrIndex - 1;
			currY = currY - DY;
		}
		tmpCurrIndex = currIndex+1;
		currY = middleY + DY;
		while(currY<(getBottom() - getTop())&&tmpCurrIndex<mCurrLyric.getSentences().size()){
			canvas.drawText(mCurrLyric.getSentences().get(tmpCurrIndex).getContent(), middleX, currY, mOtherPaint);
			tmpCurrIndex = tmpCurrIndex + 1;
			currY = currY + DY;
		}
		
		super.onDraw(canvas);
	}
	
	public int fontHeight(){
		return (int) (mCurrPaint.getFontMetrics().bottom - mCurrPaint.getFontMetrics().top)+13;
	}
	
	public int strWidth(String str){
		return (int) mCurrPaint.measureText(str);
	}
	
	public synchronized void reflush(){
		//mCurrLyric.printSentences();
		currIndex = mCurrLyric.getNowSentenceIndex(getCurrTime());
		//System.out.println("currIndex is " + currIndex);
		if(tmpCurrIndex!=currIndex){
			scrollCount = 0;
			scollStartX = 0;
			postInvalidate();
			tmpCurrIndex = currIndex;
		}
	}
	
	 class  ScrollThread extends Thread{
		@Override
		public synchronized void run() {
			// TODO Auto-generated method stub
			try {
				sleep(250);	
				if(isPlayEnable()){
					setCurrTime(getCurrTime()+ 250); 
					scrollCount ++ ;
				}
								
				postInvalidate();				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}	

}
