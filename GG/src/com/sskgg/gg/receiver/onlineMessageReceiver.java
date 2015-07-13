package com.sskgg.gg.receiver;

import java.util.Date;

import com.sskgg.gg.MessageActivity;
import com.sskgg.gg.MultiUserInvite;
import com.sskgg.gg.R;
import com.sskgg.gg.entity.MessageInfo;
import com.sskgg.gg.entity.MessageList;
import com.sskgg.gg.utils.DatabaseHelper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 在线状态收到广播
 * @author 杀死凯 QQ565204031
 *
 */
public class onlineMessageReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		MessageList list=(MessageList) intent.getExtras().getSerializable("list");
		//查找消息列表id
		long listid=DatabaseHelper.queryMessageListIdByNameAndTo(context,list.getUsername(),list.getTo());
		if(listid<=0)
		{
			//消息列表无id,新增
			list.setIsremove(false);
			listid=DatabaseHelper.addMessageList(context, list);
		}
		MessageInfo info=new MessageInfo();
		info.setListid(listid);
		info.setMode(1);
		info.setBody(list.getBody());
		info.setRead(false);
		info.setTime(new Date().toString());
		info.setType("txt");
		//添加内容表
		if(DatabaseHelper.addMessageInfo(context,info)>0)
		{
			list.setNumber(DatabaseHelper.getMessageInfoNumber(context,listid));
			list.setTime(new Date().toString());
			list.setId(listid);
			//修改消息表
			DatabaseHelper.updateMessageList(context, list);
			intent = new Intent();
			//调频
			intent.setAction("com.sskgg.gg.messageadd");
			context.sendBroadcast(intent);
			ShowNotification(context,list.getUsername(),list.getBody());
		}	
	}
	private void ShowNotification(Context ct,String user,String body){
		//获得通知管理器
		NotificationManager manager=(NotificationManager) ct.getSystemService(Context.NOTIFICATION_SERVICE);
        //构建一个通知对象(需要传递的参数有三个,分别是图标,标题和 时间)
        Notification notification = new Notification(R.drawable.ic_launcher,body,System.currentTimeMillis());
        Intent intent = new Intent(ct,MessageActivity.class);
        intent.putExtra("user", user);
        PendingIntent pendingIntent = PendingIntent.getActivity(ct,0,intent,0);
        notification.setLatestEventInfo(ct, "GG", body, pendingIntent);
        notification.flags = Notification.FLAG_AUTO_CANCEL;//点击后自动消失
        notification.defaults = Notification.DEFAULT_SOUND;//声音默认
        manager.notify(10086, notification);
        //发动通知,id由自己指定，每一个Notification对应的唯一标志
        //其实这里的id没有必要设置,只是为了下面要用到它才进行了设置
	}
}
