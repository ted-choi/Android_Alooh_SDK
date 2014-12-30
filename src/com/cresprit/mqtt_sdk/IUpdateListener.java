package com.cresprit.mqtt_sdk;


public interface IUpdateListener
{
	public int SHOW_DIALOG  = 0;
	public int REMOVE_DIALOG = 1;
	
	void update(String message);
}