package com.sskgg.gg.listener;

import java.util.Collection;

import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.packet.Presence;

import android.content.Context;
import android.content.Intent;

import com.sskgg.gg.utils.LogUtils;

public class RosterStatusListener implements RosterListener{

	private Context mContext;
	public RosterStatusListener(Context ct){
		mContext=ct;
	}
	@Override
	public void entriesAdded(Collection<String> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void entriesDeleted(Collection<String> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void entriesUpdated(Collection<String> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void presenceChanged(Presence arg0) {
		//好友状态改变，发送广播通知改变UI
		if(arg0.getStatus()!=null)
		{
			//上线
			
		}else
		{
			//下线
		}
		LogUtils.i("INFO", "好友改变状态了。");
		Intent intent = new Intent();
		intent.setAction("com.sskgg.gg.friendstatus");
		mContext.sendBroadcast(intent);
	}

}
