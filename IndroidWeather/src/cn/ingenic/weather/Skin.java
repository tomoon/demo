package cn.ingenic.weather;

import java.lang.reflect.Field;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

class Skin {
	class id{
		static final String tv_city = "tv_city";
		static final String tv_weather = "tv_weather";
		static final String tv_current = "tv_current";
		static final String tv_min = "tv_min";
		static final String tv_max = "tv_max";
		static final String tv_min_to_max = "tv_min_to_max";
		static final String iv_icon = "iv_icon";
		static final String iv_time_1 = "iv_time_1";
		static final String iv_time_2 = "iv_time_2";
		static final String iv_time_3 = "iv_time_3";
		static final String iv_time_4 = "iv_time_4";
		static final String iv_time_apm = "iv_time_apm";
		static final String tv_week = "tv_week";
		static final String tv_date = "tv_date";
	}
	
	class layout{
		static final String widget_layout = "widget_layout";
	}
	
	class drawable{
		static final String n0 = "n0";
		static final String n1 = "n1";
		static final String n2 = "n2";
		static final String n3 = "n3";
		static final String n4 = "n4";
		static final String n5 = "n5";
		static final String n6 = "n6";
		static final String n7 = "n7";
		static final String n8 = "n8";
		static final String n9 = "n9";
		static final String am = "am";
		static final String pm = "pm";
		static final String widget_preview = "widget_preview";
	}
	
	final static String[] NUMBERS = {drawable.n0, drawable.n1, drawable.n2, drawable.n3, 
		drawable.n4, drawable.n5, drawable.n6, drawable.n7, drawable.n8,
		drawable.n9, drawable.am, drawable.pm};

	Context mContext;
	Context mRemoteContext;
	
	private boolean useDefault = false;

	Skin(Context context, String pkg) {
		mContext = context;
		if(TextUtils.isEmpty(pkg) || context.getPackageName().equals(pkg)){
			useDefault = true;
			mRemoteContext = mContext;
		}else{
			try {
				mRemoteContext = mContext.createPackageContext(pkg,
						Context.CONTEXT_IGNORE_SECURITY
								| Context.CONTEXT_INCLUDE_CODE);
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	String getPackageName(){
		return mRemoteContext.getPackageName();
	}

	
	int getId(String resName) throws ClassNotFoundException,
			IllegalArgumentException, IllegalAccessException {
		Class<?> c = mRemoteContext.getClassLoader().loadClass(
				mRemoteContext.getPackageName() + ".R");
		Class<?>[] cl = c.getClasses();
		int id = 0;
		for (int i = 0; i < cl.length; i++) {
			Field field[] = cl[i].getFields();
			for (int j = 0; j < field.length; j++) {
				if (field[j].getName().equals(resName)) {
					id = field[j].getInt(field[j].getName());
//					klilog.i("resName:"+resName+", id:"+id);
					return id;
				}
			}
		}
		return id;
	}
	
	public Bitmap getPreview(){
		Bitmap bitmap = null;
		try {
			BitmapDrawable bd;
			bd = (BitmapDrawable)mRemoteContext.getResources().getDrawable(getId(drawable.widget_preview));
			bitmap = bd.getBitmap();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//TODO:if can't find preview, return a default one
		return bitmap;
	}
	
	public static boolean isSkinPackage(String packageName) {
		if (packageName.startsWith("cn.ingenic.weather.skin.")) {
			return true;
		}
		return false;
	}
}
