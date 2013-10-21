package cn.ingenic.weather;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.ingenic.weather.EngineManager.DataChangedListener;
import cn.ingenic.weather.engine.City;
import cn.ingenic.weather.engine.Weather;

public class WeatherDisplay extends Activity implements OnClickListener,
 			DataChangedListener{
	
	private final static int MSG_SHOW_WEATHER = 1;
	private final static int MSG_FRESH_ANIM_START = 2;
	private final static int MSG_FRESH_ANIM_STOP = 3;
	
	private EngineManager mEngine;
	private ImageButton mIbFresh;
	private ProgressBar mPbFreshing;

	private Animation mFreshAnim;
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			case MSG_SHOW_WEATHER:
				City city = (City)msg.obj;
				displayWeather(city);
				break;
			case MSG_FRESH_ANIM_START:
				mIbFresh.setVisibility(View.GONE);
				mPbFreshing.setVisibility(View.VISIBLE);
//			    mIbFresh.startAnimation(mFreshAnim);
//			    mIbFresh.setEnabled(false);
				break;
			case MSG_FRESH_ANIM_STOP:
				mIbFresh.setVisibility(View.VISIBLE);
				mPbFreshing.setVisibility(View.GONE);
//				mIbFresh.clearAnimation();
//			    mIbFresh.setEnabled(true);
				break;
			}
		}
		
	};
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.weather_details);

	    mFreshAnim = AnimationUtils.loadAnimation(this, R.anim.fresh_anim);
	    mFreshAnim.setRepeatCount(Animation.INFINITE);
	    mPbFreshing = (ProgressBar)findViewById(R.id.pb_freshing);

	    mIbFresh = (ImageButton)findViewById(R.id.ib_fresh);
	    mIbFresh.setOnClickListener(this);
	    
	    //prepared
	    mEngine = EngineManager.getInstance(this);
	    mEngine.register(this);
	    
		findViewById(R.id.ib_settings).setOnClickListener(this);
	}
	
	

	@Override
	protected void onResume() {
		super.onResume();
	    //get weather
	    final City city = mEngine.getDefaultMarkCity();
	    if(city == null){
	    	return;
	    }else if(city.weather != null){
	    	Message msg = mHandler.obtainMessage(MSG_SHOW_WEATHER);
	    	msg.obj = city;
	    	msg.sendToTarget();
	    }else if(!mEngine.isRequesting()){
	    	mEngine.getWeatherByIndex(city.index);
	    }
	    if(getIntent().getBooleanExtra("notification", false)){
		    mEngine.clearWeahterNotif();
	    }
	}



	private void displayWeather(City city) {
		if (city == null || city.weather == null) {
			Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
			return;
		}
		
		String degree = this.getString(R.string.sheshidu);
		
		Weather firstWeather = city.weather.get(0);
		Weather secondWeather = city.weather.get(1);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
		
		//update time
		TextView updateTime = (TextView)findViewById(R.id.update_time);
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd HH:mm");
		String update = getString(R.string.update_time)+sdf2.format(new Date(city.updateTime));
		updateTime.setText(update);
		updateTime.setShadowLayer(2F, 2F,1F, Color.BLACK);
		
		//current weather
		TextView cityName = (TextView)findViewById(R.id.tv_city_name);
		cityName.setText(city.name);
		
		TextView currentTemp = (TextView)findViewById(R.id.current_temp);
		currentTemp.setText(firstWeather.currentTemp + degree);
		currentTemp.setShadowLayer(2F, 2F,1F, Color.BLACK);
		
		TextView currentWeather = (TextView)findViewById(R.id.current_weather);
		currentWeather.setText(WeatherUtils.getWeather(this, firstWeather.weather));
		currentWeather.setShadowLayer(2F, 2F,1F, Color.BLACK);

		TextView currentWind = (TextView)findViewById(R.id.current_wind);
		currentWind.setText(firstWeather.wind);
		currentWind.setShadowLayer(2F, 2F,1F, Color.BLACK);
		
		//1st weather
		TextView fstDate = (TextView)findViewById(R.id.tv_prv_date);
		fstDate.setText(sdf.format(firstWeather.calendar.getTime()));
		
		TextView fstWeather = (TextView)findViewById(R.id.tv_prv_weather);
		fstWeather.setText(WeatherUtils.getWeather(this, firstWeather.weather));
		
		ImageView fstImg = (ImageView)findViewById(R.id.iv_prv_pic);
		fstImg.setImageResource(WeatherUtils.getHDrawable(firstWeather.weather));

		TextView fstMaxTemp = (TextView)findViewById(R.id.tv_prv_maxtemp);
		fstMaxTemp.setText(firstWeather.maxTemp + degree);

		TextView fstMinTemp = (TextView)findViewById(R.id.tv_prv_mintemp);
		fstMinTemp.setText(firstWeather.minTemp + degree);
		
		//2st weather
		TextView fstDate2 = (TextView)findViewById(R.id.tv_prv_date2);
		fstDate2.setText(sdf.format(secondWeather.calendar.getTime()));
		
		TextView fstWeather2 = (TextView)findViewById(R.id.tv_prv_weather2);
		fstWeather2.setText(WeatherUtils.getWeather(this, secondWeather.weather));
		
		ImageView fstImg2 = (ImageView)findViewById(R.id.iv_prv_pic2);
		fstImg2.setImageResource(WeatherUtils.getHDrawable(secondWeather.weather));

		TextView fstMaxTemp2 = (TextView)findViewById(R.id.tv_prv_maxtemp2);
		fstMaxTemp2.setText(secondWeather.maxTemp + degree);

		TextView fstMinTemp2 = (TextView)findViewById(R.id.tv_prv_mintemp2);
		fstMinTemp2.setText(secondWeather.minTemp + degree);
		
	}
	
	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.ib_fresh:
		    City city = mEngine.getDefaultMarkCity();
	    	mEngine.getWeatherByIndex(city.index);
			break;
		case R.id.ib_settings:
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			break;
		}
	}
	
	private void startFreshAnim(){
		mHandler.sendEmptyMessage(MSG_FRESH_ANIM_START);
	}
	
	private void stopFreshAnim(){
		mHandler.sendEmptyMessage(MSG_FRESH_ANIM_STOP);
	}

	@Override
	public void onWeatherChanged(City city) {
    	Message msg = mHandler.obtainMessage(MSG_SHOW_WEATHER);
    	msg.obj = city;
    	msg.sendToTarget();
	}

	@Override
	public void onStateChanged(boolean isRequesting) {
		if(isRequesting){
			startFreshAnim();
		}else{
			stopFreshAnim();
		}
	}
}
