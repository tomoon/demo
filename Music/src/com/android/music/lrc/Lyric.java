package com.android.music.lrc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;



public class Lyric {
	
	private String lrcTitle;
	private String lrcSinger;
	private String lrcAlbum;
	private List<Sentence> sentences = new ArrayList<Sentence>();
	private File lrcFile;
	private boolean initDown = false; 
	
	public boolean isInitDown() {
		return initDown;
	}

	public void setInitDown(boolean initDown) {
		this.initDown = initDown;
	}

	public String getLrcTitle() {
		return lrcTitle;
	}

	public void setLrcTitle(String lrcTitle) {
		this.lrcTitle = lrcTitle;
	}

	public String getLrcSinger() {
		return lrcSinger;
	}

	public void setLrcSinger(String lrcSinger) {
		this.lrcSinger = lrcSinger;
	}

	public String getLrcAlbum() {
		return lrcAlbum;
	}

	public void setLrcAlbum(String lrcAlbum) {
		this.lrcAlbum = lrcAlbum;
	}

	public List<Sentence> getSentences() {
		return sentences;
	}

	public void setSentences(List<Sentence> sentences) {
		this.sentences = sentences;
	}

	public Lyric(String lrcFilePath,String title){
		setInitDown(false);
		setLrcTitle(title);
		this.lrcFile = new File(lrcFilePath+".LRC");
		if(lrcFile != null){
			initSentences(lrcFile);
		}
	}
	
	public boolean isLrcFileExists(){
		return lrcFile.exists();
	}
	
	
	public void initSentences(File file){
		try {
			/*modify by hwlin, 2012.02.16;*/
			InputStream is = new FileInputStream(file);
			InputStreamReader inr = null;
			BufferedReader reader;
//			InputStreamReader inr = new InputStreamReader(is,"GBK");
			byte [] b = new byte[100];
			is.read(b);

			if((b[0]==0xffffffef && b[1]==0xffffffbb && b[2]==0xffffffbf) || (b[4]==0x4e && b[5]==0x6f) || (b[4]==0x48 && b[5]==0x65)
					|| (b[4]==0xffffffe5 && b[5]==0xffffff8f)){ 
				inr = new InputStreamReader(is, "UTF-8");
			}else if((b[0]==0x5b && b[1]==0x30 && b[2]==0x30)||(b[3]==0x3a && b[4]==0xffffffa7 && b[5]==0x64)) {
				inr = new InputStreamReader(is, "Big-5");
			}else if(b[0]==0xffffffff && b[1]==0xfffffffe ){
				inr = new InputStreamReader(is, "UTF-16LE");
			}else if(b[0]==0xfffffffe && b[1]==0xffffffff && b[2]==0x0){
				inr = new InputStreamReader(is, "UTF-16BE");
			}else if(b[0]==0x5b && b[1]==0x74){
				inr = new InputStreamReader(is, "GBK");	
			}else {
				inr = new InputStreamReader(is, "GB2312");	
			}
//			Log.d("Lyric", "inr.getEncoding() = " + inr.getEncoding());
			reader = new BufferedReader(inr);
			String line = null;
			while((line = reader.readLine())!=null){
				if(line!=null){
					paserLine(line);
				}
			}
			inr.close();
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Collections.sort(sentences, new Comparator<Sentence>() {

			public int compare(Sentence o1, Sentence o2) {
				return (int) (o1.getFromTime() - o2.getFromTime());
			}
		});
		
		if(sentences.size() == 0){
			sentences.add(new Sentence(this.getLrcTitle(), 0,
					Integer.MAX_VALUE));
		}else{
			//Sentence first = sentences.get(0);
			//sentences.add(new Sentence(this.getLrcTitle(), 0,
			//		first.getFromTime()));
		}
		
		int size = sentences.size();
		for (int i = 0; i < size; i++) {
			Sentence next = null;
			if (i + 1 < size) {
				next = sentences.get(i + 1);
			}
			Sentence now = sentences.get(i);
			if (next != null) {
				now.setToTime(next.getFromTime() - 1);
			}
		}
		
		if (sentences.size() == 1) {
			sentences.get(0).setToTime(Integer.MAX_VALUE);
		} else {
			Sentence last = sentences.get(sentences.size() - 1);
			last.setToTime(Integer.MAX_VALUE);
		}
		
		setInitDown(true);
		
	}
	
	
	public long stringToTime(String timeStr){
		long curTime = 0;
		String[] s = timeStr.split(":");  
        int min = Integer.parseInt(s[0]);  
        String[] ss = s[1].split("\\.");  
	int sec = 0;
	int mill = 0 ;
	if(ss.length > 1){
	    sec = Integer.parseInt(ss[0]);  
	    mill = Integer.parseInt(ss[1]);  
	}else{
	    sec = Integer.parseInt(ss[0]);   
	}
        curTime =  min * 60 * 1000 + sec * 1000 + mill * 10;
		return curTime;
	}
	
	
	public void paserLine(String line){
		if(line.startsWith("[ti:")){
			String title = line.substring(4, line.length()-1);
			this.setLrcTitle(title);
		}
		else if(line.startsWith("[ar:")){
			String singer = line.substring(4, line.length()-1);
			this.setLrcSinger(singer);
		}
		else if(line.startsWith("[al:")){
			String album = line.substring(4, line.length()-1);
			this.setLrcAlbum(album);
		}
		else{
			String reg = "\\[((\\d{2}:\\d{2}\\.\\d{2})|(\\d{2}:\\d{2}))\\]"; 
			long currentTime = 0;
			String currentContent="";
           
            Pattern pattern = Pattern.compile(reg);  
            Matcher matcher = pattern.matcher(line);  
  
            
            while (matcher.find()) {  
               
                String msg = matcher.group();  
               
                int start = matcher.start();  
               
                int end = matcher.end();  
  
             
                int groupCount = matcher.groupCount();  
               
                for (int i = 0; i <= groupCount; i++) {  
                    String timeStr = matcher.group(i);  
                    if (i == 1) {  
                       
                        currentTime = stringToTime(timeStr);  
                    }  
                }  
  
                
                String[] content = pattern.split(line);  
               
                for (int i = 0; i < content.length; i++) {  
                    if (i == content.length - 1) {  
                        
                        currentContent = content[i];  
                    }  
                }  
                if(currentContent.equals("")){
                	currentContent = "......";
                }
                sentences.add(new Sentence(currentContent,currentTime));
            }
		}
	}

	int getNowSentenceIndex(long t) {
		for (int i = 0; i < sentences.size(); i++) {
			if (sentences.get(i).isInTime(t)) {
				return i;
			}
		}
		return -1;
	}
	
	
	public void printSentences(){
		for(int i=0;i<sentences.size();i++){
			System.out.println(sentences.get(i).toString());
		}
	}
	
}
