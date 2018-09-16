package com.shiyuanhui.util;


import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;



public class SharedPreferencesUtil {
	private static SharedPreferencesUtil mSpUtil;
	private static SharedPreferences mSharedPreferences;
	private static SharedPreferences.Editor editor;
	final static private String masterPassword = "abceecde";  //aes算法用，自己替换

	/*
	 * 构造函数私有化
	 */
	private SharedPreferencesUtil(Context context){
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		editor = mSharedPreferences.edit();
	}

	public synchronized static SharedPreferencesUtil getSpUtil(Context context) {
		if (mSpUtil == null) {
			mSpUtil = new SharedPreferencesUtil(context);
		}
		return mSpUtil;
	}

	/**
	 * 存储用户的账号和密码
	 */
	public  void saveUserCount(String username,String password){
		try {
			String account_aes;
			String password_aes;
			if (android.os.Build.VERSION.SDK_INT <= 23)
			{
				 account_aes = JIAMI.encrypt(masterPassword, username);
				 password_aes = JIAMI.encrypt(masterPassword, password);
			}
			else
			{
				//android 7.0及以上由于加密算法修改，原加密算法无法使用，此处先不加密，修改方案可参考谷歌方案，需要科学上网
				 account_aes = username;
				 password_aes = password;
			}
			editor.putString("account", account_aes);//存储加密后的账号和密码
			editor.putString("password", password_aes);
			editor.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//将账号和密码加密

	}

	/**
	 * 读取存储的账号和密码
	 */
	public   Map<String, String> getUsercount(){
		String account = mSharedPreferences.getString("account", "");
		String password = mSharedPreferences.getString("password", "");
		String account_jieaes = "";
		String password_jieaes = "";
		Map<String,String> user = new HashMap<String, String>();
		try {
			if (android.os.Build.VERSION.SDK_INT <= 23)
			{
				account_jieaes = JIAMI.decrypt(masterPassword, account);
				password_jieaes = JIAMI.decrypt(masterPassword, password);
			}
			else
			{
				account_jieaes = account;
				password_jieaes = password;
			}

			user.put("username", account_jieaes);
			user.put("password", password_jieaes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return user;
	}

	/**
	 * 存储ip地址
	 */
	public  void saveIPAddress(String ip_address){
		editor.putString("IP", ip_address);
		editor.commit();
	}

	/**
	 * 获取IP地址
	 */
	public  String getIPAddress(){
		String ip = mSharedPreferences.getString("IP", "");
		return ip;
	}
	/**
	 * 获取是否是第一次进入app
	 */
	public boolean getIsFirstIn(){
		boolean isFirst = mSharedPreferences.getBoolean("isFirst", true);
		return isFirst;
	}
	/**
	 * 设置第一次进入
	 */
	public void setNotFirstIn(){
		editor.putBoolean("isFirst", false);
		editor.commit();
	}
	/**
	 * 设置是否选择自动登录
	 */
	public void setAutoIn(boolean auto){
		editor.putBoolean("autologin", auto);
		editor.commit();
	}
	/**
	 * 获取是否设置了自动登录
	 * @return
	 */
	public boolean getIsAutoIn(){
		boolean isAuto = mSharedPreferences.getBoolean("autologin", false);
		return isAuto;
	}



}
