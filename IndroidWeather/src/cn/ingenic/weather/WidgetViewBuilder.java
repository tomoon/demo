package cn.ingenic.weather;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

public class WidgetViewBuilder {
	private final static boolean HOUR_24 = false;
	
	private Context mContext;
	private RemoteViews mRv;
	private Bundle mWeatherData;
	private boolean mUpdateTime;
	private boolean mUpdateWeather;
	private Skin mSkin;
	
	WidgetViewBuilder(Context context, Skin skin){
		mContext = context;
		mSkin = skin;
	}
	
	public void updateTime(boolean update){
		mUpdateTime = update;
	}
	
	public void updateWeather(boolean update){
		mUpdateWeather = update;
	}
	
	public void setWeatherData(Bundle data){
		mWeatherData = data;
	}
	
	public RemoteViews build(){
		buildFromRemote();
		return mRv;
	}
	
	private int getId(String resName){
		try {
			return mSkin.getId(resName);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private int getNumId(int i){
		return getId(Skin.NUMBERS[i]);
	}
	
	private void buildFromRemote(){

		mRv = new RemoteViews(mSkin.getPackageName(), getId(Skin.layout.widget_layout));
		if(mUpdateWeather){
			if(mWeatherData != null){
				mRv.setViewVisibility(getId(Skin.id.tv_weather), View.VISIBLE);
				mRv.setViewVisibility(getId(Skin.id.tv_current),View.VISIBLE);
				mRv.setViewVisibility(getId(Skin.id.tv_min), View.VISIBLE);
				mRv.setViewVisibility(getId(Skin.id.tv_min_to_max), View.VISIBLE);
				mRv.setViewVisibility(getId(Skin.id.tv_max), View.VISIBLE);
				mRv.setViewVisibility(getId(Skin.id.iv_icon), View.VISIBLE);
				
				mRv.setTextViewText(getId(Skin.id.tv_city), mWeatherData.getString("city"));
				mRv.setTextViewText(getId(Skin.id.tv_weather), mWeatherData.getString("weather"));
				mRv.setTextViewText(getId(Skin.id.tv_current), mWeatherData.getString("current_temp"));
				mRv.setTextViewText(getId(Skin.id.tv_min), mWeatherData.getString("min_temp"));
				mRv.setTextViewText(getId(Skin.id.tv_max), mWeatherData.getString("max_temp"));
				int icon = mWeatherData.getInt("icon", 0);
				if(icon != 0){
					BitmapDrawable bd = (BitmapDrawable)mContext.getResources().getDrawable(icon);
					mRv.setImageViewBitmap(getId(Skin.id.iv_icon), bd.getBitmap());
				}
			}else{
				mRv.setViewVisibility(getId(Skin.id.tv_weather), View.INVISIBLE);
				mRv.setViewVisibility(getId(Skin.id.tv_current),View.INVISIBLE);
				mRv.setViewVisibility(getId(Skin.id.tv_min), View.INVISIBLE);
				mRv.setViewVisibility(getId(Skin.id.tv_min_to_max), View.INVISIBLE);
				mRv.setViewVisibility(getId(Skin.id.tv_max), View.INVISIBLE);
				mRv.setViewVisibility(getId(Skin.id.iv_icon), View.INVISIBLE);
				mRv.setTextViewText(getId(Skin.id.tv_city), mContext.getString(R.string.no_weather_data));
			}
		}
		
		if(mUpdateTime){
			Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minite = c.get(Calendar.MINUTE);
			if(HOUR_24){
				mRv.setImageViewResource(getId(Skin.id.iv_time_1), getNumId(hour/10));
				mRv.setImageViewResource(getId(Skin.id.iv_time_2), getNumId(hour%10));
				mRv.setViewVisibility(getId(Skin.id.iv_time_apm), View.GONE);
			}else{
				mRv.setImageViewResource(getId(Skin.id.iv_time_1), hour > 12 ? getNumId((hour - 12)/10) : getNumId(hour/10));
				mRv.setImageViewResource(getId(Skin.id.iv_time_2), hour > 12 ? getNumId((hour - 12)%10) : getNumId(hour%10));
				mRv.setViewVisibility(getId(Skin.id.iv_time_apm), View.VISIBLE);
				mRv.setImageViewResource(getId(Skin.id.iv_time_apm), hour > 12 ? getNumId(11) : getNumId(10));
			}
			mRv.setImageViewResource(getId(Skin.id.iv_time_3), getNumId(minite/10));
			mRv.setImageViewResource(getId(Skin.id.iv_time_4), getNumId(minite%10));
			
			int i = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
			String[] days = mContext.getResources().getStringArray(R.array.weekday);
			mRv.setTextViewText(getId(Skin.id.tv_week), days[i-1]);

			String dateFormat = mContext.getString(R.string.date_format);
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			mRv.setTextViewText(getId(Skin.id.tv_date), sdf.format(new Date()));
			
		}
	}
	
}
