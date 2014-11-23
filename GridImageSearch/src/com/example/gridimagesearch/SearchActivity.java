package com.example.gridimagesearch;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.gridimagesearch.adapters.ImageResultsAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {

	private EditText etQuery;
	private GridView gvResults;
	private ArrayList<ImageResult> imageResults;
	private ImageResultsAdapter aImageResults;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_search);
		setupViews();
		imageResults = new ArrayList<ImageResult>();
		aImageResults = new ImageResultsAdapter(this, imageResults);
		gvResults.setAdapter(aImageResults);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	private void setupViews() {
		//etQuery = (EditText) findViewById(R.id.etQuery);
		//gvResults = (GridView) findViewById(R.id.gvResults);
	}

	public void onImageSearch(View v) {
		String query = etQuery.getText().toString();
		Toast.makeText(this, "Search for: " + query, Toast.LENGTH_LONG).show();
		AsyncHttpClient client = new AsyncHttpClient();
		// https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=android&rsz=8
		String url = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="
				+ query + "&rsz=8";
		client.get(url, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				Log.d("DEBUG", response.toString());
				JSONArray imageResultsJson = null;
				try {
					imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
					imageResults.clear();
					aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		//if (id == R.id.action_settings) {
	//		return true;
	//	}
		return super.onOptionsItemSelected(item);
	}
}