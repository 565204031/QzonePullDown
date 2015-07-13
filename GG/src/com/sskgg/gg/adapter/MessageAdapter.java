package com.sskgg.gg.adapter;

import java.util.List;

import com.sskgg.gg.R;
import com.sskgg.gg.entity.MessageInfo;
import com.sskgg.gg.utils.LogUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 聊天窗口
 * @author 杀死凯 QQ565204031
 *
 */
public class MessageAdapter extends BaseAdapter{

	public List<MessageInfo> data;
	private Context mContext;
	public MessageAdapter(Context ct)
	{
		mContext=ct;
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
		return data.get(position).getId();
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHandle viewhandle;
		if(convertView==null)
		{
			viewhandle=new ViewHandle();
			convertView=LayoutInflater.from(mContext).inflate(R.layout.message_tiem, null);
			viewhandle.tv_content=(TextView) convertView.findViewById(R.id.tv_content);
			viewhandle.iv_portrait=(ImageView) convertView.findViewById(R.id.iv_portrait);
			convertView.setTag(viewhandle);
		}else
		{
			viewhandle=(ViewHandle) convertView.getTag();
		}
		
		MessageInfo info=data.get(position);
		viewhandle.tv_content.setText(info.getBody());
		LogUtils.i("INFO", info.getBody());
		return convertView;
	}
	private class ViewHandle {
        TextView tv_content;
        ImageView iv_portrait;
	}

}
