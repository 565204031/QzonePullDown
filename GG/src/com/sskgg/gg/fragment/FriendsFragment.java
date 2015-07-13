package com.sskgg.gg.fragment;

import java.util.List;

import org.jivesoftware.smack.RosterEntry;

import com.sskgg.gg.MessageActivity;
import com.sskgg.gg.R;
import com.sskgg.gg.adapter.FriendsAdapter;
import com.sskgg.gg.fragment.MessageFragment.MessageInfoReceive;
import com.sskgg.gg.listener.RosterStatusListener;
import com.sskgg.gg.utils.LogUtils;
import com.sskgg.gg.utils.XmppConnection;

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

public class FriendsFragment extends Fragment implements OnItemClickListener{

	private View v;
	private ListView listview;
	private List<RosterEntry> data;
	private FriendsAdapter adapter;
	private Intent intent;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v=inflater.inflate(R.layout.friends_fragment, null);
		listview=(ListView) v.findViewById(R.id.listview);
		adapter=new FriendsAdapter(getActivity());
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
		adapter.data=data;
		adapter.notifyDataSetInvalidated();
		registerBroadcastReceiver();
		return v;
		//return super.onCreateView(inflater, container, savedInstanceState);
	}
	public void setData(List<RosterEntry> data){
		this.data=data;
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		intent=new Intent(getActivity(), MessageActivity.class);
		intent.putExtra("user", adapter.data.get(position).getUser().split("@")[0]);
		startActivity(intent);
	}
	 /** 
     * 注册广播 
     */  
    private void registerBroadcastReceiver(){  
    	MessageInfoReceive receiver = new MessageInfoReceive();  
        IntentFilter filter = new IntentFilter("com.sskgg.gg.friendstatus");  
        filter.setPriority(0);
        getActivity().registerReceiver(receiver, filter);  
    }
	public class MessageInfoReceive extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			adapter.notifyDataSetInvalidated();
		}
	}

}
