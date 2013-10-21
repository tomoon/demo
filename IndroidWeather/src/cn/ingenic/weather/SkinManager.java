package cn.ingenic.weather;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.text.TextUtils;

public class SkinManager {
	private final static String SKIN_PREF = "skin_pref";
	private final static String CURRENT_SKIN = "current_skin";
	
	private Context mContext;
	private static SkinManager sInstance;
	private ArrayList<Skin> mSkinList = new ArrayList<Skin>();
	
	private SkinManager(Context context){
		mContext = context;
	}
	
	public static SkinManager getInstance(Context context){
		if(sInstance == null){
			sInstance = new SkinManager(context);
		}
		return sInstance;
	}
	
	public Skin getCurrentSkin(){
		return new Skin(mContext, getCurrentSkinPkg());
	}
	
	public void freshSkinList(){
		mSkinList.clear();
		//add this package
		mSkinList.add(new Skin(mContext, mContext.getPackageName()));
		
		List<PackageInfo> packs = mContext.getPackageManager()
				.getInstalledPackages(0);
		
		for (PackageInfo p : packs) {
			if (Skin.isSkinPackage(p.packageName)) {
				mSkinList.add(new Skin(mContext, p.packageName));
			}
		}
	}
	
	public ArrayList<Skin> getSkins(){
		if(mSkinList == null || mSkinList.size() == 0){
			freshSkinList();
		}
		return mSkinList;
	}
	
	public boolean isCurrentSkinEnable(){
		List<PackageInfo> packs = mContext.getPackageManager()
				.getInstalledPackages(0);
		
		for (PackageInfo p : packs) {
			if (Skin.isSkinPackage(p.packageName)) {
				return true;
			}
		}
		return false;
	}
	
	public void setCurrentSkinPkg(String pkg){
		if(TextUtils.isEmpty(pkg)){
			pkg = mContext.getPackageName();
		}
		SharedPreferences prefs = mContext.getSharedPreferences(SKIN_PREF, 0);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(CURRENT_SKIN, pkg);
		editor.commit();
		
		//notify widget
		Intent intent = new Intent(WidgetProvider.ACTION_UPDATE_SKIN);
		mContext.sendBroadcast(intent);
	}
	
	private String getCurrentSkinPkg(){
		SharedPreferences prefs = mContext.getSharedPreferences(SKIN_PREF, 0);
		return prefs.getString(CURRENT_SKIN, null);
	}
}
