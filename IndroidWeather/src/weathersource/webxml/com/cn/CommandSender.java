package weathersource.webxml.com.cn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

public class CommandSender {
	private Context mContext;
	
	public CommandSender(Context context){
		mContext = context;
	}
	
	public void sendCommand(int cmd, String data){
		Message msg = new Message();
		msg.what = cmd;
		Bundle bundle = new Bundle();
		bundle.putString("data", data);
		msg.setData(bundle);
		klilog.i("CommandSender data = "+data);
		
		Intent intent = new Intent();
		intent.setAction("cn.ingenic.action.weather");
		intent.putExtra("app", msg);
		mContext.sendBroadcast(intent);

		klilog.i("CommandSender broadcast send");
	}
}
