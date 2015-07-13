package com.sskgg.gg.fragment;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.RosterEntry;

import com.sskgg.gg.MessageActivity;
import com.sskgg.gg.R;
import com.sskgg.gg.MessageActivity.MessageInfoReceive;
import com.sskgg.gg.adapter.MessageListAdapter;
import com.sskgg.gg.entity.MessageList;
import com.sskgg.gg.utils.DatabaseHelper;
import com.sskgg.gg.utils.LogUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 消息列表页面
 * @author 杀死凯 QQ565204031
 *
 */
public class MessageFragment extends Fragment implements OnItemClickListener {

	private List<RosterEntry> data;
	private List<MessageList> datemsg;
	private List<MessageList> adatepterdate;
	private View v;
	private ListView listview;
	private MessageListAdapter adapter;
	private Intent intent;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v=inflater.inflate(R.layout.messagelist_fragment, null);
		listview=(ListView) v.findViewById(R.id.listview);
		adapter=new MessageListAdapter(getActivity());
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
		registerBroadcastReceiver();
		initdate();
		return v;
	}
	public void setData(List<RosterEntry> data){
		this.data=data;
	}
	public void initdate(){
		datemsg=DatabaseHelper.queryLastBodyByUsernameAndTo(getActivity());
		adatepterdate=new ArrayList<MessageList>();
		//数据库和服务器对比数据
		for(MessageList info:datemsg)
		{
			for(RosterEntry re:this.data)
			{
				LogUtils.i("INFO", re.getUser());
				if(info.getUsername().equals(re.getUser().split("@")[0]))
				{
					adatepterdate.add(info);
				}
			}
		}
		adapter.data=adatepterdate;
		adapter.notifyDataSetInvalidated();
	}
	 /** 
     * 注册广播 
     */  
    private void registerBroadcastReceiver(){  
    	MessageInfoReceive receiver = new MessageInfoReceive();  
        IntentFilter filter = new IntentFilter("com.sskgg.gg.messageadd");  
        filter.setPriority(0);
        getActivity().registerReceiver(receiver, filter);  
    }
	public class MessageInfoReceive extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// 收到广播，操作数据库，拿出数据
			initdate();
		}
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		intent=new Intent(getActivity(), MessageActivity.class);
		intent.putExtra("user",  adapter.data.get(position).getUsername());
		startActivity(intent);
	}
	
}
