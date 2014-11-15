package com.example.imageviewer;

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
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ImageViewerActivity extends Activity {

	public static final String CLIENT_ID = "a7a08e31469d40b09efe38218dc346d4";
	private ArrayList<InstagramPhoto> photos;
	private InstagramPhotoAdapter aPhotos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_viewer);
		fetchPopularPhotos();
	}

	private void fetchPopularPhotos() {
		photos = new ArrayList<InstagramPhoto>();
		aPhotos = new InstagramPhotoAdapter(this, photos);
		ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
		lvPhotos.setAdapter(aPhotos);
		// https://api.instagram.com/v1/media/popular?client_id=
		// {"date" => [x] => "images" => "standard_resolution" => "url"}
		
		// Setup popular url endpoint
		String popularUrl = "https://api.instagram.com/v1/media/popular?client_id=a7a08e31469d40b09efe38218dc346d4";
		// create the network client 
		AsyncHttpClient client = new AsyncHttpClient();
		// trigger the netwrok request
		client.get(popularUrl, new JsonHttpResponseHandler() {
			// define success and failure callbacks
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				Log.i("INFO", response.toString());
				JSONArray photosJson = null;
				photos.clear();
				try {
					photosJson = response.getJSONArray("data");
					for (int i = 0; i < photosJson.length(); i++) {
						JSONObject photoJson = photosJson.getJSONObject(i);
						InstagramPhoto photo = new InstagramPhoto();
						photo.username = photoJson.getJSONObject("user").getString("username");
						if (photoJson.has("caption")) {
							photo.caption = photoJson.getJSONObject("caption").getString("text");
						}
						photo.imageUrl = photoJson.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
						photo.imageHeight = photoJson.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
						photo.likeCounts = photoJson.getJSONObject("likes").getInt("count");
						Log.i("DEBUG", photo.toString());
						photos.add(photo);
					}
					aPhotos.notifyDataSetChanged();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
			}
		});
		// Handle the successful response 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_viewer, menu);
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
}
