package info.leewong.ibot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Secure;
import android.util.Log;
import bitoflife.chatterbean.AliceBot;

public class Load extends Activity{
	
	private AliceBot bot;
	private AliceBotMother xmother = new AliceBotMother();
	private Handler handler;
	Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load);
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				Log.d("Welcome","msg.what---"+msg.what);
				switch(msg.what){
				case 0:
					Static.showToast("初始化错误!", Load.this);
					break;
				case 1:
					Static.showToast("初始化完成!", Load.this);
					intent = new Intent(Load.this, Main.class);
					startActivity(intent);
					finish();
					break;
				}
			}
			
		};
		new Thread(){
			public void run(){
				//初始化bot
				initBot();
			}
		}.start();
	}
	
	private void initBot(){
		try {
			bot = xmother.newInstance(this);
			xmother.setUp(); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			handler.sendEmptyMessage(0);
			return;
		}
		Static.bot = bot;
		handler.sendEmptyMessage(1);
	}
}
