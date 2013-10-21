package cn.ingenic.weather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AppChangedReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		/*
		String action = intent.getAction();
		if(Intent.ACTION_PACKAGE_ADDED.equals(action)){
			
		}else if(Intent.ACTION_PACKAGE_REMOVED.equals(action)){
			
		}
		*/
		SkinManager skinManager = SkinManager.getInstance(context);
		skinManager.freshSkinList();
		
		if(!skinManager.isCurrentSkinEnable()){
			skinManager.setCurrentSkinPkg(null);
		}
	}

}
