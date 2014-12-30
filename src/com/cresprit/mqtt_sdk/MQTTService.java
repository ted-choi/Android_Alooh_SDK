package com.cresprit.mqtt_sdk;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


public class MQTTService extends Service {


    /* In a real application, you should get an Unique Client ID of the device and use this, see
    http://android-developers.blogspot.de/2011/03/identifying-app-installations.html */
    public static final String clientId = "android-client";
    private static final String FEEDID = "com.cresprit.mqtt_sdk.feedid";
    private MqttClient mqttClient;
    private MqttConnectOptions options;
    private String mFeedId = null;
    private static IUpdateListener mListener = null;
    
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
    		mFeedId = intent.getStringExtra(FEEDID);
        try {
            mqttClient = new MqttClient(ConnectionMgr.MQTT_SERVER_URL, clientId, new MemoryPersistence());
            ConnectionMgr.getConnectionInstance(ConnectionMgr.MQTT_SERVER_URL);
            options = new MqttConnectOptions();
                        
            options.setUserName(UserManager.GetAuthKey());
            options.setPassword("default".toCharArray());
            mqttClient.setCallback(new SubscribeCallback());
            mqttClient.connect(options);
            Log.i("", "feedId = "+mFeedId);
            mqttClient.subscribe(mFeedId);

        } catch (MqttException e) {
            Toast.makeText(getApplicationContext(), "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        try {
            mqttClient.disconnect();
        } catch (MqttException e) {
            Toast.makeText(getApplicationContext(), "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
    
    public static void setCallback(IUpdateListener _listener)
    {
    	mListener = _listener;
    }
}
