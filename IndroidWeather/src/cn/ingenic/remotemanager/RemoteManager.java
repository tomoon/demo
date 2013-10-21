package cn.ingenic.remotemanager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class RemoteManager {
	private static RemoteManager sInstance;
	private Context mContext;
	private Object mRequestLock = new Object();
	private RemoteConnection mConnection;
	
	private CommandSender mSender;
	
	private RemoteManager(Context context){
		mContext = context;
		mSender = new CommandSender(mContext);
	}

	public static RemoteManager getInstance(Context context){
		if(sInstance == null){
			sInstance = new RemoteManager(context);
		}
		return sInstance;
	}
	
	public void request(int cmd, final String data){
		mSender.sendCommand(cmd, data);
	}
	
	public void request(int cmd, final String data, final Handler handler) {
		/*
		ComponentName cn = new ComponentName("cn.ingenic.indroidsync",
				"cn.ingenic.indroidsync.devicemanager.DeviceLocalService");
		Intent intent = new Intent();
		intent.setComponent(cn);
		mConnection = new RemoteConnection(cmd, data, handler);

		if (!mContext.bindService(intent, mConnection, Context.BIND_AUTO_CREATE)) {
			klilog.e("bind device local service error!!!!");
			return;
		}*/
	}
	
	public void close(){
//		mContext.unbindService(mConnection);
	}

	private class RemoteConnection implements ServiceConnection{
		int cmd;
		String data;
		Handler handler;
		RemoteConnection(int cmd, String data, Handler handler){
			this.cmd = cmd;
			this.data = data;
			this.handler = handler;
		}

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder service) {
			Messenger msger = new Messenger(service);
			Message msg = new Message();
			msg.what = cmd;
			Bundle bundle = new Bundle();
			bundle.putString("data", data);
			msg.setData(bundle);
			msg.replyTo = new Messenger(handler);
			try {
				msger.send(msg);
				klilog.i("msg send!!!");
			} catch (RemoteException e) {
				klilog.e("request send error");
				e.printStackTrace();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			
		}
		
	}
}
