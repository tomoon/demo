package cn.ingenic.weather;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RemoteViews;

public class WidgetProvider extends AppWidgetProvider {
	public final static String ACTION_UPDATE_TIME = "cn.ingenic.weatherwidget.update_time";
	public final static String ACTION_UPDATE_SKIN = "cn.ingenic.weatherwidget.update_skin";
	
	private PendingIntent mFreshIntent;
	
	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
		klilog.i("onEnabled");
		setWidgetUpdateAlarm(context);
		updateAll(context);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		klilog.i("onReceive");
		String action = intent.getAction();
		klilog.i("WidgetProvider received:"+action);
		if(ACTION_UPDATE_TIME.equals(action)){
			updateAll(context);
		}else if(EngineManager.ACTION_FRESH_WIDGET.equals(action)){
			updateWidgetWeather(context, intent.getExtras());
		}else if(ACTION_UPDATE_SKIN.equals(action)){
			updateAll(context);
		}
	}
	
	private void updateAll(Context context){
		updateWidgetTime(context);
		EngineManager.getInstance(context).updateWidget();
	}
	

	@Override
	public void onAppWidgetOptionsChanged(Context context,
			AppWidgetManager appWidgetManager, int appWidgetId,
			Bundle newOptions) {
		super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId,
				newOptions);
		klilog.i("onAppWidgetOptionsChanged");
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
		klilog.i("onDeleted");
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		klilog.i("onUpdate");
		EngineManager.getInstance(context).updateWidget();
		updateWidgetTime(context);
	}
	
	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
		klilog.i("onDisabled");
		removeWidgetUpdateAlarm(context);
	}
	
	private void updateWidgetWeather(Context context, Bundle bundle){
		klilog.i("updateWidgetWeather");
		WidgetViewBuilder builder = getWidgetBuilder(context);
		if(builder == null){
			return;
		}
		builder.updateWeather(true);
		builder.setWeatherData(bundle);
		notifyWidget(context, builder.build());
	}
	
	private void updateWidgetTime(Context context){
		klilog.i("updateWidgetTime");
		WidgetViewBuilder builder = getWidgetBuilder(context);
		if(builder == null){
			return;
		}
		builder.updateTime(true);
		RemoteViews rv = builder.build();
		klilog.i("updateWidgetTime  rv is null?"+(rv == null));
		notifyWidget(context, rv);
	}
	
	private void notifyWidget(Context context, RemoteViews rv){
		if(rv == null){
			return;
		}
		//update widget
		AppWidgetManager awm = AppWidgetManager.getInstance(context);
		int[] appIds = awm.getAppWidgetIds(new ComponentName(context, WidgetProvider.class));
		awm.updateAppWidget(appIds, rv);
	}
	
	private void setWidgetUpdateAlarm(Context context) {
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		mFreshIntent = getUpdateTimePendingIntent(context);
		int interval = 1000 * 60;

		Calendar notify = Calendar.getInstance();
//		notify.add(Calendar.HOUR_OF_DAY, 1);
		notify.add(Calendar.MINUTE, 1);
		notify.set(Calendar.SECOND, 0);
		notify.set(Calendar.MILLISECOND, 0);
		
		am.setRepeating(AlarmManager.RTC, notify.getTimeInMillis(), interval, mFreshIntent);
	}
	
	private void removeWidgetUpdateAlarm(Context context) {
		if(mFreshIntent != null){
			AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			am.cancel(mFreshIntent);
		}
	}
	
	private PendingIntent getUpdateTimePendingIntent(Context context){
		Intent intent = new Intent(ACTION_UPDATE_TIME);
		return  PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
	}
	
	private WidgetViewBuilder getWidgetBuilder(Context context){
		WidgetViewBuilder builder = new WidgetViewBuilder(context, 
				SkinManager.getInstance(context).getCurrentSkin());
		return builder;
	}
	
}
