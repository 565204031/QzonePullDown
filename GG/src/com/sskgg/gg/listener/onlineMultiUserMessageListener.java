package com.sskgg.gg.listener;

import java.util.Date;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.StringUtils;

import android.content.Context;
import android.content.Intent;

import com.sskgg.gg.entity.MultiUserMessageInfo;
import com.sskgg.gg.utils.DatabaseHelper;
import com.sskgg.gg.utils.LogUtils;
import com.sskgg.gg.utils.XmppConnection;

/**
 * 会议监听聊天
 * @author 杀死凯 QQ565204031
 *
 */
public class onlineMultiUserMessageListener implements PacketListener{

	public String room;
	public Context mContext;
	//数据库会议室列表id
	private long listid; 
	public onlineMultiUserMessageListener(Context ct,String r){
		this.room=r;
		this.mContext=ct;
		listid=DatabaseHelper.getMultiUserListId(ct,room);
		if(listid<=0)
		{
			//新增会议室列表
			listid=DatabaseHelper.addMultiUserList(ct,room,XmppConnection.getInstance().getConnection().getUser());
		}
	}
	@Override
	public void processPacket(Packet packet) {
		 Message message = (Message) packet; 
		 MultiUserMessageInfo info= new MultiUserMessageInfo();
			String from = StringUtils.parseResource(message.getFrom()); 
	     	//String fromRoomName = StringUtils.parseName(message.getFrom());
		 	//会议室有消息，新增数据库
	      	info.setListid(listid);
	      	info.setMode(1);
	      	info.setFrom(from);
	     	info.setBody(message.getBody());
	     	info.setRead(true);
	     	info.setType("txt");
	     	info.setTime(new Date().toString());
	     	DatabaseHelper.addMultiUserInfo(mContext, info);
	     	Intent intent = new Intent();
	     	intent.putExtra("room", room);
	    	intent.putExtra("listid", listid);
	     	intent.setAction("com.sskgg.gg.multiusermessage");
	     	mContext.sendBroadcast(intent);
	}
	
}
