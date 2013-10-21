package cn.ingenic.weather.engine;

import java.util.List;

import weathersource.weather.com.cn.SourceWeatherComCn;
import weathersource.webxml.com.cn.SourceWebXml;
import android.content.Context;

public class WeatherEngine {
	public final static int RES_SUCCESS = 0;
	public final static int RES_FAIL = 1;
	
	private final static String WEATHER_COM_CN = "��������̨";
	private final static String WEB_XML = "WebXml";
	
	private final static String[] SOURCE_LIST = {WEB_XML, WEATHER_COM_CN};
	
	private static WeatherEngine mInstance;
	private Context mContext;
	private WeatherSource mSource;
	
	private WeatherEngine(Context context){
		mContext = context;
	}
	
	public static WeatherEngine getInstance(Context context){
		if(mInstance == null){
			mInstance = new WeatherEngine(context);
		}
		return mInstance;
	}
	
	
	public String[] getSourceList(){
		return SOURCE_LIST;
	}
	
	private WeatherSource getSourceByName(String name){
		WeatherSource res;
		if(WEATHER_COM_CN.equals(name)){
			res = new SourceWeatherComCn(mContext);
		}else if(WEB_XML.equals(name)){
			res = new SourceWebXml(mContext);
		}else{
			//default
			res = new SourceWebXml(mContext);
		}
		return res;
	}
	
	/**
	 * Must be called in thread.
	 * Get city datas from internet and write to database.
	 */
	synchronized public int init(String source){
		klilog.i("init source = "+source);
		mSource = getSourceByName(source);
		return mSource.onInit();
	}

	/**
	 * Must be called in thread.
	 * Get city weather from internet.
	 */
	public City getWeatherByIndex(String index){
		City city = mSource.getWeatherByIndex(index);
		city.updateTime = System.currentTimeMillis();
		return city;
	}
	
	public List<City> getCityList(City city){
		return mSource.getCityList(city);
	}
	
	public City getCityByIndex(String index){
	    return mSource.getCityByIndex(index);
	}
}
