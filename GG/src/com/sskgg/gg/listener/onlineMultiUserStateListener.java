package com.sskgg.gg.listener;

import org.jivesoftware.smackx.muc.ParticipantStatusListener;

import android.content.Context;

import com.sskgg.gg.utils.LogUtils;

public class onlineMultiUserStateListener implements ParticipantStatusListener{

	public onlineMultiUserStateListener(Context ct,String room){
		
	}
	@Override
	public void voiceRevoked(String arg0) {
		LogUtils.i("voiceRevoked", arg0);
	}
	
	@Override
	public void voiceGranted(String arg0) {
		// TODO Auto-generated method stub
		LogUtils.i("voiceGranted", arg0);
	}
	@Override
	public void ownershipRevoked(String arg0) {
		// TODO Auto-generated method stub
		LogUtils.i("ownershipRevoked", arg0);
	}
	
	@Override
	public void ownershipGranted(String arg0) {
		// TODO Auto-generated method stub
		LogUtils.i("ownershipGranted",  arg0);
	}
	
	@Override
	public void nicknameChanged(String arg0, String arg1) {
		// TODO Auto-generated method stub
		LogUtils.i("moderatorRevoked", arg0);
	}
	
	@Override
	public void moderatorRevoked(String arg0) {
		// TODO Auto-generated method stub
		LogUtils.i("moderatorRevoked", arg0);
	}
	
	@Override
	public void moderatorGranted(String arg0) {
		// TODO Auto-generated method stub
		LogUtils.i("moderatorGranted", arg0);
	}
	
	@Override
	public void membershipRevoked(String arg0) {
		// TODO Auto-generated method stub
		LogUtils.i("membershipRevoked", arg0);
	}
	
	@Override
	public void membershipGranted(String arg0) {
		// TODO Auto-generated method stub
		LogUtils.i("membershipGranted", arg0);
	}
	
	@Override
	public void left(String arg0) {
		// TODO Auto-generated method stub
		LogUtils.i("left", arg0);
	}
	
	@Override
	public void kicked(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		LogUtils.i("kicked", arg0+"/"+arg1+"/"+arg2);
	}
	
	@Override
	public void joined(String arg0) {
		// 加入
		LogUtils.i("joined", arg0+"加入");
	}
	
	@Override
	public void banned(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		LogUtils.i("banned", arg0+"/"+arg1+"/"+arg2);
	}
	
	@Override
	public void adminRevoked(String arg0) {
		// TODO Auto-generated method stub
		LogUtils.i("adminRevoked", arg0);
	}
	
	@Override
	public void adminGranted(String arg0) {
		// TODO Auto-generated method stub
		LogUtils.i("adminGranted", arg0);
	}

}
