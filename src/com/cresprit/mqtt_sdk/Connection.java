package com.cresprit.mqtt_sdk;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

class Connection {

    private MqttConnectOptions options;
    private MqttClient client;
    private static String serverUri;
    private String clientID;
    private String userName;
    private String feed;
    private String message;
    
    public Connection(String _serverUri) {
        try {
        	serverUri = _serverUri;
            client = new MqttClient(serverUri, MqttClient.generateClientId(), new MemoryPersistence());
            Log.i("","serverUri : "+_serverUri);
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public boolean connect() {

        try {
            options = new MqttConnectOptions();
            options.setUserName(userName);
            Log.i("","userName : "+userName);
            options.setPassword("default".toCharArray());
            options.setCleanSession(true);
            options.setKeepAliveInterval(20000);
            client.connect(options);
            
            return true;
            
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void disconnect(){
    	try {
    		if(client.isConnected())
    			client.disconnect();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    public void publish(String _feedId){// throws MqttException {
        final MqttTopic topic = client.getTopic(_feedId);				

        final MqttMessage msg = new MqttMessage(String.valueOf(message).getBytes());
        try{
        topic.publish(msg);
        }catch(Exception e)
        {
        	
        }

        System.out.println("Published data. Topic: " + topic.getName() + "  Message: " + message);
    }

    public void subscribe(String _feedID, IUpdateListener _listener) throws MqttException {
    	  
          options = new MqttConnectOptions();
          options.setUserName(UserManager.GetAuthKey());
          options.setPassword("default".toCharArray());
          client.setCallback(new SubscribeCallback(_listener));
          client.connect(options);

          //Subscribe to all subtopics of homeautomation
          client.subscribe(_feedID);
    }
    public static int createRandomNumberBetween(int min, int max) {

        return new Random().nextInt(max - min + 1) + min;
    }


    public void setUsername(String _username)
    {
    	this.userName = _username;
    }
    
    public void setClientId(String _clientId)
    {
    	this.clientID = _clientId;
    }
    
    public MqttTopic getTopic(String _feedId)
    {
    	MqttTopic topic = client.getTopic(_feedId);
    	return topic;
    }
    
    public void setFeed(String _feed)
    {
    	this.feed = _feed;
    }
    
    public void setMessage(String _message)
    {
    	this.message = _message;
    }
}
