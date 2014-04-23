package com.example.zensah;

import com.example.zensah.Entity;


import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

 

public class CustomListAdapter extends BaseAdapter {

	private ArrayList<Store> listData;

	private LayoutInflater layoutInflater;

	public CustomListAdapter(Context context, ArrayList<Store> listData) {
		this.listData = listData;
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.list_row_layout, null);
			holder = new ViewHolder();
			holder.brandView = (TextView) convertView.findViewById(R.id.brand);
			//holder.priceView = (TextView) convertView.findViewById(R.id.price);
			holder.imageView = (ImageView) convertView.findViewById(R.id.thumbImage);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Store closetItem = (Store) listData.get(position);
		
//		System.out.println("Entity brand: "+closetItem.get("Brand"));
//		System.out.println("Entity price: "+closetItem.get("Price"));
		holder.brandView.setText("Target: " +closetItem.storename);
		//holder.priceView.setText("Reps: " + closetItem.storeurl);
		

		if (holder.imageView != null) {
			System.out.println("Get Store url to image thingyyy");
			new ImageDownloaderTask(holder.imageView).execute(closetItem.getStoreurl());
		}

		return convertView;
	}

	static class ViewHolder {
		TextView brandView;
		//TextView priceView;
		ImageView imageView;
	}
}
