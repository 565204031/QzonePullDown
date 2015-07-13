package com.sskgg.gg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.ParticipantStatusListener;

import com.sskgg.gg.adapter.MultiUserMessageAdapter;
import com.sskgg.gg.entity.MultiUserMessageInfo;
import com.sskgg.gg.fragment.MessageFragment.MessageInfoReceive;
import com.sskgg.gg.utils.DatabaseHelper;
import com.sskgg.gg.utils.LogUtils;
import com.sskgg.gg.utils.ReflectionUtils;
import com.sskgg.gg.utils.XmppConnection;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MultiUserMessage extends Activity implements OnClickListener{

	private ListView listview;
	private Intent intent;
	private String room;
	private String inviter;
	private String reason;
	private String password;
	private MultiUserChat mc;
	private Button bt_submit;
	private EditText et_content;
	private MultiUserMessageAdapter adapter;
	private Context mContext;
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			LogUtils.i("info", "asdasdasd");
			
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.multiusermessage_activity);
		ReflectionUtils.initViews(this);
		mContext=this;
		intent=getIntent();
		room=intent.getExtras().getString("room");
		inviter=intent.getExtras().getString("inviter");
		reason=intent.getExtras().getString("reason");
		password=intent.getExtras().getString("password");
		bt_submit.setOnClickListener(this);
		registerBroadcastReceiver();
		 adapter=new MultiUserMessageAdapter(mContext);
		 adapter.data=new ArrayList<MultiUserMessageInfo>();
	     listview.setAdapter(adapter);
	     mc=XmppConnection.getInstance().joinMultiUserChat(mContext,"周杰伦",room,"");
	}
	@Override
	public void onClick(View v) {
		//发送消息
		try {
			mc.sendMessage(et_content.getText().toString());
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 /** 
     * 注册广播 
     */  
    private void registerBroadcastReceiver(){  
    	MessageInfoReceive receiver = new MessageInfoReceive();  
        IntentFilter filter = new IntentFilter("com.sskgg.gg.multiusermessage");  
        filter.setPriority(0);
        this.registerReceiver(receiver, filter);  
    }
	public class MessageInfoReceive extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// 收到广播，操作数据库，拿出数据
			String _room=intent.getExtras().getString("room");
			long _listid=intent.getExtras().getLong("listid");
			if(_room.equals(room))
			{
				//如果收到的消息是当前开打页面的会议室才更新Ui
				 adapter.data=DatabaseHelper.getListMessageInfo(context,_listid,0);
				 adapter.notifyDataSetInvalidated();
			}
			 
		}
	}
}
