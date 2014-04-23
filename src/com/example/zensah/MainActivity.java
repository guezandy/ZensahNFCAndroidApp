package com.example.zensah;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends Activity {

	public static Context appContext;

	
	SparseArray<Group> groups = new SparseArray<Group>();
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
		appContext = getApplicationContext();
		
		

	    createData();
	    System.out.println("Data created");
	    final ListView listView = (ListView) findViewById(R.id.listView3);
	    if (listView == null) { Log.w("", "TextView is null"); }
	    System.out.println("About to populate lists");
		final Context context = this.getApplicationContext();
		CustomListAdapter clist=  new CustomListAdapter(this, storesx);
		//  System.out.println(lv.toString());
		listView.setAdapter(clist);
		
		listView.setOnItemClickListener(new OnItemClickListener() {	
		@Override
		public void onItemClick(AdapterView<?> a, View v, int position, long id) {
			Object o = listView.getItemAtPosition(position);
			Store closetData = (Store) o;

	        Intent zipInt = new Intent(MainActivity.this, WorkoutActivity.class);
			zipInt.putExtra("type", closetData.getStorename());
			System.out.println("Main hit");
			MainActivity.this.startActivity(zipInt);
			System.out.println("Next intent");
			Toast.makeText(context, "Selected :"+position,
					Toast.LENGTH_LONG).show();
				}
	});
		SharedPreferences prefs = this.getSharedPreferences("com.example.zensah", Context.MODE_PRIVATE);
		//String report = prefs.getString("current", "");
        //InternalData.addItem(prefs, CART_KEY, "Lacoste~$89.5~http://slimages.macys.com/is/image/MCY/products/8/optimized/1242258_fpx.tif|");
			InternalData.addItem(prefs, "count", "8");
			System.out.println("Just added to internal data");
	  }
/*		@Override
		public void onResume() {
			super.onResume();
			Bundle bundle = getIntent().getExtras();
			//if(bundle.getString("openstore")!=null) {
				//Client kinveyClient = new Client.Builder(KINVEY_KEY, KINVEY_SECRET_KEY, this).build();
				//getEntity(bundle.getString("nfcId"), KINVEY_ENTITY_COLLECTION_KEY, KINVEY_UPDATE_ZIP_ACTIVITY_CASE,kinveyClient);
				//mTextField.setVisibility(View.GONE);
				//mEnableWriteButton.setVisibility(View.GONE);
				
			//}
		}*/

	public String[] grouplist4 =  {"Vince Camuto", "Theory", "Lulu Lemon","Gap", "American Apparel", "Salvatore Ferragamo", "J Crew","Express"};
	
	Store s1 = new Store("Recent","http://i1287.photobucket.com/albums/a632/guezandy/core_zps10fcc2da.jpg");
	Store s2 = new Store("Core","http://i1287.photobucket.com/albums/a632/guezandy/core_zps10fcc2da.jpg");
	Store s3 = new Store("Arms","http://i1287.photobucket.com/albums/a632/guezandy/arms_zps3f2ce338.jpg");
	Store s4 = new Store("Back","http://i1287.photobucket.com/albums/a632/guezandy/back_zps89154c90.jpg");
	Store s5 = new Store("Chest","http://i1287.photobucket.com/albums/a632/guezandy/chest_zpsdf544c57.jpg");
	Store s6 = new Store("Legs","http://i1287.photobucket.com/albums/a632/guezandy/legs_zps02f5c92e.jpg");
