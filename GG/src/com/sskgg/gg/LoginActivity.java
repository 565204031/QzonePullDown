package com.sskgg.gg;



import com.sskgg.gg.listener.onlineMessageListener;
import com.sskgg.gg.service.ConnectService;
import com.sskgg.gg.utils.LogUtils;
import com.sskgg.gg.utils.ReflectionUtils;
import com.sskgg.gg.utils.XmppConnection;
import com.sskgg.gg.utils.XmppHelp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 登陆页面
 * @author 杀死凯 QQ565204031
 *
 */
public class LoginActivity extends Activity implements OnClickListener{

	private EditText et_name,et_password;
	private Button bt_login,bt_sign;
	private Context mContext;
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		LogUtils.isDebug=true;
		mContext=this;
		ReflectionUtils.initViews(this);
		bt_login.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
	
	     // 登录  
        LoginTask task=new LoginTask();
        task.execute("");
        
	}
	private class LoginTask extends AsyncTask<String, Void, Boolean>{

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				//XmppHelp.login("test2000", "test2000");
				XmppConnection.getInstance().login("test2000", "test2000");
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			if(result)
			{
				Toast.makeText(mContext, "登陆成功", 1000).show();
				intent =new Intent(mContext,ConnectService.class);
				startService(intent);
				intent =new Intent(mContext,MainActivity.class);
				startActivity(intent);
				finish();
			}else
			{
				Toast.makeText(mContext, "登陆失败", 1000).show();
			}
			super.onPostExecute(result);
		}
	}
}
