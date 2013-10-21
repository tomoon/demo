package cn.ingenic.weather;

import cn.ingenic.weather.engine.Weather;
import android.content.Context;

public class WeatherUtils {

	public static int getLDrawable(String weather) {
		int res = R.drawable.l_nothing;
		try {
			int weatherId = Integer.parseInt(weather.split(",")[0]);
			res = getLDrawable(weatherId);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public static int getMDrawable(String weather) {
		int res = R.drawable.l_nothing;
		try {
			int weatherId = Integer.parseInt(weather.split(",")[0]);
			res = getMDrawable(weatherId);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public static int getHDrawable(String weather) {
		int res = R.drawable.l_nothing;
		try {
			int weatherId = Integer.parseInt(weather.split(",")[0]);
			res = getHDrawable(weatherId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public static int getHDrawable(int weather) {
		int res = R.drawable.a_nothing;
		switch(weather){
		case 0:
			res = R.drawable.a_0;
			break;
		case 1:
			res = R.drawable.a_1;
			break;
		case 2:
			res = R.drawable.a_2;
			break;
		case 3:
			res = R.drawable.a_3;
			break;
		case 4:
			res = R.drawable.a_4;
			break;
		case 5:
			res = R.drawable.a_5;
			break;
		case 6:
			res = R.drawable.a_6;
			break;
		case 7:
			res = R.drawable.a_7;
			break;
		case 8:
			res = R.drawable.a_8;
			break;
		case 9:
			res = R.drawable.a_9;
			break;
		case 10:
			res = R.drawable.a_10;
			break;
		case 11:
			res = R.drawable.a_11;
			break;
		case 12:
			res = R.drawable.a_12;
			break;
		case 13:
			res = R.drawable.a_13;
			break;
		case 14:
			res = R.drawable.a_14;
			break;
		case 15:
			res = R.drawable.a_15;
			break;
		case 16:
			res = R.drawable.a_16;
			break;
		case 17:
			res = R.drawable.a_17;
			break;
		case 18:
			res = R.drawable.a_18;
			break;
		case 19:
			res = R.drawable.a_19;
			break;
		case 20:
			res = R.drawable.a_20;
			break;
		case 21:
			res = R.drawable.a_21;
			break;
		case 22:
			res = R.drawable.a_22;
			break;
		case 23:
			res = R.drawable.a_23;
			break;
		case 24:
			res = R.drawable.a_24;
			break;
		case 25:
			res = R.drawable.a_25;
			break;
		case 26:
			res = R.drawable.a_26;
			break;
		case 27:
			res = R.drawable.a_27;
			break;
		case 28:
			res = R.drawable.a_28;
			break;
		case 29:
			res = R.drawable.a_29;
			break;
		case 30:
			res = R.drawable.a_30;
			break;
		case 31:
			res = R.drawable.a_31;
			break;
		}
		return res;
	}

	public static int getMDrawable(int weather) {
		int res = R.drawable.b_nothing;
		switch(weather){
		case 0:
			res = R.drawable.b_0;
			break;
		case 1:
			res = R.drawable.b_1;
			break;
		case 2:
			res = R.drawable.b_2;
			break;
		case 3:
			res = R.drawable.b_3;
			break;
		case 4:
			res = R.drawable.b_4;
			break;
		case 5:
			res = R.drawable.b_5;
			break;
		case 6:
			res = R.drawable.b_6;
			break;
		case 7:
			res = R.drawable.b_7;
			break;
		case 8:
			res = R.drawable.b_8;
			break;
		case 9:
			res = R.drawable.b_9;
			break;
		case 10:
			res = R.drawable.b_10;
			break;
		case 11:
			res = R.drawable.b_11;
			break;
		case 12:
			res = R.drawable.b_12;
			break;
		case 13:
			res = R.drawable.b_13;
			break;
		case 14:
			res = R.drawable.b_14;
			break;
		case 15:
			res = R.drawable.b_15;
			break;
		case 16:
			res = R.drawable.b_16;
			break;
		case 17:
			res = R.drawable.b_17;
			break;
		case 18:
			res = R.drawable.b_18;
			break;
		case 19:
			res = R.drawable.b_19;
			break;
		case 20:
			res = R.drawable.b_20;
			break;
		case 21:
			res = R.drawable.b_21;
			break;
		case 22:
			res = R.drawable.b_22;
			break;
		case 23:
			res = R.drawable.b_23;
			break;
		case 24:
			res = R.drawable.b_24;
			break;
		case 25:
			res = R.drawable.b_25;
			break;
		case 26:
			res = R.drawable.b_26;
			break;
		case 27:
			res = R.drawable.b_27;
			break;
		case 28:
			res = R.drawable.b_28;
			break;
		case 29:
			res = R.drawable.b_29;
			break;
		case 30:
			res = R.drawable.b_30;
			break;
		case 31:
			res = R.drawable.b_31;
			break;
		}
		return res;
	}
	
	public static int getLDrawable(int weather) {
		int res = R.drawable.l_nothing;
		switch(weather){
		case 0:
			res = R.drawable.l_0;
			break;
		case 1:
			res = R.drawable.l_1;
			break;
		case 2:
			res = R.drawable.l_2;
			break;
		case 3:
			res = R.drawable.l_3;
			break;
		case 4:
			res = R.drawable.l_4;
			break;
		case 5:
			res = R.drawable.l_5;
			break;
		case 6:
			res = R.drawable.l_6;
			break;
		case 7:
			res = R.drawable.l_7;
			break;
		case 8:
			res = R.drawable.l_8;
			break;
		case 9:
			res = R.drawable.l_9;
			break;
		case 10:
			res = R.drawable.l_10;
			break;
		case 11:
			res = R.drawable.l_11;
			break;
		case 12:
			res = R.drawable.l_12;
			break;
		case 13:
			res = R.drawable.l_13;
			break;
		case 14:
			res = R.drawable.l_14;
			break;
		case 15:
			res = R.drawable.l_15;
			break;
		case 16:
			res = R.drawable.l_16;
			break;
		case 17:
			res = R.drawable.l_17;
			break;
		case 18:
			res = R.drawable.l_18;
			break;
		case 19:
			res = R.drawable.l_19;
			break;
		case 20:
			res = R.drawable.l_20;
			break;
		case 21:
			res = R.drawable.l_21;
			break;
		case 22:
			res = R.drawable.l_22;
			break;
		case 23:
			res = R.drawable.l_23;
			break;
		case 24:
			res = R.drawable.l_24;
			break;
		case 25:
			res = R.drawable.l_25;
			break;
		case 26:
			res = R.drawable.l_26;
			break;
		case 27:
			res = R.drawable.l_27;
			break;
		case 28:
			res = R.drawable.l_28;
			break;
		case 29:
			res = R.drawable.l_29;
			break;
		case 30:
			res = R.drawable.l_30;
			break;
		case 31:
			res = R.drawable.l_31;
			break;
		}
		return res;
	}
	
	public static String getWeather(Context context, String weather){
		String res = context.getString(R.string.error);
		String[] weathers = weather.split(",");
		if(weathers.length == 2){
			if(weathers[0].equals(weathers[1])){
				res = context.getString(getName(Integer.parseInt(weathers[0])));
			}else{
				res = context.getString(getName(Integer.parseInt(weathers[0])))
						+ context.getString(R.string.weather_to)
						+ context.getString(getName(Integer.parseInt(weathers[1])));
			}
		}
		return res;
	}
	
	public static boolean isBadWeather(Weather weather){
		boolean res = true;
		String[] weathers = weather.weather.split(",");
		int weatherFrom = Integer.parseInt(weathers[0]);
		int weatherTo = Integer.parseInt(weathers[1]);
		if(weatherFrom == weatherTo){
			res = isBadWeather(weatherFrom);
		}else{
			res = isBadWeather(weatherFrom) | isBadWeather(weatherTo);
		}
		return res;
	}
	
	private static boolean isBadWeather(int weather){
		boolean res = true;
		switch(weather){
		case 0:
		case 1:
		case 2:
		case 18:
			res = false;
			break;
		}
		return res;
	}
	
	public static int getName(int weather){
		int res = R.string.weather_nothing;
		switch(weather){
		case 0:
			res = R.string.weather_q;
			break;
		case 1:
			res = R.string.weather_dyun;
			break;
		case 2:
			res = R.string.weather_y;
			break;
		case 3:
			res = R.string.weather_zhy;
			break;
		case 4:
			res = R.string.weather_lzhy;
			break;
		case 5:
			res = R.string.weather_lzhybbybb;
			break;
		case 6:
			res = R.string.weather_yjx;
			break;
		case 7:
			res = R.string.weather_xy;
			break;
		case 8:
			res = R.string.weather_zhy;
			break;
		case 9:
			res = R.string.weather_dy;
			break;
		case 10:
			res = R.string.weather_by;
			break;
		case 11:
			res = R.string.weather_dby;
			break;
		case 12:
			res = R.string.weather_tdby;
			break;
		case 13:
			res = R.string.weather_zhx;
			break;
		case 14:
			res = R.string.weather_xx;
			break;
		case 15:
			res = R.string.weather_zhongx;
			break;
		case 16:
			res = R.string.weather_dx;
			break;
		case 17:
			res = R.string.weather_bx;
			break;
		case 18:
			res = R.string.weather_w;
			break;
		case 19:
			res = R.string.weather_dy;
			break;
		case 20:
			res = R.string.weather_schb;
			break;
		case 21:
			res = R.string.weather_xyzhy;
			break;
		case 22:
			res = R.string.weather_zhydy;
			break;
		case 23:
			res = R.string.weather_dyby;
			break;
		case 24:
			res = R.string.weather_bydby;
			break;
		case 25:
			res = R.string.weather_dbytdby;
			break;
		case 26:
			res = R.string.weather_xxzhx;
			break;
		case 27:
			res = R.string.weather_zhxdx;
			break;
		case 28:
			res = R.string.weather_dxbx;
			break;
		case 29:
			res = R.string.weather_fch;
			break;
		case 30:
			res = R.string.weather_ysh;
			break;
		case 31:
			res = R.string.weather_qshchb;
			break;
		}
		return res;
	}
	
}
