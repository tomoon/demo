package cn.ingenic.weather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class WeatherReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		klilog.i("Weather Receiver received action:"+action);
		if(Intent.ACTION_BOOT_COMPLETED.equals(action)){
			EngineManager.getInstance(context).refreshWeather();
			EngineManager.getInstance(context).updateTime();
		}else if(EngineManager.ACTION_UPDATE_WEATHER.equals(action)){
			EngineManager.getInstance(context).refreshWeather();
		}else if(EngineManager.ACTION_NOTIFY_WEATHER.equals(action)){
			EngineManager.getInstance(context).checkNotifyWeather();
		}else if(EngineManager.ACTION_FRESH_WIDGET_TIME.equals(action)){
			EngineManager.getInstance(context).updateWidget();
		}
	}

}
