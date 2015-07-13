package com.sskgg.gg.service;

import java.io.File;
import java.util.Iterator;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;
import org.jivesoftware.smackx.muc.InvitationListener;
import org.jivesoftware.smackx.muc.MultiUserChat;

import com.sskgg.gg.listener.onlineMessageListener;
import com.sskgg.gg.listener.onlineMultiUserChatListener;
import com.sskgg.gg.utils.LogUtils;
import com.sskgg.gg.utils.LruCacheUtils;
import com.sskgg.gg.utils.XmppConnection;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ConnectService extends Service{

	private onlineMessageListener onlinemessagelistener;
	private onlineMultiUserChatListener onlinemultiuserchatlistener;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		LogUtils.i("onStart", "onStart");
		//注册监听在线消息
		onlinemessagelistener =new onlineMessageListener(this);
		XmppConnection
		.getInstance()
		.getConnection()
		.getChatManager()
		.addChatListener(onlinemessagelistener);
		
		 //监听聊天室发来邀请
		onlinemultiuserchatlistener=new onlineMultiUserChatListener(this);
	      MultiUserChat.addInvitationListener(XmppConnection
	    			.getInstance()
	    			.getConnection(),onlinemultiuserchatlistener);
	      
	      
	   // 创建文件传输管理器
	      final FileTransferManager manager = new FileTransferManager(XmppConnection.getInstance().getConnection());

	      // 创建监听器
	      manager.addFileTransferListener(new FileTransferListener() {
	            public void fileTransferRequest(FileTransferRequest request) {
	                  // 监查此请求是否应该被接受
	                  if(true) {
	                	  LogUtils.i("看到了。", "asdasdasd");
	                        // 接受
	                        IncomingFileTransfer transfer = request.accept();
	                        try {
								transfer.recieveFile(new File(LruCacheUtils.SD_PATH + "/sskgg"));
							} catch (XMPPException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	                  } else {
	                        // 拒绝
	                        request.reject();
	                  }
	            }
	      });
	      
	}
	@Override
	public void onCreate() {
		LogUtils.i("onCreate", "onCreate");
		super.onCreate();
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
			//移除监听在线消息
				XmppConnection
				.getInstance()
				.getConnection()
				.getChatManager()
				.removeChatListener(onlinemessagelistener);
				
		//关闭连接
		XmppConnection.getInstance().closeConnection();
		 //监听聊天室发来邀请
			MultiUserChat.addInvitationListener(XmppConnection
				.getInstance()
				.getConnection(),onlinemultiuserchatlistener);
	}

}
