package com.lizhenghome.android.bujicheck;

import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ZoomControls;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class BujiDetailActivity extends MapActivity {

	MapView mapView;
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.detail);
		mapView = (MapView)findViewById(R.id.mapview);
        MapController c = mapView.getController();
        c.setZoom(15);
        
        String position = getIntent().getStringExtra("position");
        if(position != null && position.indexOf(',') > 0) {
        	String[] positionArray = position.split(",");
        	if(positionArray.length == 2) {
        		 c.setCenter(new GeoPoint(Integer.parseInt(positionArray[0]),Integer.parseInt(positionArray[1])));
        	}
         }
       

	}

}
