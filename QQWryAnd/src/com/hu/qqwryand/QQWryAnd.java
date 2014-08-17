package com.hu.qqwryand;

import java.io.UnsupportedEncodingException;

/**
 * QQWryAnd�ӿ���⣬ͨ���������jni�ӿ�
 * @author HZY
 *
 */
public class QQWryAnd {

	/**
	 * ���췽��
	 * @param datPath dat�ļ�����·��
	 */
	public QQWryAnd(String datPath) {
		this.jniOpen(datPath);
	}

	/**
	 * 
	 * @return QQwry�汾��Ϣ
	 */
	public String getVersion() {
		return getStr(jniGetVersionBytes());
	}

	/**
	 * ��ȡIP��ַ
	 * @param ip String���͵�IP�ַ���������"192.168.56.1"
	 * @return ip��������Ϣ
	 */
	public String getIpAddr(String ip) {
		return getStr(jniGetIpAddrBytes(ip));
	}

	/**
	 * ��ȡIP��Χ��Ϣ
	 * @param ip
	 * @return �÷�Χ�ڵ�IP��������ͬ
	 */
	public String getIpRange(String ip) {
		return getStr(jniGetIpRangeBytes(ip));
	}
	
	/**
	 * ��ȡIP��¼����
	 * @return
	 */
	public int getIpCount(){
		return jniGetIpCount();
	}

	/**
	 * �ͷ�jni��Դ
	 */
	public void close() {
		this.jniClose();
	}
	
	/**
	 * ��byte����ת�����ַ���
	 * @param array
	 * @return
	 */
	private String getStr(byte[] array){	//��byte���鰴GBK��ʽת����String
		String str = "";
		try {
			str = new String(array, "GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	private native void jniOpen(String datPath);

	private native byte[] jniGetVersionBytes();

	private native byte[] jniGetIpAddrBytes(String ip);

	private native byte[] jniGetIpRangeBytes(String ip);

	private native int jniGetIpCount();
	
	private native void jniClose();

	static {
		System.loadLibrary("qqand");
	}

}
