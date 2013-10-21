package weathersource.webxml.com.cn;

import cn.ingenic.weather.engine.City;

public class MyCity extends City {
	//be used to query weather
	public String parent;

	MyCity(){
		
	}
	
	MyCity(City city){
		name = city.name;
		index = city.index;
		weather = city.weather;
	}
	
}
