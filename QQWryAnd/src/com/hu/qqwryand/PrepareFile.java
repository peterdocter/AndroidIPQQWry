package com.hu.qqwryand;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.hu.andun7z.AndUn7z;

import android.content.Context;
import android.os.Handler;

public class PrepareFile {

	Handler handler = null;
	String appPath;
	String filePath;
	Context context = null;

	public PrepareFile(Handler handler,Context context, String appPath) {
		this.handler = handler;
		this.appPath = appPath;
		this.filePath = appPath + Consts.fileName;
		this.context = context;
	}

	public void start() {
		File file = new File(filePath);
		if (file.exists()){// && MD5(file).equalsIgnoreCase(Consts.fileMD5)) {
			handler.sendEmptyMessage(Consts.msgFileOk);//文件已经存在且校验成功
		} else {//文件检查失败，重新解压数据
			handler.sendEmptyMessage(Consts.msgHandleFileStart);
			new Thread() {

				@Override
				public void run() {
					try {
						moveAsset();	//move from assets
						AndUn7z.extract7z(appPath + Consts.assetName, appPath);	//extract file
						new File(appPath + Consts.assetName).delete(); //delete temp
					} catch (Exception e) {
						handler.sendEmptyMessage(Consts.msgHandleFileError);
						e.printStackTrace();
					}
					handler.sendEmptyMessage(Consts.msgHandleFileEnd);
				}
			}.start();
		}
	}

	private void moveAsset() throws IOException { // 将文件从asset拷贝到手机
		InputStream inputStream = context.getAssets().open(Consts.assetName);
		FileOutputStream fileOutputStream = new FileOutputStream(appPath + Consts.assetName);
		int length = -1;
		byte[] buffer = new byte[Consts.bufferSize];
		while ((length = inputStream.read(buffer)) != -1) {
			fileOutputStream.write(buffer, 0, length);
		}
		fileOutputStream.flush();
		fileOutputStream.close();
		inputStream.close();
	}

//	private String MD5(File file) { // 获取文件的MD5值
//		MessageDigest messageDigest = null;
//		FileInputStream fileInStream = null;
//		byte buffer[] = new byte[Consts.bufferSize];
//		int length = -1;
//		try {
//			messageDigest = MessageDigest.getInstance("MD5");
//			fileInStream = new FileInputStream(file);
//			while ((length = fileInStream.read(buffer, 0, Consts.bufferSize)) != -1) {
//				messageDigest.update(buffer, 0, length);
//			}
//			fileInStream.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//		BigInteger bigInt = new BigInteger(1, messageDigest.digest());
//		return bigInt.toString(16);
//	}
}
