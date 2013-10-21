package weathersource.weather.com.cn;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;

class InternetAccess {
	private static final String ENCODE="UTF-8";
	private static InternetAccess sInstance;
	private HttpClient mHttpClient;
	
	private Context mContext;
	
	private InternetAccess(Context context){
		mContext = context;
		mHttpClient = new DefaultHttpClient();
	}
	
	public static InternetAccess getInstance(Context context){
		if(sInstance == null){
			sInstance = new InternetAccess(context);
		}
		return sInstance;
	}
	
	synchronized public String request(String url){
		HttpGet get = new HttpGet(url);
		String result = null;
		klilog.i("Internet request: " +url);
		try {
			HttpResponse response = mHttpClient.execute(get);
			if(response.getStatusLine().getStatusCode() == 200){
				result = EntityUtils.toString(response.getEntity(), ENCODE);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
