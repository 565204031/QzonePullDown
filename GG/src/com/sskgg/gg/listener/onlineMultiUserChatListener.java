package com.sskgg.gg.listener;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.muc.InvitationListener;
import org.jivesoftware.smackx.muc.MultiUserChat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.widget.Toast;

import com.sskgg.gg.MultiUserInvite;
import com.sskgg.gg.utils.LogUtils;
import com.sskgg.gg.utils.XmppConnection;

/**
 * 会议邀请监听
 * @author 杀死凯 565204031
 */
public class onlineMultiUserChatListener implements InvitationListener{

	private Context mContext;
	private Intent intent;
	public onlineMultiUserChatListener(Context ct){
		mContext=ct;
	};
	@Override
	public void invitationReceived(final Connection conn, final String room, final String inviter,
			String reason, String password, Message mes) {
		    LogUtils.i("INFo",  room);
			intent =new Intent(mContext, MultiUserInvite.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("room", room);
			intent.putExtra("inviter", inviter);
			intent.putExtra("reason", reason);
			intent.putExtra("password", password);
			mContext.startActivity(intent);
	}
}
