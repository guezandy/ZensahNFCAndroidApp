package com.example.zensah;

import java.util.ArrayList;
import java.util.List;
import com.example.zensah.*;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
public class WorkoutActivity extends Activity {
	private NfcAdapter mNfcAdapter;
	private Button mEnableWriteButton;
	private EditText mTextField;
	private ProgressBar mProgressBar;
	private TextView count;
	public Button backB;
	public Button start;
	public Button home;	
	public String oye;
	public int repCount = 10;
	public static final String CURRENT_SNAG = "current";
	public final static String PACKAGE_NAME = "com.example.zensah";
	public final Entity ex2 = new Entity("http://i1287.photobucket.com/albums/a632/guezandy/crunches_zps3833737b.jpg", "Second workout", "15");

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout);
		final Context context = this.getApplicationContext();
		home = (Button) findViewById(R.id.zipBack);
		mTextField = (EditText) findViewById(R.id.nfcWriteString);
		mProgressBar = (ProgressBar) findViewById(R.id.pbWriteToTag);
		mEnableWriteButton = (Button) findViewById(R.id.writeToTagButton);
		count = (TextView) findViewById(R.id.count);
		start = (Button) findViewById(R.id.purchaseButton);
		start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//saveEntity(entity, "Favorites", kinveyClient);
				Toast.makeText(getApplicationContext(), "WORKOUT STARTED!", Toast.LENGTH_SHORT).show();
				}
			//updateZipActivityEntity(ex2);
			
		});
		home.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(WorkoutActivity.this, MainActivity.class);
				WorkoutActivity.this.startActivity(intent);	
			}
		});
		mProgressBar.setVisibility(View.GONE);
		//mEnableWriteButton.setVisibility(View.GONE);
		//mTextField.setVisibility(View.GONE);
		Bundle bundle = getIntent().getExtras();
		oye = bundle.getString("type");
		if(bundle.getString("type")!=null) {
			//Toast.makeText(context, "Selected :"+bundle.getString("type"),
			//		Toast.LENGTH_LONG).show();
		}
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if (mNfcAdapter == null) {
			Toast.makeText(this, "Sorry, NFC is not available on this device", Toast.LENGTH_SHORT).show();
			finish();
		}
		Entity ex = new Entity("http://i1287.photobucket.com/albums/a632/guezandy/crunches_zps3833737b.jpg", "A crunch", "8");
		updateZipActivityEntity(ex);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.workout, menu);
		return true;
	}
	
	
	public void updateZipActivityEntity(final Entity entity){
		//kinveyClient.push().initialize(getApplication());
		//TextView idText = (TextView) findViewById(R.id.textViewId);
		TextView brand = (TextView) findViewById(R.id.textViewBrand);
		//TextView price = (TextView) findViewById(R.id.textViewPrice);
	
		ProgressBar mProgressBar = (ProgressBar) findViewById(R.id.pbWriteToTag);
		mProgressBar.setVisibility(View.GONE);
		
		SharedPreferences prefs = this.getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE);

		ArrayList<Entity> repCounter = extractUserData("count", prefs, ",",",");
		int counting = Integer.parseInt(repCounter.get(0).getId());
		count.setText("Reps: "+repCounter.get(0).getId()+" of "+entity.getPrice());
		//count.setText(repCounter.get(0).getId());
		counting--;
		System.out.println("INTERNAL DATA: "+repCounter.get(0).getId()+"Count: "+count);
		InternalData.addItem(prefs, "count", Integer.toString(counting));

		
		
		//Button mPurchaseButton = (Button) findViewById(R.id.purchaseButton);
		Button mFavoriteButton = (Button) findViewById(R.id.favoriteButton);
	
		System.out.println("Inside if statement " +entity.getId());  
		//idText.setText("Id: " + entity.getType());
		//price.setText("Reps: "+entity.getPrice());
		brand.setText("Description: "+entity.getType());
//		SharedPreferences prefs = this.getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE);
		//InternalData.addItem(prefs, CURRENT_SNAG, entity.toString());
