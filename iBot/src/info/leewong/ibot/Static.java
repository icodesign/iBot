package info.leewong.ibot;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;
import bitoflife.chatterbean.AliceBot;

public class Static {
	
	public static AliceBot bot = null;
	public static final int LEFT=3;
	public static final int RIGHT=4;
	public static final int SETMOD=1;
	public static final int SETMSG=2;
	public static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
	public static void showToast(String text,Context context){
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
	
	
}
