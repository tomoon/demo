package weathersource.webxml.com.cn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import cn.ingenic.weather.engine.City;

class CityDbHelper extends SQLiteOpenHelper {
	
	//database
	static final String DB_NAME = "webxml.com.cn.database";
	private static final int DB_VERSION = 1;
	
	//table city
	private static final String TABLE_CITY = "city";
	public static final String CITY_NAME = "name";
	public static final String CITY_PARENT = "parent";
	public static final String CITY_INDEX = "_index";
	
	private Context mContext;
	
	public CityDbHelper(Context context){
		this(context, DB_NAME, null, DB_VERSION);
	}

	private CityDbHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createTableCity = "create table " + TABLE_CITY+"("+
				CITY_NAME + " text,"+
				CITY_PARENT + " text,"+
				CITY_INDEX + " text)";
		klilog.i(createTableCity);
		db.execSQL(createTableCity);
		
	}

      
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}
	
	public void addCity(MyCity city){
		SQLiteDatabase db = getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(CITY_NAME, city.name);
		cv.put(CITY_INDEX, city.index);
		cv.put(CITY_PARENT, city.parent);
		db.insert(TABLE_CITY, null, cv);
	}

	public MyCity getCityByIndex(String index){
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(TABLE_CITY, 
				null,
				CITY_INDEX+"=?", 
				new String[]{index}, 
				null, null, null);

		cursor.moveToFirst();
		
        MyCity city = getMyCity(cursor,Locale.getDefault().getLanguage().startsWith("zh"));
//		city.name = cursor.getString(cursor.getColumnIndex(CITY_NAME));
//		city.index = cursor.getString(cursor.getColumnIndex(CITY_INDEX));
//		city.parent = cursor.getString(cursor.getColumnIndex(CITY_PARENT));
//		city=getMyCity(cursor);
		cursor.close();
		return city;
	}

	public List<City> getCityList(City city){
		List<City> list = new ArrayList<City>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = null;
		
		//get cursor by query the database
		if(city == null){
			cursor = db.query(TABLE_CITY, 
					null,
					CITY_PARENT+" is null", 
					null, 
					null, null, null);
		}else{
			cursor = db.query(TABLE_CITY, 
					null,
					CITY_PARENT+"=?", 
					new String[]{city.index}, 
					null, null, null);
		}
		
		//translate cursor to list
		list = getCityFromCursor(cursor);
		cursor.close();
		return list;
	}
	
	public String getCityName(String index){
	    Cursor c=getReadableDatabase().query(TABLE_CITY, new String[]{"name","_name"}, "_index="+index, null, null, null, null);
	    c.moveToFirst();
	    String name=c.getString(Locale.getDefault().getLanguage().startsWith("zh")?0:1);
	    c.close();
	    return name;
	}
	
	private List<City> getCityFromCursor(Cursor cursor){
		List<City> list = new ArrayList<City>();
		boolean isZh=Locale.getDefault().getLanguage().startsWith("zh");
		if(cursor.moveToFirst()){
			do{
//				MyCity city = new MyCity();
//				city.name = cursor.getString(cursor.getColumnIndex(CITY_NAME));
//				city.index = cursor.getString(cursor.getColumnIndex(CITY_INDEX));
//				city.parent = cursor.getString(cursor.getColumnIndex(CITY_PARENT));
				list.add(getMyCity(cursor,isZh));
			}while(cursor.moveToNext());
		}
		return list;
	}
	
	private MyCity getMyCity(Cursor cursor,boolean isZh){
	    MyCity city = new MyCity();
        city.name = cursor.getString(cursor.getColumnIndex(isZh?CITY_NAME:"_name"));
        city.index = cursor.getString(cursor.getColumnIndex(CITY_INDEX));
        city.parent = cursor.getString(cursor.getColumnIndex(CITY_PARENT));
        return city;
	}
}
