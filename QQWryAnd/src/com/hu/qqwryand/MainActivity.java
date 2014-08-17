package com.hu.qqwryand;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	private QQWryAnd qqWryAnd = null;
	private EditText editText = null;
	private Button button = null;
	private TextView textViewVersion, textViewCount, textViewLocation, textViewRange;
	
	private PrepareFile prepareWork = null;
	private Handler handler = null;
	private ProgressDialog progressDialog = null;
	private String appPath = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Ŀ���������SD��
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			appPath =  Environment.getExternalStorageDirectory() + File.separator + Consts.sdDir + File.separator;
			File dir = new File(appPath);
			if(!dir.exists()){
				dir.mkdirs();
			}
		}else{
			appPath = this.getFilesDir().getAbsolutePath() + File.separator;
		}
		this.prepareData();
	}
	
	/**
	 * ȷ��dat�����ļ��Ѿ�׼����
	 */
	private void prepareData(){
		
		this.handler = new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				switch (msg.what) {
				case Consts.msgFileOk:	//�ļ��Ѿ�׼������
					startQuery();
					break;
				case Consts.msgHandleFileStart:	//��ʼ��ѹ
					progressDialog = new ProgressDialog(MainActivity.this);
					progressDialog.setTitle(R.string.title_tip);
					progressDialog.setMessage(getText(R.string.msg_load_wait));
					progressDialog.setCancelable(false);
					progressDialog.show();
					break;
				case Consts.msgHandleFileEnd:	//��ѹ���
					progressDialog.dismiss();
					prepareWork.start();	//��ѹ��ϣ����¼��һ���ļ�
					break;
				case Consts.msgHandleFileError:	//�ļ�����
					
					break;
				default:
					break;
				}
				return false;
			}
		});
		prepareWork = new PrepareFile(handler, this, appPath);
		prepareWork.start();
	}
	
	/* Java ��֤Ip�Ƿ�Ϸ� */
	private boolean isIPAddress(String ipaddr) {
		boolean flag = false;
		Pattern pattern = Pattern
				.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\." +
						"((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\." +
						"((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\." +
						"((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
		Matcher m = pattern.matcher(ipaddr);
		flag = m.matches();
		return flag;
	}
	
	/**
	 * �ļ�׼���ã����Կ�ʼ��ѯ
	 */
	private void startQuery(){
		String datPath = this.appPath + Consts.fileName;
		qqWryAnd = new QQWryAnd(datPath);
		
		editText = (EditText) findViewById(R.id.editText1);
		button = (Button) findViewById(R.id.button1);
		textViewVersion = (TextView) findViewById(R.id.textViewVersion);
		textViewCount = (TextView) findViewById(R.id.textViewCount);
		textViewLocation = (TextView) findViewById(R.id.textViewLocation);
		textViewRange = (TextView) findViewById(R.id.textViewRange);

		String version = qqWryAnd.getVersion();
		int count = qqWryAnd.getIpCount();
		textViewVersion.setText(version);
		textViewCount.setText(String.valueOf(count));
		

		/**
		 * ��ѯ��ť�¼�
		 */
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String ip = editText.getText().toString();
				String addr = "";
				String range = getResources().getString(R.string.invalid_ip);
				if (isIPAddress(ip)) {
					addr = qqWryAnd.getIpAddr(ip);
					range = qqWryAnd.getIpRange(ip);
				}else{
					editText.selectAll();
				}
				textViewLocation.setText(addr);
				textViewRange.setText(range);
			}
		});
	}

	//�����˵�
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (qqWryAnd != null){
			qqWryAnd.close();
		}
		super.onDestroy();
	}

	//�˵��¼�
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.action_exit:
			this.finish();
			break;
		case R.id.action_about:
			break;
		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

}
