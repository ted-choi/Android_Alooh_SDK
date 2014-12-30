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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

class ServerAPIManager{
	
	public static String GetFeedId(String _deviceName)
	{
		int ret = 0;
		String feedId = null;
		
		JSONObject data = new JSONObject();
		JSONObject json = new JSONObject();
		
		try {
			data.put("name"	, _deviceName);
			data.put("type",  "3");
			json.put("data", data);
		} catch (JSONException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		HttpClient client = new DefaultHttpClient();
		final HttpParams params = client.getParams();
		
		HttpConnectionParams.setConnectionTimeout(params, 10 * 1000);
		HttpConnectionParams.setSoTimeout(params, 10 * 1000);

		HttpPost request = new HttpPost();
		request.setHeader("Content-Type", "application/json");
		request.setHeader("Authorization", "Bearer "+UserManager.GetAuthKey());

		try {
			request.setURI(new URI(ConnectionMgr.SERVER_API_GET_DEVICE_URL));
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
			HttpResponse response = client.execute(request);
			int responseCode = 0;
			HttpEntity responseEntity = response.getEntity();
			Log.i("", "get http response: STATUS_CODE: " + response.getStatusLine().getStatusCode()+response.getStatusLine().getReasonPhrase());
			response.getStatusLine().getReasonPhrase();
			if(responseCode == 406 || responseCode == 401 || responseCode == 404 || responseCode == 500)
			{
				
				return Integer.toString(responseCode);
			}
			
			HttpEntity entity = response.getEntity();
			String jsonStr = EntityUtils.toString(entity);
			
			try {
				JSONObject resObj = new JSONObject(jsonStr);
				JSONObject resData = resObj.getJSONObject("data");
				JSONArray keyArray = resData.getJSONArray("keys");
				JSONObject keyItem = keyArray.getJSONObject(0);

				feedId = keyItem.getString("feed_id");

				//username = resData.getString("id");
				Log.i("","Get Feed ID  : "+feedId);
				return feedId;
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ret = -1;
				return Integer.toString(ret);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret = -2;
			return Integer.toString(ret);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret = -3;
			return Integer.toString(ret);
		}
	}
		
	
}