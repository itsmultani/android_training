package com.example.gridimagesearch;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.gridimagesearch.adapters.ImageResultsAdapter;
import com.example.gridimagesearch.listeners.EndlessScrollListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {

	private EditText etQuery;
	private GridView gvResults;
	private ArrayList<ImageResult> imageResults;
	private ImageResultsAdapter aImageResults;
	private String query = "";
	private String imgSize = "none";
	private String imgColor = "none";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupViews();
		imageResults = new ArrayList<ImageResult>();
		aImageResults = new ImageResultsAdapter(this, imageResults);
		gvResults.setAdapter(aImageResults);
		Intent intent = getIntent();
		if (intent != null) {
			imgSize = intent.getStringExtra("size");
			imgColor = intent.getStringExtra("color");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	private void setupViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
		gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);
				ImageResult result = imageResults.get(position);
				i.putExtra("url", result.fullUrl);
				startActivity(i);
			}
			
		});
		gvResults.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// TODO Auto-generated method stub
				customLoadMoreDataFromApi(page); 
			}
			
			// Append more data into the adapter
		    public void customLoadMoreDataFromApi(int offset) {
		      // This method probably sends out a network request and appends new data items to your adapter. 
		      // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
		      // Deserialize API response and then construct new objects to append to the adapter
		    	String url = generateUrl(SearchActivity.this.query, offset * 8);
		    	Log.i("INFO", "url=" + url);
		    	System.out.println("url=" + url);
		    	getImages(url);
		    }
			
		});
	}

	public void onImageSearch(View v) {
		String query = etQuery.getText().toString();
		Toast.makeText(this, "Search for: " + query, Toast.LENGTH_LONG).show();
		
		// https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=android&rsz=8
		this.query = query;
		String url = generateUrl(query, 0);
		imageResults.clear();
		getImages(url);

	}
	
	private void getImages(String url) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				Log.d("DEBUG", response.toString());
				JSONArray imageResultsJson = null;
				try {
					imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
					aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	}

	private String generateUrl(String query, int start) {
		String params = "";
		if (imgColor != null && !imgColor.equals("none")) {
			params += "&imgcolor=" + imgColor;
		}
		
		if (imgColor != null && !imgSize.equals("none")) {
			params += "&imgsz=" + imgSize;			
		}
		String url = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="
				+ query + "&rsz=8&start=" + start + params;
		return url;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent i = new Intent(this, SettingActivity.class);
			startActivity(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
