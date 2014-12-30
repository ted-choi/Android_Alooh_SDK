package com.cresprit.mqtt_sdk;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


class UserManager{
	private static String m_pId;
	private static String m_pAuthKey;
	private static String m_pAuid;
	private static String m_pDeviceName;
	private static UserManager __instance;
	//private IUpdateListener mListener = null;
	String m_pPasswd;
	Context context;
	
	
	public static UserManager getInstance(Context appContext) {
		if (__instance == null) {
			__instance = new UserManager(appContext);
			
		}
		return __instance;
	}
	
	public UserManager(Context ctx)
	{
		this.context=ctx;
	}	
	
	public UserManager(String _id, String _passwd){
		m_pId = _id;
		this.m_pPasswd = _passwd;
	}
	
	public static void SetAuthKey(String _key)
	{
		m_pAuthKey = _key;
	}
	
	public static String GetAuthKey()
	{
		return m_pAuthKey;
	}
	
	public static String GetUserId()
	{
		return m_pId;
	}
	
	public void setUserId(String _id)
	{
		m_pId = _id;
	}	
	
	public String getPassword()
	{
		return m_pPasswd;
	}
	
	public void setPassword(String _password)
	{
		m_pPasswd = _password;
	}
	
	}