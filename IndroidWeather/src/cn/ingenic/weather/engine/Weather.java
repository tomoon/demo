package cn.ingenic.weather.engine;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 
 * @author kli
 *
 *
 *        0          «Á          
          1          ∂‡‘∆          
          2          “ı          
          3          ’Û”Í          
          4          ¿◊’Û”Í          
          5          ¿◊’Û”Í≤¢∞È”–±˘±¢          
          6          ”Íº”—©          
          7          –°”Í          
          8          ÷–”Í          
          9          ¥Û”Í          
          10         ±©”Í          
          11         ¥Û±©”Í          
          12         Ãÿ¥Û±©”Í          
          13         ’Û—©          
          14         –°—©          
          15         ÷–—©          
          16         ¥Û—©          
          17         ±©—©          
          18         ŒÌ          
          19         ∂≥”Í          
          20         …≥≥æ±©          
          21         –°”Í-÷–”Í          
          22         ÷–”Í-¥Û”Í          
          23         ¥Û”Í-±©”Í          
          24         ±©”Í-¥Û±©”Í          
          25         ¥Û±©”Í-Ãÿ¥Û±©”Í          
          26         –°—©-÷–—©          
          27         ÷–—©-¥Û—©          
          28         ¥Û—©-±©—©          
          29         ∏°≥æ          
          30         —Ô…≥          
          31         «ø…≥≥æ±©          
          nothing    √ª”– ˝æ›
 */

public class Weather implements Parcelable{
	public Calendar calendar;
	public String currentTemp;
	public String maxTemp;
	public String minTemp;
	public String weather;
	public String wind;

	@Override
	public int describeContents() {
		return 0;
	}
	
	public static final Parcelable.Creator<Weather> CREATOR
							= new Parcelable.Creator<Weather>() {
		public Weather createFromParcel(Parcel in) {
			Weather weather = new Weather();
			if(in.readInt() == 1){
				weather.calendar = (Calendar) in.readSerializable();
			}
			
			if(in.readInt() == 1){
				weather.currentTemp = in.readString();
			}

			if(in.readInt() == 1){
				weather.maxTemp = in.readString();
			}
			
			if(in.readInt() == 1){
				weather.minTemp = in.readString();
			}

			if(in.readInt() == 1){
				weather.weather = in.readString();
			}

			if(in.readInt() == 1){
				weather.wind = in.readString();
			}
			
			return weather;
		}

		public Weather[] newArray(int size) {
			return new Weather[size];
		}
	};

	@Override
	public void writeToParcel(Parcel parcel, int arg1) {
		//calendar
		if(calendar == null){
			parcel.writeInt(0);
		}else{
			parcel.writeInt(1);
			parcel.writeSerializable(calendar);
		}
		//currentTemp;
		if(currentTemp == null){
			parcel.writeInt(0);
		}else{
			parcel.writeInt(1);
			parcel.writeString(currentTemp);
		}
		
		//maxTemp;
		if(maxTemp == null){
			parcel.writeInt(0);
		}else{
			parcel.writeInt(1);
			parcel.writeString(maxTemp);
		}
		
		//minTemp;
		if(minTemp == null){
			parcel.writeInt(0);
		}else{
			parcel.writeInt(1);
			parcel.writeString(minTemp);
		}
		
		//weather;
		if(weather == null){
			parcel.writeInt(0);
		}else{
			parcel.writeInt(1);
			parcel.writeString(weather);
		}
		
		//wind;
		if(wind == null){
			parcel.writeInt(0);
		}else{
			parcel.writeInt(1);
			parcel.writeString(wind);
		}
	}
	
	
	public String toString(){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyƒÍMM‘¬dd»’");
			return "calendar:" + sdf.format(calendar.getTime()) + "; currentTemp:"
					+ currentTemp + "; " + "maxTemp:" + maxTemp + "; "
					+ "minTemp:" + minTemp + "; " + "weather:" + weather + "; "
					+ "wind:" + wind + ";";
		} catch (Exception e) {
			return "";
		}
	}

}
