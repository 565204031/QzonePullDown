package com.example.qzonepull;

import com.example.qzonepull.view.ParallaxScrollListView;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private ParallaxScrollListView mParallaxScrollListView;
	private Context mContext;
	private ImageView iv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext=this;
		initdata();
	}
	private void initdata() {
		// TODO Auto-generated method stub
		
		mParallaxScrollListView=(ParallaxScrollListView) findViewById(R.id.listview);
		View view=View.inflate(mContext, R.layout.listview_header, null);
		iv=(ImageView) view.findViewById(R.id.header_img);
		mParallaxScrollListView.addHeaderView(view);
		mParallaxScrollListView.setParallaxImageView(iv);
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,new String[]{
				"item1",
				"item2",
				"item3",
				"item4",
				"item5",
				"item6",
				"item7",
				"item8",
				"item9",
				"item10"
		});
		mParallaxScrollListView.setAdapter(adapter);
	}
	//当布局全部显示完成回调
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if(hasFocus)
		{
			//设置mListView里面的ImageView高度
			mParallaxScrollListView.setViewsBounds(4);//设置缩放级别
		}
	}
}
