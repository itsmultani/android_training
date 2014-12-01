package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.activeandroid.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeTweetActivity extends Activity {
	private TwitterClient client;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_tweet);
		client = TwitterApplication.getRestClient();
	}
	
	public void postTweet(View v) throws InterruptedException {
		EditText edTweetInputBody = (EditText) findViewById(R.id.etTweetInputBody);
		String tweetPostBody = edTweetInputBody.getText().toString();
		client.postTweet(new JsonHttpResponseHandler() {
			public void onSuccess(JSONObject json) {
				Log.d("DEBUG", json.toString());
			};
		} , tweetPostBody);
		Thread.sleep(1000);
		Intent intent = new Intent();
		intent.setClass(this, TimelineActivity.class);
		startActivity(intent);
	}
}
