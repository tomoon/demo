package cn.ingenic.weather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class StyleChooseActivity extends Activity implements OnItemClickListener {
	private GalleryFlow mGallery;
	private StyleAdapter mAdatper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_style_activity);
		//init data
		mAdatper = new StyleAdapter(this, SkinManager.getInstance(this).getSkins());
		
		//init views
		mGallery = (GalleryFlow)findViewById(R.id.gf_style_gallery);
		mGallery.setFadingEdgeLength(0);
		mGallery.setSpacing(-40); //图片之间的间距
		mGallery.setAdapter(mAdatper);
        
		mGallery.setOnItemClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		SkinManager.getInstance(this).setCurrentSkinPkg(mAdatper.getItem(arg2).getPackageName());
		finish();
	}
	
}
