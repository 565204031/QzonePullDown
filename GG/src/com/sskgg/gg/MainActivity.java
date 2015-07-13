package com.sskgg.gg;


import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.util.StringUtils;

import com.sskgg.gg.adapter.FriendsAdapter;
import com.sskgg.gg.fragment.FriendsFragment;
import com.sskgg.gg.fragment.MessageFragment;
import com.sskgg.gg.fragment.MultiUserFragment;
import com.sskgg.gg.listener.RosterStatusListener;
import com.sskgg.gg.utils.LogUtils;
import com.sskgg.gg.utils.ReflectionUtils;
import com.sskgg.gg.utils.XmppConnection;
import com.sskgg.gg.utils.XmppHelp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * 联系人页面
 * @author 杀死凯 QQ565204031
 *
 */
public class MainActivity extends FragmentActivity implements OnClickListener{

	private Context mContext;
	private FragmentAdapter adapter;
	private getFriends getFriends;
	private Intent intent;
	private FriendsFragment ff;
	private MessageFragment mf;
	private ImageView iv_message,iv_friends,iv_multiuser;
	private FragmentManager fragmentManager;
	private FragmentTransaction transaction;
	private ViewPager viewpage;
	private List<Fragment> flist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		ReflectionUtils.initViews(this);
		mContext=this;
		getFriends=new getFriends();
		getFriends.execute("");
		iv_message.setOnClickListener(this);
		iv_friends.setOnClickListener(this);
		iv_multiuser.setOnClickListener(this);
		flist=new ArrayList<Fragment>();
	

	}
	private class getFriends extends AsyncTask<String,Void, List<RosterEntry>>{

		private Roster roster;
		private MultiUserFragment muf;
		@Override
		protected List<RosterEntry> doInBackground(String... params) {
			return XmppConnection.getInstance().getAllEntries();
		}
		@Override
		protected void onPostExecute(List<RosterEntry> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result!=null)
			{
				mf=new MessageFragment();
				mf.setData(result);
				flist.add(mf);
				ff=new FriendsFragment();
				ff.setData(result);
				flist.add(ff);
				muf=new MultiUserFragment();
				flist.add(muf);
				roster=XmppConnection.getInstance().getConnection().getRoster();
				roster.addRosterListener(new RosterStatusListener(mContext));
				adapter=new FragmentAdapter(getSupportFragmentManager());
				viewpage.setAdapter(adapter);
			}
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_message:
			viewpage.setCurrentItem(0);
			break;
		case R.id.iv_friends:
			viewpage.setCurrentItem(1);
			break;
		case R.id.iv_multiuser:
			viewpage.setCurrentItem(2);
			break;
		default:
			break;
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		XmppConnection.getInstance().closeConnection();
	}
	private class FragmentAdapter extends FragmentPagerAdapter{

		public FragmentAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int p) {
			// TODO Auto-generated method stub
			return flist.get(p);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return flist.size();
		}
	}

}
