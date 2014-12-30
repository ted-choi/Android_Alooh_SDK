package com.cresprit.mqtt_sdk;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.lang.String;
import java.util.ArrayList;

import org.eclipse.paho.client.mqttv3.MqttException;

class ConnectionMgr{
//	public static final String MQTT_SERVER_URL = "tcp://pino.io:1883";
//	public static String SERVER_API_LOGIN_URL = "http://pino.io:50001/api/v1/session";
//	public static String SERVER_API_CREATE_USER_URL = "http://pino.io:50001/api/v1/user/new";
//	public static String SERVER_API_RESET_USER_URL = "http://pino.io:50001/api/v1/user/reset";
//	public static String SERVER_API_GET_DEVICE_URL = "http://pino.io:50001/api/v1/devices/name";
//	public static String SERVER_API_REGIST_DEVICE_NAME_CHECK_URL = "http://pino.io:50001/api/v1/devices/check";
//	public static String SERVER_API_GET_DEVICE_LIST_URL = "http://pino.io:50001/api/v1/devices/name/list";
	
	public static String SERVER_API_LOGIN_URL = "http://api.alooh.io:50001/api/v1/session";
	public static String SERVER_API_CREATE_USER_URL = "http://api.alooh.io:50001/api/v1/user/new";
	public static String SERVER_API_RESET_USER_URL = "http://api.alooh.io:50001/api/v1/user/reset";
	public static String SERVER_API_REGIST_DEVICE_NAME_CHECK_URL = "http://api.alooh.io:50001/api/v1/devices/check";
	public static String SERVER_API_REGIST_DEVICE_AUID_URL = "http://api.alooh.io:50001/api/v1/products/542271621846451c43bae192/device/new";	
	public static String SERVER_API_GET_DEVICE_LIST_URL = "http://api.alooh.io:50001/api/v1/devices/name/list";
	public static String SERVER_API_GET_DEVICE_URL = "http://api.alooh.io:50001/api/v1/devices/name";	
    private static final String PROPERTY_SERVER_URL = "server_url";
    private static final String PROPERTY_FEED_ID = "feed_id";
    private static final String PROPERTY_ACCESS_KEY = "access_key";
    public static final String MQTT_SERVER_URL = "tcp://api.alooh.io:1883";
	static Connection connection;
	

	public static String MSG_RECEIVE = "com.cresprit.mqtt_sdk.ConnectionMgr.receive_message";

	private Context mContext= null;
	
	public ConnectionMgr(Context ctx)
	{
		mContext = ctx;
			connection = new Connection(MQTT_SERVER_URL);

	}
	
	
	public static Connection getConnectionInstance(String serverUri)
	{
		connection = new Connection(serverUri);
		return connection;
	}
	
}