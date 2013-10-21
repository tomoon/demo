package weathersource.weather.com.cn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.json.JSONException;
import org.json.JSONObject;

import cn.ingenic.weather.engine.Weather;

public class WeatherParser {
	
	public static MyCity parser(MyCity city, String source){
		try {
			JSONObject jsonObject = new JSONObject(source).getJSONObject("weatherinfo");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyƒÍMM‘¬dd»’");
			String date_y = jsonObject.getString("date_y");
			Calendar calendar = null;
			try {
				Date date = sdf.parse(date_y);
				calendar = new GregorianCalendar();
				calendar.setTime(date);
			} catch (ParseException e) {
				return null;
			}

			ArrayList<Weather> weathers = new ArrayList<Weather>();
			//get 'days' weather from today
			int days = 3;
			for(int i = 1; i <= days; i++){
				Weather weather = new Weather();
				Calendar cal = (Calendar) calendar.clone();
				cal.add(Calendar.DAY_OF_MONTH, i - 1);
				weather.calendar = cal;
				if(i == 1){
					weather.currentTemp = jsonObject.getString("fchh");
				}
				String[] tempRange = jsonObject.getString("temp"+i).split("~");
				weather.maxTemp = tempRange[0];
				weather.minTemp = tempRange[1];
				weather.weather = jsonObject.getString("weather"+i);
				weather.wind = jsonObject.getString("wind"+i);
				weathers.add(weather);
			}
			
			city.weather = weathers;
		} catch (JSONException e) {
			city = null;
			e.printStackTrace();
		}
		
		return city;
	}
}
