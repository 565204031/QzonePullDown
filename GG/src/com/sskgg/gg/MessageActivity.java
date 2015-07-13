package com.sskgg.gg;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import com.sskgg.gg.adapter.MessageAdapter;
import com.sskgg.gg.entity.MessageInfo;
import com.sskgg.gg.entity.MessageList;
import com.sskgg.gg.listener.RosterStatusListener;
import com.sskgg.gg.utils.DatabaseHelper;
import com.sskgg.gg.utils.LogUtils;
import com.sskgg.gg.utils.ReflectionUtils;
import com.sskgg.gg.utils.XmppConnection;
import com.sskgg.gg.utils.XmppHelp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * 聊天窗口页面
 * @author 杀死凯 QQ565204031
 *
 */
public class MessageActivity extends Activity implements OnClickListener{
	
	private ListView listview;
	private Button bt_submit;
	private EditText et_content;
	private Chat chat;
	private Intent intent;
	private MessageAdapter adapter;
	private Context mContext;
	private String user;
	private String toname;
	private long listid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_activity);
		mContext=this;
		ReflectionUtils.initViews(this);
		bt_submit.setOnClickListener(this);
		intent=getIntent();
		adapter=new MessageAdapter(mContext);
		adapter.data=new ArrayList<MessageInfo>();
		listview.setAdapter(adapter);
		//用户名
		user=intent.getExtras().getString("user");
		//本地用户名
		toname=XmppConnection.getInstance().getConnection().getUser().split("@")[0];
		chat=XmppConnection.getInstance().getFriendChat(user,null);
		registerBroadcastReceiver();
		listid=DatabaseHelper.queryMessageListIdByNameAndTo(mContext,user,toname);
		if(listid<=0){
			//消息列表无id,新增
			MessageList list=new MessageList();
			list.setIsremove(false);
			list.setUsername(user);
			list.setTo(toname);
			listid=DatabaseHelper.addMessageList(mContext, list);
		}else
		{
			//修改为已读
			
		}
		 adapter.data=DatabaseHelper.getMessageInfoList(mContext, listid);
		 adapter.notifyDataSetInvalidated();
	
	}
	@Override
	public void onClick(View v) {
		//发送消息
		try {  
		    chat.sendMessage(et_content.getText().toString());  
		     MessageInfo info=new MessageInfo(); 
			 info.setType("txt");
			 info.setListid(listid);
			 info.setMode(2);
			 info.setTime(new Date().toString());
			 info.setRead(true);
			 info.setBody(et_content.getText().toString());
			 //插入数据库
			if(DatabaseHelper.addMessageInfo(mContext, info)>0)
			{
				 adapter.data.add(info);
				 adapter.notifyDataSetInvalidated();
				 et_content.setText("");
			}
		    //发送成功添加数据库
		} catch (XMPPException e) {  
		    e.printStackTrace();  
		}
	}  
	 /** 
     * 注册广播 
     */  
    private void registerBroadcastReceiver(){  
    	MessageInfoReceive receiver = new MessageInfoReceive();  
        IntentFilter filter = new IntentFilter("com.sskgg.gg.messageadd");  
        filter.setPriority(0);
        registerReceiver(receiver, filter);  
    }
	public class MessageInfoReceive extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// 收到广播，操作数据库，拿出数据
			 adapter.data=DatabaseHelper.getMessageInfoList(mContext, listid);
			 adapter.notifyDataSetInvalidated();
		}
		
	}
	
}
