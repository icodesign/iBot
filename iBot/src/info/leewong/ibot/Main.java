package info.leewong.ibot;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import bitoflife.chatterbean.AliceBot;

public class Main extends Activity implements OnClickListener{

	private AliceBot bot;
	public ProgressDialog progressDialog;
	Handler handler;
	private ListView itemlist = null;
	private List<DetailEntity> list;
	private ImageButton ivbtnPopup;
	public ImageButton ivbtnSetmod;
	private ImageButton ivbtnTmsg;
	private LinearLayout LLSendMsg;
	private EditText etMsgContent;
	private Button btnSendMsg;
	private Button btnSpeak;
	private ListAdapter adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		bot = Static.bot;
		initView();
		refreshListItems();	
	}
	
	
	private void initView(){
		itemlist = (ListView) findViewById(R.id.itemlist);
		ivbtnPopup=(ImageButton)findViewById(R.id.ivbtn_popup);
		ivbtnSetmod=(ImageButton)findViewById(R.id.ivbtn_setmod);
		ivbtnSetmod.setOnClickListener(this);
		ivbtnTmsg=(ImageButton)findViewById(R.id.ivbtn_tmsg);
		ivbtnTmsg.setOnClickListener(this);
		LLSendMsg=(LinearLayout)findViewById(R.id.linearlayout_sendmsg);
		etMsgContent=(EditText)findViewById(R.id.et_msgcontent);
		btnSendMsg=(Button)findViewById(R.id.btn_sendmsg);
		btnSendMsg.setOnClickListener(this);
		btnSpeak=(Button)findViewById(R.id.btnSpeak);
		list = buildListForSimpleAdapter();

		//语音识别初始化
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() !=0) {
            btnSpeak.setOnClickListener(this);
        }else {
        	btnSpeak.setEnabled(false);
        	Static.showToast("error...", Main.this);
        }
	}
	/**
	 * 
	 * 刷新list
	 */
	private void refreshListItems() {		
		adapter=new SmsAdapter(Main.this, list);
		itemlist.setAdapter(adapter);	
		itemlist.setSelection(list.size());
	}
	
	/**
	 * 
	 * 初始化list
	 */
	private List<DetailEntity> buildListForSimpleAdapter() {
		List<DetailEntity> list = new ArrayList<DetailEntity>();
		// Build a map for the attributes
		DetailEntity d=new DetailEntity(bot.respond("welcome"),
				Static.LEFT);
		list.add(d);
		return list;
	}
	

	public void onClick(View v) {
		// TODO Auto-generated method stub
		//trimInput(input.getText().toString());
		switch(v.getId()){
		case R.id.ivbtn_setmod:
			WhichGone(Static.SETMOD);
			break;
		case R.id.ivbtn_tmsg:
			WhichGone(Static.SETMSG);
			break;
		case R.id.btn_sendmsg:
			String input = etMsgContent.getText().toString();
			localChat(input);
			break;
		case R.id.btnSpeak:
			//启动录音
			startVoiceRecognitionActivity();
			break;
		}
		
	
	}
	
	private void localChat(String input) {
		// TODO Auto-generated method stub
		showInput(input);
		showLocalResponse(input);
	}

	
	/**
	 * 
	 * 在list显示用户输入
	 */
	private void showInput(String input){
		Log.d("ask",input);
		DetailEntity d=new DetailEntity(input,
				Static.RIGHT);
		list.add(d);
		refreshListItems();
		etMsgContent.setText("");
		//强制关闭输入法(解决某些机型的bug)
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
		imm.hideSoftInputFromWindow(etMsgContent.getWindowToken(), 0);
	}
	/**
	 * 
	 * 在list显示机器人本地输出
	 */
	private void showLocalResponse(String input) {
		// TODO Auto-generated method stub
		String response="Alice: error";	
		response=bot.respond(input);
		Log.d("answer",response);
		DetailEntity d=new DetailEntity(response,
				Static.LEFT);
		list.add(d);
		refreshListItems();
	}

	/**
     * Fire an intent to start the speech recognition activity.
     */
    private void startVoiceRecognitionActivity() {
    	//通过Intent传递语音识别的模式,开启语音 
        Intent intent =new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        //语言模式和自由形式的语音识别 
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //提示语音开始 
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition demo"); 
        //启动resultactivity
        startActivityForResult(intent, Static.VOICE_RECOGNITION_REQUEST_CODE);
    }
    /**
     * 处理语音返回
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Static.VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            //默认去录音的第一个
            DetailEntity detailEntity=new DetailEntity(matches.get(0), Static.RIGHT);
            list.add(detailEntity);
            refreshListItems();
            //转到发送信息界面
            //WhichGone(Static.SETMSG);
            //etMsgContent.setText(matches.get(0).toString());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    
	

	/**
	 * 
	 * 设置语音发送信息和打字输入信息的转换
	 */
	public void WhichGone(int flag){
		switch(flag){
		case Static.SETMOD:
			LLSendMsg.setVisibility(View.GONE);
			ivbtnSetmod.setVisibility(View.GONE);
			ivbtnTmsg.setVisibility(View.VISIBLE);
			btnSpeak.setVisibility(View.VISIBLE);
			break;
		case Static.SETMSG:
			LLSendMsg.setVisibility(View.VISIBLE);
			ivbtnSetmod.setVisibility(View.VISIBLE);
			ivbtnTmsg.setVisibility(View.GONE);
			btnSpeak.setVisibility(View.GONE);
			break;
		}
	}
	/**
	 * 
	 * 显示进度条
	 */
	public void showProgress(String title, String message){
		progressDialog = new ProgressDialog(this);
		progressDialog.setTitle(title);
		progressDialog.setMessage(message);
		progressDialog.show();
	}
	/**
	 * 
	 * 取消进度条
	 */
	public void dismissProgress() {
		if (progressDialog != null) {
			try {
				progressDialog.dismiss();
			} catch (Exception e) {

			}
		}
	}
}
