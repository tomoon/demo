package weathersource.weather.com.cn;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import cn.ingenic.weather.engine.City;
import cn.ingenic.weather.engine.WeatherEngine;
import cn.ingenic.weather.engine.WeatherSource;

public class SourceWeatherComCn implements WeatherSource {
	private final static String WEATHER_URL = "http://m.weather.com.cn/data/xxx.html";
	private final static String CITY_URL = "http://m.weather.com.cn/data5/cityxxx.xml";
	private final static String SOURCE = "weather.com.cn";
	
	private static boolean mIniting = false;
	private Context mContext;
	private DataProxy mDataProxy;
	private InternetAccess mAccess;
	
	public SourceWeatherComCn(Context context){
		mContext = context;
	}
	
	@Override
	public String getSourceName() {
		return SOURCE;
	}

	@Override
	public int onInit() {
		mDataProxy = DataProxy.getInstance(mContext);
		mAccess = InternetAccess.getInstance(mContext);
		
		klilog.i("prepared = "+mDataProxy.getDataPrepared()+", mIniting = "+mIniting);
		if(!mDataProxy.getDataPrepared() &&!mIniting){
			mIniting = true;
			try {
				ArrayList<MyCity> cities = getCitiesByIndex("");
				saveCities(cities);
				mDataProxy.setDataPrepared(true);
			} catch (Exception e) {
				return WeatherEngine.RES_FAIL;
			}
		}else{
			
		}
		return WeatherEngine.RES_SUCCESS;
	}

	private void saveCities(ArrayList<MyCity> cities){
		for(MyCity city : cities){
			mDataProxy.addCity(city);
			if(city.level <= 2){
				ArrayList<MyCity> childCities = getCitiesByIndex(city.index);
				if(city.level == 2){
					checkCodeByCity(childCities);
				}
				saveCities(childCities);
			}
		}
	}
	
	private ArrayList<MyCity> getCitiesByIndex(String index){
		if(index == null){
			index = "";
		}
		String url = CITY_URL.replace("xxx", index);
		ArrayList<MyCity> cities = new ArrayList<MyCity>();
		String response = mAccess.request(url);
		klilog.i("response = "+response);
		if(!TextUtils.isEmpty(response)){
			cities = CityParser.parser(response);
		}else{
			klilog.i("error respose null");
			throw new NullPointerException();
		}
		return cities;
	}
	
	private void checkCodeByCity(ArrayList<MyCity> cities){
		if(cities == null){
			return;
		}
		for(MyCity city : cities){
			String url = CITY_URL.replace("xxx",city.index);
			String response = mAccess.request(url);
			klilog.i("response = "+response);
			if(!TextUtils.isEmpty(response)){
				city.code = response.split("\\|")[1];
			}else{
				klilog.i("error respose null");
				throw new NullPointerException();
			}
		}
	}

	/**
	 * Must be called in thread.
	 */
	@Override
	public City getWeatherByIndex(String index) {
		MyCity city = mDataProxy.getCityByIndex(index);
		String url = WEATHER_URL.replace("xxx", city.code);
		String response = mAccess.request(url);
		klilog.i("response = "+response);
		if(!TextUtils.isEmpty(response)){
			city = WeatherParser.parser(city, response);
			klilog.i("finish");
		}else{
			klilog.i("error respose null");
			throw new NullPointerException();
		}
		return city;
	}

	@Override
	public List<City> getCityList(City city) {
		return mDataProxy.getCityList(city);
	}

    @Override
    public City getCityByIndex(String index) {
        // TODO Auto-generated method stub
        return null;
    }
}
