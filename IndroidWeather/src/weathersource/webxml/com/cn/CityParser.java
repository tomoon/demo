package weathersource.webxml.com.cn;

import java.util.ArrayList;
import java.util.List;

public class CityParser {

	public static ArrayList<MyCity> parser(String res, String parentIndex){
		ArrayList<MyCity> cities = new ArrayList<MyCity>();
		List<String> infos = new StringArrayXmlParser(res).parser();
		for(String item : infos){
			String[] values = item.split(",");
			MyCity city = new MyCity();
			city.name = values[0];
			city.index = values[1];
			city.parent = parentIndex;
			cities.add(city);
		}
		return cities;
	}
	
	public static ArrayList<MyCity> parser(String res){
		return parser(res, null);
	}

}
