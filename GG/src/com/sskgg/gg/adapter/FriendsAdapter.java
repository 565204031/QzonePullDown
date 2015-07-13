package com.sskgg.gg.adapter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.RosterPacket.ItemStatus;
import org.jivesoftware.smack.packet.RosterPacket.ItemType;
import org.jivesoftware.smackx.packet.VCard;

import com.sskgg.gg.R;
import com.sskgg.gg.listener.RosterStatusListener;
import com.sskgg.gg.utils.LogUtils;
import com.sskgg.gg.utils.LruCacheUtils;
import com.sskgg.gg.utils.XmppConnection;
import com.sskgg.gg.utils.XmppHelp;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 联系人列表
 * @author 杀死凯 QQ565204031
 *
 */
public class FriendsAdapter extends BaseAdapter{

	public List<RosterEntry> data;
	private Context mContext;
	private Map<String,Bitmap> images;
	private Roster roster;
	public FriendsAdapter(Context ct)
	{
		mContext=ct;
		data=new ArrayList<RosterEntry>();
		images=new HashMap<String, Bitmap>();
		roster=XmppConnection.getInstance().getConnection().getRoster();
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
		if (convertView == null) {
			viewhandle=new ViewHandle();
			convertView=LayoutInflater.from(mContext).inflate(R.layout.friend_item, null);
			viewhandle.tv_friend_name=(TextView) convertView.findViewById(R.id.tv_friend_name);
			viewhandle.tv_time=(TextView) convertView.findViewById(R.id.tv_time);
			viewhandle.tv_content=(TextView) convertView.findViewById(R.id.tv_content);
			viewhandle.iv_portrait=(ImageView) convertView.findViewById(R.id.iv_portrait);
			convertView.setTag(viewhandle);
		} else {
			viewhandle=(ViewHandle) convertView.getTag();
		}
		RosterEntry entry=data.get(position);
	    Presence presence =roster.getPresence(entry.getUser());
	    String strstatu;
		 if(presence.getStatus()!=null)
		 {
			 //在线
			 strstatu="("+presence.getStatus()+")";
		 }else
		 {
			 //不在线
			 strstatu="(离线)";
		 }
		viewhandle.tv_friend_name.setText(entry.getName()+strstatu);
		if(images.get(entry.getUser())==null)
		{
			LodingImageTask lodingiamge=new LodingImageTask();
			lodingiamge.execute(entry.getUser());
		}else
		{
			//说明有图片
			viewhandle.iv_portrait.setImageBitmap(images.get(entry.getUser()));
		}
		return convertView;
	}
	private class ViewHandle {
        TextView tv_friend_name;
        TextView tv_time;
        TextView tv_content;
        ImageView iv_portrait;
	}
    private class LodingImageTask extends AsyncTask<String, Void, Bitmap>{

		@Override
		protected Bitmap doInBackground(String... params) {
				VCard vcard=XmppConnection.getInstance().getUserVCard(params[0]);
				Bitmap bitmap=null;
				bitmap=LruCacheUtils.SDloadImage(vcard.getAvatarHash());
				if(bitmap==null)
				{
					bitmap=LruCacheUtils.getBitmap(vcard.getAvatar(),vcard.getAvatarHash());
				}
				images.put(params[0], bitmap);
				return bitmap;
		}
		@Override
		protected void onPostExecute(Bitmap result) {
			if(result!=null)
			{
				//有图片更新
				FriendsAdapter.this.notifyDataSetInvalidated();
			}
			super.onPostExecute(result);
		}
    }
    

}
