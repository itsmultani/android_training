package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MentionsTimelneFragment extends TweetsListFragment {
	private String maxId = null;
	private TwitterClient client;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterApplication.getRestClient();
		maxId = null;
		populateTimeline();
	}
	
	@SuppressLint("NewApi")
	public void populateTimeline() {
		Log.d("DEBUG", "=========================== debug");
		client.getMentionsTimeLine(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				Log.d("DEBUG", "json: " + json.toString());
				ArrayList<Tweet> resps = Tweet.fromJsonArray(json);
				Tweet tweet = resps.get(resps.size() - 1);
				if (tweet != null) {
					maxId = tweet.getUid();
				}
				addAll(resps);
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", "================" + e.toString());
				Log.d("debug", s.toString());
			}
		}, maxId);
	}
}
