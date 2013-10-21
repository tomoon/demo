package cn.ingenic.weather;

import android.app.Application;

public class WeatherApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		klilog.i("WeatherApp onCreate update widget");
		EngineManager.getInstance(this).updateTime();
		EngineManager.getInstance(this).init(null);
	}

}
