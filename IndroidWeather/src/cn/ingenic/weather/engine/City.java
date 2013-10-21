package cn.ingenic.weather.engine;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class City implements Parcelable{
	public String name;
	public String index;
	public ArrayList<Weather> weather;
	public long updateTime;

	public static final Parcelable.Creator<City> CREATOR = new Parcelable.Creator<City>() {
		public City createFromParcel(Parcel in) {
			City city = new City();
			if (in.readInt() == 1) {
				city.name = in.readString();
			}
			
			if(in.readInt() == 1){
				city.index = in.readString();
			}
			
			if(in.readInt() == 1){
				city.weather = (ArrayList<Weather>) in.readSerializable();
			}
			
			city.updateTime = in.readLong();

			return city;
		}

		public City[] newArray(int size) {
			return new City[size];
		}
	};
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int arg1) {

		//name
		if(name == null){
			parcel.writeInt(0);
		}else{
			parcel.writeInt(1);
			parcel.writeString(name);
		}
		
		//index
		if(index == null){
			parcel.writeInt(0);
		}else{
			parcel.writeInt(1);
			parcel.writeString(index);
		}
		
		//weathers
		if(weather == null){
			parcel.writeInt(0);
		}else{
			parcel.writeInt(1);
			parcel.writeSerializable(weather);
		}
		
		parcel.writeLong(updateTime);
	}
	

	@Override
	public String toString() {
		return "name:"+name+
				", index:"+index+
				"; ";
	}
}
