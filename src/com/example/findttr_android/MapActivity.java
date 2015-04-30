package com.example.findttr_android;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



public class MapActivity extends FragmentActivity {
	
	GoogleMap googleMap;
	Button searchBtn;
	List<String> arrayList = new ArrayList<String>();
	Double latitude [] = new Double[100];
	Double longitude [] = new Double[100];
	String[] address = new String[100];
	String[] reference = new String[100];
	String[] phoneNo = new String[100];
	static int k;
	Spinner spinner;
	String type = new String();
	public static String flagValue = new String();
	int len = 0;
	String autocompleteText = new String();
	private static final String LOG_TAG = "ExampleApp";
	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
	private static final String OUT_JSON = "/json";
	private static final String API_KEY = "AIzaSyBakPqSPJuwpAeGxQB-ZR2iGQkZx8566CM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        searchBtn = (Button) findViewById(R.id.button1); 
		searchBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getLatitudeLogitude();
				flagValue = "button";
				k = 1;
			}
		});
		
		spinner =(Spinner) findViewById(R.id.spinner1);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				type = spinner.getSelectedItem().toString();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				type = "taxi";
			}
		});
		
		
		AutoCompleteTextView autoCompView = (AutoCompleteTextView) findViewById(R.id.autocomplete);
	    autoCompView.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.list_item));
	    
	    autoCompView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				autocompleteText = (String) adapterView.getItemAtPosition(position);
				//system.out.println("text is "+ autocompleteText);
		       // Toast.makeText(MapActivity.this, autocompleteText, Toast.LENGTH_SHORT).show();
			}
		});

    }
    
    public void drawingMap(){
    	int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
    			
    			if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available

    	            int requestCode = 10;
    	            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
    	            dialog.show();

    	        }
    			else{
    				//system.out.println("else");
    				SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    				googleMap = fm.getMap();
    				googleMap.setMyLocationEnabled(true); 
    				googleMap.clear();
    				
    				if(latitude.length !=0 && longitude.length !=0){
    					Double lat;
    					Double lng;
    					
    					for(int i=0;i<len;i++){
    					//	for(int j=0;j<len;j++){
    							lat=latitude[i];
    							lng=longitude[i];
    							//system.out.println("lat    "+ lat);;
    							//system.out.println("lng   "+ lng);
    							System.out.println("phoneno  "+ phoneNo[i]);
    							drawMarker(new LatLng(lat,lng),address[i], phoneNo[i]);
    							googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng((lat),(lng))));
    							googleMap.animateCamera(CameraUpdateFactory.zoomTo(9));
    						//}
    					}
    				}
    				
    			}
    		}
    		
    		private void drawMarker(LatLng point,String adrs, String phNO){
    	    	// Adding marker on the Google Map
    	    	googleMap.addMarker(new MarkerOptions().position(point).title(adrs ).snippet(phNO));    		
    	    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

  /*  *//**
     * A placeholder fragment containing a simple view.
     *//*
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_map, container, false);
            return rootView;
        }
    }*/
    
    private class LongRunningGetIO extends AsyncTask<String, Void, String> {
		protected String getASCIIContentFromEntity(HttpEntity entity)
				throws IllegalStateException, IOException {
			InputStream in = entity.getContent();

			StringBuffer out = new StringBuffer();
			int n = 1;
			while (n > 0) {
				byte[] b = new byte[4096];
				n = in.read(b);

				if (n > 0)
					out.append(new String(b, 0, n));
			}

			return out.toString();
		}

		protected void onPostExecute(String results) {
			arrayList.clear();
			try {
				if(flagValue.equalsIgnoreCase("button")){
						System.out.println("entered into post");
						JSONObject root = new JSONObject(results);
						JSONArray result = root.getJSONArray("results");
						////system.out.println("the root lenght   "+ result.length());
						 Toast.makeText(MapActivity.this, "Total "+result.length()+" results found!!!!!", Toast.LENGTH_SHORT).show();
						len = result.length();
						if(len != 0){
							for (int i = 0; i < result.length(); i++) {
								JSONObject data = result.getJSONObject(i);
								// populating reference
								String ref = data.getString("reference");
								reference[i] = ref;
								System.out.println("refer      "+reference[i]);
								flagValue = "phone";
								String[] stringArray = new String[2];
								stringArray[0] = "https://maps.googleapis.com/maps/api/place/details/json?reference="+reference[i]+"&sensor=false&key=AIzaSyBakPqSPJuwpAeGxQB-ZR2iGQkZx8566CM";
								System.out.println("called service");
								new LongRunningGetIO().execute(stringArray);
								
								
								JSONObject geometry =data.getJSONObject("geometry");
								//system.out.println("egemoert  "+ "   "+geometry.length());
								JSONObject location = geometry.getJSONObject("location");
								System.out.println("next dsteoadg  "+location.length());
								//system.out.println("location is "+ location.getString("lat"));
								Double latitude = location.getDouble("lat");
								Double longitude = location.getDouble("lng");
								MapActivity.this.latitude[i] = latitude;
								MapActivity.this.longitude[i] = longitude;
							    //system.out.println( "latitude: " + latitude );
							    //system.out.println( "longitude: " + longitude );
							    String name = data.getString("name");
							    String vicinity = data.getString("vicinity");
							    address[i] = name+vicinity;
							}
							
							/*Intent intent = new Intent(MainActivity.this,MapActivity.class);
							intent.putExtra("lat", latArray);
							intent.putExtra("lang", lngArray);
							startActivity(intent);*/
						} 
						else{
							Toast.makeText(MapActivity.this, "No Details Found!!!!!", Toast.LENGTH_SHORT).show();
						}
				}
				else if(flagValue.equalsIgnoreCase("phone")){
					System.out.println("entered into phone"+ results.length());
					if(results != null){
						System.out.println("result is not null");
					JSONObject root = new JSONObject(results);
					JSONObject result = root.getJSONObject("result");
					String phNo = result.getString("formatted_phone_number");
					System.out.println("phono is   "+ phNo+" k  "+ k);
					phoneNo[k++] = phNo;
				/*	if(result.length() != 0){
						for (int i = 0; i < result.length(); i++) {
							JSONObject data = result.getJSONObject(i);
							String phNo = data.getString("formatted_phone_number");
							phoneNo[i] = phNo;
						}
					 }*/
					System.out.println("len  "+len+"   kis  "+ k);
						if(len == k){
							drawingMap();
						}
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			
		}


		@Override
		protected String doInBackground(String... params) {
			Log.i("pradeep", "length is :   "+params.length+"  "+ params[0]+ "    "+params[1]);
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			HttpGet httpGet = new HttpGet(params[0]);
			String text = new String();
			try {
				HttpResponse response = httpClient.execute(httpGet,
						localContext);

				HttpEntity entity = response.getEntity();

				text = getASCIIContentFromEntity(entity);

			} catch (Exception e) {
				text = e.getMessage();
				return text;
			}

			return text;
		}

	}
	
	
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public void getLatitudeLogitude(){
		if(Geocoder.isPresent()){
	        try {
	            String location = autocompleteText;
	            Geocoder gc = new Geocoder(this);
	            List<Address> addresses= gc.getFromLocationName(location, 5); // get the found Address Objects

	            List<GetLatLong> ll = new ArrayList<GetLatLong>(addresses.size()); // A list to save the coordinates if they are available
	            ll.clear();
	            for(Address a : addresses){
	                if(a.hasLatitude() && a.hasLongitude()){
	                    ll.add(new GetLatLong(a.getLatitude(), a.getLongitude()));
	                }  
	            }  
	            if (ll != null) {
	            	String[] stringArray = new String[2];
	            	stringArray[0] = "https://maps.googleapis.com/maps/api/place/search/json?keyword="+type+"&location="+ll.get(0).getLatitude()+","+ll.get(0).getLongitude()+"&radius=1000&sensor=false&key=AIzaSyBakPqSPJuwpAeGxQB-ZR2iGQkZx8566CM";
	            	//stringArray[1] = ""+ll.get(1).getLongitude();
	            	
	            	new LongRunningGetIO().execute(stringArray);
				}
	        } catch (IOException e) {
	             // handle the exception
	        }
		}
	}

	private ArrayList<String> autocomplete(String input) {
	    ArrayList<String> resultList = null;

	    HttpURLConnection conn = null;
	    StringBuilder jsonResults = new StringBuilder();
	    try {
	        StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
	        sb.append("?sensor=false&key=" + API_KEY);
	        sb.append("&components=country:us");
	        sb.append("&input=" + URLEncoder.encode(input, "utf8"));

	        URL url = new URL(sb.toString());
	        conn = (HttpURLConnection) url.openConnection();
	        InputStreamReader in = new InputStreamReader(conn.getInputStream());

	        // Load the results into a StringBuilder
	        int read;
	        char[] buff = new char[1024];
	        while ((read = in.read(buff)) != -1) {
	            jsonResults.append(buff, 0, read);
	        }
	    } catch (MalformedURLException e) {
	        Log.e(LOG_TAG, "Error processing Places API URL", e);
	        return resultList;
	    } catch (IOException e) {
	        Log.e(LOG_TAG, "Error connecting to Places API", e);
	        return resultList;
	    } finally {
	        if (conn != null) {
	            conn.disconnect();
	        }
	    }

	    try {
	        // Create a JSON object hierarchy from the results
	        JSONObject jsonObj = new JSONObject(jsonResults.toString());
	        JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

	        // Extract the Place descriptions from the results
	        resultList = new ArrayList<String>(predsJsonArray.length());
	        for (int i = 0; i < predsJsonArray.length(); i++) {
	            resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
	        }
	    } catch (JSONException e) {
	        Log.e(LOG_TAG, "Cannot process JSON results", e);
	    }

	    return resultList;
	}
	private class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
	    private ArrayList<String> resultList;

	    public PlacesAutoCompleteAdapter(Context context, int textViewResourceId) {
	        super(context, textViewResourceId);
	    }

	    @Override
	    public int getCount() {
	        return resultList.size();
	    }

	    @Override
	    public String getItem(int index) {
	        return resultList.get(index);
	    }

	    @Override
	    public Filter getFilter() {
	        Filter filter = new Filter() {
	            @Override
	            protected FilterResults performFiltering(CharSequence constraint) {
	                FilterResults filterResults = new FilterResults();
	                if (constraint != null) {
	                    // Retrieve the autocomplete results.
	                    resultList = autocomplete(constraint.toString());

	                    // Assign the data to the FilterResults
	                    filterResults.values = resultList;
	                    filterResults.count = resultList.size();
	                }
	                return filterResults;
	            }

	            @Override
	            protected void publishResults(CharSequence constraint, FilterResults results) {
	                if (results != null && results.count > 0) {
	                    notifyDataSetChanged();
	                }
	                else {
	                    notifyDataSetInvalidated();
	                }
	            }};
	        return filter;
	    }
	}
	
	
}