//		mPurchaseButton.setOnClickListener(new OnClickListener() {
//				
//			@Override
//			public void onClick(View v) {
//				saveEntity(entity, "Closet", kinveyClient);
//				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(entity.get("PurchaseUrl").toString()));
//				startActivity(browserIntent);
//			}
//		});
		mFavoriteButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//saveEntity(entity, "Favorites", kinveyClient);
				Toast.makeText(getApplicationContext(), "Added to Favorites", Toast.LENGTH_SHORT).show();
				}
		});
		ImageView mImageView = (ImageView) findViewById(R.id.itemImage);
		if(mImageView != null) {
				new ImageDownloaderTask(mImageView).execute( entity.getImageUrl());
		}
		//else  {}
		//saveEntity(entity, KINVEY_TAGS_KEY, kinveyClient);
	}

	private boolean isWriteReady = false;

	/**
	 * Enable this activity to write to a tag
	 * 
	 * @param isWriteReady
	 */
	public void setTagWriteReady(boolean isWriteReady) {
		this.isWriteReady = isWriteReady;
		if (isWriteReady) {
			IntentFilter[] writeTagFilters = new IntentFilter[] { new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED) };
			mNfcAdapter.enableForegroundDispatch(WorkoutActivity.this, NfcUtils.getPendingIntent(WorkoutActivity.this),
					writeTagFilters, null);
		} else {
			// Disable dispatch if not writing tags
			mNfcAdapter.disableForegroundDispatch(WorkoutActivity.this);
		}
		mProgressBar.setVisibility(isWriteReady ? View.VISIBLE : View.GONE);
	}

	@Override
	public void onNewIntent(Intent intent) {
		// onResume gets called after this to handle the intent
		setIntent(intent);
	}

	@Override
	public void onResume() {
		super.onResume();
		Bundle bundle = getIntent().getExtras();
		if (isWriteReady && NfcAdapter.ACTION_TAG_DISCOVERED.equals(getIntent().getAction())) {
			processWriteIntent(getIntent());
		} else if (!isWriteReady
				&& (NfcAdapter.ACTION_TAG_DISCOVERED.equals(getIntent().getAction()) || NfcAdapter.ACTION_NDEF_DISCOVERED
						.equals(getIntent().getAction()))) {
			mTextField.setVisibility(View.GONE);
			mEnableWriteButton.setVisibility(View.GONE);
			processReadIntent(getIntent());
		} else if(bundle.getString("type")!=null) {
			//Client kinveyClient = new Client.Builder(KINVEY_KEY, KINVEY_SECRET_KEY, this).build();
			//getEntity(bundle.getString("nfcId"), KINVEY_ENTITY_COLLECTION_KEY, KINVEY_UPDATE_ZIP_ACTIVITY_CASE,kinveyClient);
			mTextField.setVisibility(View.GONE);
			mEnableWriteButton.setVisibility(View.GONE);
			
//			SharedPreferences prefs = this.getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE);
//
//			ArrayList<Entity> repCounter = extractUserData("count", prefs, ",",",");
//			int count = Integer.parseInt(repCounter.get(0).getId());
//			count--;
//			System.out.println("INTERNAL DATA: "+repCounter.get(0).getId()+"Count: "+count);
//			InternalData.addItem(prefs, "count", Integer.toString(count));
		}
	}

	private static final String MIME_TYPE = "application/com.tapped.nfc.tag";

	/**
	 * Write to an NFC tag; reacting to an intent generated from foreground
	 * dispatch requesting a write
	 * 
	 * @param intent
	 */
	public void processWriteIntent(Intent intent) {
		if (isWriteReady && NfcAdapter.ACTION_TAG_DISCOVERED.equals(getIntent().getAction())) {

			Tag detectedTag = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);

			String tagWriteMessage = mTextField.getText().toString();
			byte[] payload = new String(tagWriteMessage).getBytes();

			if (detectedTag != null && NfcUtils.writeTag(
					NfcUtils.createMessage(MIME_TYPE, payload), detectedTag)) {
				
				Toast.makeText(this, "Wrote '" + tagWriteMessage + "' to a tag!", 
						Toast.LENGTH_LONG).show();
				setTagWriteReady(false);
			} else {
				Toast.makeText(this, "Write failed. Please try again.", Toast.LENGTH_LONG).show();
			}
		}
	}

	public void processReadIntent(Intent intent) {
		List<NdefMessage> intentMessages = NfcUtils.getMessagesFromIntent(intent);
		List<String> payloadStrings = new ArrayList<String>(intentMessages.size());

		for (NdefMessage message : intentMessages) {
			for (NdefRecord record : message.getRecords()) {
				byte[] payload = record.getPayload();
				String payloadString = new String(payload);

				if (!TextUtils.isEmpty(payloadString))
					payloadStrings.add(payloadString);
			}
		}

		if (!payloadStrings.isEmpty()) {
			String content =  TextUtils.join(",", payloadStrings);
			Toast.makeText(WorkoutActivity.this, "Read from tag: " + content,
					Toast.LENGTH_LONG).show();
			System.out.println(content);

		}
	}
		public void addEntity(View v){
			SharedPreferences prefs = this.getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE);
			String report = prefs.getString(CURRENT_SNAG, "");
	        //InternalData.addItem(prefs, CART_KEY, "Lacoste~$89.5~http://slimages.macys.com/is/image/MCY/products/8/optimized/1242258_fpx.tif|");

			if( !report.isEmpty()){
				InternalData.addItem(prefs, InternalData.CART_KEY, report);
				System.out.println("Just added to internal data");
			}
		
	}
		//Parses SharedPreference and returns an ArrayList of strings
		public static ArrayList<String> parseInternalData(String delim,String key,SharedPreferences prefs){
			
			// get current data
			String data = prefs.getString(key,"");
			                 
			String[] tokens = data.split("["+delim+"]");
			ArrayList<String> parsedData= new ArrayList<String>();
			for(String token:tokens){
				if(!token.isEmpty()){
				parsedData.add(token);
				}
			}
		    return parsedData;
		}
		public static ArrayList<Entity> stringsToEntities(ArrayList<String> entityArray) {
			
			ArrayList<Entity> entities = new ArrayList<Entity>();
			
			for(String entityString : entityArray ){
				String[] entityComponents = entityString.split(",");
				Entity entity = new Entity();
				entity.setId(entityComponents[0]);
				//entity.put("Price",entityComponents[1] );
				//entity.put("ImageUrl",entityComponents[2] );
				entities.add(entity);
				}
			return entities;
		}
		public static ArrayList<Entity> extractUserData( String key,SharedPreferences prefs,String entityDelim,String detailDelim){
			ArrayList<String> parsedString = parseInternalData(entityDelim,key,prefs);
			//String parded = parseInternalData(entityDelim)
			return stringsToEntities(parsedString);
		}
	
}
