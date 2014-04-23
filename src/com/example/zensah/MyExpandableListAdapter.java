package com.example.zensah;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class MyExpandableListAdapter extends BaseExpandableListAdapter {

  private final SparseArray<GroupItems> groups;
  public LayoutInflater inflater;
  public Activity activity;

  public MyExpandableListAdapter(Activity act, SparseArray<GroupItems> groups) {
    activity = act;
    this.groups = groups;
    inflater = act.getLayoutInflater();
  }

  @Override
  public Object getChild(int groupPosition, int childPosition) {
    return groups.get(groupPosition).children.get(childPosition);
  }

  @Override
  public long getChildId(int groupPosition, int childPosition) {
    return 0;
  }

  @Override
  public View getChildView(int groupPosition, final int childPosition,
      boolean isLastChild, View convertView, ViewGroup parent) {
    final Entity children = (Entity) getChild(groupPosition, childPosition);
    //ViewHolder2 holder;
    TextView text = null;
    TextView text2 = null;
    ImageView image = null;
/*    if (convertView == null) {
      convertView = inflater.inflate(R.layout.listrow_details, null);
      holder = new ViewHolder2();
		holder.TitleView = (TextView) convertView.findViewById(R.id.secondLine);
		//holder.priceView = (TextView) convertView.findViewById(R.id.price);
		holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
		convertView.setTag(holder);
	} else {
		holder = (ViewHolder2) convertView.getTag();
    }
    holder.PriceView.setText(children.getPrice());
    if (children.getImageUrl() != null) {
		new ImageDownloaderTask(holder.imageView).execute(children.getImageUrl());
	}
    */
    if (convertView == null) {
        convertView = inflater.inflate(R.layout.listrow_details, null);
    }
    text = (TextView) convertView.findViewById(R.id.secondLine);
    text.setText(children.getPrice());
    text2 = (TextView) convertView.findViewById(R.id.firstLine);
    text2.setText(children.getTitle());
    System.out.println("text2 "+ text2.getText());
    image = (ImageView) convertView.findViewById(R.id.icon);
/*    if (children.getImageUrl()!=null && !children.loaded) {
		new ImageDownloaderTask(image).execute(children.getImageUrl());
		children.setLoaded(true);
	    System.out.println("GroupPosition "+groupPosition);
	    System.out.println("ChildPosition "+childPosition);
	} */ 
    UrlImageViewHelper.setUrlDrawable(image, children.getImageUrl());
	convertView.setBackgroundColor(Color.parseColor("#F7F7F0"));
//    image = (ImageView) convertView.findViewById(R.id.icon);
//    text = (TextView) convertView.findViewById(R.id.secondLine);
 //   text.setText(children.getPrice());
	
    convertView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(activity, children.getId(),
            Toast.LENGTH_SHORT).show();
        Intent zipInt = new Intent(activity, WorkoutActivity.class);
		zipInt.putExtra("nfcId", children.getId());
		activity.startActivity(zipInt);
      }
    });
    
    return convertView;
  }

	static class ViewHolder2 {
		TextView TitleView;
		TextView PriceView;
		ImageView imageView;
	}
  
  @Override
  public int getChildrenCount(int groupPosition) {
    return groups.get(groupPosition).children.size();
  }

  @Override
  public Object getGroup(int groupPosition) {
    return groups.get(groupPosition);
  }

  @Override
  public int getGroupCount() {
    return groups.size();
  }

  @Override
  public void onGroupCollapsed(int groupPosition) {
    super.onGroupCollapsed(groupPosition);
  }

  @Override
  public void onGroupExpanded(int groupPosition) {
    super.onGroupExpanded(groupPosition);
  }

  @Override
  public long getGroupId(int groupPosition) {
    return 0;
  }
  
  
  @Override
  public View getGroupView(int groupPosition, boolean isExpanded,
      View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.listrow_group, null);
    }
    GroupItems group = (GroupItems) getGroup(groupPosition);
    Context c = MyApplication.getAppContext();
    ((CheckedTextView) convertView).setChecked(isExpanded);
    
    if(groupPosition == 0){
        ((CheckedTextView) convertView).setBackground(c.getResources().getDrawable(R.drawable.recent_banner));
        ((CheckedTextView) convertView).setText(" ");
    }
    else if(groupPosition == 1){
        ((CheckedTextView) convertView).setBackground(c.getResources().getDrawable(R.drawable.zensah_core));
        ((CheckedTextView) convertView).setText(" ");
    }
    else if (groupPosition==2){
        ((CheckedTextView) convertView).setBackground(c.getResources().getDrawable(R.drawable.zensah_arms));
        ((CheckedTextView) convertView).setText(" ");
    }
    else if (groupPosition==3){
        ((CheckedTextView) convertView).setBackground(c.getResources().getDrawable(R.drawable.zensah_back));
        ((CheckedTextView) convertView).setText(" ");
    }
    else if (groupPosition==4){
        ((CheckedTextView) convertView).setBackground(c.getResources().getDrawable(R.drawable.zensah_chest));
        ((CheckedTextView) convertView).setText(" ");
    }
    else {
        ((CheckedTextView) convertView).setBackground(c.getResources().getDrawable(R.drawable.zensah_legss));
        ((CheckedTextView) convertView).setText(group.string);
    }
    return convertView;
  }

  @Override
  public boolean hasStableIds() {
    return false;
  }

  @Override
  public boolean isChildSelectable(int groupPosition, int childPosition) {
    return false;
  }
} 