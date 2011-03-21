package com.lizhenghome.android.bujicheck;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchResultArrayAdaptor extends ArrayAdapter<BujiInfoItem> {

	private LayoutInflater inflater;

	private int textViewResourceId;

	private List<BujiInfoItem> items;
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	public SearchResultArrayAdaptor(Context context, int textViewResourceId,
			List<BujiInfoItem> items) {
		super(context, textViewResourceId, items);

		this.textViewResourceId = textViewResourceId;
		this.items = items;
		
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if(convertView != null) {
			view = convertView;
		} else {
			view = inflater.inflate(textViewResourceId, null);
		}
		
		BujiInfoItem item = items.get(position);
		
		ImageView imageView = (ImageView)view.findViewWithTag("icon");
		if("1".equals(item.getBujiStatus())) {
			imageView.setImageResource(R.drawable.danger_icon);	
		} else {
			imageView.setImageResource(R.drawable.safty_icon);				
		}
		
		TextView textView = (TextView)view.findViewWithTag("text");
		if(item.getSendDate() != null) {
			textView.setText(df.format(item.getSendDate()));
		} else {
			textView.setText("");
		}
		return view;
	}

}
