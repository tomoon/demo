package weathersource.weather.com.cn;

import cn.ingenic.weather.engine.City;

public class MyCity extends City {
	//be used to query weather
	public int level;
	public String code;

	MyCity(){
		
	}
	
	MyCity(City city){
		name = city.name;
		index = city.index;
		weather = city.weather;
	}
	
	void setIndex(String _index){
		index = _index;
		level = index.length() / 2;
	}
	
}
