package com.android.music.lrc;


/**
 * 
 * @author zhuzhu
 * @category 
 * 
 */
public class Sentence {
	
	/**
	 * @property 
	 * 			fromTime:
	 * 			toTime
	 * 			content	
	 */
	private long fromTime;
	private long toTime;
	private String content;
	
	public Sentence(String content,long formTime,long toTime){
		this.content = content;
		this.fromTime = formTime;
		this.toTime = toTime;
	}
	
	public Sentence(String content,long formTime){
		this(content,formTime,0);
	}
	
	public Sentence(String content){
		this(content,0,0);
	}

	public long getFromTime() {
		return fromTime;
	}

	public void setFromTime(long fromTime) {
		this.fromTime = fromTime;
	}

	public long getToTime() {
		return toTime;
	}

	public void setToTime(long toTime) {
		this.toTime = toTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public boolean isInTime(long time){
		return fromTime < time && toTime > time;
	}
	
	/**
	 * 
	 * @return 
	 */
	public long getDuring(){
		return toTime - fromTime;
	}
	
	public String toString(){
		return "from:"+ fromTime + ",content:"+content+",to:"+toTime;
		
	}
	
}
