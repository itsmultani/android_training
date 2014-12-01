package com.codepath.apps.basictwitter;

import java.util.ArrayList;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {
	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;
	private String maxId = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		client = TwitterApplication.getRestClient();
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(this, tweets);
		lvTweets.setAdapter(aTweets);
		maxId = null;
		Log.d("DEBUG", "timeline activity on Create");
		populateTimeline();
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				populateTimeline();
			}
		});
	}

	@SuppressLint("NewApi")
	public void populateTimeline() {
		client.getHomeTimeLine(new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray json) {
				Log.d("DEBUG", "json: " + json.toString());
				ArrayList<Tweet> resps = Tweet.fromJsonArray(json);
				Tweet tweet = resps.get(resps.size() - 1);
				if (tweet != null) {
					maxId = tweet.getUid();
				}
				aTweets.addAll(resps);
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		}, maxId);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.timeline_menu, menu);
		return true;
	}
	
	public void composeTweet(MenuItem menu) {
		Intent intent = new Intent();
		intent.setClass(this, ComposeTweetActivity.class);
		startActivity(intent);
	}
}
