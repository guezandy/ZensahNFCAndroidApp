package com.example.zensah;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class ListAdapterMainMenu extends BaseExpandableListAdapter {

  private final SparseArray<Group> groups;
  public LayoutInflater inflater;
  public Activity activity;

  public ListAdapterMainMenu(Activity act, SparseArray<Group> groups) {
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
    final String children = (String) getChild(groupPosition, childPosition);
    final Group parent1 =  (Group) getGroup(groupPosition);
    TextView text = null;
    Context c = MyApplication.getAppContext();
    System.out.println("Geting throught the list adapter");
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.detailsformainmenu, null);
    }
    text = (TextView) convertView.findViewById(R.id.secondLine);
    if(childPosition == 0){
    	convertView.setBackground(c.getResources().getDrawable(R.drawable.no_banner));
    	text.setText(" ");
    }
    else if(childPosition == 1){
    	convertView.setBackground(c.getResources().getDrawable(R.drawable.no_banner));
    	text.setText(" ");
    }
    convertView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
    	  if(childPosition == 0){
    	        Intent zipInt = new Intent(activity, WorkoutActivity.class);
    			zipInt.putExtra("openstore", parent1.string);
    			activity.startActivity(zipInt);
              //startActivity(new Intent(activity, TagHistoryActivity.class));
              //finish();
    	  }
    	  else{
    		  String strURL=parent1.url;
    		  Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(strURL));
    		  activity.startActivity(myIntent);
    	  }
        //Toast.makeText(activity, children,
          //  Toast.LENGTH_SHORT).show();
      }
    });
    return convertView;
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
    System.out.println("Inflated listrow group");
    
    Group group = (Group) getGroup(groupPosition);
    Context c = MyApplication.getAppContext();
    System.out.println("Get Context");
    ((CheckedTextView) convertView).setChecked(isExpanded);
    System.out.println("Checking group position");
    if(groupPosition == 0){
        ((CheckedTextView) convertView).setBackground(c.getResources().getDrawable(R.drawable.recent_banner));
        ((CheckedTextView) convertView).setText(" ");
    }
    else if (groupPosition==1){
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
    else if (groupPosition==5){
        ((CheckedTextView) convertView).setBackground(c.getResources().getDrawable(R.drawable.zensah_legss));
        ((CheckedTextView) convertView).setText(" ");
    } 
//    else if (groupPosition==6){
//        ((CheckedTextView) convertView).setBackground(c.getResources().getDrawable(R.drawable.no_banner));
//        ((CheckedTextView) convertView).setText(" ");
//    }
//    else if (groupPosition==7){
//        ((CheckedTextView) convertView).setBackground(c.getResources().getDrawable(R.drawable.no_banner));
//        ((CheckedTextView) convertView).setText(" ");
//    } 
    else {
        ((CheckedTextView) convertView).setBackground(c.getResources().getDrawable(R.drawable.no_banner));
        ((CheckedTextView) convertView).setText(group.string);
    }
    System.out.println("About to return the convertView");
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