//	Store s7 = new Store("J Crew","http://www.jcrew.com/index.jsp");
//	Store s8 = new Store("Express","http://www.express.com/");
	ArrayList<Store> storesx = new ArrayList<Store>();
	
	public void populatelists2(){
		storesx.add(s1);
		storesx.add(s2);
		storesx.add(s3);
		storesx.add(s4);
		storesx.add(s5);
		storesx.add(s6);
	//	storesx.add(s7);
		//storesx.add(s8);
	}
	  public void createData() {
		 populatelists2();
	    for (int j = 0; j < 6; j++) {
	      Group group = new Group(storesx.get(j).getStorename(),storesx.get(j).getStoreurl());
	      for (int i = 0; i < 2; i++) {
	        if(i==0){
	        	group.children.add("Name");
	        }
	        else{
	        	group.children.add("Url");
	        }
	      }
	      groups.append(j, group);
	    }
	  }
	  
	  
		public void updateListView(final Store[] entities, final ListView lv){
			//ListView lv2 = (ListView) findViewById(R.id.custom_list);
			System.out.println("first Brand= "+ entities[0].getStorename());
			final Context context = this.getApplicationContext();
			System.out.println("arraysize " +entities.length);
			ArrayList<Store> arrayResults = new ArrayList<Store>();
			for(Store x:entities){
				arrayResults.add(x);
			}
			//System.out.println("brand= "+ arrayResults.get(0).get("Brand"));
			
			
			CustomListAdapter clist=  new CustomListAdapter(context, arrayResults);
			//  System.out.println(lv.toString());
			lv.setAdapter(clist);
			//lv.setOnItemClickListener(new OnItemClickListener() {	
//				@Override
//				public void onItemClick(AdapterView<?> a, View v, int position, long id) {
//					Object o = lv.getItemAtPosition(position);
//					Entity closetData = (Entity) o;
//					Toast.makeText(context, "Selected :" + " " + closetData,
//							Toast.LENGTH_LONG).show();
//						}
			//});
			
		}
	
	
		public static void addItem(SharedPreferences prefs, String key, String toAdd){
			// manipulate new Entity
			Editor editor = prefs.edit();
			editor.putString(key, toAdd);
			editor.commit();
		}
	
/*	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		appContext = getApplicationContext();

		
		kinveyClient = new Client.Builder(appKey, appSecret
			    , this.getApplicationContext()).build();*/
		/*
		Button goToCloset = (Button) findViewById(R.id.goToClosetButton);
		goToCloset.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainMenu.this, GoToClosetActivity.class));
			}
		});
		*/
//		Button zipButton = (Button) findViewById(R.id.zipButtonAct);
//		zipButton.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Intent goToZip = new Intent(MainMenu.this, ZipActivity.class);
//				goToZip.putExtra("nfcId", "n");
//				startActivity(goToZip);
//			}
//		});
		
		/*
		Button tagHistoryButton = (Button) findViewById(R.id.zipButton);
		tagHistoryButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainMenu.this, TagsHistoryActivity.class));
			}
		});
		/*
SUPEEEERRR IMPORTANT LOGOUT BUTTON ON ACTION BAR!!!
		Button logout = (Button) findViewById(R.id.logout);
		logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				kinveyClient.user().logout().execute();
				startActivity(new Intent(MainMenu.this, MainActivity.class));
			}
		});
		*/
	//}
	
//		@Override
//		public boolean onCreateOptionsMenu(Menu menu) {
//			// Inflate the menu; this adds items to the action bar if it is present.
//			getMenuInflater().inflate(R.menu.zip, menu);
//			return true;
//		}

		
	    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
		@Override
	    protected void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
	    }
}

//class MyTabsListener implements ActionBar.TabListener {
//	public Fragment fragment;
//
//	public MyTabsListener(Fragment fragment) {
//		this.fragment = fragment;
//	}
//
//	@Override
//	public void onTabReselected(Tab tab, FragmentTransaction ft) {
//		Toast.makeText(MainMenu.appContext, "Reselected!", Toast.LENGTH_LONG).show();
//	}
//
//	@Override
//	public void onTabSelected(Tab tab, FragmentTransaction ft) {
//		ft.replace(R.id.fragment_container, fragment);
//	}
//
//	@Override
//	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
//		ft.remove(fragment);
//	}
//}


