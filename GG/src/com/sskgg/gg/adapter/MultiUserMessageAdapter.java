package com.sskgg.gg.adapter;

import java.util.ArrayList;
import java.util.List;


import com.sskgg.gg.R;
import com.sskgg.gg.entity.MultiUserMessageInfo;
import com.sskgg.gg.utils.LogUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 会议室聊天窗口
 * @author 杀死凯 QQ565204031
 *
 */
public class MultiUserMessageAdapter extends BaseAdapter{

	public List<MultiUserMessageInfo> data; 
	private Context mContext;
	public MultiUserMessageAdapter(Context ct)
	{
		mContext=ct;
		data=new ArrayList<MultiUserMessageInfo>();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHandle viewhandle;
		if(convertView==null)
		{
			viewhandle=new ViewHandle();
			convertView=LayoutInflater.from(mContext).inflate(R.layout.multiusermessage_tiem, null);
			viewhandle.tv_content=(TextView) convertView.findViewById(R.id.tv_content);
			viewhandle.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
			viewhandle.iv_portrait=(ImageView) convertView.findViewById(R.id.iv_portrait);
			convertView.setTag(viewhandle);
		}else
		{
			viewhandle=(ViewHandle) convertView.getTag();
		}
		
		MultiUserMessageInfo info=data.get(position);
		viewhandle.tv_content.setText(info.getBody());
		viewhandle.tv_name.setText(info.getFrom());
		LogUtils.i("INFO", info.getBody());
		return convertView;
	}
	private class ViewHandle {
        TextView tv_content;
        TextView tv_name;
        ImageView iv_portrait;
	}

}
