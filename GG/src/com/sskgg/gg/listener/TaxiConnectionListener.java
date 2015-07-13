package com.sskgg.gg.listener;

import org.jivesoftware.smack.ConnectionListener;

import com.sskgg.gg.utils.LogUtils;

public class TaxiConnectionListener implements ConnectionListener{

	@Override
	public void connectionClosed() {
		// TODO Auto-generated method stub
		LogUtils.i("INFO", "connectionClosed");
	}

	@Override
	public void connectionClosedOnError(Exception arg0) {
		// TODO Auto-generated method stub
		LogUtils.i("INFO", "connectionClosedOnError");
	}

	@Override
	public void reconnectingIn(int arg0) {
		// TODO Auto-generated method stub
		LogUtils.i("INFO", "reconnectingIn");
	}

	@Override
	public void reconnectionFailed(Exception arg0) {
		// TODO Auto-generated method stub
		LogUtils.i("INFO", "reconnectionFailed");
	}

	@Override
	public void reconnectionSuccessful() {
		// TODO Auto-generated method stub
		LogUtils.i("INFO", "reconnectionSuccessful");
	}

}
