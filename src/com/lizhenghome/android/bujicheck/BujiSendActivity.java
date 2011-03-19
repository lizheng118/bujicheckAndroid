package com.lizhenghome.android.bujicheck;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class BujiSendActivity extends Activity implements LocationListener{
    /** Called when the activity is first created. */
	
	private ImageButton safeBtn;
	
	private ImageButton dangerBtn;
	
	private LocationManager mLocationManager;
	
	private double latitude = -1;
	
	private double longitude = -1;
	
	private boolean isLocationAvailable;
	
	private static final int DIALOG_PROGRESS_KEY = 1;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        
        safeBtn = (ImageButton)findViewById(R.id.safe_button);
        dangerBtn = (ImageButton)findViewById(R.id.danger_button);
        
        safeBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				confirmSelection(Constants.BUJI_STATUS_SAFE);
			}
		});
        
        dangerBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				confirmSelection(Constants.BUJI_STATUS_DANGER);
			}
		});
    }
    
    private void confirmSelection(int bujiStatus) {
    	final int status = bujiStatus;
    	AlertDialog dialog = new AlertDialog.Builder(BujiSendActivity.this).
	    	setPositiveButton(R.string.confirm_send, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					sendBujiStatus(status);
				}
			}).
			setNegativeButton(R.string.confirm_cancel, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// do nothing
				}
			}).
			create();
    	
    	switch(bujiStatus) {
    	case Constants.BUJI_STATUS_SAFE:
    		dialog.setTitle(R.string.confirm_message_safe);
    		dialog.show();
    		break;
    	case Constants.BUJI_STATUS_DANGER:
    		dialog.setTitle(R.string.confirm_message_danger);
    		dialog.show();
    		break;
    	default:
    	}
    }
    
    @Override
	protected void onPause() {
		if (mLocationManager != null) {
			mLocationManager.removeUpdates(this);
		}
		super.onPause();
    }

	@Override
	protected void onResume() {    	
		if (mLocationManager != null) {
			mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		}
		super.onResume();
	}

	private void sendBujiStatus(int bujiStatus) {
    	final int status = bujiStatus;

	    showDialog(DIALOG_PROGRESS_KEY);
	    	    	
    	Thread t = new Thread() {
    		public void run() {
    	    	
    			//Looper.prepare(); //For Preparing Message Pool for the child Thread
    			
    			HttpClient client = new DefaultHttpClient();
    			HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
    			HttpResponse response;
    			JSONObject json = new JSONObject();
    			
    			try {
	    			HttpPost post = new HttpPost(Constants.BUJI_SEND_URL);
	    			json.put(Constants.PARAM_PHONE_NUMBER, "08033491218");
	    			json.put(Constants.PARAM_BUJI_STATUS, String.valueOf(status));
	    				    			 
	    			if(isLocationAvailable) {
	    				json.put(Constants.PARAM_LOCATION, latitude + "," + longitude);
	    			} else {
	    				json.put(Constants.PARAM_LOCATION, "");
	    			}
	    			StringEntity se = new StringEntity("JSON: " + json.toString());
	    			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
	    			post.setEntity(se);
	    			response = client.execute(post);
	    			
	    			if(response!=null){
	    				InputStream in = response.getEntity().getContent(); //Get the data in the entity
	    				BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    				
	    				String line = br.readLine();
	    				while(line != null) {
	    					line = br.readLine();
	    				}
	    			}
    			} catch(Exception ex) {
    				ex.printStackTrace();
    			}
    			//Looper.loop(); //Loop in the message queue
    			
    			dismissDialog(DIALOG_PROGRESS_KEY);
    			runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
		    			Toast.makeText(BujiSendActivity.this, R.string.progressed, Toast.LENGTH_SHORT).show();						
					}
				});
    		}
    	};
    	t.start();
    }

	@Override
	public void onLocationChanged(Location location) {
		this.latitude = location.getLatitude();
		this.longitude = location.getLongitude();
	}

	@Override
	public void onProviderDisabled(String provider) {		
	}

	@Override
	public void onProviderEnabled(String provider) {		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		this.isLocationAvailable = (status == LocationProvider.AVAILABLE);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		ProgressDialog dialog = new ProgressDialog(BujiSendActivity.this);
		String progressMsg = getString(R.string.progressing);
        dialog.setMessage(progressMsg);
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        return dialog;
	}
}