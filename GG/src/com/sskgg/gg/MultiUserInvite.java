package com.sskgg.gg;

import java.util.Iterator;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.MultiUserChat;

import com.sskgg.gg.entity.MultiUserMessageInfo;
import com.sskgg.gg.utils.LogUtils;
import com.sskgg.gg.utils.XmppConnection;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;

public class MultiUserInvite extends Activity{

	
	private Intent intent;
	private String room;
	private String inviter;
	private String reason;
	private String password;
	private Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.multiuserinvite_activity);
		mContext=this;
		intent=getIntent();
		room=intent.getExtras().getString("room");
		inviter=intent.getExtras().getString("inviter");
		reason=intent.getExtras().getString("reason");
		password=intent.getExtras().getString("password");
		
		new AlertDialog.Builder(MultiUserInvite.this)    
        .setTitle("邀请")  
        .setMessage("你的好友"+inviter+"邀请你加入"+room+"聊天室")
        .setPositiveButton("接受邀请", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				//跳转到聊天室Activity
			      intent=new Intent(mContext, MultiUserMessage.class);
			      intent.putExtra("room",room);
			      intent.putExtra("password", password);
			      intent.putExtra("inviter",inviter);
			      intent.putExtra("reason",reason);
			      startActivity(intent);
			      finish();
			}
		})  
        .setNegativeButton("拒绝",new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				MultiUserChat.decline(XmppConnection.getInstance().getConnection(), room, inviter, "I'm busy right now");
			    finish();
			}
		})
        .show();  
	}
}
