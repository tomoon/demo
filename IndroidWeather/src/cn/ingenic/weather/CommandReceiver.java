package cn.ingenic.weather;

import weathersource.webxml.com.cn.InternetAccess;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

public class CommandReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		InternetAccess.onInternetResponse(bundle.getInt("cmd"), bundle.getString("data"));
		Long.valueOf("1111");
		SystemClock.currentThreadTimeMillis();
	}

}
