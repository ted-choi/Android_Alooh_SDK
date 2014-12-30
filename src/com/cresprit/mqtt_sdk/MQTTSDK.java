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
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


public class MQTTSDK
{
	public static final String SERVER_API_LOGIN_URL = "http://api.alooh.io:50001/api/v1/session";
	private static final String FEEDID = "com.cresprit.mqtt_sdk.feedid";
	ConnectionMgr connMgr;
	static Connection connection;
	
	public static String LogIn(String _email, String _passwd)
	{
		Log.i("","test1");
		String loginKey = null;
		try{
		JSONObject data = new JSONObject();
		JSONObject json = new JSONObject();
		
		try {
			data.put("email"	, _email);
			data.put("password", _passwd);
			json.put("data", data);				
		} catch (JSONException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		HttpClient client = new DefaultHttpClient();
		final HttpParams params = client.getParams();
		
		HttpConnectionParams.setConnectionTimeout(params, 30 * 1000);
		HttpConnectionParams.setSoTimeout(params, 30 * 1000);

		HttpPost request = new HttpPost();
		request.setHeader("Content-Type", "application/json");

		try {
			request.setURI(new URI(SERVER_API_LOGIN_URL));
		} catch (URISyntaxException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		try {
			request.setEntity(new StringEntity(json.toString(), "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		
		try {
			int responseCode=0;	
			
			HttpResponse response = client.execute(request);
			HttpEntity responseEntity = response.getEntity();
			Log.i("", "get http response: STATUS_CODE: " + response.getStatusLine().getStatusCode()+response.getStatusLine().getReasonPhrase());
			responseCode = response.getStatusLine().getStatusCode();
			if(responseCode == 406 || responseCode == 401 || responseCode == 404 || responseCode == 500)
			{
				loginKey = Integer.toString(responseCode);			
				return loginKey;
			}
			
			HttpEntity entity = response.getEntity();
			String jsonStr = EntityUtils.toString(entity);
			try {
				JSONObject resObj = new JSONObject(jsonStr);
				JSONObject resData = resObj.getJSONObject("data");
				Log.i("","resData : "+resData.toString());
				loginKey = resData.getString("key");
				UserManager.SetAuthKey(loginKey);
				Log.i("","loginKey : "+loginKey);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.i("","JSON ERROR");
				e.printStackTrace();
			}
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}catch(Exception e)
		{
			
		}
		return loginKey;
	}
	
	public static int Publish(String _deviceName, String _message)
	{
		int ret = 0;
		String feedId = null;
		
		feedId = ServerAPIManager.GetFeedId(_deviceName);
		
		connection =  ConnectionMgr.getConnectionInstance(ConnectionMgr.MQTT_SERVER_URL);
    	
    	connection.setClientId("Guest");
    	connection.setUsername(UserManager.GetAuthKey());
    	connection.connect();            	
    	//connection.setFeed(feedId);
    	connection.setMessage(_message);

		connection.publish(feedId);
		return ret; 
	}
	
	public static int subscribe(Context context, String _deviceName, IUpdateListener _listener)
	{
		int ret=0;
		String feedId = ServerAPIManager.GetFeedId(_deviceName);
		
		SubscribeCallback.setListener(_listener);
		final Intent intent = new Intent(context, MQTTService.class);
		intent.putExtra(FEEDID, feedId);
		
		 context.startService(intent);
		
 		try {
            MqttClient mqttClient = new MqttClient(ConnectionMgr.MQTT_SERVER_URL, "android-client", new MemoryPersistence());
            ConnectionMgr.getConnectionInstance(ConnectionMgr.MQTT_SERVER_URL);
            MqttConnectOptions options = new MqttConnectOptions();
                        
            options.setUserName(UserManager.GetAuthKey());
            options.setPassword("default".toCharArray());
            mqttClient.setCallback(new SubscribeCallback());
            mqttClient.connect(options);
            Log.i("", "feedId = "+feedId);
            mqttClient.subscribe(feedId);		 
        } catch (MqttException e) {
            Toast.makeText(context.getApplicationContext(), "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
		return ret;
	}
}