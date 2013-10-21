package cn.ingenic.remotemanager;

import android.content.Context;
import android.content.Intent;

public class CommandSender {
	private Context mContext;
	
	public CommandSender(Context context){
		mContext = context;
	}
	
	public void sendCommand(int cmd, String data){
		Intent intent = new Intent();
		intent.setAction(Commands.ACTION_SYNC);
		intent.putExtra("cmd", cmd);
		intent.putExtra("data", data);
		mContext.sendBroadcast(intent);

		klilog.i("CommandSender broadcast send. cmd:"+cmd+", data:"+data);
	}
}
