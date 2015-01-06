package com.cresprit.mqtt_sdk;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

import android.util.Log;


public class SubscribeCallback implements MqttCallback {
	static IUpdateListener listener;
    
    public SubscribeCallback(IUpdateListener _listener) {
    	listener = _listener;
    }


	public void messageArrived(String topic, MqttMessage message)
			throws Exception {
		// TODO Auto-generated method stub
		Log.i("",""+message.getPayload().toString());
		listener.update(new String(message.getPayload()));
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		
	}


	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub
		
	}
	

}
