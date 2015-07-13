package com.sskgg.gg.fragment;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.muc.HostedRoom;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.RoomInfo;

import com.sskgg.gg.R;
import com.sskgg.gg.entity.MultiUserList;
import com.sskgg.gg.utils.LogUtils;
import com.sskgg.gg.utils.ReflectionUtils;
import com.sskgg.gg.utils.XmppConnection;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * 会议室房间列表
 * @author 杀死凯 565204031
 *
 */
public class MultiUserFragment extends Fragment{

	private View v;
	private ListView listview;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v=inflater.inflate(R.layout.multiuser_fragment, null);
		ReflectionUtils.initViews(v);
		
		 // 获得user3@host.org加入的聊天室
		 String user=XmppConnection.getInstance().getConnection().getUser();
		  boolean supports = MultiUserChat.isServiceEnabled(XmppConnection.getInstance().getConnection(), user);  
          //获取某用户所加入的聊天室   
		  Iterator<String> joinedRooms = MultiUserChat.getJoinedRooms(XmppConnection.getInstance().getConnection(), user);  
          while(joinedRooms.hasNext()) {   
              LogUtils.i("INFO222222", joinedRooms.next());
          }  
         try {
			Collection<HostedRoom> hrooms = MultiUserChat.getHostedRooms(XmppConnection.getInstance().getConnection(), XmppConnection.getInstance().getConnection().getServiceName());
			for(HostedRoom r:hrooms){
				RoomInfo roominfo = MultiUserChat.getRoomInfo(XmppConnection.getInstance().getConnection(), r.getJid());
				LogUtils.i("INFO3333333",roominfo.getRoom()+"|"+roominfo.getSubject()+"|"+r.getName());
			}
         } catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
         LogUtils.i("INFOS1111",  user+"|"+supports);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LogUtils.i("INFOS","onCreate");
		
	}
}
